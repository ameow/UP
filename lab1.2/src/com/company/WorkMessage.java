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
            System.out.println("Произошла ошибка с добалением сообщения.");
        }
    }

    public void showAllMessage() {
        if (listMessage.isEmpty()) {
            System.out.println("История сообщений пуста.");
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
            System.out.println("Удаление выполнено успешно.");
            workFile.writeLogfile("   Удалено одно сообщение.\n");
        } else {
            System.out.println("Сообщения с таким id не существует.");
            workFile.writeLogfile("   Не удалось выполнить удаление сообщения, т.к. сообщения с введенным id не существует.\n");
        }

    }

    public void searchMessageAuthor(String author) {
        List<Message> listAuthor = listMessage.stream().filter(message -> author.equals(message.getAuthor())).collect(Collectors.toList());
        if (listAuthor.isEmpty()) {
            System.out.println("Сообщений с таким автором не найдено.");
            workFile.writeLogfile("   Сообщений с таким автором не найдено.\n");
        } else {
            showMessage(listAuthor);
            workFile.writeLogfile("   Количество найденных сообщений: " + listAuthor.size() + "\n");
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
            System.out.println("Сообщений с такой лексемой не найдено.");
            workFile.writeLogfile("   Сообщений с таким автором не найдено.\n");
        } else {
            showMessage(listLexeme);
            workFile.writeLogfile("   Количество найденных сообщений: " + listLexeme.size() + "\n");
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
            System.out.println("Сообщений, соответствующих данному регулярному выражению, не найдено.");
            workFile.writeLogfile("   Сообщений, соответствующих данному регулярному выражению, не найдено.\n");
        } else {
            showMessage(listPattern);
            workFile.writeLogfile("   Количество найденных сообщений: " + listPattern.size() + "\n");
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
            System.out.println("Сообщений, соответствующих данному временному периоду, не найдено.");
            workFile.writeLogfile("   Сообщений, соответствующих данному временному периоду, не найдено.\n");
        } else {
            showMessage(listTime);
            workFile.writeLogfile("   Количество найденных сообщений:" + listTime.size() + "\n");
        }


    }


}
