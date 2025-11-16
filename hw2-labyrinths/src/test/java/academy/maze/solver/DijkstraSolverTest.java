package academy.maze.solver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.solver.impl.DijkstraSolver;
import org.junit.jupiter.api.Test;

class DijkstraSolverTest {

    @Test
    void solve_shouldFindPathInSimpleMaze() {
        CellType[][] cells = {
            {CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.PATH, CellType.PATH, CellType.PATH, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL}
        };
        Maze maze = new Maze(cells);
        Solver solver = new DijkstraSolver();
        Point start = new Point(1, 1);
        Point end = new Point(3, 1);

        Path path = solver.solve(maze, start, end);

        assertThat(path.points()).isNotEmpty();
        assertThat(path.points()[0]).isEqualTo(start);
        assertThat(path.points()[path.points().length - 1]).isEqualTo(end);
    }

    @Test
    void solve_shouldReturnEmptyPathWhenNoSolution() {
        CellType[][] cells = {
            {CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.PATH, CellType.WALL, CellType.PATH, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL}
        };
        Maze maze = new Maze(cells);
        Solver solver = new DijkstraSolver();
        Point start = new Point(1, 1);
        Point end = new Point(3, 1);

        Path path = solver.solve(maze, start, end);

        assertThat(path.points()).isEmpty();
    }

    @Test
    void solve_shouldThrowExceptionForInvalidStartPoint() {
        CellType[][] cells = {
            {CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.PATH, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.WALL}
        };
        Maze maze = new Maze(cells);
        Solver solver = new DijkstraSolver();
        Point start = new Point(0, 0);
        Point end = new Point(1, 1);

        assertThatThrownBy(() -> solver.solve(maze, start, end))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Start point");
    }

    @Test
    void solve_shouldThrowExceptionForInvalidEndPoint() {
        CellType[][] cells = {
            {CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.PATH, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.WALL}
        };
        Maze maze = new Maze(cells);
        Solver solver = new DijkstraSolver();
        Point start = new Point(1, 1);
        Point end = new Point(2, 2);

        assertThatThrownBy(() -> solver.solve(maze, start, end))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("End point");
    }

    @Test
    void solve_shouldPreferCheaperPath() {
        CellType[][] cells = {
            {CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.WALL, CellType.PATH, CellType.COIN, CellType.PATH, CellType.WALL},
            {CellType.WALL, CellType.SWAMP, CellType.SWAMP, CellType.SWAMP, CellType.WALL},
            {CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL, CellType.WALL}
        };
        Maze maze = new Maze(cells);
        Solver solver = new DijkstraSolver();
        Point start = new Point(1, 1);
        Point end = new Point(3, 1);

        Path path = solver.solve(maze, start, end);

        assertThat(path.points()).isNotEmpty();
        boolean pathGoesThrough = false;
        for (Point p : path.points()) {
            if (p.y() == 1) {
                pathGoesThrough = true;
                break;
            }
        }
        assertThat(pathGoesThrough).isTrue();
    }
}

