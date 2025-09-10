package com.example.bankcards.controller;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.UserCreateDto;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //admins
    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto dto) {
        return ApiResponse.controller(userService.createUser(dto));
    }

    @GetMapping("/admin/getOne/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return ApiResponse.controller(userService.getOne(id));
    }

    @GetMapping("/admin/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.controller(userService.getAll(PageRequest.of(page, size)));
    }

    @PatchMapping("/admin/update/active/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateActive(@PathVariable Long id,
                                          @RequestParam Boolean active) {
        return ApiResponse.controller(userService.updateActive(id, active));
    }

    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ApiResponse.controller(userService.delete(id));
    }

}
