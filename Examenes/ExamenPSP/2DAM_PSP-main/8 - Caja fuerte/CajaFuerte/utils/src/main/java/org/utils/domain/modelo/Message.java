package org.utils.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Message {
    int id;
    String messageText;
    int folder;

    public Message() {

    }

    public Message(String messageText, int folder) {
        this.id = 0;
        this.messageText = messageText;
        this.folder = folder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
