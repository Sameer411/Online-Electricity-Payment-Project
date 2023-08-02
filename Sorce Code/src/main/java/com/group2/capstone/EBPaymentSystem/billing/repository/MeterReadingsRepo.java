package com.group2.capstone.EBPaymentSystem.billing.repository;

import java.util.List;

import com.group2.capstone.EBPaymentSystem.billing.models.MeterReadings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterReadingsRepo extends JpaRepository<MeterReadings, Long>{

	@Query(value="select * from meter_readings where meter_meter_id = ?1 and month(Date)=?2 and year(Date)=?3",nativeQuery = true)
	List<MeterReadings> findByMeter(long id, int month, int year);
	
}
