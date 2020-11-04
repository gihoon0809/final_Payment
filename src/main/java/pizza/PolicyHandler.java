package pizza;

import pizza.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    // LDH 소스추가
    @Autowired
    PaymentRepository PaymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_CancelPayment(@Payload OrderCanceled orderCanceled){

        if(orderCanceled.isMe()){

            // 소스추가 LDH
            Payment payment = new Payment();
            payment.setOrderId(orderCanceled.getId());
            payment.setPaymentStatus("Canceled");

            PaymentRepository.save(payment);

            System.out.println("##### listener CancelPayment : " + orderCanceled.toJson());
        }
    }

}
