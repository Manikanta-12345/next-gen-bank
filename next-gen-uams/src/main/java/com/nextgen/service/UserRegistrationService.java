package com.nextgen.service;

import com.netflix.discovery.converters.Auto;
import com.nextgen.dto.KycStatus;
import com.nextgen.dto.UserRegistrationDto;
import com.nextgen.entity.UserRegistration;
import com.nextgen.exception.UserRegistrationException;
import com.nextgen.repository.UserRegistrationRepository;
import com.nextgen.utils.KeycloakUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserRegistrationService {

    private UserRegistrationRepository userRegistrationRepository;

    private KeycloakAdminClient keycloakAdminClient;

    UserRegistrationService(UserRegistrationRepository userRegistrationRepository, KeycloakAdminClient keycloakAdminClient) {
        this.userRegistrationRepository = userRegistrationRepository;
        this.keycloakAdminClient = keycloakAdminClient;

    }

    @Transactional
    public UserRegistrationDto registerUser(UserRegistrationDto userRegistrationDto) {
        try {
            ResponseEntity<String> response = keycloakAdminClient.createUserInKeycloak(userRegistrationDto);

            if (response.getStatusCode().is2xxSuccessful()) {

                ResponseEntity<String> keycloakResponse = keycloakAdminClient.getUserByEmailFromKeycloak(userRegistrationDto.getEmail());
                if (keycloakResponse.getStatusCode().is2xxSuccessful()) {
                    String keycloakUserId = keycloakResponse.getBody();
                    userRegistrationDto.setId(keycloakUserId);
                    UserRegistration savedUser = saveUser(userRegistrationDto);
                    BeanUtils.copyProperties(savedUser, userRegistrationDto);
                    return userRegistrationDto;
                }

            } else if (response.getStatusCode().value() == 409) {
                log.error("User already exists in Keycloak: {}", userRegistrationDto.getEmail());
                throw new UserRegistrationException("User with email " + userRegistrationDto.getEmail() + " already exists");

            } else if (response.getStatusCode().value() == 400) {
                log.error("Invalid request: {}", response.getBody());
                throw new RuntimeException(response.getBody());

            } else {
                log.error("Unexpected error response from Keycloak: {}", response.getStatusCode());
                throw new RuntimeException("Failed to register user in Keycloak");
            }

        } catch (UserRegistrationException e) {
            throw e; // let specific exceptions bubble up

        } catch (Exception e) {
            log.error("Exception while registering user", e);
            throw new RuntimeException("Unexpected error occurred during user registration", e);
        }
        return null;
    }

    private UserRegistration saveUser(UserRegistrationDto dto) {
        UserRegistration user = UserRegistration.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .emailVerified(dto.isEmailVerified())
                .kycStatus(KycStatus.PENDING)
                .build();

        return userRegistrationRepository.save(user);
    }


    public List<UserRegistrationDto> getAllUsers() {
        List<UserRegistration> users = userRegistrationRepository.findAll();
        return users
                .stream()
                .map(user -> UserRegistrationDto.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .emailVerified(user.isEmailVerified())
                        .kycStatus(user.getKycStatus())
                        .build())
                .toList();
    }
}
