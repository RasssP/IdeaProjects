class EnglishAlphabet {

    public static StringBuilder createEnglishAlphabet() {

        // write your code here
        // ASCII A -> no. 65
        // ASCII Z -> no. 90
        StringBuilder sb = new StringBuilder();
        char chr = 'A';
        while (chr <= 'Z') {
            sb.append(chr).append(" ");
            ++chr;
        }
        return sb.deleteCharAt(sb.lastIndexOf(" "));
    }
}