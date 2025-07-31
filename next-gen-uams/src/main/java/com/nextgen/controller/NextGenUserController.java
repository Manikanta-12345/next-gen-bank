package com.nextgen.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nextgen.dto.ApiResponse;
import com.nextgen.dto.UserRegistrationDto;
import com.nextgen.service.UserRegistrationService;
import com.nextgen.views.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/next-gen-user")
public class NextGenUserController {

    private UserRegistrationService userRegistrationService;

    public NextGenUserController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        userRegistrationDto = userRegistrationService.registerUser(userRegistrationDto);
        return new ResponseEntity<>(ApiResponse.success(userRegistrationDto, "User registered successfully"), HttpStatus.CREATED);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.UI.class)
    public ResponseEntity<ApiResponse> getAllUsers() {
        return new ResponseEntity<>(ApiResponse.success(userRegistrationService.getAllUsers(), "Users fetched successfully"), HttpStatus.OK);
    }
}
