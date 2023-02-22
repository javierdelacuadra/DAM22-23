package org.utils.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Folder {
    private int id;
    private String name;

    private String password;
    private boolean owner;
    private boolean readOnly;

    private String nameUserWithAccess;

    private Integer originalFolderId;
    private List<Message> messages;

    public Folder(int id, String name, String nameUserWithAccess, boolean owner, boolean readOnly, Integer originalFolderId) {
        this.id = id;
        this.name = name;
        this.nameUserWithAccess = nameUserWithAccess;
        this.owner = owner;
        this.readOnly = readOnly;
        this.originalFolderId = originalFolderId;
    }

    //Constructor para crear una carpeta nueva, no tiene id ni originalID
    public Folder(String name, String password, String nameUserWithAccess, boolean owner, boolean readOnly) {
        this.id = 0;
        this.name = name;
        this.password = password;
        this.nameUserWithAccess = nameUserWithAccess;
        this.owner = owner;
        originalFolderId = null;
        this.readOnly = readOnly;
    }

    public Folder() {
    }
}
