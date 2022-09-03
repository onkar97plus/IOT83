package com.iot.kafka.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.hibernate.engine.spi.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;
import com.google.gson.Gson;
import com.iot.kafka.model.KafkaModel;
import com.iot.kafka.service.Producer;


@RestController
@RequestMapping("/kafkaapp")
public class KafkaController {

	@Autowired
	Producer producer;
	
	
	@PostMapping(value="/post")
	public void sendMessage(@RequestParam("file") String msg) {
		
		
		File input = new File(msg);
		
		
		final CsvMapper mapper = new CsvMapper();
	 
		MappingIterator<List<String>> it;
		try {
			it = mapper
					  .readerForListOf(String.class)
					  .with(CsvParser.Feature.WRAP_AS_ARRAY) // !!! IMPORTANT
					  .readValues(input);
			
			
			
			List<List<String>> all = it.readAll();
			
			for(int i=1;i<all.size();i++) {
				

					KafkaModel km = new KafkaModel();
					km.setId(Integer.parseInt(all.get(i).get(0)));
					km.setFname(all.get(i).get(1));
					km.setLname(all.get(i).get(2));
					km.setEmail(all.get(i).get(3));
					  

					String json = new Gson().toJson(km);
					System.out.println(json);
					producer.publishToTopic(json);
					
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
				
		//producer.publishToTopic(msg);
	}
	
}
