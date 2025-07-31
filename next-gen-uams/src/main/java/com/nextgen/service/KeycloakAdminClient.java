package com.nextgen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextgen.dto.UserRegistrationDto;
import com.nextgen.utils.KeycloakUtils;
import com.nextgen.views.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class KeycloakAdminClient {

    @Value("${keycloak.user_url}")
    private String keyCloackUserUrl;

    private KeycloakUtils keycloakUtils;

    KeycloakAdminClient(KeycloakUtils keycloakUtils) {
        this.keycloakUtils = keycloakUtils;
    }

    public ResponseEntity<String> createUserInKeycloak(UserRegistrationDto request) throws JsonProcessingException {

        String accessToken = keycloakUtils.getAccessToken().block();
        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken); // get from Keycloak token endpoint

        ObjectMapper mapper = new ObjectMapper();

        String payload = mapper
                .writerWithView(Views.InternalApi.class)
                .writeValueAsString(request);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(keyCloackUserUrl, entity, String.class);
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    public ResponseEntity<String> getUserByEmailFromKeycloak(String email) throws JsonProcessingException {
        String accessToken = keycloakUtils.getAccessToken().block();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(keyCloackUserUrl) // Ex: https://keycloak.example.com/admin/realms/your-realm/users
                .queryParam("email", email)
                .build()
                .encode()
                .toUri();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // Parse response body as JSON array
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            // Check if the array is not empty and return first user's id
            if (rootNode.isArray() && rootNode.size() > 0) {
                String userId = rootNode.get(0).get("id").asText();
                return ResponseEntity.ok(userId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

}


