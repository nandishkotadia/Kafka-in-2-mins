package com.kafka.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kafka.system.utility.KafkaProducer;

@Component
public class ProducerExample {

	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Value("${kafka.topic}")
	private String KAFKA_TOPIC;
	
	public void startProduer() {
		System.out.println("Start Producer for topic:"+KAFKA_TOPIC);
		for(int i=1;i<=100;i++){
			String data = "data:"+i;
			kafkaProducer.send(KAFKA_TOPIC, data);
		}
	}

}
