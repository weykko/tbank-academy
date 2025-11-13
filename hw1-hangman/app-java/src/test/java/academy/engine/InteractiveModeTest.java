package academy.engine;

import academy.enums.Difficulty;
import academy.dictionary.WordEntry;
import academy.ui.TextRender;
import academy.ui.HangmanRender;
import academy.ui.InputHandler;
import academy.dictionary.WordsDictionary;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class InteractiveModeTest {
    private GameModel gameModel;
    private GameController gameController;
    private TextRender textRender;
    private HangmanRender ui;
    private WordEntry word;

    @BeforeEach
    public void setUp() {
        textRender = new TextRender();
        word = new WordEntry("собака", "Лучший друг человека");
        gameModel = new GameModel(word, 8);
        ui = new HangmanRender();

        InputHandler inputHandler = mock(InputHandler.class);
        WordsDictionary dictionary = mock(WordsDictionary.class);

        gameController = new GameController(inputHandler, dictionary, textRender);
    }

    @Test
    void whenCorrectLetterGuessed_thenGameStateUpdates() {
        boolean result = gameController.guessLetter(gameModel, 'с');

        assertTrue(result);
        assertEquals(8, gameModel.getAttemptsLeft());
        assertEquals("с*****", gameModel.getCurrentStateWord());
    }

    @Test
    void whenWrongLetterGuessed_thenAttemptsDecrease() {
        boolean result = gameController.guessLetter(gameModel, 'д');

        assertFalse(result);
        assertEquals(7, gameModel.getAttemptsLeft());
        assertEquals("******", gameModel.getCurrentStateWord());
    }

    @Test
    void whenUserAcceptHint_shouldDisplayUpdateState() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ui.renderGameState(4, 8, "с*****", word.hint(), "д", Difficulty.EASY);
        String consoleOutput = output.toString();

        assertTrue(consoleOutput.contains("Осталось попыток: 4/8"));
        assertTrue(consoleOutput.contains("Неправильные буквы: д"));
        assertTrue(consoleOutput.contains("Слово: с*****"));
        assertTrue(consoleOutput.contains("Подсказка: Лучший друг человека"));
        assertTrue(consoleOutput.contains(
            "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "      |\n" +
                "      |\n" +
                "========="));
    }

    @Test
    void whenUserDontAcceptHint_shouldDisplayUpdateState() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ui.renderGameState(4, 8, "с*****", "", "д", Difficulty.EASY);
        String consoleOutput = output.toString();

        assertTrue(consoleOutput.contains("Осталось попыток: 4/8"));
        assertTrue(consoleOutput.contains("Неправильные буквы: д"));
        assertTrue(consoleOutput.contains("Слово: с*****"));
        assertTrue(consoleOutput.contains("Подсказка: "));
        assertTrue(consoleOutput.contains(
            "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "      |\n" +
                "      |\n" +
                "========="));
    }


    @Test
    void whenGameAlreadyWon_thenGuessLetterReturnsFalse() {
        gameModel.setGameState(academy.enums.GameState.WIN);

        boolean result = gameController.guessLetter(gameModel, 'с');

        assertFalse(result);
    }

    @Test
    void whenGameAlreadyLost_thenGuessLetterReturnsFalse() {
        gameModel.setGameState(academy.enums.GameState.LOST);

        boolean result = gameController.guessLetter(gameModel, 'с');

        assertFalse(result);
    }

    @Test
    void whenLetterAlreadyGuessed_thenGuessLetterReturnsFalse() {
        gameController.guessLetter(gameModel, 'с');

        boolean result = gameController.guessLetter(gameModel, 'с');

        assertFalse(result);
    }

    @Test
    void whenWordFullyGuessed_thenGameStateIsWin() {
        gameController.guessLetter(gameModel, 'с');
        gameController.guessLetter(gameModel, 'о');
        gameController.guessLetter(gameModel, 'б');
        gameController.guessLetter(gameModel, 'а');
        gameController.guessLetter(gameModel, 'к');

        assertTrue(gameModel.isWon());
        assertEquals("собака", gameModel.getCurrentStateWord());
    }

    @Test
    void whenAllAttemptsUsed_thenGameStateIsLost() {
        for (int i = 0; i < 8; i++) {
            gameController.guessLetter(gameModel, (char)('х' + i));
        }

        assertTrue(gameModel.isLost());
    }
}
