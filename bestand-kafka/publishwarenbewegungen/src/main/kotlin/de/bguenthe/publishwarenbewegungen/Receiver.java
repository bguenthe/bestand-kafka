//package de.bguenthe.subscribewarenbewegungenmongo;

//import org.springframework.stereotype.Component;

//@Component
//public class Receiver {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
//
//    private CountDownLatch latch = new CountDownLatch(1);
//
//    public CountDownLatch getLatch() {
//        return latch;
//    }
//
//    @KafkaListener(topics = "test")
//    public void receive(ConsumerRecord<?, ?> consumerRecord) {
//        LOGGER.info("received payload='{}'", consumerRecord.toString());
//        latch.countDown();
//    }
//}

