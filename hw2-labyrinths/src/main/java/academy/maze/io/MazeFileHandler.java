package academy.maze.io;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Обработчик файлов лабиринтов. Отвечает за чтение и запись лабиринтов в текстовом формате. */
public class MazeFileHandler {
    private static final char WALL_CHAR = '#';
    private static final String UNICODE_WALL_CHARS = "═║╔╗╚╝╦╩╠╣╬";

    private static final char SWAMP = '~';
    private static final char SAND = '*';
    private static final char COIN = '$';

    private boolean enableSurfaces = false;

    /**
     * Читает лабиринт из файла.
     *
     * @param filePath путь к файлу
     * @return прочитанный лабиринт
     * @throws IOException если произошла ошибка чтения
     */
    public Maze read(Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        if (lines.isEmpty()) {
            throw new IOException("File is empty: " + filePath);
        }

        int height = lines.size();
        int width = lines.stream().mapToInt(String::length).max().orElse(0);

        CellType[][] cells = new CellType[height][width];

        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    char ch = line.charAt(x);
                    cells[y][x] = charToCellType(ch);
                } else {
                    cells[y][x] = CellType.WALL;
                }
            }
        }

        return new Maze(cells);
    }

    /**
     * Преобразует символ в тип ячейки.
     *
     * @param ch символ
     * @return тип ячейки
     */
    private CellType charToCellType(char ch) {
        if (isWallChar(ch)) {
            return CellType.WALL;
        }

        if (enableSurfaces) {
            return switch (ch) {
                case SWAMP -> CellType.SWAMP;
                case SAND -> CellType.SAND;
                case COIN -> CellType.COIN;
                default -> CellType.PATH;
            };
        }

        return CellType.PATH;
    }

    /**
     * Проверяет, является ли символ стеной (ASCII или Unicode).
     *
     * @param ch проверяемый символ
     * @return true, если символ является стеной
     */
    private boolean isWallChar(char ch) {
        return ch == WALL_CHAR || UNICODE_WALL_CHARS.indexOf(ch) >= 0;
    }

    /**
     * Записывает содержимое в файл.
     *
     * @param filePath путь к файлу
     * @param content содержимое для записи
     * @throws IOException если произошла ошибка записи
     */
    public void write(Path filePath, String content) throws IOException {
        Path parent = filePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(content);
        }
    }

    /**
     * Включает поддержку специальных поверхностей (болото, песок, монеты).
     *
     * @param enable true для включения, false для отключения
     */
    public void setEnableSurfaces(boolean enable) {
        this.enableSurfaces = enable;
    }
}
