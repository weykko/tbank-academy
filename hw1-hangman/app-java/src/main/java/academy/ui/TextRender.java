package academy.ui;

import academy.enums.Category;
import academy.enums.Difficulty;
import academy.engine.GameModel;

public class TextRender {

    private final String CATEGORY_MENU = """
        1. Животные
        2. Еда
        3. Одежда
        4. Технологии
        5. Случайная категория
        """;
    private final String DIFFICULTY_MENU = """
        1. Легко
        2. Средне
        3. Сложно
        4. Случайная сложность
        """;

    private final String HINT_MENU = """
        1. Да
        2. Нет
        """;

    public void printHeader() {
        System.out.println("==Виселица==\n");
    }

    public void printGameInfo(Category category, Difficulty difficulty, int maxAttempts) {
        System.out.println("Категория: " + category.getValue());
        System.out.println("Сложность: " + difficulty.getValue());
        System.out.println("Количество попыток: " + maxAttempts);

    }

    public void printGuessResult(char letter, boolean isCorrect) {
        if (isCorrect) {
            System.out.println("Вы угадали букву - " + letter);
        } else {
            System.out.println("Вы не угадали букву - " + letter);
        }
    }

    public void showGameResult(GameModel gameModel) {
        if (gameModel.isWon()) {
            System.out.println("\nПобеда!");
            System.out.println("\nВы угадали слово: " + gameModel.getSelectedWord());
        } else if (gameModel.isLost()) {
            System.out.println("\nПоражение!");
            System.out.println("\nИгра закончена! Вы не угадали слово: " + gameModel.getSelectedWord());
        }
    }

    public void displayCategoryMenu() {
        System.out.println("Выберите категорию, чтобы получить слово:");
        System.out.println(CATEGORY_MENU);
    }

    public void displayDifficultyMenu() {
        System.out.println("Выберите сложность слова");
        System.out.println(DIFFICULTY_MENU);
    }

    public void displayHintMenu() {
        System.out.println("У вас есть возможность получить подсказку");
        System.out.println("Варианты:");
        System.out.println(HINT_MENU);
    }

    public void displaySelectedCategory(Category category) {
        System.out.println("Выбранная категория: " + category);
    }

    public void displaySelectedDifficulty(Difficulty difficulty) {
        System.out.println("Выбранная сложность: " + difficulty);
    }

    public void displayInvalidCategorySelection() {
        System.out.println("Недопустимое значение для выбора категории\n");
    }

    public void displayInvalidDifficultySelection() {
        System.out.println("Недопустимое значение для выбора сложности\n");
    }

    public void displayInvalidHintSelection() {
        System.out.println("Недопустимое значение при выборе\n");
    }

    public void displayNonInteractiveResult(String resultWord, String outcome) {
        System.out.println(resultWord + ";" + outcome);
    }
}
