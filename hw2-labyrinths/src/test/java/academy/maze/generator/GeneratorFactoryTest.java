package academy.maze.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class GeneratorFactoryTest {

    @Test
    void createGenerator_shouldReturnDfsGeneratorForDfs() {
        GeneratorFactory factory = new GeneratorFactory();

        Generator generator = factory.createGenerator("dfs");

        assertThat(generator).isNotNull();
        assertThat(generator.getClass().getSimpleName()).isEqualTo("DfsGenerator");
    }

    @Test
    void createGenerator_shouldReturnPrimGeneratorForPrim() {
        GeneratorFactory factory = new GeneratorFactory();

        Generator generator = factory.createGenerator("prim");

        assertThat(generator).isNotNull();
        assertThat(generator.getClass().getSimpleName()).isEqualTo("PrimGenerator");
    }

    @Test
    void createGenerator_shouldBeCaseInsensitive() {
        GeneratorFactory factory = new GeneratorFactory();

        Generator dfs1 = factory.createGenerator("DFS");
        Generator dfs2 = factory.createGenerator("Dfs");
        Generator prim1 = factory.createGenerator("PRIM");
        Generator prim2 = factory.createGenerator("Prim");

        assertThat(dfs1).isNotNull();
        assertThat(dfs2).isNotNull();
        assertThat(prim1).isNotNull();
        assertThat(prim2).isNotNull();
    }

    @Test
    void createGenerator_shouldThrowExceptionForUnknownType() {
        GeneratorFactory factory = new GeneratorFactory();

        assertThatThrownBy(() -> factory.createGenerator("unknown"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown");
    }
}

