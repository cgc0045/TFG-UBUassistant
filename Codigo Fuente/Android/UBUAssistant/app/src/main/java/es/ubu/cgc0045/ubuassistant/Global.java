package es.ubu.cgc0045.ubuassistant;

import android.app.Application;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Global extends Application {

    private List<String> words;
    private int state;
    private ConstraintLayout cl;

    public Global(){
        words = new ArrayList<>();
        state=-1;
    }


    public void setWords(String words){
        this.words = Arrays.asList(words.split(" "));
    }

    public List<String> getWords(){
        return words;
    }

    public void setState(int state){
        this.state = state;
    }

    public int getState(){
        return state;
    }

    public void setCl(ConstraintLayout cl){ this.cl = cl; }

    public ConstraintLayout getCl() {
        return cl;
    }
}
