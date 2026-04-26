package com.example.wastemanagement.repository;

import com.example.wastemanagement.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface ContainerRepository extends JpaRepository<Container, Long> {

    @Query(value = """
            SELECT lcs.fill_percent
            FROM latest_container_state lcs
            WHERE lcs.container_id = :containerId
            """, nativeQuery = true)
    Optional<BigDecimal> findLatestFillPercentByContainerId(@Param("containerId") Long containerId);
}