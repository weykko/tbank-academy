package academy.maze.solver;

import academy.maze.solver.impl.AstarSolver;
import academy.maze.solver.impl.DijkstraSolver;

/** Фабрика решателей лабиринтов. Позволяет создавать решатели по их типу. */
public class SolverFactory {

    /**
     * Создаёт решатель по типу алгоритма.
     *
     * @param algorithm тип алгоритма решения
     * @return решатель лабиринта
     * @throws IllegalArgumentException если алгоритм не поддерживается
     */
    public Solver createSolver(SolverType algorithm) {
        return switch (algorithm) {
            case ASTAR -> new AstarSolver();
            case DIJKSTRA -> new DijkstraSolver();
        };
    }

    /**
     * Создаёт решатель по строковому имени алгоритма.
     *
     * @param algorithmName имя алгоритма (регистронезависимо)
     * @return решатель лабиринта
     * @throws IllegalArgumentException если алгоритм не поддерживается
     */
    public Solver createSolver(String algorithmName) {
        try {
            SolverType type = SolverType.valueOf(algorithmName.toUpperCase());
            return createSolver(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown solver algorithm: " + algorithmName + ". Supported algorithms: astar, dijkstra");
        }
    }

    /** Типы поддерживаемых алгоритмов решения лабиринтов. */
    public enum SolverType {
        ASTAR,
        DIJKSTRA
    }
}
