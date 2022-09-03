package com.iot.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iot.kafka.model.KafkaModel;
import com.iot.kafka.repo.DataRepository;

@Service
public class Consumer {
	
	private DataRepository dp;
	
	@Autowired
	private Consumer(DataRepository dp) {
		this.dp = dp;
		
	}
	
	@KafkaListener(topics="mytopic", groupId="mygroup") 
	public void consumeFromTopic(String message) {
		System.out.println("Consummed message "+message);
		
		KafkaModel km = new Gson().fromJson(message, KafkaModel.class);
		
		dp.save(km);
		
		System.out.println(km.toString());
	}
	
	

}
