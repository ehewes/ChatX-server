package com.chatx.core.handlers;

public class ModelMessage {

    private String name;
    private String content;

    public ModelMessage() {
    }

    public ModelMessage(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return content;
    }

    public void setMessage(String content) {
        this.content = content;
    }
}