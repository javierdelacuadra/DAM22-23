package org.servidor.dao.modelo.datamapper;

import org.servidor.dao.modelo.FolderHibernate;
import org.servidor.dao.modelo.MessageHibernate;
import org.servidor.dao.modelo.UserHibernate;
import org.utils.domain.modelo.Folder;
import org.utils.domain.modelo.Message;
import org.utils.domain.modelo.Rol;
import org.utils.domain.modelo.User;

public  class DataMapper {

    public DataMapper() {
        // solo tiene los mappings, no atributos
    }

    public Folder folderHibToFolder(FolderHibernate folderHibernate) {
        return new Folder(folderHibernate.getId(), folderHibernate.getName(), folderHibernate.getPassword(), folderHibernate.isReadOnly(), folderHibernate.getUser().getName(), folderHibernate.getMessages().stream().map(this::mensajeHibToMensaje).toList());
    }
    public FolderHibernate folderToFolderHibernate(Folder folder) {
        if (folder.getMessages() != null) {
            return new FolderHibernate(folder.getId(), folder.getName(), folder.getPassword(), folder.isReadOnly(), folder.getMessages().stream().map(this::mensajeToMensajeHibernate).toList());
        }
        return new FolderHibernate(folder.getId(), folder.getName(), folder.getPassword(), folder.isReadOnly());

    }

    public UserHibernate userToUserHibernate(User user) {
        return new UserHibernate(user.getName(), user.getPassword(), user.getRole().toString());
    }

    public User userHibToUser(UserHibernate user) {
        return new User(user.getName(), user.getPassword(), Rol.valueOf(user.getRole()));
    }


    public MessageHibernate mensajeToMensajeHibernate(Message mensaje) {
        return new MessageHibernate(mensaje.getId(), mensaje.getMessageText());
    }
    private Message mensajeHibToMensaje(MessageHibernate messageHibernate) {
        return new Message(messageHibernate.getId(), messageHibernate.getMessage(), messageHibernate.getFolder().getId());
    }

    public Folder folderHibToFolderBasic(FolderHibernate folderHibernate) {
        return new Folder(folderHibernate.getId(), folderHibernate.getName(), folderHibernate.getUser().getName(), folderHibernate.isReadOnly());
    }
}

