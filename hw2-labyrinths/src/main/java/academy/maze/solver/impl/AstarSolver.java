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

/** Решатель лабиринта алгоритмом A* (A-star). */
public class AstarSolver implements Solver {

    @Override
    public Path solve(Maze maze, Point start, Point end) {
        SolverUtils.validatePoints(maze, start, end);

        CellType[][] cells = maze.cells();

        Map<Point, Node> visited = new HashMap<>();

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));

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

            for (Point neighbor : SolverUtils.getNeighbors(cells, current.point)) {
                int cellCost = cells[neighbor.y()][neighbor.x()].getCost();
                double tentativeGScore = current.gScore + cellCost;

                if (!visited.containsKey(neighbor) || tentativeGScore < visited.get(neighbor).gScore) {

                    double hScore = heuristic(neighbor, end);
                    Node neighborNode = new Node(neighbor, tentativeGScore, tentativeGScore + hScore, current);
                    openSet.add(neighborNode);
                }
            }
        }

        return new Path(new Point[0]);
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

    /** Внутренний класс для представления узла в алгоритме A*. */
    private record Node(Point point, double gScore, double fScore, Node parent) {}
}
