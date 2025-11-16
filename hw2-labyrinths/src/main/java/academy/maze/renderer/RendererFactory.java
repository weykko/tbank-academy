package academy.maze.renderer;

import academy.maze.renderer.impl.AsciiRenderer;
import academy.maze.renderer.impl.UnicodeRenderer;

/**
 * Фабрика для создания рендереров лабиринта.
 * Поддерживает создание различных типов рендереров на основе строкового идентификатора.
 */
public class RendererFactory {

    /**
     * Создаёт рендерер на основе типа.
     *
     * @param type тип рендерера: "ascii" или "unicode"
     * @return экземпляр рендерера
     * @throws IllegalArgumentException если тип рендерера неизвестен
     */
    public MazeRenderer createRenderer(String type) {
        if (type == null) {
            return new AsciiRenderer();
        }

        return switch (type.toLowerCase()) {
            case "ascii" -> new AsciiRenderer();
            case "unicode" -> new UnicodeRenderer();
            default -> throw new IllegalArgumentException(
                "Unknown renderer type: " + type + ". Supported types: ascii, unicode"
            );
        };
    }
}

