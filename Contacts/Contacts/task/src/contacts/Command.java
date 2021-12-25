package contacts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Command {

    /*
    Typically all command have chain-reaction
    eg: ListCommand --> ListAction
        SearchCommand --> SearchAction
     */
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void mainCommand() {

        StaticQuestion.mainCommand();
        String input = scanner.nextLine().toLowerCase();

        switch (input) {
            case "add" -> addCommand(entities);
            case "list"  -> {
                if (entities.size() == 0) {
                    System.out.println("No record.\n");
                    mainCommand();
                }
                listCommand(entities);
            }
            case "search" -> {
                if (entities.size() == 0) {
                    System.out.println("No record.\n");
                    mainCommand();
                }
                searchCommand(entities);
            }
            case "count" -> countCommand(entities);
            case "exit" -> {
                 System.exit(0);
            }
            default -> mainCommand();
        }
        mainCommand();
    }

    static void addCommand(ArrayList<Entity> entities) {

        StaticQuestion.qType();
        String type = scanner.nextLine().toLowerCase();
        switch (type) {
            case "person" -> entities.add(createPersonContact());
            case "organization" -> entities.add(createOrgContact());
            case "cancel" -> mainCommand();
            default -> addCommand(entities);
        }
    }

    static void listCommand(ArrayList<Entity> entities) {

        int i = 0;

        for (Entity entity : entities) {
            ++i;
            System.out.println(i + ". " + entity.getFullName());
        }
        listAction(entities);
    }

    static void listAction(ArrayList<Entity> entities) {

        StaticQuestion.qlistAction();
        String input = scanner.nextLine().toLowerCase();
        if ("back".equals(input)) {
            System.out.println();
            mainCommand();
        }
        try {
            int in = Integer.parseInt(input);
            if (in <= entities.size()) {
                viewRecord(entities, in - 1);
                recordAction(entities, in - 1);
            } else if (in > entities.size()) {
                System.out.println("out of index\n");
            }
        } catch (NumberFormatException e) {
            listAction(entities);
        }

    }

    static void viewRecord(ArrayList<Entity> list, int index) {

        // Override method to String in all specific class
        if (list.get(index) instanceof Person p) {
            System.out.println(p);
        } else if (list.get(index) instanceof Organization o) {
            System.out.println(o);
        } else {
            mainCommand();
        }
        System.out.println();
    }

    static void recordAction(ArrayList<Entity> entities, int index) {

        StaticQuestion.qRecordCommand();
        String input = scanner.nextLine().toLowerCase();
        switch (input) {
            case "edit" -> {
                entities.add(index, entities.get(index).edit());
                System.out.println("Saved\n");
            }
            case "delete" -> {
                deleteCommand(entities, index);
                System.out.println("Deleted\n");
            }
            case "menu" -> {
                System.out.println();
                mainCommand();
            }
            default -> recordAction(entities, index);
        }
    }

    static void deleteCommand(ArrayList<Entity> entities, int index) {
        entities.remove(index);
    }

    static void searchCommand(ArrayList<Entity> entities) {

        StaticQuestion.qSearchQuery();
        String input = scanner.nextLine().toLowerCase();
        int searchCounter = 0;
        ArrayList<Integer> indexSearchResult = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getAllAttributes().toLowerCase().contains(input)) {
                indexSearchResult.add(i);
            }
        }
        // Display Result
        System.out.println("Found " + indexSearchResult.size() + " results:");
        if (indexSearchResult.size() == 0) mainCommand();
        for (int i : indexSearchResult) {
            System.out.println(++searchCounter + ". " +
                    entities.get(i).getFullName());
        }
        searchAction(entities, indexSearchResult);
    }

    static void searchAction(ArrayList<Entity> entities,
                      ArrayList<Integer> indexSearchResult) {

        StaticQuestion.qsearchAction();
        String input = scanner.nextLine();
        if ("back".equals(input)) {
            mainCommand();
        } else if ("again".equals(input)) {
            searchCommand(entities);
        } else {
            try {
                int inNumber = Integer.parseInt(input) - 1;
                viewRecord(entities, indexSearchResult.get(inNumber));
                recordAction(entities, indexSearchResult.get(inNumber));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                searchAction(entities, indexSearchResult);
            }
        }
    }

    static void countCommand(ArrayList<Entity> entities) {
        String s = "The Phone Book has " + entities.size() + " records.\n";
        System.out.println(s);
    }

    private static Person createPersonContact() {

        StaticQuestion.qName();
        String name = scanner.nextLine();
        StaticQuestion.qSurname();
        String surname = scanner.nextLine();

        // Catch bad format
        StaticQuestion.qBDate();
        String bDate = scanner.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            bDate = format.parse(bDate).toString();
        } catch (ParseException e) {
            System.out.println("Bad birth date!");
        }

        StaticQuestion.qGender();
        String gender = scanner.nextLine();
        if (!("m".equalsIgnoreCase(gender) || "f".equalsIgnoreCase(gender))) {
            System.out.println("Bad gender!");
        }

        StaticQuestion.qNumber();
        String number = scanner.nextLine();

        Person.PersonContactBuilder personContactBuilder = new Person.PersonContactBuilder();
        Person result = personContactBuilder.name(name)
                .surname(surname)
                .birthDate(bDate)
                .gender(gender)
                .phoneNumber(number)
                .make();

        System.out.println("The record added.\n");
        return result;
    }

    private static Organization createOrgContact() {

        StaticQuestion.qOrgName();
        String name = scanner.nextLine();
        StaticQuestion.qAddress();
        String address = scanner.nextLine();
        StaticQuestion.qNumber();
        String number = scanner.nextLine();

        Organization.OrgContactBuilder orgContactBuilder = new Organization.OrgContactBuilder();
        Organization result = orgContactBuilder.name(name)
                .address(address)
                .phoneNumber(number)
                .make();

        System.out.println("The record added.\n");
        return result;
    }
}



