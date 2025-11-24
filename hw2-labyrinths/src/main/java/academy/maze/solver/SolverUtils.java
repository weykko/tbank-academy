package academy.maze.solver;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import java.util.ArrayList;
import java.util.List;

/** Утилитарный класс для решения лабиринта */
public class SolverUtils {

    /**
     * Получает список соседних точек (вверх, вниз, влево, вправо).
     *
     * @param cells сетка ячеек лабиринта
     * @param point текущая точка
     * @return список соседних точек
     */
    public static List<Point> getNeighbors(CellType[][] cells, Point point) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] dir : directions) {
            Point neighbor = new Point(point.x() + dir[0], point.y() + dir[1]);
            if (isValidPoint(cells, neighbor)) {
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    /**
     * Проверяет, что точка находится внутри границ лабиринта и является проходимой.
     *
     * @param cells сетка ячеек лабиринта
     * @param point проверяемая точка
     * @return true, если точка валидна, иначе false
     */
    private static boolean isValidPoint(CellType[][] cells, Point point) {
        return point.y() >= 0
                && point.y() < cells.length
                && point.x() >= 0
                && point.x() < cells[0].length
                && cells[point.y()][point.x()].isPassable();
    }

    /**
     * Проверяет корректность начальной и конечной точек.
     *
     * @param maze лабиринт.
     * @param start начальная точка.
     * @param end конечная точка.
     */
    public static void validatePoints(Maze maze, Point start, Point end) {
        CellType[][] cells = maze.cells();

        if (!isValidPoint(cells, start)) {
            throw new IllegalArgumentException("Start point is out of bounds or is a wall: " + start);
        }

        if (!isValidPoint(cells, end)) {
            throw new IllegalArgumentException("End point is out of bounds or is a wall: " + end);
        }
    }
}
