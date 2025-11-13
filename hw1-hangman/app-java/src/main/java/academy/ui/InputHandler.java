package academy.ui;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public int getValidateIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Ошибка: пожалуйста, введите число\n");
                scanner.next();
            }
        }
    }

    public char getValidLetterInput() {
        while (true) {
            try {
                System.out.print("\nВведите букву: ");
                String input = scanner.next();
                if (!(input.length() == 1 && Character.isLetter(input.charAt(0))
                    && (input.charAt(0) >= 'а' && input.charAt(0) <= 'я'))) {

                    throw new IllegalArgumentException();
                }
                return Character.toLowerCase(input.charAt(0));

            } catch (IllegalArgumentException e) {
                System.out.println("Пожалуйста, введите одну русскую букву");
            }
        }
    }

    public void close() {
        scanner.close();
    }
}
