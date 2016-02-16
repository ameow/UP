package com.company;

import java.io.FileWriter;

public class Main {

    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("Output.json", false);
            writer.close();
             FileWriter writer1 = new FileWriter("logfile.txt", false);
            writer1.close();
            Transaction transaction = new Transaction();
            transaction.start();
        } catch (Exception e) {
            System.out.println("Произошла ошибка.");
        }
    }
}