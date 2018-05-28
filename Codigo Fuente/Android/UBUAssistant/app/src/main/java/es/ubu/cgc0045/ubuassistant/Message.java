package es.ubu.cgc0045.ubuassistant;

public class Message {

    private int id;
    private String mes;

    public Message(int id, String mes){
        this.id = id;
        this.mes = mes;
    }

    public int getId(){
        return id;
    }

    public String getMessage(){
        return mes;
    }
}
