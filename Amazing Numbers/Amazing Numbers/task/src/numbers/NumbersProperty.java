package numbers;

import java.math.BigInteger;
import java.util.ArrayList;

public class NumbersProperty {

    ArrayList<NumberProperty> numberProperties = new ArrayList<>();
    String property;

    public NumbersProperty(String startNumber, int sequence) {

        BigInteger startingBigNumber = new BigInteger(startNumber);
        for (int i = 1; i < sequence; ++i) {
            startingBigNumber = startingBigNumber.add(BigInteger.ONE);
            numberProperties.add(new NumberProperty(startingBigNumber));

        }
    }

    public NumbersProperty(String startNumber, String sequence) {

        BigInteger startingBigNumber = new BigInteger(startNumber);
        numberProperties.add(new NumberProperty(startingBigNumber));
        for (int i = 1; i < Integer.parseInt(sequence); ++i) {
            startingBigNumber = startingBigNumber.add(BigInteger.ONE);
            numberProperties.add(new NumberProperty(startingBigNumber));
        }
    }

    public NumbersProperty(String startNumber, String sequence, String property) {

        this.property = property;
        int counter = 0;
        BigInteger startingBigNumber = new BigInteger(startNumber);
        numberProperties.add(new NumberProperty(startingBigNumber));
        for (int i = 0; i < Integer.MAX_VALUE; ++i){
            startingBigNumber = startingBigNumber.add(BigInteger.ONE);
        }

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (NumberProperty np : numberProperties) {
            sb.append(np.getStrNumber())
                    .append(" is ")
                    .append(property(np))
                    .append("\n");
        }
        return sb.toString();
    }

    private static String property(NumberProperty numberProperty) {

        StringBuilder sb = new StringBuilder();
        if (numberProperty.isBuzz()) sb.append("buzz, ");
        if (numberProperty.isDuck()) sb.append("duck, ");
        if (numberProperty.isPalindromic()) sb.append("palindromic, ");
        if (numberProperty.isGapful()) sb.append("gapful, ");
        if (numberProperty.isEven()) {
            sb.append("even");
        } else {
            sb.append("odd");
        }
        return sb.toString();
    }


}
