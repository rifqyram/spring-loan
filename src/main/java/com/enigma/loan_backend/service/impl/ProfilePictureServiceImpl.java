package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.constant.Constant;
import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.entity.ProfilePicture;
import com.enigma.loan_backend.model.response.FileResponse;
import com.enigma.loan_backend.repository.ProfilePictureRepository;
import com.enigma.loan_backend.service.CustomerService;
import com.enigma.loan_backend.service.FileService;
import com.enigma.loan_backend.service.ProfilePictureService;
import com.enigma.loan_backend.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProfilePictureServiceImpl implements ProfilePictureService {

    private final ProfilePictureRepository profilePictureRepository;
    private final FileService fileService;
    private final CustomerService customerService;

    @Override
    public ProfilePicture create(String id, MultipartFile multipartFile) {
        boolean validateImage = Utility.validateImageFile(multipartFile.getOriginalFilename());
        if (!validateImage) throw new RuntimeException(String.format("unsupported of content type %s", multipartFile.getContentType()));

        Customer customer = customerService.getCustomerById(id);

        ProfilePicture profilePicture;

        if (customer.getProfilePicture() == null) {
            FileResponse fileResponse = fileService.create(multipartFile, Constant.PATH_FILES_IMAGE);
            profilePicture = new ProfilePicture(
                    fileResponse.getName(),
                    multipartFile.getContentType(),
                    fileResponse.getUrl(),
                    multipartFile.getSize());
        } else {
            profilePicture = update(id, multipartFile);
        }

        ProfilePicture saveProfilePicture = profilePictureRepository.save(profilePicture);

        customer.setProfilePicture(saveProfilePicture);
        customerService.saveCustomer(customer);
        return profilePicture;
    }

    @Override
    public Resource get(String id) {
        Customer customer = customerService.getCustomerById(id);
        ProfilePicture profilePicture = findByIdOrThrowNotFound(customer.getProfilePicture().getId());
        return fileService.get(profilePicture.getUrl(), profilePicture.getName());
    }

    @Override
    public ProfilePicture update(String id, MultipartFile multipartFile) {
        Customer customer = customerService.getCustomerById(id);

        ProfilePicture profilePicture = findByIdOrThrowNotFound(customer.getProfilePicture().getId());

        FileResponse update = fileService.update(
                profilePicture.getName(),
                profilePicture.getUrl(),
                multipartFile);

        ProfilePicture newProfilePicture = new ProfilePicture(
                profilePicture.getId(),
                update.getName(),
                multipartFile.getContentType(),
                update.getUrl(),
                multipartFile.getSize()
        );

        return profilePictureRepository.save(newProfilePicture);
    }

    @Override
    public void delete(String id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer.getProfilePicture() == null) throw new RuntimeException("profile picture not found");
        fileService.delete(customer.getProfilePicture().getUrl(), customer.getProfilePicture().getName());
        customerService.deleteProfilePicture(id);
    }

    private ProfilePicture findByIdOrThrowNotFound(String id) {
        return profilePictureRepository.findById(id).orElseThrow(() -> new RuntimeException("profile picture not found"));
    }
}
