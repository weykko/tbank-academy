package academy.maze.generator.impl;

import academy.maze.generator.Generator;
import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import java.util.Random;

/**
 * Абстрактный базовый класс для генераторов лабиринтов.
 * Реализует Template Method Pattern
 * Содержит общую логику инициализации и валидации.
 */
public abstract class AbstractMazeGenerator implements Generator {

    protected final Random random;

    protected static final int[][] DIRECTIONS = {
        {0, -2},
        {0, 2},
        {-2, 0},
        {2, 0}
    };

    protected AbstractMazeGenerator() {
        this.random = new Random();
    }

    @Override
    public final Maze generate(int width, int height) {
        validateDimensions(width, height);

        CellType[][] cells = initializeMazeGrid(width, height);

        generateMazeStructure(cells, width, height);

        return new Maze(cells);
    }

    /**
     * Инициализирует сетку лабиринта заданного размера.
     * Создает сетку (width + 2) x (height + 2), заполненную стенами.
     *
     * @param width  внутренняя ширина лабиринта
     * @param height внутренняя высота лабиринта
     * @return инициализированная сетка, заполненная стенами
     */
    protected CellType[][] initializeMazeGrid(int width, int height) {
        int gridWidth = width + 2;
        int gridHeight = height + 2;
        CellType[][] cells = new CellType[gridHeight][gridWidth];

        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                cells[y][x] = CellType.WALL;
            }
        }

        return cells;
    }

    /**
     * Генерирует структуру лабиринта.
     * Должен быть реализован в подклассах в соответствии с конкретным алгоритмом.
     *
     * @param cells  сетка ячеек лабиринта
     * @param width  внутренняя ширина лабиринта
     * @param height внутренняя высота лабиринта
     */
    protected abstract void generateMazeStructure(CellType[][] cells, int width, int height);

    /**
     * Проверяет корректность размеров лабиринта.
     *
     * @param width  ширина лабиринта
     * @param height высота лабиринта
     * @throws IllegalArgumentException если размеры некорректны
     */
    protected void validateDimensions(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                "Dimensions must be positive: width=" + width + ", height=" + height
            );
        }
    }

    /**
     * Проверяет, что ячейка находится внутри границ лабиринта (не на краю).
     *
     * @param cells сетка ячеек лабиринта
     * @param point проверяемая ячейка
     * @return true, если ячейка внутри границ, иначе false
     */
    protected boolean isValidInnerCell(CellType[][] cells, Point point) {
        return point.y() > 0 && point.y() < cells.length - 1
            && point.x() > 0 && point.x() < cells[0].length - 1;
    }
}
