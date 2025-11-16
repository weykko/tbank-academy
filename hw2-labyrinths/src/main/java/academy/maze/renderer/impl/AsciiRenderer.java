package academy.maze.renderer.impl;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.renderer.MazeRenderer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ASCII рендерер лабиринта.
 * Использует простые ASCII символы для отображения:
 * - '#' для стен
 * - ' ' для путей
 * - '.' для найденного маршрута
 * - 'O' для начальной точки
 * - 'X' для конечной точки
 */
public class AsciiRenderer implements MazeRenderer {
    private static final char WALL = '#';
    private static final char PATH = ' ';

    private static final char SWAMP = '~';
    private static final char SAND = '*';
    private static final char COIN = '$';

    private static final char ROUTE = '.';
    private static final char START = 'O';
    private static final char END = 'X';

    @Override
    public String render(Maze maze) {
        CellType[][] cells = maze.cells();
        StringBuilder sb = new StringBuilder();

        for (CellType[] cell : cells) {
            for (CellType cellType : cell) {
                sb.append(getCellChar(cellType));
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    @Override
    public String render(Maze maze, Path path, Point start, Point end) {
        CellType[][] cells = maze.cells();

        Set<Point> pathPoints = new HashSet<>();
        if (path.points() != null) {
            pathPoints.addAll(Arrays.asList(path.points()));
        }

        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                Point current = new Point(x, y);
                char symbol;

                if (current.equals(start)) {
                    symbol = START;
                } else if (current.equals(end)) {
                    symbol = END;
                } else if (pathPoints.contains(current)) {
                    symbol = ROUTE;
                } else {
                    symbol = getCellChar(cells[y][x]);
                }

                sb.append(symbol);
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * Возвращает символ для отображения типа ячейки.
     *
     * @param cellType тип ячейки
     * @return символ для отображения
     */
    private char getCellChar(CellType cellType) {
        return switch (cellType) {
            case WALL -> WALL;
            case PATH -> PATH;
            case SWAMP -> SWAMP;
            case SAND -> SAND;
            case COIN -> COIN;
        };
    }
}

