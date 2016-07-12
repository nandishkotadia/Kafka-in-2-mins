package com.kafka.system;

import java.io.IOException;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ComponentScan(basePackages = {"com.kafka.system"})
public class kafkaApplication {	
	public static void main(String[] args) throws IOException {
		try(
			ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml")){
			
			final ProducerExample producerExample = ac.getBean(ProducerExample.class);
			producerExample.startProduer();
			
			//sleep for 1 seconds and then start consumer
			Thread.sleep(1000);
			final ConsumerExample consumerExample = ac.getBean(ConsumerExample.class);
			consumerExample.startConsumer();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
