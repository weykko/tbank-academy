package academy.engine;

import academy.enums.Category;
import academy.enums.Difficulty;
import academy.dictionary.WordsDictionary;
import academy.enums.GameState;
import academy.ui.HangmanRender;
import academy.ui.TextRender;
import academy.ui.InputHandler;

public class GameController {
    private final InputHandler inputHandler;
    private final TextRender textRender;
    private final WordsDictionary dictionary;
    private final HangmanRender hangmanRender;

    public GameController(InputHandler inputHandler, WordsDictionary dictionary, TextRender textRender) {
        this.inputHandler = inputHandler;
        this.dictionary = dictionary;
        this.textRender = textRender;
        this.hangmanRender = new HangmanRender();
    }

    public void startGame(GameModel gameModel, Category category, Difficulty difficulty) {
        textRender.printGameInfo(category, difficulty, gameModel.getMaxAttempts());

        while (!gameModel.isGameOver()) {
            renderGameState(gameModel, difficulty);
            char letter = inputHandler.getValidLetterInput();
            boolean isCorrect = guessLetter(gameModel, letter);
            textRender.printGuessResult(letter, isCorrect);

            if (gameModel.checkAttemptsLeft() && !gameModel.isHintOffered()) {
                gameModel.setHintOffered(true);
                String hint = dictionary.getHint(category, difficulty, gameModel.getWordEntry().word());
                gameModel.setHint(getHintSelection(hint));
            }
        }

        renderGameState(gameModel, difficulty);
        textRender.showGameResult(gameModel);
    }

    public Category selectCategory() {
        while (true) {
            try {
                textRender.displayCategoryMenu();
                int input = inputHandler.getValidateIntInput("Выбор: ");

                Category selectedCategory = dictionary.getWordCategory(input);
                textRender.displaySelectedCategory(selectedCategory);
                return selectedCategory;
            } catch (IllegalArgumentException e) {
                textRender.displayInvalidCategorySelection();
            }
        }
    }

    public Difficulty selectDifficulty() {
        while (true) {
            try {
                textRender.displayDifficultyMenu();
                int input = inputHandler.getValidateIntInput("Выбор: ");
                Difficulty difficulty = dictionary.getWordDifficulty(input);
                textRender.displaySelectedDifficulty(difficulty);
                return difficulty;
            } catch (IllegalArgumentException e) {
                textRender.displayInvalidDifficultySelection();
            }
        }
    }

    public boolean guessLetter(GameModel gameModel, char letter) {
        if (gameModel.getGameState() != GameState.IN_PROGRESS) {
            return false;
        }

        char lowerLetter = Character.toLowerCase(letter);

        if (gameModel.getGuessedLetters().contains(lowerLetter) ||
            gameModel.getWrongLetters().contains(lowerLetter)) {
            return false;
        }

        boolean found = false;
        String word = gameModel.getWord();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == lowerLetter) {
                gameModel.getCurrentStateWordArray()[i] = word.charAt(i);
                found = true;
            }
        }

        if (found) {
            gameModel.getGuessedLetters().add(lowerLetter);
            if (gameModel.isWordGuessed()) {
                gameModel.setGameState(GameState.WIN);
            }
            return true;
        } else {
            gameModel.getWrongLetters().add(lowerLetter);
            gameModel.setAttemptsLeft(gameModel.getAttemptsLeft() - 1);
            if (gameModel.getAttemptsLeft() <= 0) {
                gameModel.setGameState(GameState.LOST);
            }
            return false;
        }
    }

    public String getHintSelection(String hint) {
        while (true) {
            try {
                textRender.displayHintMenu();
                int input = inputHandler.getValidateIntInput("Выбор: ");
                if (input == 1) {
                    return hint;
                } else if (input == 2) {
                    return "";
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                textRender.displayInvalidHintSelection();
            }
        }
    }

    private void renderGameState(GameModel gameModel, Difficulty difficulty) {
        hangmanRender.renderGameState(
            gameModel.getAttemptsLeft(),
            gameModel.getMaxAttempts(),
            gameModel.getCurrentStateWord(),
            gameModel.getHint(),
            gameModel.getWrongLettersString(),
            difficulty
        );
    }
}
