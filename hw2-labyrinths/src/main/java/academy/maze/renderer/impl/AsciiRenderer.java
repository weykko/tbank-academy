package academy.maze.renderer.impl;

import academy.maze.dto.CellType;

/**
 * ASCII рендерер лабиринта. Использует простые ASCII символы для отображения: - '#' для стен - ' ' для путей - '.' для
 * найденного маршрута - 'O' для начальной точки - 'X' для конечной точки
 */
public class AsciiRenderer extends AbstractMazeRenderer {
    private static final char WALL = '#';
    private static final char PATH = ' ';

    private static final char SWAMP = '~';
    private static final char SAND = '*';
    private static final char COIN = '$';

    private static final char ROUTE = '.';

    @Override
    protected char getRouteChar() {
        return ROUTE;
    }

    @Override
    protected char getCellChar(CellType[][] cells, int x, int y) {
        return getCellCharByType(cells[y][x]);
    }

    /**
     * Возвращает символ для отображения типа ячейки.
     *
     * @param cellType тип ячейки
     * @return символ для отображения
     */
    private char getCellCharByType(CellType cellType) {
        return switch (cellType) {
            case WALL -> WALL;
            case PATH -> PATH;
            case SWAMP -> SWAMP;
            case SAND -> SAND;
            case COIN -> COIN;
        };
    }
}
