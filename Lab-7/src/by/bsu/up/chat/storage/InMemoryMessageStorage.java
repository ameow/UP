package by.bsu.up.chat.storage;

import by.bsu.up.chat.common.models.Message;
import by.bsu.up.chat.logging.Logger;
import by.bsu.up.chat.logging.impl.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageStorage implements MessageStorage {

    private static final String DEFAULT_PERSISTENCE_FILE = "messages.srg";

    private static final Logger logger = Log.create(InMemoryMessageStorage.class);

    private List<Message> messages = new ArrayList<>();

    public InMemoryMessageStorage(DEFAULT_PERSISTENCE_FILE) {
        messages = readFile();
    }

    @Override
    public synchronized List<Message> getPortion(Portion portion) {
        int from = portion.getFromIndex();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        to = Math.max(to, messages.size());
        return messages.subList(from, to);
    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
        writeFile(messages);
    }

    @Override
    public boolean updateMessage(Message message) {
        String id = message.getId();
        for (Message item: messages) {
            if (item.getId().compareTo(id)) {
                item.setText(message.getText);
                writeFile(messages);
                return true;
            }
        }

        throw new UnsupportedOperationException("Update for messages is not supported yet");
        return false;
    }

    @Override
    public synchronized boolean removeMessage(String messageId) {
        for (Message item: messages) {
            if (item.getId().compareTo(messageId)) {
                messages.remove(item);
                writeFile(messages);
                return true;
            }
        }
        throw new UnsupportedOperationException("Removing of messages is not supported yet");
        return false;
    }

    @Override
    public int size() {
        return messages.size();
    }

    public void writeFile(List<Message> listMessage) {
        try {
            FileWriter writer = new FileWriter("Output.json", true);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listMessage, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println("There was an error writing to the file Output.json");
        }
    }


    public List<Message> readFile(String filename) {
        List<Message> listFileMessage = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type collectionType = new TypeToken<List<Message>>() {
            }.getType();
            listFileMessage = gson.fromJson(bufferedReader, collectionType);
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("There was an error reading from file.");
        }
        return listFileMessage;
    }

}
