package com.ouronet.ouronet.controllers;
import com.ouronet.ouronet.dto.UserDto;
import com.ouronet.ouronet.models.User;
import com.ouronet.ouronet.usecase.UserControllerUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserControllerUseCase userService;

    public UserController(UserControllerUseCase userService) {
        this.userService = userService;
    }

    // UserDto -> User dönüşümü (örnek basit Mapper metodu)
    private UserDto toDto(com.ouronet.ouronet.models.User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId() != null ? user.getId().toString() : null);
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setVipState(user.getVipState());
        return dto;
    }

    private com.ouronet.ouronet.models.User toEntity(UserDto dto) {
        if (dto == null) return null;
        com.ouronet.ouronet.models.User user = new com.ouronet.ouronet.models.User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setVipState(dto.getVipState());
        return user;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto, @RequestParam String password) {
        com.ouronet.ouronet.models.User user = toEntity(userDto);
        user.setPassword(password);
        com.ouronet.ouronet.models.User registered = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(registered));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.authenticate(username, password);
        return ResponseEntity.ok(token);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto, @RequestParam String password) {
        com.ouronet.ouronet.models.User user = toEntity(userDto);
        user.setPassword(password);
        com.ouronet.ouronet.models.User created = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(created));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        com.ouronet.ouronet.models.User user = userService.getUserById(UUID.fromString(id));
        return ResponseEntity.ok(toDto(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> dtos = users.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserDto userDto,
            @RequestParam(required = false) String password
    ) {
        User userDetails = toEntity(userDto);
        if (password != null) userDetails.setPassword(password);
        User updated = userService.updateUser(UUID.fromString(id), userDetails);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}