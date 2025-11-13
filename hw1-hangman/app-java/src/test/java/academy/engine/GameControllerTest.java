package academy.engine;

import academy.dictionary.WordsDictionary;
import academy.ui.InputHandler;
import academy.ui.TextRender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    private GameController gameController;
    private GameModel gameModel;

    @BeforeEach
    void setUp() {
        InputHandler inputHandler = mock(InputHandler.class);
        WordsDictionary dictionary = mock(WordsDictionary.class);
        TextRender textRender = mock(TextRender.class);
        gameController = new GameController(inputHandler, dictionary, textRender);

        // Создаем реальную GameModel для тестирования
        gameModel = new GameModel(
            new academy.dictionary.WordEntry("тест", "подсказка"),
            5
        );
    }

    @Test
    void whenValidLetter_thenGuessLetterUpdatesGameModel() {
        // Act
        boolean result = gameController.guessLetter(gameModel, 'т');

        // Assert
        assertTrue(result);
        assertEquals("т**т", gameModel.getCurrentStateWord());
    }

    @Test
    void whenInvalidLetter_thenGuessLetterReturnsFalse() {
        // Act
        boolean result = gameController.guessLetter(gameModel, 'ю');

        // Assert
        assertFalse(result);
        assertEquals(4, gameModel.getAttemptsLeft());
    }

    @Test
    void whenDuplicateLetter_thenGuessLetterReturnsFalse() {
        // Arrange
        gameController.guessLetter(gameModel, 'т');

        // Act
        boolean result = gameController.guessLetter(gameModel, 'т');

        // Assert
        assertFalse(result);
    }
}
