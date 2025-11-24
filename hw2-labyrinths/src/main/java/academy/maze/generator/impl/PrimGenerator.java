package academy.maze.generator.impl;

import academy.maze.dto.CellType;
import academy.maze.dto.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Генератор лабиринта алгоритмом Прима. Создает более сбалансированную структуру с большим количеством тупиков и
 * ответвлений.
 */
public class PrimGenerator extends AbstractMazeGenerator {

    public PrimGenerator() {
        super();
    }

    @Override
    protected void generateMazeStructure(CellType[][] cells, int width, int height) {
        List<Point> frontiers = new ArrayList<>();

        Point start = new Point(1, 1);
        cells[start.y()][start.x()] = CellType.PATH;

        addFrontiers(cells, start, frontiers);

        while (!frontiers.isEmpty()) {
            Point frontier = frontiers.remove(random.nextInt(frontiers.size()));

            List<Point> neighbors = getPathNeighbors(cells, frontier);

            if (!neighbors.isEmpty()) {
                Point neighbor = neighbors.get(random.nextInt(neighbors.size()));

                cells[frontier.y()][frontier.x()] = CellType.PATH;

                int wallX = frontier.x() + (neighbor.x() - frontier.x()) / 2;
                int wallY = frontier.y() + (neighbor.y() - frontier.y()) / 2;
                cells[wallY][wallX] = CellType.PATH;

                addFrontiers(cells, frontier, frontiers);
            }
        }
    }

    /**
     * Добавляет соседние клетки на расстоянии 2 от текущей в список frontiers.
     *
     * @param cells сетка лабиринта
     * @param cell текущая ячейка
     * @param frontiers список frontier клеток
     */
    private void addFrontiers(CellType[][] cells, Point cell, List<Point> frontiers) {
        for (int[] dir : DIRECTIONS) {
            Point neighbor = new Point(cell.x() + dir[0], cell.y() + dir[1]);

            if (isValidInnerCell(cells, neighbor)
                    && cells[neighbor.y()][neighbor.x()] == CellType.WALL
                    && !frontiers.contains(neighbor)) {
                frontiers.add(neighbor);
            }
        }
    }

    /**
     * Находит соседние клетки типа PATH на расстоянии 2 от указанной клетки.
     *
     * @param cells сетка лабиринта
     * @param cell текущая ячейка
     * @return список соседних PATH клеток
     */
    private List<Point> getPathNeighbors(CellType[][] cells, Point cell) {
        List<Point> neighbors = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int nx = cell.x() + dir[0];
            int ny = cell.y() + dir[1];

            if (isValidInnerCell(cells, new Point(nx, ny)) && cells[ny][nx] == CellType.PATH) {
                neighbors.add(new Point(nx, ny));
            }
        }

        return neighbors;
    }
}
