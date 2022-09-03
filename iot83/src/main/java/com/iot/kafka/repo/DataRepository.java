package com.iot.kafka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iot.kafka.model.KafkaModel;

@Repository
public interface DataRepository extends JpaRepository<KafkaModel, Integer> {
	

}
