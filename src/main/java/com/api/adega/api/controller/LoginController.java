package com.api.adega.api.controller;

import com.api.adega.api.dto.LoginDto;
import com.api.adega.api.entities.Image;
import com.api.adega.api.security.JwtUtils;
import com.api.adega.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class LoginController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public LoginController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    @Operation(summary = "Realiza login.", description = "Realiza login do usuário no sistema")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content)
    })
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        String userType = userService.getUserTypeByCredentials(loginDto.getEmail(), loginDto.getPassword());

        if (userType != null) {
            String token = jwtUtils.generationToken(loginDto.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("userType", userType);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Credenciais inválidas!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
