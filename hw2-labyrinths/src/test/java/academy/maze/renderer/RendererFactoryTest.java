package academy.maze.renderer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class RendererFactoryTest {

    @Test
    void createRenderer_shouldReturnAsciiRendererForAscii() {
        RendererFactory factory = new RendererFactory();

        MazeRenderer renderer = factory.createRenderer("ascii");

        assertThat(renderer).isNotNull();
        assertThat(renderer.getClass().getSimpleName()).isEqualTo("AsciiRenderer");
    }

    @Test
    void createRenderer_shouldReturnUnicodeRendererForUnicode() {
        RendererFactory factory = new RendererFactory();

        MazeRenderer renderer = factory.createRenderer("unicode");

        assertThat(renderer).isNotNull();
        assertThat(renderer.getClass().getSimpleName()).isEqualTo("UnicodeRenderer");
    }

    @Test
    void createRenderer_shouldReturnAsciiRendererForNull() {
        RendererFactory factory = new RendererFactory();

        MazeRenderer renderer = factory.createRenderer(null);

        assertThat(renderer).isNotNull();
        assertThat(renderer.getClass().getSimpleName()).isEqualTo("AsciiRenderer");
    }

    @Test
    void createRenderer_shouldBeCaseInsensitive() {
        RendererFactory factory = new RendererFactory();

        MazeRenderer ascii1 = factory.createRenderer("ASCII");
        MazeRenderer ascii2 = factory.createRenderer("Ascii");
        MazeRenderer unicode1 = factory.createRenderer("UNICODE");
        MazeRenderer unicode2 = factory.createRenderer("Unicode");

        assertThat(ascii1).isNotNull();
        assertThat(ascii2).isNotNull();
        assertThat(unicode1).isNotNull();
        assertThat(unicode2).isNotNull();
    }

    @Test
    void createRenderer_shouldThrowExceptionForUnknownType() {
        RendererFactory factory = new RendererFactory();

        assertThatThrownBy(() -> factory.createRenderer("html"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown");
    }
}
