package com.example.timrocket_backend.repository;

import com.example.timrocket_backend.domain.CoachInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoachInformationRepository extends JpaRepository<CoachInformation, UUID> {

}
