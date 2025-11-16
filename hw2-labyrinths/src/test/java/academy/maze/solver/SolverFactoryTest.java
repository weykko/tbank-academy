package academy.maze.solver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SolverFactoryTest {

    @Test
    void createSolver_shouldReturnAstarSolverForAstar() {
        SolverFactory factory = new SolverFactory();

        Solver solver = factory.createSolver("astar");

        assertThat(solver).isNotNull();
        assertThat(solver.getClass().getSimpleName()).isEqualTo("AstarSolver");
    }

    @Test
    void createSolver_shouldReturnDijkstraSolverForDijkstra() {
        SolverFactory factory = new SolverFactory();

        Solver solver = factory.createSolver("dijkstra");

        assertThat(solver).isNotNull();
        assertThat(solver.getClass().getSimpleName()).isEqualTo("DijkstraSolver");
    }

    @Test
    void createSolver_shouldBeCaseInsensitive() {
        SolverFactory factory = new SolverFactory();

        Solver astar1 = factory.createSolver("ASTAR");
        Solver astar2 = factory.createSolver("Astar");
        Solver dijkstra1 = factory.createSolver("DIJKSTRA");
        Solver dijkstra2 = factory.createSolver("Dijkstra");

        assertThat(astar1).isNotNull();
        assertThat(astar2).isNotNull();
        assertThat(dijkstra1).isNotNull();
        assertThat(dijkstra2).isNotNull();
    }

    @Test
    void createSolver_shouldThrowExceptionForUnknownType() {
        SolverFactory factory = new SolverFactory();

        assertThatThrownBy(() -> factory.createSolver("bfs"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown");
    }
}

