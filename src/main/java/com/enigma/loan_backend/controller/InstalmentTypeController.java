package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.entity.InstalmentType;
import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.service.InstalmentTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instalment-type")
@Tag(name = "Instalment Type")
@RequiredArgsConstructor
public class InstalmentTypeController {

    private final InstalmentTypeService instalmentTypeService;

    @PostMapping
    @Operation(summary = "Create Instalment Type")
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> createInstalmentType(@RequestBody InstalmentType instalmentType) {
        InstalmentType type = instalmentTypeService.create(instalmentType);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "instalment type successfully created",
                        type
                ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Instalment Type By Id")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> getInstalmentTypeById(@PathVariable String id) {
        InstalmentType instalmentType = instalmentTypeService.get(id);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "successfully get instalment type",
                instalmentType
        ));
    }
}
