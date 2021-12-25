package contacts;

public class StaticQuestion {

    static void mainCommand() {
        String q = "[menu] Enter action (add, list, search, count, exit):";
        System.out.println(q);
    }

    static void qsearchAction() {
        String q = "[search] Enter action ([number], back, again)";
        System.out.println(q);
    }

    static void qRecordCommand() {
        String q = "[record] Enter action (edit, delete, menu):";
        System.out.println(q);
    }

    static void qlistAction() {
        String q = "[list] Enter action ([number], back):";
        System.out.println(q);
    }

    static void qType() {
        System.out.println("Enter the type (person, organization):");
    }

    static void qName() {
        System.out.println("Enter the name:");
    }

    static void qNumber() {
        System.out.println("Enter the number:");
    }

    static void qSurname() {
        System.out.println("Enter the surname:");
    }

    static void qBDate() {
        System.out.println("Enter the birth date:");
    }

    static void qGender() {
        System.out.println("Enter the gender (M, F):");
    }

    static void qOrgName() {
        System.out.println("Enter the organization name:");
    }

    static void qAddress() {
        System.out.println("Enter the address:");
    }

    static void qFieldPerson() {

        System.out.println("Select a field (name, surname, birth, gender, number):");
    }

    static void qFieldOrg() {
        System.out.println("Select a field (name, address, number):");
    }

    static void qSearchQuery() {
        System.out.println("Enter search query:");
    }
}
