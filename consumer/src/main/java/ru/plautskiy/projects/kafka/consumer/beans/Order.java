package ru.plautskiy.projects.kafka.consumer.beans;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Order {
    private String id;
    private String originator;
    private String info;

    public Order(){
    }

    public String getId() {
        return id;
    }

    public String getOriginator() {
        return originator;
    }

    public String getInfo() {
        return info;
    }

    public void setId(String id) { this.id = id;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toJson(){
        return String.format("{ \"id\" : \"%s\" , \"originator\" : \"%s\" , \"info\" : %s }" , this.id , this.originator
                , this.info);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
