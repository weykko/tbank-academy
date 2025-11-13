package academy.dictionary;

import academy.enums.Category;
import academy.enums.Difficulty;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class WordsDictionary {
    private final Random random;
    private final Map<Category, Map<Difficulty, List<WordEntry>>> wordsMap;

    protected WordsDictionary(Random random, Map<Category, Map<Difficulty, List<WordEntry>>> wordsMap) {
        this.random = random;
        this.wordsMap = wordsMap;
    }

    public String getWord(Category category, Difficulty difficulty) {
        List<WordEntry> wordEntries = wordsMap
            .getOrDefault(category, Collections.emptyMap())
            .get(difficulty);

        if (wordEntries == null || wordEntries.isEmpty()) {
            throw new IllegalArgumentException("Не найдено ни одного слова");
        }

        return wordEntries.get(random.nextInt(wordEntries.size())).word().toLowerCase();
    }

    public Category getWordCategory(int value) {
        return switch (value) {
            case 1 -> Category.ANIMALS;
            case 2 -> Category.FOOD;
            case 3 -> Category.CLOTHES;
            case 4 -> Category.TECHNOLOGIES;
            case 5 -> getRandomCategory();
            default -> throw new IllegalArgumentException();
        };
    }

    public Difficulty getWordDifficulty(int value) {
        return switch (value) {
            case 1 -> Difficulty.EASY;
            case 2 -> Difficulty.MEDIUM;
            case 3 -> Difficulty.HARD;
            case 4 -> getRandomDifficulty();
            default -> throw new IllegalArgumentException();
        };
    }

    public String getHint(Category category, Difficulty difficulty, String word) {
        List<WordEntry> wordEntries = wordsMap
            .getOrDefault(category, Collections.emptyMap())
            .get(difficulty);

        return wordEntries.stream()
            .filter(entry -> entry.word().equalsIgnoreCase(word))
            .findFirst()
            .map(WordEntry::hint)
            .orElse("Подсказка не найдена");
    }

    public List<String> getAllWordsByCategoryAndDifficulty(Category category, Difficulty difficulty) {
        List<WordEntry> wordEntries = wordsMap
            .getOrDefault(category, Collections.emptyMap())
            .get(difficulty);

        if (wordEntries == null) {
            return Collections.emptyList();
        }

        return wordEntries.stream()
            .map(WordEntry::word)
            .map(String::toLowerCase)
            .toList();
    }

    private Category getRandomCategory() {
        Category[] categories = Category.values();
        return categories[random.nextInt(categories.length)];
    }

    private Difficulty getRandomDifficulty() {
        Difficulty[] difficulties = Difficulty.values();
        return difficulties[random.nextInt(difficulties.length)];
    }
}
