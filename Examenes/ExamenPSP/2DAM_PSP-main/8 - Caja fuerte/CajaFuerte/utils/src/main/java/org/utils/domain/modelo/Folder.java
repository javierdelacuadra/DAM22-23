package org.utils.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Folder {
    int id;
    String name;

    String password;
    boolean readOnly;
    String owner;

    List<Message> messages;

    public Folder(int id, String name, String owner, boolean readOnly) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.readOnly = readOnly;
    }

    public Folder(String name, String password, String owner, boolean readOnly) {
        this.id = 0;
        this.name = name;
        this.password = password;
        this.owner = owner;
        this.readOnly = readOnly;
    }

    public Folder() {
    }
}
