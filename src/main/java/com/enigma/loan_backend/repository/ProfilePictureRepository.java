package com.enigma.loan_backend.repository;

import com.enigma.loan_backend.entity.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, String> {
}
