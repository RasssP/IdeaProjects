import java.util.*;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        List<String> list = new ArrayList<>();
        String[] strings = input.split("\\s+");
        list = Arrays.stream(strings).collect(Collectors.toList());
        System.out.println(list);
    }
}