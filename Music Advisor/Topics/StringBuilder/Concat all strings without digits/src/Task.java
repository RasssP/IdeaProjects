import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ConcatenateStringsProblem {

  public static String concatenateStringsWithoutDigits(String[] strings) {

    StringBuilder stringBuilder = new StringBuilder();
    for (String s : strings) {
      stringBuilder.append(s);
    }

    Pattern pattern = Pattern.compile("\\d");
    Matcher matcher = pattern.matcher(stringBuilder);

    return matcher.replaceAll("");
    // return matcher.group();
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String[] strings = scanner.nextLine().split("\\s+");
    String result = concatenateStringsWithoutDigits(strings);
    System.out.println(result);
  }
}