package com.api.adega.api.controller;

import com.api.adega.api.entities.Image;
import com.api.adega.api.entities.User;
import com.api.adega.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Lista de usuários.", description = "Gera lista de usuários presentes no sistema")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)
    })
    public List<User> listUsers() {
        return userService.listAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pesquisa usuário.", description = "Pesquisa usuário por ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)
    })
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Criar usuário.", description = "Criar novo usuário")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)
    })
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza usuário.", description = "Atualizar usuário por ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "204", description = "No content",
                    content = @Content)
    })
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userUpdated) {
        User user = userService.updateUser(id, userUpdated);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário.", description = "Excluir usuário por ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Exclusão realizada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Image.class)) }),
            @ApiResponse(responseCode = "204", description = "No content",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
