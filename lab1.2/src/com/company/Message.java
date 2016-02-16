package com.company;


import java.util.Comparator;

public class Message {
    private String id;
    private String author;
    private String message;
    private String timestamp;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

class MessageTimeComparator implements Comparator<Message> {

    @Override
    public int compare(Message message1, Message message2) {
        return Integer.parseInt(message1.getTimestamp()) - Integer.parseInt(message2.getTimestamp());
    }

}
