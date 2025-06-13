package com.lievasoft.cefac.controller;

import com.lievasoft.cefac.dto.user.UserResponseDto;
import com.lievasoft.cefac.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class CustomUserController {

    private final CustomUserService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(service.obtainAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/disabled")
    public ResponseEntity<Void> updateDisabledState(@PathVariable("id") UUID uuid) {
        service.toggleDisabledState(uuid);
        return ResponseEntity.noContent().build();
    }
}
