package academy.maze.renderer.impl;

import academy.maze.dto.CellType;

/**
 * Unicode рендерер лабиринта. Использует Unicode символы для красивого отображения лабиринта с правильными углами и
 * соединениями.
 */
public class UnicodeRenderer extends AbstractMazeRenderer {
    private static final char ROUTE = '·';

    private static final char H_WALL = '═';
    private static final char V_WALL = '║';
    private static final char TL_CORNER = '╔';
    private static final char TR_CORNER = '╗';
    private static final char BL_CORNER = '╚';
    private static final char BR_CORNER = '╝';
    private static final char T_UP = '╩';
    private static final char T_DOWN = '╦';
    private static final char T_LEFT = '╣';
    private static final char T_RIGHT = '╠';
    private static final char CROSS = '╬';
    private static final char EMPTY = ' ';

    private static final char SWAMP = '~';
    private static final char SAND = '*';
    private static final char COIN = '$';

    @Override
    protected char getRouteChar() {
        return ROUTE;
    }

    @Override
    protected char getCellChar(CellType[][] cells, int x, int y) {
        if (cells[y][x] == CellType.WALL) {
            return getWallChar(cells, x, y);
        }
        return getCellCharByType(cells[y][x]);
    }

    /**
     * Возвращает символ для отображения типа ячейки (не стены).
     *
     * @param cellType тип ячейки
     * @return символ для отображения
     */
    private char getCellCharByType(CellType cellType) {
        return switch (cellType) {
            case PATH, WALL -> EMPTY;
            case SWAMP -> SWAMP;
            case SAND -> SAND;
            case COIN -> COIN;
        };
    }

    /**
     * Определяет символ стены в зависимости от соседних клеток.
     *
     * @param cells массив клеток лабиринта
     * @param x координата x
     * @param y координата y
     * @return символ для отрисовки стены
     */
    private char getWallChar(CellType[][] cells, int x, int y) {
        boolean top = hasWall(cells, x, y - 1);
        boolean bottom = hasWall(cells, x, y + 1);
        boolean left = hasWall(cells, x - 1, y);
        boolean right = hasWall(cells, x + 1, y);

        if (top && bottom && left && right) {
            return CROSS;
        } else if (top && bottom && left) {
            return T_LEFT;
        } else if (top && bottom && right) {
            return T_RIGHT;
        } else if (top && left && right) {
            return T_UP;
        } else if (bottom && left && right) {
            return T_DOWN;
        } else if (top && right) {
            return BL_CORNER;
        } else if (top && left) {
            return BR_CORNER;
        } else if (bottom && right) {
            return TL_CORNER;
        } else if (bottom && left) {
            return TR_CORNER;
        } else if (left || right) {
            return H_WALL;
        } else if (top || bottom) {
            return V_WALL;
        }

        return H_WALL;
    }

    /**
     * Проверяет, является ли клетка стеной.
     *
     * @param cells массив клеток лабиринта
     * @param x координата x
     * @param y координата y
     * @return true, если клетка является стеной или находится за границами
     */
    private boolean hasWall(CellType[][] cells, int x, int y) {
        if (y < 0 || y >= cells.length || x < 0 || x >= cells[0].length) {
            return false;
        }
        return cells[y][x] == CellType.WALL;
    }
}
