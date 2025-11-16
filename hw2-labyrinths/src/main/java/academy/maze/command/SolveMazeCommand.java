package academy.maze.command;

import academy.maze.solver.Solver;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.io.MazeFileHandler;
import academy.maze.renderer.MazeRenderer;
import academy.maze.renderer.RendererFactory;
import academy.maze.solver.SolverFactory;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Команда для решения лабиринта.
 */
@Command(
    name = "solve",
    description = "Solve a maze with specified algorithm and points."
)
public class SolveMazeCommand implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SolveMazeCommand.class);

    @Option(
        names = {"-a", "--algorithm"},
        description = "Solving algorithm: astar, dijkstra",
        required = true)
    private String algorithm;

    @Option(
        names = {"-f", "--file"},
        description = "Input maze file path",
        required = true)
    private String filePath;

    @Option(
        names = {"-s", "--start"},
        description = "Start point in format x,y",
        required = true)
    private String startPoint;

    @Option(
        names = {"-e", "--end"},
        description = "End point in format x,y",
        required = true)
    private String endPoint;

    @Option(
        names = {"-o", "--output"},
        description = "Output file path")
    private String outputPath;

    @Option(
        names = {"-r", "--renderer"},
        description = "Renderer type: ascii, unicode",
        defaultValue = "ascii")
    private String rendererType;

    @Override
    public void run() {
        try {
            MazeFileHandler fileHandler = new MazeFileHandler();
            fileHandler.setEnableSurfaces(true);
            Maze maze = fileHandler.read(Paths.get(filePath));
            Point start = parsePoint(startPoint);
            Point end = parsePoint(endPoint);
            SolverFactory solverFactory = new SolverFactory();
            Solver solver = solverFactory.createSolver(algorithm);

            LOGGER.info("Solving maze with algorithm: {}, start: {}, end: {}", algorithm, start, end);
            Path path = solver.solve(maze, start, end);

            if (path.points().length == 0) {
                System.err.println("No path found!");
                System.exit(1);
                return;
            }

            RendererFactory rendererFactory = new RendererFactory();
            MazeRenderer renderer = rendererFactory.createRenderer(rendererType);
            String mazeString = renderer.render(maze, path, start, end);

            if (outputPath != null) {
                fileHandler.write(Paths.get(outputPath), mazeString);
                System.out.printf("Solution saved to: %s", outputPath);
            } else {
                System.out.print(mazeString);
            }

        } catch (Exception e) {
            LOGGER.error("Error solving maze", e);
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private Point parsePoint(String pointStr) {
        String[] parts = pointStr.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid point format: " + pointStr + ". Expected format: x,y");
        }
        try {
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            return new Point(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid point coordinates: " + pointStr, e);
        }
    }
}
