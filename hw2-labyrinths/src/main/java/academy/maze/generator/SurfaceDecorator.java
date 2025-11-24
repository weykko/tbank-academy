package academy.maze.generator;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import java.util.Random;

/**
 * Декоратор для добавления специальных поверхностей в лабиринт. Случайным образом заменяет обычные пути на специальные
 * поверхности.
 */
public class SurfaceDecorator {

    private static final Random RANDOM = new Random();

    private static final float SWAMP_PROBABILITY = 1;
    private static final float SAND_PROBABILITY = 2;
    private static final float COIN_PROBABILITY = 3;

    /**
     * Добавляет случайные поверхности в лабиринт. Заменяет некоторые обычные пути (PATH) на специальные поверхности.
     *
     * @param maze исходный лабиринт
     * @return лабиринт с добавленными поверхностями
     */
    public Maze addSurfaces(Maze maze) {
        CellType[][] cells = maze.cells();
        CellType[][] newCells = new CellType[cells.length][cells[0].length];

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                if (cells[y][x] == CellType.PATH) {
                    newCells[y][x] = getRandomSurface();
                } else {
                    newCells[y][x] = cells[y][x];
                }
            }
        }

        return new Maze(newCells);
    }

    /**
     * Случайным образом выбирает тип поверхности.
     *
     * @return тип поверхности (PATH, SWAMP, SAND или COIN)
     */
    private CellType getRandomSurface() {
        float chance = RANDOM.nextFloat(100);

        if (chance < COIN_PROBABILITY) {
            return CellType.COIN;
        } else if (chance < COIN_PROBABILITY + SWAMP_PROBABILITY) {
            return CellType.SWAMP;
        } else if (chance < COIN_PROBABILITY + SWAMP_PROBABILITY + SAND_PROBABILITY) {
            return CellType.SAND;
        }

        return CellType.PATH;
    }
}
