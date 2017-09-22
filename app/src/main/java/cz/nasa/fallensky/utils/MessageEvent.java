package cz.nasa.fallensky.utils;

public class MessageEvent {
    public final String message;
    public String body;




    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(String message, String body) {
        this.message = message;
        this.body = body;
    }
}