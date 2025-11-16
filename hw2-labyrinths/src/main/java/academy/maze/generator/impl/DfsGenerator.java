package academy.maze.generator.impl;

import academy.maze.dto.CellType;
import academy.maze.dto.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Генератор лабиринта методом поиска в глубину (DFS, Recursive Backtracking).
 * Использует рекурсивный обход для создания извилистых коридоров с минимальным количеством тупиков.
 */
public class DfsGenerator extends AbstractMazeGenerator {

    public DfsGenerator() {
        super();
    }

    @Override
    protected void generateMazeStructure(CellType[][] cells, int width, int height) {
        boolean[][] visited = new boolean[cells.length][cells[0].length];
        carvePassagesFrom(cells, visited, new Point(1, 1));
    }

    /**
     * Рекурсивно вырезает проходы в лабиринте, начиная с текущей точки.
     *
     * @param cells   сетка лабиринта
     * @param visited матрица посещенных ячеек
     * @param current текущая точка
     */
    private void carvePassagesFrom(CellType[][] cells, boolean[][] visited, Point current) {
        visited[current.y()][current.x()] = true;
        cells[current.y()][current.x()] = CellType.PATH;

        List<int[]> shuffledDirections = getShuffledDirections();

        for (int[] direction : shuffledDirections) {
            Point neighbor = new Point(
                current.x() + direction[0],
                current.y() + direction[1]
            );

            if (isValidInnerCell(cells, neighbor) && !visited[neighbor.y()][neighbor.x()]) {
                Point wall = new Point(
                    current.x() + direction[0] / 2,
                    current.y() + direction[1] / 2
                );
                cells[wall.y()][wall.x()] = CellType.PATH;

                carvePassagesFrom(cells, visited, neighbor);
            }
        }
    }

    /**
     * Возвращает список направлений в случайном порядке.
     *
     * @return список направлений
     */
    private List<int[]> getShuffledDirections() {
        List<int[]> directions = new ArrayList<>(List.of(DIRECTIONS));
        Collections.shuffle(directions, random);
        return directions;
    }
}

