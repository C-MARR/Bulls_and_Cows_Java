package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int turn = 1;
        int codeSize = setCodeSize();
        if (codeSize < 1 || codeSize > 36) {
            return;
        }
        String possibleSymbols = setPossibleSymbols(codeSize);
        if (possibleSymbols.isEmpty()) {
            return;
        }
        String secretCode = codeGenerator(codeSize, possibleSymbols);
        System.out.println("Okay, let's start a game!");
        while (true) {
            System.out.printf("Turn %d:\n", turn);
            turn++;
            String guess = scanner.nextLine();
            int bulls = 0;
            int cows = 0;
            for (int i = 0; i < codeSize; i++) {
                if (secretCode.charAt(i) == guess.charAt(i)) {
                    bulls++;
                } else {
                    for (int j = 0; j < codeSize; j++) {
                        if (j == i) {
                            continue;
                        }
                        if (secretCode.charAt(i) == guess.charAt(j)) {
                            cows++;
                        }
                    }
                }
            }
            System.out.println("Grade: " + (bulls == 0 && cows == 0 ? "None" : "") + (bulls > 0 ? bulls
                    + " bull(s)" : "") + (bulls > 0 && cows > 0 ? " and " : "") + (cows > 0 ? cows + " cow(s)" : ""));
            if (bulls == codeSize){
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
        }
    }

    private static int setCodeSize() {
        Scanner scanner = new Scanner(System.in);
        int size = 0;
            do {
                System.out.println("Input the length of the secret code:");
                String input = scanner.nextLine();
                try {
                    size = Integer.parseInt(input);
                    if (size < 1 || size > 36) {
                        System.out.printf("Error: can't generate a secret number with a length of %d" +
                                " because there aren't enough unique digits.\n", size);
                        size = 0;
                    }
                } catch (NumberFormatException e) {
                    System.out.printf("Error: \"%s\" isn't a valid number.\n", input);
                    size = 0;
                }
                return size;
            } while (true);
    }

    public static String setPossibleSymbols(int codeSize) {
        Scanner scanner = new Scanner(System.in);
        int possibleSymbols;
        System.out.println("Input the number of possible symbols in the code:");
        String input = scanner.nextLine();
        try {
            possibleSymbols = Integer.parseInt(input);
            if (possibleSymbols < codeSize) {
                System.out.printf("Error: it's not possible to generate a code with a " +
                        "length of %d with %d unique symbols.\n", codeSize, possibleSymbols);
                return "";
            } else if (possibleSymbols > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                return "";
            }
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            return "";
        }
        StringBuilder symbols = new StringBuilder().append("0123456789abcdefghijklmnopqrstuvwxyz");
        StringBuilder digits = new StringBuilder().append("0")
                .append(possibleSymbols > 1 ? "-9" : "");
        if (possibleSymbols > 1) {
            digits.setCharAt(2, possibleSymbols < 10 ? symbols.charAt(possibleSymbols - 1) : '9');
        }
        StringBuilder letters = new StringBuilder()
                .append(possibleSymbols < 10 ? "" : ", a")
                .append(possibleSymbols > 11 ? "-z" : "");
        if (possibleSymbols > 11) {
            letters.setCharAt(4, possibleSymbols < 36 ? symbols.charAt(possibleSymbols - 1) : 'z');
        }
        System.out.printf("The secret is prepared: " + "*".repeat(codeSize) + " (%s%s).\n", digits, letters);
        return symbols.substring(0, possibleSymbols);
    }

    private static String codeGenerator(int codeSize, String symbols) {
        Random rand = new Random();
        StringBuilder secretCode = new StringBuilder();
        while (secretCode.length() < codeSize){
            int newDigit = rand.nextInt(symbols.length());
            if (secretCode.indexOf(String.valueOf(symbols.charAt(newDigit))) != -1) {
                continue;
            }
            secretCode.append(symbols.charAt(newDigit));
        }
        return secretCode.toString();
    }
}

