package com.example.caorui.mdsimplenotes;

/**
 * Created by caorui on 2015/7/21.
 */
public class Note {
    private int id;
    private String firstTime;
    private String lastTime;
    private String text;

    /*
    public Note(String firstTime, String lastTime, String text) {
        this.firstTime = firstTime;
        this.lastTime = lastTime;
        this.text = text;
    } */

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }
    public String getFirstTime() {
        return firstTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
    public String getLastTime() {
        return lastTime;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
