package academy.engine;

import academy.dictionary.WordEntry;
import academy.enums.GameState;
import java.util.HashSet;
import java.util.Set;

public class GameModel {
    private final WordEntry wordEntry;
    private final Set<Character> guessedLetters;
    private final Set<Character> wrongLetters;
    private final Character[] currentStateWord;
    private int attemptsLeft;
    private final int maxAttempts;
    private GameState gameState;

    private String currentHint;
    private final double HINT_THRESHOLD;
    private boolean isHintOffered;


    public GameModel(WordEntry wordEntry, int maxAttempts) {
        this.wordEntry = wordEntry;
        this.maxAttempts = maxAttempts;
        this.attemptsLeft = maxAttempts;
        this.guessedLetters = new HashSet<>();
        this.wrongLetters = new HashSet<>();
        this.currentStateWord = new Character[wordEntry.word().length()];
        this.HINT_THRESHOLD = maxAttempts * 0.5;
        this.currentHint = wordEntry.hint();
        this.isHintOffered = false;
        this.gameState = attemptsLeft <= 0 ? GameState.LOST : GameState.IN_PROGRESS;

        hidingWord(wordEntry);
    }

    private void hidingWord(WordEntry wordEntry) {
        for (int i = 0; i < wordEntry.word().length(); i++) {
            char currentChar = wordEntry.word().charAt(i);
            if (Character.isLetter(currentChar)) {
                currentStateWord[i] = '*';
            } else {
                currentStateWord[i] = currentChar;
            }
        }
    }

    public boolean isWordGuessed() {
        for (Character character : currentStateWord) {
            if (character == '*' && !Character.isLetter(character)) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return gameState != GameState.IN_PROGRESS;
    }

    public boolean isWon() {
        return gameState == GameState.WIN;
    }

    public boolean isLost() {
        return gameState == GameState.LOST;
    }

    public String getCurrentStateWord() {
        StringBuilder currentStateWordString = new StringBuilder();
        for (Character character : currentStateWord) {
            currentStateWordString.append(character);
        }
        return currentStateWordString.toString();
    }

    public String getWrongLettersString() {
        return wrongLetters.toString().replaceAll("[\\[\\]]", "");
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getSelectedWord() {
        return wordEntry.word();
    }

    public boolean checkAttemptsLeft() {
        return attemptsLeft <= HINT_THRESHOLD;
    }

    public WordEntry getWordEntry() {
        return wordEntry;
    }

    public String getHint() {
        return currentHint;
    }

    public void setHint(String hint) {
        this.currentHint = hint;
    }

    public boolean isHintOffered() {
        return isHintOffered;
    }

    public void setHintOffered(boolean hintOffered) {
        isHintOffered = hintOffered;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public Set<Character> getWrongLetters() {
        return wrongLetters;
    }

    public Character[] getCurrentStateWordArray() {
        return currentStateWord;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public String getWord() {
        return wordEntry.word();
    }
}
