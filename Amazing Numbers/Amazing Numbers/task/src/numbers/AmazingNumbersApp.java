package numbers;

import java.util.Arrays;
import java.util.Scanner;

public class AmazingNumbersApp implements Application {

    private static AmazingNumbersApp instanceSingleton;
    private static String[] processedInput;
    private static boolean isCloseable;
    private static Scanner scanner = new Scanner(System.in);

    private enum Property
    {
        EVEN,
        ODD,
        BUZZ,
        DUCK,
        PALINDROMIC,
        GAPFUL,
        SPY;

        static String printNames() {

            String temp = "[";
            for (Property p : Property.values()) {
                temp += p.name() + ", ";
            }
            return (temp + "]").replace(", ]", "]");
        }
    }
    private enum InputError
    {
        ERROR_00 ("Not natural number error"),
        ERROR_01 ("First Argument error"),
        ERROR_02 ("Second Argument error"),
        ERROR_03 ("Property Argument error");

        private String errorMessage;

        private InputError(String errorMessage) {

            this.errorMessage = errorMessage;
        }

        String getErrorMessage() {

            return errorMessage;
        }
    }

    private AmazingNumbersApp() {}

    public static AmazingNumbersApp getApp() {

        if (instanceSingleton == null) {
            instanceSingleton = new AmazingNumbersApp();
        }
        return instanceSingleton;
    }

    @Override
    public void startApplicationLoop() {
        
        while (!isCloseable) {
            processedInput = processInput(scanner.nextLine()).split(" ");
            if (processedInput[0].matches("error\\s*")) displayErrorMessage(processedInput);
            else if (processedInput[0].equals("0")) isCloseable = true;
            else {
                System.out.println("Do some action");
            }
        }
    }

    private String processInput(String input) {

        String[] tempInputs = input.split(" ");
        String temp = "error";

        switch (tempInputs.length) {
            case 0 -> {
                return "";
            } case 1 -> {
                if (tempInputs[0].matches("\\d+")) {
                    if (Integer.parseInt(tempInputs[0]) < 0) temp += " " + InputError.ERROR_00.name();
                    else temp = tempInputs[0];
                } else {
                    temp += " " + InputError.ERROR_01;
                }
            } case 2 -> {
                boolean arg1 = tempInputs[0].matches("\\d+");
                boolean arg2 = tempInputs[1].matches("\\d+");
                if (arg1 && arg2) temp = String.format("%s %s", tempInputs[0], tempInputs[1]);
                 else {
                    if (!arg1) temp += " " + InputError.ERROR_01.name();
                    if (!arg2) temp += " " + InputError.ERROR_02.name();
                }

            } case 3 -> {
                boolean arg1 = tempInputs[0].matches("\\d+");
                boolean arg2 = tempInputs[1].matches("\\d+");
                boolean args3 = false;
                for (Property p : Property.values()) {
                    if (tempInputs[2].toUpperCase().equals(p.name())) {
                        args3 = true;
                        break;
                    }
                }
                if (arg1 && arg2 && args3) temp =
                        String.format("%s %s %s", tempInputs[0], tempInputs[1], tempInputs[2]);
                else {
                    if (!arg1) temp += " " + InputError.ERROR_01.name();
                    if (!arg2) temp += " " + InputError.ERROR_02.name();
                    if (!args3) {
                        temp += " " + InputError.ERROR_03.name();
                        temp += " " + tempInputs[2];
                    }
                }
            }default -> {
                return temp += " " + InputError.ERROR_01;
            }
        }
        return temp;
    }

    private void displayErrorMessage(String[] errorCode) {

        for (int i = 0; i < errorCode.length; ++i) {
            if (errorCode[i].equals(InputError.ERROR_00.name())) {
                System.out.println("The first parameter should be a natural number or zero.");
            }
            if (errorCode[i].equals(InputError.ERROR_01.name())) {
                System.out.println("The first parameter should be a natural number or zero.");
            }
            if (errorCode[i].equals(InputError.ERROR_02.name())) {
                System.out.println("The second parameter should be a natural number.");
            }
            if (errorCode[i].equals(InputError.ERROR_03.name())) {
                System.out.printf("The property [%s] is wrong.\n", errorCode[i + 1]);
                System.out.printf("Available properties: %s\n", Property.printNames());
            }
        }
    }
}
