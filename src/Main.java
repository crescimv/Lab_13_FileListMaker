import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.io.PrintWriter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> menu = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        boolean confirmQuit = false;
        boolean needsToBeSaved = false;
        String fileName = "";

        //loop for main operation
        do {
            String userChoice = SafeInput.getRegExString(in, "\nChoose an option:\nA-Add item to list\nD-Delete item from list\nV-View list\nO-Open a file from disk\nS-Save the current list file to disk\nC-Clear all the elements from the current list\nQ-Quit program ", "[AaDdVvOoSsCcQq]");

            if (userChoice.equalsIgnoreCase("a")) {
                addListItem(in, menu);
                displayList(in, menu);
                needsToBeSaved = true;
            } else if (userChoice.equalsIgnoreCase("d")) {
                deleteListItem(in, menu);
                displayList(in, menu);
                needsToBeSaved = true;
            } else if (userChoice.equalsIgnoreCase("v")) {
                printList(menu);
            } else if (userChoice.equalsIgnoreCase("o")) {
                openListFile(in, menu, needsToBeSaved);
            } else if (userChoice.equalsIgnoreCase("s")) {
                saveListFile(menu, fileName);
            } else if (userChoice.equalsIgnoreCase("c")) {
                clearList(menu);
                needsToBeSaved = true;
            } else if (userChoice.equalsIgnoreCase("q")) {
                if (SafeInput.getYNConfirm(in, "Are you sure?")) {
                    confirmQuit = true;
                    if (needsToBeSaved) {
                        saveListFile(menu, fileName);
                    } confirmQuit = false;
                } else {
                    in.nextLine();
                }
            }
        } while (!confirmQuit);
    }

    //method for display of list
    private static void displayList(Scanner in, ArrayList menu) {
        System.out.println("List below:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }

    //methods for adding and deleting items from the list, as well as printing (displaying) the list
    public static void addListItem(Scanner in, ArrayList menu) {
        String addItem = SafeInput.getNonZeroLenString(in, "Input name of item to be added to list");
        menu.add(addItem);
    }

    public static void deleteListItem(Scanner in, ArrayList menu) {
        int deleteItem = SafeInput.getRangedInt(in, "Input item number to be deleted from list", 1, menu.size());
        menu.remove(deleteItem-1);
        in.nextLine();
    }

    public static void printList(ArrayList menu) {
        System.out.println("List below:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }
    public static void clearList(ArrayList menu) {
        menu.clear();
    }
    private static String openListFile(Scanner in, ArrayList menu, boolean needsToBeSaved) {
        if (needsToBeSaved) {
            boolean saveOrNot = SafeInput.getYNConfirm(in,"Current list data will be lost. Do you want to save?");
            if (!saveOrNot) {
                return "";
            }
        }
        clearList(menu);
        JFileChooser chooser = new JFileChooser();
        Scanner inFile;
        Path target = new File(System.getProperty("user.dir")).toPath();
        target = target.resolve("src");
        chooser.setCurrentDirectory(target.toFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        chooser.setFileFilter(filter);
        String line;

        try {
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                target = chooser.getSelectedFile().toPath();
                inFile = new Scanner(target);
                System.out.println("Opening... " + target.getFileName());
                while (inFile.hasNextLine()) {
                    line = in.nextLine();
                    menu.add(line);
                }
                inFile.close();
            } else {
                System.out.println("No file selected");
            }
        } catch (IOException e) {
            System.out.println("IO error");
        }
        return target.toFile().toString();
    }
    public static void saveListFile(ArrayList menu, String fileName) {
        PrintWriter outFile;
        Path target = new File(System.getProperty("user.dir")).toPath();
        if (fileName.equals("")) {
            target = target.resolve("src\\list.txt");
        } else {
            target = target.resolve(fileName);
        }
        try {
            outFile = new PrintWriter(target.toString());
            for (int i = 0; i < menu.size(); i++) {
                outFile.println(menu.get(i));
            } outFile.close();
            System.out.printf("File \"%s\" saved\n", target.getFileName());
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }
}