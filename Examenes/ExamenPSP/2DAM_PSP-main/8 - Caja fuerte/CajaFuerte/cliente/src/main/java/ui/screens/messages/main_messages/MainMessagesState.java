package ui.screens.messages.main_messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.utils.domain.modelo.Folder;
import org.utils.domain.modelo.Message;

import java.util.List;

@Data
@AllArgsConstructor
public class MainMessagesState {
    private String error;

    private Folder workingFolder;

    private List<Message> messages;

    private Message messageSelected;

    private String success;

}
