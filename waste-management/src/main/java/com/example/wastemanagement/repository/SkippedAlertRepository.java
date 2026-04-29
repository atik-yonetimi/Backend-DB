package com.example.wastemanagement.repository;

import com.example.wastemanagement.entity.SkippedAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkippedAlertRepository extends JpaRepository<SkippedAlert, Long> {
}