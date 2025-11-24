package academy.maze.generator;

import static org.assertj.core.api.Assertions.assertThat;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import org.junit.jupiter.api.Test;

class SurfaceDecoratorTest {

    @Test
    void addSurfaces_shouldChangeSomePaths() {
        CellType[][] cells = new CellType[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = CellType.PATH;
            }
        }
        Maze originalMaze = new Maze(cells);
        SurfaceDecorator decorator = new SurfaceDecorator();

        Maze decoratedMaze = decorator.addSurfaces(originalMaze);

        CellType[][] newCells = decoratedMaze.cells();
        boolean hasDifferentType = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (newCells[i][j] != CellType.PATH) {
                    hasDifferentType = true;
                    break;
                }
            }
        }

        assertThat(hasDifferentType).isTrue();
    }

    @Test
    void addSurfaces_shouldOnlyUseValidTypes() {
        CellType[][] cells = new CellType[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cells[i][j] = CellType.PATH;
            }
        }
        Maze originalMaze = new Maze(cells);
        SurfaceDecorator decorator = new SurfaceDecorator();

        Maze decoratedMaze = decorator.addSurfaces(originalMaze);

        CellType[][] newCells = decoratedMaze.cells();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                CellType type = newCells[i][j];
                assertThat(type).isIn(CellType.PATH, CellType.SWAMP, CellType.SAND, CellType.COIN);
            }
        }
    }

    @Test
    void addSurfaces_shouldPreserveOriginalMaze() {
        CellType[][] cells = {
            {CellType.WALL, CellType.PATH},
            {CellType.PATH, CellType.WALL}
        };
        Maze originalMaze = new Maze(cells);
        SurfaceDecorator decorator = new SurfaceDecorator();

        decorator.addSurfaces(originalMaze);

        assertThat(originalMaze.cells()[0][0]).isEqualTo(CellType.WALL);
        assertThat(originalMaze.cells()[0][1]).isEqualTo(CellType.PATH);
    }
}
