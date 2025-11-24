package academy.maze.renderer.impl;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.renderer.MazeRenderer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** Абстрактный базовый класс для рендереров лабиринта. Содержит общую логику отрисовки лабиринта с путём. */
public abstract class AbstractMazeRenderer implements MazeRenderer {
    protected static final char START = 'O';
    protected static final char END = 'X';

    @Override
    public String render(Maze maze) {
        CellType[][] cells = maze.cells();
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                sb.append(getCellChar(cells, x, y));
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
                    symbol = getRouteChar();
                } else {
                    symbol = getCellChar(cells, x, y);
                }

                sb.append(symbol);
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * Возвращает символ для отображения маршрута.
     *
     * @return символ маршрута
     */
    protected abstract char getRouteChar();

    /**
     * Возвращает символ для отображения ячейки.
     *
     * @param cells массив клеток лабиринта
     * @param x координата x
     * @param y координата y
     * @return символ для отображения
     */
    protected abstract char getCellChar(CellType[][] cells, int x, int y);
}
