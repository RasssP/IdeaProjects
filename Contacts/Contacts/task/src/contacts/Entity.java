package contacts;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Scanner;

public abstract class Entity implements Contact {

    /*
    This abstract class will implement Contact Interface
    For now, it will be Parent Class of Person and Organization
     */

    private final String phoneNumber;
    private final String name;
    LocalDateTime dateCreated;
    LocalDateTime lastEdited;

    // Constructor

    Entity(String name, String phoneNumber) {

        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    // Getter

    public abstract String getFullName();

    public abstract String getAllAttributes();

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    // Method

    public abstract Entity edit();




}

class Person extends Entity {

    private final String surname;
    private final String birthDate;
    private final String gender;

    // Constructor
    private Person(PersonContactBuilder personContactBuilder) {
        super(personContactBuilder.name, personContactBuilder.phoneNumber);
        this.surname = personContactBuilder.surname;
        this.birthDate = personContactBuilder.birthDate;
        this.gender = personContactBuilder.gender;
        this.dateCreated = personContactBuilder.dateCreated;
        this.lastEdited = personContactBuilder.lastEdited;

    }
    // Setter

    // Getter

    @Override
    public String getFullName() {
        return (getName() + " " + surname);
    }
    @Override
    public String getAllAttributes() {
        return (getFullName() + " " +
                getGender() + " " +
                getBirthDate() + " " +
                getPhoneNumber());
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {

        return gender;
    }

    @Override
    public Person edit() {
        Scanner scanner = new Scanner(System.in);
        StaticQuestion.qFieldPerson();
        String field = scanner.nextLine();
        System.out.println("Enter " + field + ":");
        String value = scanner.nextLine();

        Person.PersonContactBuilder personContactBuilder = new Person.PersonContactBuilder();
        return personContactBuilder.edit(this, field, value);
    }

    @Override
    public String toString() {
        return "Name: " + getName() +
                "\nSurname: " + getSurname() +
                "\nBirth date: " + getBirthDate() +
                "\nGender: " + getGender() +
                "\nNumber: " + getPhoneNumber() +
                "\nTime created: " + getDateCreated() +
                "\nTime last edit: " + getLastEdited();
    }

    // Builder class
    // Modifier public static

    public static class PersonContactBuilder {

        private String name;
        private String surname;
        private String birthDate;
        private String gender;
        private String phoneNumber;
        private LocalDateTime dateCreated;
        private LocalDateTime lastEdited;

        public PersonContactBuilder name(String name) {
            this.name = name;
            return this;
        }
        public PersonContactBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }
        public PersonContactBuilder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }
        public PersonContactBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }
        public PersonContactBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Person make(){
            this.dateCreated = LocalDateTime.now();
            this.lastEdited = LocalDateTime.now();
            validator();
            return new Person(this);
        }

        public Person edit(Person person, String field, String value) {
            copyValue(person);
            field = field.toLowerCase();
            switch (field) {
                case "name" -> this.name = value;
                case "surname" -> this.surname = value;
                case "birth" -> this.birthDate = value;
                case "gender" -> this.gender = value;
                case "number" -> this.phoneNumber = value;
            }
            this.lastEdited = LocalDateTime.now();
            validator();
            return new Person(this);
        }

        private void copyValue(Person person) {

            this.name = person.getName();
            this.surname = person.getSurname();
            this.birthDate = person.getBirthDate();
            this.gender = person.getGender();
            this.phoneNumber = person.getPhoneNumber();
            this.dateCreated = person.getDateCreated();
            this.lastEdited = person.getLastEdited();
        }

        private void validator(){

            // birthdate check
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            try {
                birthDate =  dateFormat.parse(birthDate).toString();
            } catch (ParseException e) {
                birthDate = "[no data]";
            }
            // gender check
            if (!("m".equalsIgnoreCase(gender) || "f".equalsIgnoreCase(gender))) {
                gender = "[no data]";
            }
            // phone number check
            if (!(Contact.isPhoneNumber(phoneNumber))) {
                phoneNumber = "[no data]";
            }
        }
    }
}

class Organization extends Entity {

    private final String address;

    private Organization(OrgContactBuilder orgContactBuilder) {

        super(orgContactBuilder.name, orgContactBuilder.phoneNumber);
        this.address = orgContactBuilder.address;
    }

    // Setter

    // Getter


    @Override
    public String getFullName() {
        return getName();
    }

    @Override
    public String getAllAttributes() {
        return (getFullName() + " " +
                getAddress() + " " +
                getPhoneNumber());
    }

    public String getAddress() {
        return address;
    }

    @Override
    public Organization edit() {
        Scanner scanner = new Scanner(System.in);
        StaticQuestion.qFieldOrg();
        String field = scanner.nextLine();
        System.out.println("Enter " + field + ":");
        String value = scanner.nextLine();

        Organization.OrgContactBuilder orgContactBuilder = new Organization.OrgContactBuilder();
        return orgContactBuilder.edit(this, field, value);
    }

    @Override
    public String toString() {
        return "Organization name: " + getName() +
                "\nAddress: " + getAddress() +
                "\nNumber: " + getPhoneNumber() +
                "\nTime created: " + getDateCreated() +
                "\nTime last edit: " + getLastEdited();
    }

    // Builder Inner Class

    public static class OrgContactBuilder {

        // Field
        private String name;
        private String address;
        private String phoneNumber;
        private LocalDateTime dateCreated;
        private LocalDateTime lastEdited;

        // Constructor
        public OrgContactBuilder() {}

        // Method

        public OrgContactBuilder name(String name) {
            this.name = name;
            return this;
        }
        public OrgContactBuilder address(String address) {
            this.address = address;
            return this;
        }
        public OrgContactBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Organization make() {
            this.dateCreated = LocalDateTime.now();
            this.lastEdited = LocalDateTime.now();
            validator();
            return new Organization(this);
        }

        public Organization edit(Organization organization, String field, String value) {
            copyValue(organization);
            field = field.toLowerCase();
            switch (field) {
                case "name" -> this.name = value;
                case "address" -> this.address = value;
                case "number" -> this.phoneNumber = value;
            }
            this.lastEdited = LocalDateTime.now();
            validator();
            return new Organization(this);
        }

        private void copyValue(Organization organization) {

            this.name = organization.getName();
            this.address = organization.getAddress();
            this.phoneNumber = organization.getPhoneNumber();
            this.dateCreated = organization.getDateCreated();
            this.lastEdited = organization.getLastEdited();

        }

        private void validator() {

            // phone number check
            if (!(Contact.isPhoneNumber(phoneNumber))) {
                phoneNumber = "[no data]";
            }
        }

    }
}