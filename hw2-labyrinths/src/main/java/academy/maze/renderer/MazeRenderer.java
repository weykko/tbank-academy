package academy.maze.renderer;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;

/**
 * Рендерер лабиринта - интерфейс для различных способов отображения.
 */
public interface MazeRenderer {
    /**
     * Отрисовывает лабиринт без пути.
     *
     * @param maze лабиринт для отрисовки
     * @return строковое представление лабиринта
     */
    String render(Maze maze);

    /**
     * Отрисовывает лабиринт с путём решения.
     *
     * @param maze лабиринт для отрисовки
     * @param path путь в лабиринте
     * @param start начальная точка
     * @param end конечная точка
     * @return строковое представление лабиринта с путём
     */
    String render(Maze maze, Path path, Point start, Point end);
}

