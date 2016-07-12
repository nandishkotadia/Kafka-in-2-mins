package com.kafka.utility.system;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

@Component
public class KafkaProducer {
	
	private static Producer<String, String> producer;
	
	private static Gson gson = new Gson();
	
	/*
	 *  Initializes the kafka object using the producer.properties file.
	 */
	public KafkaProducer(){
		super();
		//Properties loadAllProperties = new Properties();
		Properties kafkaProducerProperties = new Properties();
		try (
			FileInputStream fs2 = new FileInputStream("src/main/resources/producer.properties")){
			kafkaProducerProperties.load(fs2);

			ProducerConfig producerConfig = new ProducerConfig(kafkaProducerProperties);
			producer = new Producer<String, String>(producerConfig);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *   Publish the String data to the queue on the given topic .
	 */
	public void send(String topic, String data){
		try {
			KeyedMessage<String, String> message = new KeyedMessage<String, String>(topic, data);
			producer.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String send(String topic, Object data){
		String jsonData = gson.toJson(data);
		try {
			KeyedMessage<String, String> message = new KeyedMessage<String, String>(topic, jsonData);
			producer.send(message);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
}
