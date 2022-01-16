import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        // List Map of pair
        Map<String, String> mapPair = new HashMap<>();
        mapPair.put("{", "}");
        mapPair.put("[", "]");
        mapPair.put("(", ")");

        String input = scanner.nextLine();
        if (input.length() % 2 == 1) {
            System.out.println(false);
            return;
        }
        // Algorithm -> Pop neighboring pair
        // Put the rest into Deque Data Structure
        // Compare Head and Tail, pop if it's pair
        // if Deque empty, return 'true'
        Deque<String> stringDeque = new ArrayDeque<>();
        String thePair = "";
        for (String s : input.split("")) {
            if (stringDeque.peekLast() != null && mapPair.containsKey(stringDeque.peekLast())) {
                thePair = mapPair.get(stringDeque.peekLast());
            } else {
                thePair = "";
            }
            // System.out.printf("current bracket : %s pair of last bracket : %s\n", s, thePair);
            if (Objects.equals(s, thePair)) {
                stringDeque.pollLast();
            } else {
                stringDeque.offerLast(s);
            }
        }
        while (!stringDeque.isEmpty()) {
            if (Objects.equals(stringDeque.peekFirst(), stringDeque.peekLast())) {
                stringDeque.pollFirst();
                stringDeque.pollLast();
            } else {
                break;
            }
        }
        System.out.println(stringDeque.isEmpty());
    }
}