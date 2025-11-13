package academy.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputValidationTest {
    private InputHandler inputHandler;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void getValidLetterInput_WhenInputManyCharacters() {
        String input = "фвфывфвфыв\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        inputHandler = new InputHandler();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assertThrows(RuntimeException.class, inputHandler::getValidLetterInput);

        String output = outputStream.toString();
        assertTrue(output.contains("Пожалуйста, введите одну русскую букву"));

        System.setOut(originalOut);

    }

    @Test
    public void getValidLetterInput_WhenLowercaseRussianLetter_ShouldReturnSameLetter() {
        String input = "п\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        inputHandler = new InputHandler();

        char result = inputHandler.getValidLetterInput();

        assertEquals('п', result);
    }

    @Test
    public void getValidLetterInput_WhenMultipleInvalidThenValid_ShouldEventuallyAccept() {
        String input = "12\nabc\n!\nк\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        inputHandler = new InputHandler();

        char result = inputHandler.getValidLetterInput();

        assertEquals('к', result);

        String output = outputStream.toString();
        assertTrue(output.contains("Пожалуйста, введите одну русскую букву"));
    }

    @Test
    public void getValidateIntInput_WhenValidInteger_ShouldReturnInteger() {
        String input = "5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        inputHandler = new InputHandler();

        int result = inputHandler.getValidateIntInput("Введите число: ");

        assertEquals(5, result);

        String output = outputStream.toString();
        assertTrue(output.contains("Введите число: "));
    }

    @Test
    public void getValidateIntInput_WhenNegativeInteger_ShouldReturnNegativeInteger() {
        String input = "-3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        inputHandler = new InputHandler();

        int result = inputHandler.getValidateIntInput("Введите число: ");

        assertEquals(-3, result);
    }

    @Test
    public void getValidateIntInput_WhenZero_ShouldReturnZero() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        inputHandler = new InputHandler();

        int result = inputHandler.getValidateIntInput("Введите число: ");

        assertEquals(0, result);
    }

    @Test
    public void getValidateIntInput_WhenLargeNumber_ShouldReturnNumber() {
        String input = "123456\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        inputHandler = new InputHandler();

        int result = inputHandler.getValidateIntInput("Введите число: ");

        assertEquals(123456, result);
    }

    @Test
    public void getValidateIntInput_WhenVeryLongInvalidInput_ShouldHandleGracefully() {
        String longInput = "thisisareallylonginvalidinputthatshouldberejected\n42\n";
        System.setIn(new ByteArrayInputStream(longInput.getBytes(StandardCharsets.UTF_8)));
        inputHandler = new InputHandler();

        int result = inputHandler.getValidateIntInput("Введите число: ");

        assertEquals(42, result);
    }

    @Test
    public void getValidateIntInput_WhenOnlySpaces_ShouldRetryAndAcceptValid() {
        String input = "   \n    \n8\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        inputHandler = new InputHandler();

        int result = inputHandler.getValidateIntInput("Введите число: ");

        assertEquals(8, result);
    }

    @Test
    public void getValidateIntInput_WhenNumberWithSpaces_ShouldParseCorrectly() {
        String input = "   25   \n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        inputHandler = new InputHandler();

        int result = inputHandler.getValidateIntInput("Введите число: ");

        assertEquals(25, result);
    }
}
