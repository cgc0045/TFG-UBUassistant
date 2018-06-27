package es.ubu.cgc0045.ubuassistant;

import android.app.Application;
import android.support.constraint.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Carlos González Calatrava
 */
public class Global extends Application {

    private List<String> words;
    private int state;
    private ConstraintLayout cl;
    private String url;
    private String userID;

    public Global(){
        words = new ArrayList<>();
        state=-1;
    }

    /**
     * Method that set a list with search words.
     * @param words Search words list
     */
    public void setWords(ArrayList<String> words){
        this.words = words;
    }

    /**
     * Method used to get list with search words
     * @return Search words list
     */
    public List<String> getWords(){
        return words;
    }

    /**
     * Method that set the response code of a search
     * @param state Response code
     */
    public void setState(int state){
        this.state = state;
    }

    /**
     * Method used to get search state
     * @return search state
     */
    public int getState(){
        return state;
    }

    /**
     * Method used to set a main layout
     * @param cl Main layout
     */
    public void setCl(ConstraintLayout cl){ this.cl = cl; }

    /**
     * Method used to get the main layout
     * @return main layout
     */
    public ConstraintLayout getCl() { return cl; }

    /**
     * Method used to set the url of opened response
     * @param url website of opened response
     */
    public void setUrl(String url){ this.url = url; }

    /**
     * Method used to get the url of response
     * @return url of response
     */
    public String getUrl() { return url; }

    /**
     * Method used to set a generated user id.
     * @param userID generated user id.
     */
    public void setUserID (String userID){ this.userID = userID; }

    /**
     * Method used to get a user id.
     * @return user id.
     */
    public String getUserID(){ return userID; }
}
