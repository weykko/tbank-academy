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
 * Решатель лабиринта алгоритмом Дейкстры.
 * Алгоритм находит кратчайший путь от начальной до конечной точки,
 * используя только реальную стоимость пути без эвристики.
 */
public class DijkstraSolver implements Solver {

    @Override
    public Path solve(Maze maze, Point start, Point end) {
        validatePoints(maze, start, end);

        CellType[][] cells = maze.cells();

        Map<Point, Double> distances = new HashMap<>();
        distances.put(start, 0.0);

        Map<Point, Point> predecessors = new HashMap<>();

        Map<Point, Boolean> visited = new HashMap<>();

        PriorityQueue<Node> queue = new PriorityQueue<>(
            Comparator.comparingDouble(n -> n.distance)
        );

        queue.add(new Node(start, 0.0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (visited.getOrDefault(current.point, false)) {
                continue;
            }

            visited.put(current.point, true);

            if (current.point.equals(end)) {
                return reconstructPath(predecessors, start, end);
            }


            for (Point neighbor : getNeighbors(cells, current.point)) {
                if (visited.getOrDefault(neighbor, false)) {
                    continue;
                }

                // Учитываем стоимость прохождения через клетку
                int cellCost = cells[neighbor.y()][neighbor.x()].getCost();
                double newDistance = current.distance + cellCost;
                double oldDistance = distances.getOrDefault(neighbor, Double.POSITIVE_INFINITY);

                if (newDistance < oldDistance) {
                    distances.put(neighbor, newDistance);
                    predecessors.put(neighbor, current.point);
                    queue.add(new Node(neighbor, newDistance));
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
     * @return true, если точка внутри границ и является проходимой, иначе false
     */
    private boolean isValidPoint(CellType[][] cells, Point point) {
        return point.y() >= 0 && point.y() < cells.length
            && point.x() >= 0 && point.x() < cells[0].length
            && cells[point.y()][point.x()].isPassable();
    }

    /**
     * Получает соседние точки, которые являются путями.
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
     * Восстанавливает путь от начальной до конечной точки.
     *
     * @param predecessors карта предшественников для каждой точки
     * @param start        начальная точка
     * @param end          конечная точка
     * @return восстановленный путь
     */
    private Path reconstructPath(Map<Point, Point> predecessors, Point start, Point end) {
        List<Point> pathList = new ArrayList<>();
        Point current = end;

        while (current != null) {
            pathList.add(current);
            if (current.equals(start)) {
                break;
            }
            current = predecessors.get(current);
        }

        Collections.reverse(pathList);
        return new Path(pathList.toArray(new Point[0]));
    }

    /**
     * Вспомогательный класс для представления узла в приоритетной очереди.
     */
    private static class Node {
        final Point point;
        final double distance;

        Node(Point point, double distance) {
            this.point = point;
            this.distance = distance;
        }
    }
}

