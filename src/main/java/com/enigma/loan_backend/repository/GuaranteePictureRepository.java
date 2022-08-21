package com.enigma.loan_backend.repository;

import com.enigma.loan_backend.entity.GuaranteePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuaranteePictureRepository extends JpaRepository<GuaranteePicture, String> {
}
