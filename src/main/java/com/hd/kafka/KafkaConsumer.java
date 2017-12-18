package com.hd.kafka;

import com.hd.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class KafkaConsumer {

    @Value("${topicIn}")
    private String topicIn;

    @Value("${topicOut}")
    private String topicOut;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private BaseService baseService;

    @KafkaListener(topics = {"Person_Origin"})
    public void consumer(ConsumerRecord data){

        Map<String,String> map = new HashMap<String ,String>();
        map.put("data",data.value().toString());
        JSONObject obj = JSONObject.fromObject(map);

        String value = baseService.reduce(obj);
        log.info(value);
        kafkaTemplate.send(topicOut,value);

    }

}
