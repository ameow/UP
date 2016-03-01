package com.company;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WorkMessage {

    public List<Message> listMessage;

    private WorkFile workFile;

    public WorkMessage() {
        listMessage = new ArrayList<>();
        workFile = new WorkFile();
    }

    public void write() {
        WorkFile workFile = new WorkFile();
        workFile.writeFile(listMessage);
    }

    public void add(Message message) {
        try {
            listMessage.add(message);
            Collections.sort(listMessage, MessageTimeComparator.class.newInstance());
        } catch (Exception e) {
            System.out.println("An error occurred with the addition of the message.");
        }
    }

    public void showAllMessage() {
        if (listMessage.isEmpty()) {
            System.out.println("History is empty messages.");
        } else {
            showMessage(listMessage);
        }
    }

    public void showMessage(List<Message> list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (Message message : list) {
            System.out.println(gson.toJson(message));
        }

    }


    public void deleteMessage(String id) {
        int flag = 0;
        for (int i = 0; i < listMessage.size(); i++) {
            if (listMessage.get(i).getId().equals(id)) {
                listMessage.remove(i);
                flag++;
            }
        }
        if (flag != 0) {
            System.out.println("Removal completed successfully.");
            workFile.writeLogfile("   Removed one message.\n");
        } else {
            System.out.println("Messages with this id does not exist.");
            workFile.writeLogfile("   Failed to delete the message, as Messages entered id does not exist.\n");
        }

    }

    public void searchMessageAuthor(String author) {
        List<Message> listAuthor = listMessage.stream().filter(message -> author.equals(message.getAuthor())).collect(Collectors.toList());
        if (listAuthor.isEmpty()) {
            System.out.println("Posts with the author found.");
            workFile.writeLogfile("   Posts with the author found.\n");
        } else {
            showMessage(listAuthor);
            workFile.writeLogfile("   Number of found messages: " + listAuthor.size() + "\n");
        }

    }

    public void searchMessageLexeme(String lexeme) {
        List<Message> listLexeme = new ArrayList<>();
        for (Message message : listMessage) {
            String strings[] = message.getMessage().split("\\W");
            for (String string : strings) {
                if (string.equals(lexeme)) {
                    listLexeme.add(message);
                }
            }
        }
        if (listLexeme.isEmpty()) {
            System.out.println("Messages with the token found.");
            workFile.writeLogfile("   Messages with the author found.\n");
        } else {
            showMessage(listLexeme);
            workFile.writeLogfile("   Number of messages found: " + listLexeme.size() + "\n");
        }

    }

    public void searchMessagePattern(String pattern) {
        List<Message> listPattern = new ArrayList<>();
        Pattern pt = Pattern.compile(pattern);
        for (Message message : listMessage) {
            Matcher m = pt.matcher(message.getMessage());
            if (m.matches()) {
                listPattern.add(message);
            }
        }
        if (listPattern.isEmpty()) {
            System.out.println("Messages that match the regular expression is found.");
            workFile.writeLogfile("   Messages that match the regular expression is found.\n");
        } else {
            showMessage(listPattern);
            workFile.writeLogfile("   Number of messages found: " + listPattern.size() + "\n");
        }

    }

    public void searchMessageTime(long start, long finish) {
        List<Message> listTime = new ArrayList<>();
        for (Message message : listMessage) {
            if (Long.parseLong(message.getTimestamp()) > finish) {
                break;
            }
            if (Long.parseLong(message.getTimestamp()) >= start) {
                listTime.add(message);
            }
        }
        if (listTime.isEmpty()) {
            System.out.println("Messages that match the time period found.");
            workFile.writeLogfile("   Messages that match the time period found.\n");
        } else {
            showMessage(listTime);
            workFile.writeLogfile("   Number of messages found:" + listTime.size() + "\n");
        }


    }


}
