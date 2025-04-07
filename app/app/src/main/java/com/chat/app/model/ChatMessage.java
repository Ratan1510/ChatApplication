package com.chat.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.experimental.Accessors;

import java.util.Date;


@Data
@NoArgsConstructor

public class ChatMessage {
    private long id;
    private String sender;
    private String content;
    private Date timestamp; // Add timestamp for message

}
