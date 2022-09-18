package com.enigma.loan_backend;

import com.enigma.loan_backend.entity.InstalmentType;
import com.enigma.loan_backend.entity.my_enum.EInstalmentType;
import com.enigma.loan_backend.model.request.AuthRequest;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.repository.InstalmentTypeRepository;
import com.enigma.loan_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoData {

    private final InstalmentTypeRepository instalmentTypeRepository;
    private final AuthService authService;

    @Autowired
    public DemoData(InstalmentTypeRepository instalmentTypeRepository, AuthService authService) {
        this.instalmentTypeRepository = instalmentTypeRepository;
        this.authService = authService;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        authService.signUpAdmin(new AuthRequest("admin@mail.com", "password"));
        for (EInstalmentType instalmentType : EInstalmentType.values()) {
            instalmentTypeRepository.findByInstalmentType(instalmentType)
                    .orElseGet(() -> instalmentTypeRepository.save(new InstalmentType(null, instalmentType)));
        }
    }
}
