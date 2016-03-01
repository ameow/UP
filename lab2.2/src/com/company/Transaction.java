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
                        System.out.println("This transaction number is not.");
                        workFile.writeLogfile("The operation is invalid.");
                        break;
                }
            } catch (Exception e) {
                System.out.print("The operation is invalid.");
                workFile.writeLogfile("The operation is invalid.");
                sc.nextLine();
            }

        }
        workMessage.write();
    }

    private void menu() {
        System.out.print("\nSelect the operation.\n" +
                "   enter 1 to enter a message.\n" +
                "   To display the history of the message \"+\n" +
                "                 \"enter 2. \\ n To delete a message from the history enter 3. \\ n To search messages by author, enter 4. \\ n\" +\n" +
                "                 \"To search for a token message enter 5. \\ n To search for a regular expression message enter 6. \\ n\" +\n" +
                "                 \"To start viewing messages for a certain period enter 7. \\ n To enter a message from a file enter 8. \\ n\" +\n" +
                "                 \"Press 0 to complete. ");
    }

    private void enterMessage() {
        workFile.writeLogfile("Writing a message with the keyboard:\n");
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
        workFile.writeLogfile("   Added one message.\n");
    }

    private String checkLengthMessage(String textMessage) {

        while (textMessage.length() > MAX_LENGTH_MESSAGE) {
            System.out.println("\nMessage Length should not exceed 140 characters. Enter again: ");
            workFile.writeLogfile("   Messages longer than 140 characters.\n");
            textMessage = sc.next();
        }
        return textMessage;
    }

    private void showMessage() {
        workFile.writeLogfile("View message history.\n");
        workMessage.showAllMessage();
    }

    private void deleteMessage() {
        System.out.print("Enter id: ");
        workFile.writeLogfile("Delete a message from the history:\n");
        workMessage.deleteMessage(sc.next());
    }

    private void searchMessageAuthor() {
        workFile.writeLogfile("Search by Author Posts:\n");
        System.out.print("Enter the message of the author: ");
        workMessage.searchMessageAuthor(sc.next());
    }

    private void searchMessageLexeme() {
        workFile.writeLogfile("Search token messages: \n");
        System.out.print("Enter your token: ");
        workMessage.searchMessageLexeme(sc.next());
    }

    private void searchMessagePattern() {
        workFile.writeLogfile("Search for messages by regular expression:\n");
        System.out.print("Enter a regular expression: ");
        workMessage.searchMessagePattern(sc.next());
    }

    private void searchMessageTime() {
        workFile.writeLogfile("Find messages by time period: \n");
        boolean flag = true;
        while (flag) {
            try {
                System.out.print("Enter the time period:\n" +
                        "   Start: ");
                long start = sc.nextLong();
                System.out.print("  The end: ");
                long finish = sc.nextLong();
                if (checkTime(start, finish)) {
                    workMessage.searchMessageTime(start, finish);
                    flag = false;
                } else {
                    System.out.println("Enter again: \n");
                }
            } catch (Exception e) {
                System.out.println("\nInvalid input. Time must not contain letters. Enter again: ");
                workFile.writeLogfile("   Invalid input. Time must not contain letters.\n");
                sc.nextLine();
            }
        }
    }

    private boolean checkTime(long start, long finish) {
        if (start > finish) {
            System.out.println("The time period is entered incorrectly.");
            workFile.writeLogfile("   The time period is entered incorrectly.\n");
            return false;
        }
        return true;
    }

    private void enterMessageFile() {
        workFile.writeLogfile("Putting messages from a file:\n");
        System.out.print("Enter the path to the file: ");
        List<Message> list = workFile.readFile(sc.next());
        workFile.writeLogfile("   Number of messages added:" + list.size() + "\n");
        if (list.size() != 0) {
            list.forEach(workMessage::add);
            System.out.println("Messages successfully added.");
        } else {
            System.out.println("Messages have been added.");
        }


    }

}
