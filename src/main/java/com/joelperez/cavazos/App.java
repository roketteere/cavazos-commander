package com.joelperez.cavazos;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import org.json.simple.JSONArray;

/*
 * General Cavazos Commander App
 * "Command the troops with agility to achieve victory"
 * CIS-055 Data Structures, Joel Perez
 */
public class App {

  private static final String COMMANDS_FILE = "src/main/resources/commands.json";

  // undo and redo each use a stack: last-in-first-out recalls the "last command"
  private static Stack<String> issuedStack = new Stack<String>();
  private static Stack<String> redoStack = new Stack<String>();

  private static String[] commandArray;
  private static Random rand = new Random();

  public static void main(String[] args) {
    // read the 26 military commands from the json file
    JSONArray commandJSONArray = JSONFile.readArray(COMMANDS_FILE);
    commandArray = getCommandArray(commandJSONArray);

    Scanner scan = new Scanner(System.in);
    Character command = '_';

    // loop until the general quits
    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      executeCommand(command);
    }

    scan.close();
  }

  //
  // menu functions
  //
  private static void printMenuLine() {
    System.out.println(
      "----------------------------------------------------------"
    );
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t%s\n", command, desc);
  }

  // prints the menu
  public static void printMenu() {
    printMenuLine();
    System.out.println("General Cavazos Commander App");
    printMenuLine();

    printMenuCommand('i', "Issue a command");
    printMenuCommand('l', "List all of the commands");
    printMenuCommand('u', "Undo the last command that was issued");
    printMenuCommand('r', "Redo the last command that was issued");
    printMenuCommand('q', "Quit");

    printMenuLine();
  }

  // get first character from input
  private static Character menuGetCommand(Scanner scan) {
    Character command = '_';

    String rawInput = scan.nextLine();

    if (rawInput.length() > 0) {
      rawInput = rawInput.toLowerCase();
      command = rawInput.charAt(0);
    }

    return command;
  }

  //
  // commander functions
  //
  private static Boolean executeCommand(Character command) {
    Boolean success = true;

    switch (command) {
      case 'i':
        issueCommand();
        break;
      case 'l':
        print(commandArray);
        break;
      case 'u':
        undoCommand();
        break;
      case 'r':
        redoCommand();
        break;
      case 'q':
        System.out.println("Thank you for using the Cavazos Commander");
        break;
      default:
        System.out.println("ERROR: Unknown command");
        success = false;
    }

    return success;
  }

  // issue a random command from the 26 possible military commands
  private static void issueCommand() {
    int randIndex = rand.nextInt(commandArray.length);
    String issued = commandArray[randIndex];

    issuedStack.push(issued);
    // issuing a new command clears the redo history, like real-world apps
    redoStack.clear();

    System.out.printf("General Cavazos issues: %s\n", issued);
  }

  // undo the last command that was issued
  private static void undoCommand() {
    if (issuedStack.isEmpty()) {
      System.out.println("ERROR: There are no commands to undo");
      return;
    }

    String undone = issuedStack.pop();
    redoStack.push(undone);

    System.out.printf("Undid command: %s\n", undone);
  }

  // redo the last command that was undone
  private static void redoCommand() {
    if (redoStack.isEmpty()) {
      System.out.println("ERROR: There are no commands to redo");
      return;
    }

    String redone = redoStack.pop();
    issuedStack.push(redone);

    System.out.printf("Redid command: %s\n", redone);
  }

  // print command array
  public static void print(String[] commandArray) {
    System.out.printf("Number\tCommand\n");
    System.out.printf("------\t---------------\n");
    for (int i = 0; i < commandArray.length; i++) {
      System.out.printf("%04d\t%s\n", i, commandArray[i]);
    }
  }

  // get array of commands from the json array
  public static String[] getCommandArray(JSONArray commandArray) {
    String[] arr = new String[commandArray.size()];

    for (int i = 0; i < commandArray.size(); i++) {
      String command = commandArray.get(i).toString();
      arr[i] = command;
    }
    return arr;
  }
}
