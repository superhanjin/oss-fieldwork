package oss;

import oss.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHandler{

    @Autowired
    FieldWorkRepository fieldWorkRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverScheduled_InitiateFieldWork(@Payload Scheduled scheduled){

        if(scheduled.isMe()){
            FieldWork fieldWork = new FieldWork();
            fieldWork.setOrderId(scheduled.getOrderId());
            fieldWork.setCustomerName(scheduled.getCustomerName());
            fieldWork.setAddress(scheduled.getAddress());
            fieldWork.setPhoneNumber(scheduled.getPhoneNumber());
            fieldWork.setInternetCount(scheduled.getInternetCount());
            fieldWork.setTvCount(scheduled.getTvCount());
            fieldWork.setEquipmentId(scheduled.getEquipmentId());
            fieldWork.setVisitDateTime(scheduled.getVisitDateTime());
            fieldWork.setFieldworkState("InitiateFieldWork");

            fieldWorkRepository.save(fieldWork);

            System.out.println("##### listener InitiateFieldWork : " + scheduled.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverScheduleCancelled_CancelFieldWork(@Payload ScheduleCancelled scheduleCancelled){

        if(scheduleCancelled.isMe()){
            List<FieldWork> fieldWorkList = fieldWorkRepository.findByOrderId(scheduleCancelled.getOrderId());
            for(FieldWork fieldWork : fieldWorkList){
                fieldWork.setFieldworkState("CancelFieldWork");
                fieldWorkRepository.save(fieldWork);
            }

            System.out.println("##### listener CancelFieldWork : " + scheduleCancelled.toJson());
        }
    }

}
