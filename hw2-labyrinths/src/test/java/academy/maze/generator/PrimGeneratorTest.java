package academy.maze.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.generator.impl.PrimGenerator;
import org.junit.jupiter.api.Test;

class PrimGeneratorTest {

    @Test
    void generate_shouldCreateMazeWithCorrectDimensions() {
        Generator generator = new PrimGenerator();

        Maze maze = generator.generate(5, 5);

        assertThat(maze.cells().length).isEqualTo(7);
        assertThat(maze.cells()[0].length).isEqualTo(7);
    }

    @Test
    void generate_shouldHaveWallsOnBorders() {
        Generator generator = new PrimGenerator();

        Maze maze = generator.generate(3, 3);
        CellType[][] cells = maze.cells();

        for (int x = 0; x < cells[0].length; x++) {
            assertThat(cells[0][x]).isEqualTo(CellType.WALL);
            assertThat(cells[cells.length - 1][x]).isEqualTo(CellType.WALL);
        }

        for (CellType[] cell : cells) {
            assertThat(cell[0]).isEqualTo(CellType.WALL);
            assertThat(cell[cells[0].length - 1]).isEqualTo(CellType.WALL);
        }
    }

    @Test
    void generate_shouldContainPaths() {
        Generator generator = new PrimGenerator();

        Maze maze = generator.generate(5, 5);
        CellType[][] cells = maze.cells();

        boolean hasPath = false;
        for (CellType[] row : cells) {
            for (CellType cell : row) {
                if (cell == CellType.PATH) {
                    hasPath = true;
                    break;
                }
            }
        }

        assertThat(hasPath).isTrue();
    }

    @Test
    void generate_shouldThrowExceptionForInvalidDimensions() {
        Generator generator = new PrimGenerator();

        assertThatThrownBy(() -> generator.generate(-5, 5)).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> generator.generate(5, 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void generate_shouldWorkForSmallestMaze() {
        Generator generator = new PrimGenerator();

        Maze maze = generator.generate(1, 1);

        assertThat(maze.cells().length).isEqualTo(3);
        assertThat(maze.cells()[0].length).isEqualTo(3);
    }
}
