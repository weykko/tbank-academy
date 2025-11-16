package academy.maze.generator;

import academy.maze.generator.impl.DfsGenerator;
import academy.maze.generator.impl.PrimGenerator;

/**
 * Фабрика генераторов лабиринтов.
 * Позволяет создавать генераторы по их типу.
 */
public class GeneratorFactory {

    /**
     * Создаёт генератор по типу алгоритма.
     *
     * @param algorithm тип алгоритма генерации
     * @return генератор лабиринта
     * @throws IllegalArgumentException если алгоритм не поддерживается
     */
    public Generator createGenerator(GeneratorType algorithm) {
        return switch (algorithm) {
            case DFS -> new DfsGenerator();
            case PRIM -> new PrimGenerator();
        };
    }

    /**
     * Создаёт генератор по строковому имени алгоритма.
     *
     * @param algorithmName имя алгоритма (регистронезависимо)
     * @return генератор лабиринта
     * @throws IllegalArgumentException если алгоритм не поддерживается
     */
    public Generator createGenerator(String algorithmName) {
        try {
            GeneratorType type = GeneratorType.valueOf(algorithmName.toUpperCase());
            return createGenerator(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Unknown generator algorithm: " + algorithmName +
                ". Supported algorithms: dfs, prim"
            );
        }
    }

    /**
     * Типы алгоритмов генерации лабиринтов.
     */
    public enum GeneratorType {
        DFS,
        PRIM
    }
}

