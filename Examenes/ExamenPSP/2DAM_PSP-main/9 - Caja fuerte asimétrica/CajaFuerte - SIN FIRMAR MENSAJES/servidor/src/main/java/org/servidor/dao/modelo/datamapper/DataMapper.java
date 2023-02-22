package org.servidor.dao.modelo.datamapper;

import org.servidor.dao.modelo.FolderHibernate;
import org.servidor.dao.modelo.MessageHibernate;
import org.servidor.dao.modelo.UserHibernate;
import org.utils.domain.modelo.Folder;
import org.utils.domain.modelo.Message;
import org.utils.domain.modelo.Rol;
import org.utils.domain.modelo.User;

import java.util.List;

public  class DataMapper {

    public DataMapper() {
        // solo tiene los mappings, no atributos
    }

    public Folder folderHibToFolder(FolderHibernate folderHibernate) {
        return new Folder(folderHibernate.getId(), folderHibernate.getName(), folderHibernate.getPassword(), folderHibernate.isOwner(), folderHibernate.isReadOnly(), folderHibernate.getUser().getName(), folderHibernate.getOriginalFolderId(), folderHibernate.getMessages().stream().map(this::mensajeHibToMensaje).toList());
    }
    public FolderHibernate folderToFolderHibernate(Folder folder) {
        if (folder.getMessages() != null) {
            return new FolderHibernate(folder.getId(), folder.getName(), folder.getPassword(), folder.isReadOnly(), folder.isOwner(), folder.getOriginalFolderId(), folder.getMessages().stream().map(this::mensajeToMensajeHibernate).toList());
        }
        return new FolderHibernate(folder.getId(), folder.getName(), folder.getPassword(), folder.isReadOnly(), folder.isOwner(), folder.getOriginalFolderId());

    }

    public UserHibernate userToUserHibernate(User user) {
        return new UserHibernate(user.getName(), user.getCertificate(), user.getRole().toString());
    }

    public User userHibToUser(UserHibernate user) {
        return User.builder().name(user.getName()).certificate(user.getCertificate()).role(Rol.valueOf(user.getRole())).build();
    }


    public MessageHibernate mensajeToMensajeHibernate(Message mensaje) {
        return new MessageHibernate(mensaje.getId(), mensaje.getMessageText());
    }
    private Message mensajeHibToMensaje(MessageHibernate messageHibernate) {
        return new Message(messageHibernate.getId(), messageHibernate.getMessage(), messageHibernate.getFolder().getId());
    }

    public Folder folderHibToFolderBasic(FolderHibernate folderHibernate) {
        return new Folder(folderHibernate.getId(), folderHibernate.getName(), folderHibernate.getUser().getName(), folderHibernate.isOwner(), folderHibernate.isReadOnly(), folderHibernate.getOriginalFolderId());
    }

    public List<Message> mensajesHibernateToMensajes(List<MessageHibernate> messages) {
        return messages.stream().map(this::mensajeHibToMensaje).toList();
    }
}

