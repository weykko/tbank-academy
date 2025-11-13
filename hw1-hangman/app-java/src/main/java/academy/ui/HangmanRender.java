package academy.ui;

import academy.enums.Difficulty;

public class HangmanRender {
    public HangmanRender() {
    }

    private static final String[] HANGMAN_STATES_EASY = {
        // 0
        """
      +---+
      |   |
          |
          |
          |
          |
    =========""",
        // 1
        """
      +---+
      |   |
      O   |
          |
          |
          |
    =========""",
        // 2
        """
      +---+
      |   |
      O   |
      |   |
          |
          |
    =========""",
        // 3
        """
      +---+
      |   |
      O   |
     /|   |
          |
          |
    =========""",
        // 4
        """
      +---+
      |   |
      O   |
     /|\\  |
          |
          |
    =========""",
        // 5
        """
      +---+
      |   |
      O   |
     /|\\  |
      |   |
          |
    =========""",
        // 6
        """
      +---+
      |   |
      O   |
     /|\\  |
      |   |
     /    |
    =========""",
        // 7
        """
      +---+
      |   |
     !O!  |
     /|\\  |
      |   |
     / \\  |
    ========="""
    };

    private static final String[] HANGMAN_STATES_MEDIUM = {
        // 0
        """
      +---+
      |   |
          |
          |
          |
          |
    =========""",
        // 1
        """
      +---+
      |   |
      O   |
          |
          |
          |
    =========""",
        // 2
        """
      +---+
      |   |
      O   |
      |   |
          |
          |
    =========""",
        // 3
        """
      +---+
      |   |
      O   |
     /|\\  |
          |
          |
    =========""",
        // 4
        """
      +---+
      |   |
      O   |
     /|\\  |
      |   |
          |
    =========""",
        // 5
        """
      +---+
      |   |
      O   |
     /|\\  |
      |   |
     / \\  |
    =========""",
        // 6
        """
      +---+
      |   |
     [O]  |
     /|\\  |
      |   |
     / \\  |
    ========="""
    };

    private static final String[] HANGMAN_STATES_HARD = {
        // 0
        """
      +---+
      |   |
          |
          |
          |
          |
    =========""",
        // 1
        """
      +---+
      |   |
      O   |
          |
          |
          |
    =========""",
        // 2
        """
      +---+
      |   |
      O   |
     /|\\  |
          |
          |
    =========""",
        // 3
        """
      +---+
      |   |
      O   |
     /|\\  |
      |   |
          |
    =========""",
        // 4
        """
      +---+
      |   |
     [O]  |
     /|\\  |
      |   |
     / \\  |
    ========="""
    };

    private void render(int attemptsLeft, int maxAttempts, Difficulty difficulty) {
        String[] currentRenderHangman = getStatesForDifficulty(difficulty);
        int stateIndex = maxAttempts - attemptsLeft;

        if (stateIndex < currentRenderHangman.length) {
            System.out.println(currentRenderHangman[stateIndex]);
        } else {
            System.out.println(currentRenderHangman[currentRenderHangman.length - 1]);
        }
    }

    public void renderGameState(int attemptsLeft,
                                int maxAttempts,
                                String currentWord,
                                String hint,
                                String wrongLetters,
                                Difficulty difficulty
    ) {
        render(attemptsLeft, maxAttempts, difficulty);
        System.out.println("Осталось попыток: " + attemptsLeft + "/" + maxAttempts);
        if (!wrongLetters.isEmpty()) {
            System.out.println("Неправильные буквы: " + wrongLetters);
        }
        System.out.println("Подсказка: " + hint);
        System.out.println("Слово: " + currentWord);
    }


    private String[] getStatesForDifficulty(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> HANGMAN_STATES_EASY;
            case MEDIUM -> HANGMAN_STATES_MEDIUM;
            case HARD -> HANGMAN_STATES_HARD;
        };

    }
}
