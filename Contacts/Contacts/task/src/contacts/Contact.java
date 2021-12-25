package contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Contact {

    /*
    Class that implements interface Contact
    must have field 'name' and 'phoneNumber'
     */

    static boolean isPhoneNumber(String phoneNumber) {
        String regex = "^\\+?(\\([\\dA-Z]+\\)|\\([\\dA-Z]+\\)[- ][\\dA-Z]{2,}|" +
                "[\\dA-Z]+[- ]\\([\\dA-Z]{2,}\\)|[\\dA-Z]+)([- ][\\dA-Z]{2,})*";

        // Pattern Matcher method
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }
    String getName();
    String getPhoneNumber();


}
