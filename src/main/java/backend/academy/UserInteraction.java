package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.generators.KruskalGenerator;
import backend.academy.generators.PrimGenerator;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.renderers.MazeRenderer;
import backend.academy.renderers.Renderer;
import backend.academy.solvers.BfsSolver;
import backend.academy.solvers.DfsSolver;
import backend.academy.solvers.Solver;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInteraction {

    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private final PrintStream printStream = new PrintStream(System.out, false, StandardCharsets.UTF_8);
    private final Renderer renderer = new MazeRenderer();

    public void println(String message) {
        printStream.println(message);
    }

    public String requestParameter(String message) {
        printStream.println(message);
        return scanner.nextLine();
    }

    public void showMaze(Maze maze) {
        println("Ваш лабиринт:\n");
        println(renderer.render(maze));
    }

    public void showMaze(Maze maze, List<Coordinate> path) {
        println("Найденный путь:\n");
        println(renderer.render(maze, path));
    }

    public void greet() {
        println("Привет! Это программа для построения лабиринтов и нахождения пути в них. "
            + "Вам нужно задать размеры лабиринта и алгоритмы для его построения и нахождения пути.");
    }

    public int getInt(String name, int min, int max) {
        String intPrompt = "Укажите значение \"%s\" (от %d до %d): ";
        String intPromptError = "Некорректное значение. Укажите значение \"%s\" (от %d до %d): ";
        String userInput = requestParameter(String.format(intPrompt, name, min, max));
        while (userInput == null || !Pattern.compile("^[1-9]\\d*$").matcher(userInput).matches()
            || Integer.parseInt(userInput) < min || Integer.parseInt(userInput) > max) {
            userInput = requestParameter(String.format(intPromptError, name, min, max));
        }
        return Integer.parseInt(userInput);
    }

    public String getAlgorithm(String algorithm, List<String> values) {
        String algorithmPrompt = "Укажите алгоритм для %s (%s): ";
        String algorithmPromptError = "Некорректное значение. Укажите алгоритм для %s (%s): ";
        String available = String.join(", ", values);
        String userInput = requestParameter(String.format(algorithmPrompt, algorithm, available));
        while (!values.contains(userInput)) {
            userInput = requestParameter(String.format(algorithmPromptError, algorithm, available));
        }
        return userInput;
    }

    public Generator getGenerator() {
        final String Kruskal = "Kruskal";
        final String Prim = "Prim";
        List<String> values = Arrays.asList(Kruskal, Prim);
        String algorithm = getAlgorithm("построения лабиринта", values);
        return switch (algorithm) {
            case Kruskal -> new KruskalGenerator();
            case Prim -> new PrimGenerator();
            // ... другие классы генераторов
            default -> null;
        };
    }

    public Solver getSolver() {
        final String Bfs = "Bfs";
        final String Dfs = "Dfs";
        List<String> values = Arrays.asList(Bfs, Dfs);
        String algorithm = getAlgorithm("нахождения пути в лабиринте", values);
        return switch (algorithm) {
            case Bfs -> new BfsSolver();
            case Dfs -> new DfsSolver();
            default -> null;
        };
    }
}
