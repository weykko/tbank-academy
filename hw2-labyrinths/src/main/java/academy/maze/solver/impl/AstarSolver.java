package academy.maze.solver.impl;

import academy.maze.solver.Solver;
import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Решатель лабиринта алгоритмом A* (A-star).
 * Алгоритм использует эвристику (Manhattan distance) для оптимизации поиска.
 * f(n) = g(n) + h(n), где:
 * - g(n) - стоимость пути от начала до текущей точки
 * - h(n) - эвристическая оценка расстояния от текущей точки до конца
 */
public class AstarSolver implements Solver {

    @Override
    public Path solve(Maze maze, Point start, Point end) {
        validatePoints(maze, start, end);

        CellType[][] cells = maze.cells();

        Map<Point, Node> visited = new HashMap<>();

        PriorityQueue<Node> openSet = new PriorityQueue<>(
            Comparator.comparingDouble(n -> n.fScore)
        );

        Node startNode = new Node(start, 0, heuristic(start, end), null);
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.point.equals(end)) {
                return reconstructPath(current);
            }

            if (visited.containsKey(current.point)) {
                Node existingNode = visited.get(current.point);
                if (existingNode.gScore <= current.gScore) {
                    continue;
                }
            }

            visited.put(current.point, current);

            for (Point neighbor : getNeighbors(cells, current.point)) {
                // Учитываем стоимость прохождения через клетку
                int cellCost = cells[neighbor.y()][neighbor.x()].getCost();
                double tentativeGScore = current.gScore + cellCost;

                if (!visited.containsKey(neighbor)
                    || tentativeGScore < visited.get(neighbor).gScore) {

                    double hScore = heuristic(neighbor, end);
                    Node neighborNode = new Node(
                        neighbor,
                        tentativeGScore,
                        tentativeGScore + hScore,
                        current
                    );
                    openSet.add(neighborNode);
                }
            }
        }

        return new Path(new Point[0]);
    }

    /**
     * Проверяет корректность начальной и конечной точек.
     *
     * @param maze  лабиринт.
     * @param start начальная точка.
     * @param end   конечная точка.
     */
    private void validatePoints(Maze maze, Point start, Point end) {
        CellType[][] cells = maze.cells();

        if (!isValidPoint(cells, start)) {
            throw new IllegalArgumentException(
                "Start point is out of bounds or is a wall: " + start
            );
        }

        if (!isValidPoint(cells, end)) {
            throw new IllegalArgumentException(
                "End point is out of bounds or is a wall: " + end
            );
        }
    }

    /**
     * Проверяет, что точка находится внутри границ лабиринта и является проходимой.
     *
     * @param cells сетка ячеек лабиринта
     * @param point проверяемая точка
     * @return true, если точка валидна, иначе false
     */
    private boolean isValidPoint(CellType[][] cells, Point point) {
        return point.y() >= 0 && point.y() < cells.length
            && point.x() >= 0 && point.x() < cells[0].length
            && cells[point.y()][point.x()].isPassable();
    }

    /**
     * Получает список соседних точек (вверх, вниз, влево, вправо).
     *
     * @param cells сетка ячеек лабиринта
     * @param point текущая точка
     * @return список соседних точек
     */
    private List<Point> getNeighbors(CellType[][] cells, Point point) {
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
     * Эвристическая функция - Manhattan distance
     *
     * @param a первая точка
     * @param b вторая точка
     * @return эвристическое расстояние между точками
     */
    private double heuristic(Point a, Point b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Восстанавливает путь от конечной точки до начальной.
     *
     * @param endNode конечный узел
     * @return путь от начала до конца
     */
    private Path reconstructPath(Node endNode) {
        List<Point> pathList = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            pathList.add(current.point);
            current = current.parent;
        }

        Collections.reverse(pathList);
        return new Path(pathList.toArray(new Point[0]));
    }

    /**
     * Внутренний класс для представления узла в алгоритме A*.
     */
    private static class Node {
        final Point point;
        final double gScore;
        final double fScore;
        final Node parent;

        Node(Point point, double gScore, double fScore, Node parent) {
            this.point = point;
            this.gScore = gScore;
            this.fScore = fScore;
            this.parent = parent;
        }
    }
}

