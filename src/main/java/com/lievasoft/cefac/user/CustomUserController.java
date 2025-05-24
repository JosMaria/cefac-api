package com.lievasoft.cefac.user;

import com.lievasoft.cefac.user.dto.UserResponseDto;
import com.lievasoft.cefac.user.dto.UsernameUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @PreAuthorize("hasAuthority('me:write')")
    @PatchMapping("/{id}/username")
    public ResponseEntity<Void> updateUsername(@PathVariable("id") UUID id, @RequestBody UsernameUpdateRequest payload, Principal principal) {
        service.modifyUsername(payload.username());
        return ResponseEntity.noContent().build();
    }
}
