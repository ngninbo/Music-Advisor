class EnglishAlphabet {

    public static StringBuilder createEnglishAlphabet() {
        // write your code here

        StringBuilder alphabet = new StringBuilder();

        for (char i = 'A'; i <= 'Z'; i++) {
            alphabet.append(i != 'Z' ? i + " " : i);
        }

        return alphabet;
    }
}