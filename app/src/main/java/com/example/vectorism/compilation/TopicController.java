package com.example.vectorism.compilation;

public class TopicController {
    private static Boolean active_topic = false;

    public static Boolean getActive_topic() {
        return active_topic;
    }

    public static void setActive_topic(Boolean active_topic) {
        TopicController.active_topic = active_topic;
    }
}
