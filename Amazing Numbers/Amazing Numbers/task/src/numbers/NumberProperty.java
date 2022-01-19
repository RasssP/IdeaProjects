package numbers;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

public final class NumberProperty {

    private final String strNumber;
    private final BigInteger bigNumber;
    private static final BigInteger seven = new BigInteger("7");
    private boolean divisibleBySeven;
    private boolean endWithSeven;
    private boolean isNatural;
    private boolean isEven;
    private boolean isOdd;
    private boolean isBuzz;
    private boolean isDuck;
    private boolean isPalindromic;
    private boolean isGapful;

    public NumberProperty(String bigNumber) {

        this.bigNumber = new BigInteger(bigNumber);
        strNumber = bigNumber;
        setNatural();
        setEven();
        isOdd = !isEven;
        setBuzz();
        setDuck();
        setPalindromic();
        setGapful();
    }

    public NumberProperty(BigInteger bigNumber) {

        this.bigNumber = bigNumber;
        strNumber = bigNumber.toString();
        setNatural();
        setEven();
        isOdd = !isEven;
        setBuzz();
        setDuck();
        setPalindromic();
        setGapful();
    }

    private void setEven() {

        isEven = bigNumber.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO);
    }

    void setNatural() {

        isNatural = (bigNumber.compareTo(BigInteger.ZERO) == 1) &&
                (bigNumber.mod(BigInteger.ONE).compareTo(BigInteger.ZERO) == 0);
    }

    void setBuzz() {

        BigInteger lastDigit = bigNumber.mod(BigInteger.TEN);

        if (lastDigit.compareTo(seven) == 0) {
            endWithSeven = true;
        }
        if (bigNumber.mod(seven).equals(BigInteger.ZERO)) {
            divisibleBySeven = true;
        }
        isBuzz = endWithSeven || divisibleBySeven;
    }

    void setDuck() {

        isDuck = strNumber.matches("\\d*[1-9]+0\\d*");
    }

    void setPalindromic() {

        StringBuilder reverseNumber = new StringBuilder(strNumber).reverse();
        isPalindromic = reverseNumber.toString().equals(strNumber);
    }

    private void setGapful() {

        if (strNumber.length() < 3) {
            isGapful = false;
            return;
        }

        BigInteger firstLast = new BigInteger(strNumber.charAt(0)
                + strNumber.substring(strNumber.length() - 1));
        isGapful = (bigNumber.mod(firstLast).equals(BigInteger.ZERO));
    }

    public String toString() {

        String property;
        if (!isNatural) {
            property = "This bigNumber is not natural!";
            return property;
        }
        property = "Properties of " + formatNumber() +
                "\n        buzz: " + isBuzz +
                "\n        duck: " + isDuck +
                "\n palindromic: " + isPalindromic +
                "\n      gapful: " + isGapful +
                "\n        even: " + isEven +
                "\n         odd: " + isOdd + "\n";
        return property;
    }

    private String formatNumber() {

        return NumberFormat.getIntegerInstance(Locale.US).format(bigNumber);
    }

    // Getter
    public String getStrNumber() {
        return strNumber;
    }

    public boolean isNatural() {
        return isNatural;
    }

    public boolean isEven() {
        return isEven;
    }

    public boolean isOdd() {
        return isOdd;
    }

    public boolean isBuzz() {
        return isBuzz;
    }

    public boolean isDuck() {
        return isDuck;
    }

    public boolean isPalindromic() {
        return isPalindromic;
    }

    public boolean isGapful() {
        return isGapful;
    }
}
