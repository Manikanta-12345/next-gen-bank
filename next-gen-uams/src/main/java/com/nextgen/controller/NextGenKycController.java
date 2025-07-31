package com.nextgen.controller;

import com.nextgen.dto.ApiResponse;
import com.nextgen.dto.NextGenKycEvent;
import com.nextgen.service.NextGenKycService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/next-gen-kyc")
@Slf4j
public class NextGenKycController {

    private NextGenKycService nextGenKycService;

    public NextGenKycController(NextGenKycService nextGenKycService) {
        this.nextGenKycService = nextGenKycService;
    }
    @PostMapping(value = "/upload-kyc",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> uploadKyc(@RequestBody NextGenKycEvent nextGenKycEvent) {
        String response = nextGenKycService.sendNextGenKycEvent(nextGenKycEvent);
        return new ResponseEntity<>(ApiResponse.success(null, response), HttpStatus.OK);
    }
}
