package academy.dictionary;

import org.jetbrains.annotations.NotNull;

public record WordEntry(String word, String hint) {
    @Override
    public @NotNull String toString() {
        return word + (hint != null && !hint.isEmpty() ? " (" + hint + ")" : "");
    }
}
