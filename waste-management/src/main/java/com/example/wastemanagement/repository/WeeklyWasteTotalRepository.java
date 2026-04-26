package com.example.wastemanagement.repository;

import com.example.wastemanagement.entity.WeeklyWasteTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface WeeklyWasteTotalRepository extends JpaRepository<WeeklyWasteTotal, Long> {

    List<WeeklyWasteTotal> findAllByOrderByWasteTypeAsc();
    
    @org.springframework.data.jpa.repository.Query(value = "SELECT reset_weekly_waste_totals()", nativeQuery = true)
    void resetTotals(); 

}