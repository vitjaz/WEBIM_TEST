package com.example.webim_test;

public class Note {

    private String mTitle;
    private String mText;

    public static Note[] notes;


    public Note(String title, String text){
        mTitle = title;
        mText = text;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmText(){
        return mText;
    }

}
