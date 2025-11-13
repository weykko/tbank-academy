package academy.engine;

import academy.dictionary.WordsDictionary;
import academy.dictionary.providers.JsonDictionaryProvider;
import academy.enums.Category;
import academy.enums.Difficulty;
import academy.dictionary.WordEntry;
import academy.ui.TextRender;
import academy.ui.InputHandler;

public class HangmanGame {
    private final InputHandler inputHandler;
    private final TextRender textRender;
    private final WordsDictionary dictionary;

    public HangmanGame() {
        this.inputHandler = new InputHandler();
        this.dictionary = new JsonDictionaryProvider("dictionary.json");
        this.textRender = new TextRender();
    }

    public void interactiveMode() {
        textRender.printHeader();

        GameController gameController = new GameController(inputHandler, dictionary, textRender);

        Category category = gameController.selectCategory();
        Difficulty difficulty = gameController.selectDifficulty();

        int attempts = chooseAttempts(difficulty);
        String secretWord = dictionary.getWord(category, difficulty);

        GameModel gameModel = new GameModel(new WordEntry(secretWord, ""), attempts);

        gameController.startGame(gameModel, category, difficulty);

        inputHandler.close();
    }

    public void nonInteractiveMode(String hiddenWord, String testWord) {
        checkingWords(hiddenWord, testWord);

        String secretWord = hiddenWord.toLowerCase();
        String guessedWord = testWord.toLowerCase();

        StringBuilder resultWord = new StringBuilder();
        boolean success = true;

        for (int i = 0; i < secretWord.length(); i++) {
            char secretChar = secretWord.charAt(i);

            if (guessedWord.indexOf(secretChar) != -1) {
                resultWord.append(secretWord.charAt(i));
            } else {
                resultWord.append("*");
                success = false;
            }
        }

        String outcome = success ? "POS" : "NEG";

        textRender.displayNonInteractiveResult(resultWord.toString(), outcome);
    }

    private int chooseAttempts(Difficulty difficulty) {
        int maxAttempts = 0;
        switch (difficulty) {
            case EASY -> maxAttempts = 8;
            case MEDIUM -> maxAttempts = 6;
            case HARD -> maxAttempts = 4;
        }
        return maxAttempts;
    }

    private void checkingWords(String firstWord, String secondWord) {
        if (firstWord == null || secondWord == null) {
            throw new IllegalArgumentException("Не достаточно слов");
        }

        if (firstWord.length() != secondWord.length()) {
            throw new IllegalArgumentException("Разная длина слов");
        }
    }
}
