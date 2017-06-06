package com.he.message;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

/**
 * Created by he on 2017/6/6.
 */
public class Listener {

    private Log log = LogFactory.getLog(getClass());


    @KafkaListener(topics={"test1"})
    public void listen(ConsumerRecord<?, ?> record) {
        Gson gson = new Gson();
        log.error("监听器" + gson.toJson(record));
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("listen1 " + message);
        }
    }
}
