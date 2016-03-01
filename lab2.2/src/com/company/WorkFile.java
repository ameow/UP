package com.company;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WorkFile {


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

    public void writeLogfile(String string) {
        try {
            FileWriter writer = new FileWriter("logfile.txt", true);
            writer.write(string);
            writer.close();
        } catch (Exception e) {
            System.out.println("There was an error opening the file logfile.txt");
        }
    }

}
