package com.kafka.system.utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

@Component
public class KafkaConsumer implements AutoCloseable{
	
	/*
	 * Returns the kafka consumer stream list 
	 * 
	 * Params:  String topic
	 */
	public List<KafkaStream<byte[], byte[]>> getKafkaConsumerStreamList(String topic) {
		
		try(FileInputStream fs = new FileInputStream("src/main/resources/consumer.properties");){
			Properties kafkaConsumerProperties = new Properties();
			kafkaConsumerProperties.load(fs);
			ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(kafkaConsumerProperties));
			Map<String, Integer> topicMap = new HashMap<String, Integer>();
			topicMap.put(topic, new Integer(1));
			Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreamsMap = consumerConnector.createMessageStreams(topicMap);
			List<KafkaStream<byte[], byte[]>> streamList = consumerStreamsMap.get(topic);
			return streamList;		
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return null;
	}

	@Override
	public void close() throws Exception {
		
	}
}
