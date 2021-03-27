package com.example.startandroid_course_rxjava2;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    private int id;

    @SerializedName("time")
    private long time;

    @SerializedName("text")
    private String text;

    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }
}
