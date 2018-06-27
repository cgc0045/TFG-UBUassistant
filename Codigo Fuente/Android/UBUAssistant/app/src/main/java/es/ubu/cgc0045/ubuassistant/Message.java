package es.ubu.cgc0045.ubuassistant;

/**
 * @author Carlos
 */
public class Message {

    private int id;
    private String mes;

    Message(int id, String mes){
        this.id = id;
        this.mes = mes;
    }

    /**
     * Method used to get the message id
     * @return message id
     */
    public int getId(){
        return id;
    }

    /**
     * Method used to get the content of the message
     * @return message content
     */
    public String getMessage(){
        return mes;
    }
}
