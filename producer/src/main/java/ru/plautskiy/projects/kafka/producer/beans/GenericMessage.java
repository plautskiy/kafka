package ru.plautskiy.projects.kafka.producer.beans;

public class GenericMessage {
    private String id;
    private String originator;
    private String info;

    public GenericMessage(String id, String originator, String info) {
        this.id = id;
        this.originator = originator;
        this.info = info;
    }

    public GenericMessage(){

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

    public void setId(String id) { this.id = id; }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString(){
        return String.format("{ \"id\" : \"%s\" , \"originator\" : \"%s\" , \"info\" : %s }" , this.id , this.originator
                , this.info);
    }

}
