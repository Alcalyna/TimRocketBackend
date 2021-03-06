package com.example.timrocket_backend.domain.topic;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "TOPIC")
public class Topic {

    @Id
    @GeneratedValue
    @Column(name = "TOPIC_ID")
    private UUID topicId;

    @Column(name = "NAME")
    private String name;

    public UUID getTopicId() {
        return topicId;
    }

    public String getName() {
        return name;
    }
}
