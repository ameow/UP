package com.company;


import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;

public class Transaction {

    private WorkMessage workMessage;
    private WorkFile workFile;
    private Scanner sc = null;
    private static final int MAX_LENGTH_MESSAGE = 140;

    public Transaction() {
        workMessage = new WorkMessage();
        workFile = new WorkFile();
        sc = new Scanner(System.in).useDelimiter("\\n");
    }

    public void start() {
        int transactionNumber;
        boolean flag = true;
        while (flag) {
            menu();
            try {

                transactionNumber = sc.nextInt();
                switch (transactionNumber) {
                    case 0:
                        flag = false;
                        break;
                    case 1:
                        enterMessage();
                        break;
                    case 2:
                        showMessage();
                        break;
                    case 3:
                        deleteMessage();
                        break;
                    case 4:
                        searchMessageAuthor();
                        break;
                    case 5:
                        searchMessageLexeme();
                        break;
                    case 6:
                        searchMessagePattern();
                        break;
                    case 7:
                        searchMessageTime();
                        break;
                    case 8:
                        enterMessageFile();
                        break;
                    default:
                        System.out.println("Такого номера операции нет.");
                        workFile.writeLogfile("Операция введена некорректно.");
                        break;
                }
            } catch (Exception e) {
                System.out.print("Операция введена некорретно.");
                workFile.writeLogfile("Операция введена некорректно.");
                sc.nextLine();
            }

        }
        workMessage.write();
    }

    private void menu() {
        System.out.print("\nВыберите операцию.\n  Для ввода сообщения введите 1.\n  Для показа истории сообщения " +
                "введите 2.\n  Для удаления сообщения из истории введите 3.\n  Для поиска сообщения по автору введите 4.\n" +
                "  Для поиска сообщений по лексеме введите 5.\n  Для поиска сообщений по регулярному выражению введите 6.\n" +
                "  Для просмотра сообщений за определенный период введите 7.\n  Для ввода сообщения из файла введите 8.\n" +
                "  Для завершения нажмите 0.\n ");
    }

    private void enterMessage() {
        workFile.writeLogfile("Ввод сообщения с клавиатуры:\n");
        Message message = new Message();
        System.out.print("Id: ");
        SecureRandom secureRandom = new SecureRandom();
        long id = secureRandom.nextLong();
        System.out.println(id);
        message.setId(Long.toString(id));
        System.out.print("Enter author: ");
        message.setAuthor(sc.next());
        System.out.print("Enter message: ");
        message.setMessage(checkLengthMessage(sc.next()));
        System.out.print("Timestamp: " + System.currentTimeMillis());
        message.setTimestamp(Long.toString(System.currentTimeMillis()));
        workMessage.add(message);
        workFile.writeLogfile("   Добавлено одно сообщение.\n");
    }

    private String checkLengthMessage(String textMessage) {

        while (textMessage.length() > MAX_LENGTH_MESSAGE) {
            System.out.println("\nДлина сообщений не должна превышать 140 символов. Введите снова: ");
            workFile.writeLogfile("   Длина сообщения превышает 140 символов.\n");
            textMessage = sc.next();
        }
        return textMessage;
    }

    private void showMessage() {
        workFile.writeLogfile("Просмотр истории сообщений.\n");
        workMessage.showAllMessage();
    }

    private void deleteMessage() {
        System.out.print("Введите id: ");
        workFile.writeLogfile("Удаление сообщения из истории:\n");
        workMessage.deleteMessage(sc.next());
    }

    private void searchMessageAuthor() {
        workFile.writeLogfile("Поиск сообщений по автору:\n");
        System.out.print("Введите автора сообщения: ");
        workMessage.searchMessageAuthor(sc.next());
    }

    private void searchMessageLexeme() {
        workFile.writeLogfile("Поиск сообщений по лексеме:\n");
        System.out.print("Введите лексему: ");
        workMessage.searchMessageLexeme(sc.next());
    }

    private void searchMessagePattern() {
        workFile.writeLogfile("Поиск сообщений по регулярному выражению:\n");
        System.out.print("Введите регулярное выражение: ");
        workMessage.searchMessagePattern(sc.next());
    }

    private void searchMessageTime() {
        workFile.writeLogfile("Поиск сообщений по временному периоду:\n");
        boolean flag = true;
        while (flag) {
            try {
                System.out.print("Введите временной период:\n  Начало: ");
                long start = sc.nextLong();
                System.out.print("  Конец: ");
                long finish = sc.nextLong();
                if (checkTime(start, finish)) {
                    workMessage.searchMessageTime(start, finish);
                    flag = false;
                } else {
                    System.out.println("Введите снова:\n");
                }
            } catch (Exception e) {
                System.out.println("\nНекорректный ввод. Время не должно содержать буквы. Введите снова:");
                workFile.writeLogfile("   Heкорректный ввод. Время не должно содержать буквы.\n");
                sc.nextLine();
            }
        }
    }

    private boolean checkTime(long start, long finish) {
        if (start > finish) {
            System.out.println("Временной период введен неверно.");
            workFile.writeLogfile("   Временной период введен неверно.\n");
            return false;
        }
        return true;
    }

    private void enterMessageFile() {
        workFile.writeLogfile("Ввод сообщений из файла:\n");
        System.out.print("Укажите путь к файлу: ");
        List<Message> list = workFile.readFile(sc.next());
        workFile.writeLogfile("   Количество добавленных сообщений:" + list.size() + "\n");
        if (list.size() != 0) {
            list.forEach(workMessage::add);
            System.out.println("Сообщения успешно добавлены.");
        } else {
            System.out.println("Сообщения не добавлены.");
        }


    }

}
