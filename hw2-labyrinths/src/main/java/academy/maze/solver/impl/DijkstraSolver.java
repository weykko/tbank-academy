package academy.maze.solver.impl;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.solver.Solver;
import academy.maze.solver.SolverUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/** Решатель лабиринта алгоритмом Дейкстры. */
public class DijkstraSolver implements Solver {

    @Override
    public Path solve(Maze maze, Point start, Point end) {
        SolverUtils.validatePoints(maze, start, end);

        CellType[][] cells = maze.cells();

        Map<Point, Double> distances = new HashMap<>();
        distances.put(start, 0.0);

        Map<Point, Point> predecessors = new HashMap<>();

        Map<Point, Boolean> visited = new HashMap<>();

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));

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

            for (Point neighbor : SolverUtils.getNeighbors(cells, current.point)) {
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
     * Восстанавливает путь от начальной до конечной точки.
     *
     * @param predecessors карта предшественников для каждой точки
     * @param start начальная точка
     * @param end конечная точка
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

    /** Вспомогательный класс для представления узла в приоритетной очереди. */
    private record Node(Point point, double distance) {}
}
