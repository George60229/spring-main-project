package com.esm.security.auth;

import com.esm.security.exception.BadRequestException;
import com.esm.security.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        if (request.getEmail() == null || request.getPassword() == null) {
            throw new BadRequestException("User and email should be not null", ErrorCode.BAD_REQUEST_ERROR);
        }
        return ResponseEntity.ok(service.authenticate(request));
    }


}
