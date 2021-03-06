package oss;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name="FieldWork_table")
public class FieldWork {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String customerName;
    private String address;
    private String phoneNumber;
    private Integer internetCount;
    private Integer tvCount;
    private String equipmentId;
    private String visitDateTime;

    private String fieldworkState;

    @PostPersist
    public void onPostPersist(){
        if(this.getFieldworkState().equals("InitiateFieldWork")){
            System.out.println("#################### InitiateFieldWork Command has been Processed ####################");
        }else {
            System.out.println("#################### Unknown Command is Called ####################");
        }
    }

    @PostUpdate
    public void onPostUpdate(){
        if(this.getFieldworkState().equals("CompleteFieldWork")) {
            FieldWorkCompleted fieldWorkCompleted = new FieldWorkCompleted();
            BeanUtils.copyProperties(this, fieldWorkCompleted);
            fieldWorkCompleted.publishAfterCommit();
            System.out.println("#################### FieldWorkCompleted Event has been published ####################");
        }else if(this.getFieldworkState().equals("FailFieldWork")){
            FieldWorkFailed fieldWorkFailed = new FieldWorkFailed();
            BeanUtils.copyProperties(this, fieldWorkFailed);
            fieldWorkFailed.publishAfterCommit();
            System.out.println("#################### FieldWorkFailed Event has been published ####################");
        }else if(this.getFieldworkState().equals("CancelFieldWork")) {
            System.out.println("#################### CancelFieldWork Command has been Processed ####################");
        }
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Integer getInternetCount() {
        return internetCount;
    }
    public void setInternetCount(Integer internetCount) {
        this.internetCount = internetCount;
    }
    public Integer getTvCount() {
        return tvCount;
    }
    public void setTvCount(Integer tvCount) {
        this.tvCount = tvCount;
    }
    public String getEquipmentId() {
        return equipmentId;
    }
    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }
    public String getVisitDateTime() {
        return visitDateTime;
    }
    public void setVisitDateTime(String visitDateTime) {
        this.visitDateTime = visitDateTime;
    }
    public String getFieldworkState() {
        return fieldworkState;
    }
    public void setFieldworkState(String fieldworkState) {
        this.fieldworkState = fieldworkState;
    }




}
