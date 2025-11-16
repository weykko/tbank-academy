package academy.maze.command;

import academy.maze.generator.Generator;
import academy.maze.dto.Maze;
import academy.maze.generator.GeneratorFactory;
import academy.maze.generator.SurfaceDecorator;
import academy.maze.io.MazeFileHandler;
import academy.maze.renderer.MazeRenderer;
import academy.maze.renderer.RendererFactory;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Команда для генерации лабиринта.
 */
@Command(
    name = "generate",
    description = "Generate a maze with specified algorithm and dimensions."
)
public class GenerateMazeCommand implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateMazeCommand.class);

    @Option(
        names = {"-a", "--algorithm"},
        description = "Generation algorithm: dfs, prim",
        required = true)
    private String algorithm;

    @Option(
        names = {"-w", "--width"},
        description = "Maze width",
        required = true)
    private int width;

    @Option(
        names = {"-h", "--height"},
        description = "Maze height",
        required = true)
    private int height;

    @Option(
        names = {"-o", "--output"},
        description = "Output file path")
    private String outputPath;

    @Option(
        names = {"-r", "--renderer"},
        description = "Renderer type: ascii, unicode",
        defaultValue = "ascii")
    private String rendererType;

    @Option(
        names = {"-s", "--surfaces"},
        description = "Enable special surfaces (swamp, sand, coin) in generated maze")
    private boolean enableSurfaces;

    @Override
    public void run() {
        try {
            GeneratorFactory generatorFactory = new GeneratorFactory();
            Generator generator = generatorFactory.createGenerator(algorithm);
            LOGGER.info("Generating maze with algorithm: {}, width: {}, height: {}", algorithm, width, height);
            Maze maze = generator.generate(width, height);

            if (enableSurfaces) {
                SurfaceDecorator decorator = new SurfaceDecorator();
                maze = decorator.addSurfaces(maze);
                LOGGER.info("Added special surfaces to the maze");
            }

            RendererFactory rendererFactory = new RendererFactory();
            MazeRenderer renderer = rendererFactory.createRenderer(rendererType);
            String mazeString = renderer.render(maze);

            if (outputPath != null) {
                MazeFileHandler fileHandler = new MazeFileHandler();
                fileHandler.write(Paths.get(outputPath), mazeString);
                System.out.printf("Maze saved to: %s", outputPath);
            } else {
                System.out.print(mazeString);
            }

        } catch (Exception e) {
            LOGGER.error("Error generating maze", e);
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
