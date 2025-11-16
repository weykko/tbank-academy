package academy;

import academy.maze.command.GenerateMazeCommand;
import academy.maze.command.SolveMazeCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Точка входа в приложение для работы с лабиринтами.
 */
@Command(
    name = "maze-app",
    description = "Maze generator and solver CLI application.",
    mixinStandardHelpOptions = true,
    subcommands = {GenerateMazeCommand.class, SolveMazeCommand.class}
)
public class Application implements Runnable {

    /** Главный метод приложения. */
    public static void main(String[] args) {
        System.setProperty("line.separator", "\n");

        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }
}
