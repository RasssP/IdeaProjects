// write your answer here 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Task {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        // Reversing order
        List<String> reverseList = new ArrayList<>();
        List<String> list = Arrays.stream(input.split("\\s+"))
                .collect(Collectors.toList());
        for (int i = 0; i < list.size(); i += 2) {
            list.set(i, "");
        }
        // Remove Blank
        list.removeIf(n -> n.equals(""));
        for (int i = list.size() - 1; i >= 0; --i) {
            reverseList.add(list.get(i));
        }
        reverseList.forEach(e -> System.out.print(e + " "));

    }
}
