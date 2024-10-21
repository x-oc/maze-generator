package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.solvers.Solver;
import java.util.List;

public class GameManager {

    private final UserInteraction userInteraction;
    private final static int MAX_HEIGHT = 100;
    private final static int MAX_WIDTH = 100;

    public GameManager() {
        userInteraction = new UserInteraction();
    }

    public void start() {

        userInteraction.greet();

        int height = userInteraction.getInt("высота", 1, MAX_HEIGHT);
        int width = userInteraction.getInt("ширина", 1, MAX_WIDTH);

        int startX = userInteraction.getInt("координата x начала лабиринта", 1, width);
        int startY = userInteraction.getInt("координата y начала лабиринта", 1, height);
        int finishX = userInteraction.getInt("координата x конца лабиринта", 1, width);
        int finishY = userInteraction.getInt("координата y конца лабиринта", 1, height);
        Coordinate start = new Coordinate(startY, startX);
        Coordinate finish = new Coordinate(finishY, finishX);

        Generator generator = userInteraction.getGenerator();
        Maze maze = generator.generate(height, width);
        Cell[][] grid = maze.grid();
        grid[startY * 2 - 1][startX * 2 - 1] = new Cell(startY * 2 - 1, startX * 2 - 1, Cell.Type.START);
        grid[finishY * 2 - 1][finishX * 2 - 1] = new Cell(finishY * 2 - 1, finishX * 2 - 1, Cell.Type.FINISH);
        maze.grid(grid);

        userInteraction.showMaze(maze);

        Solver solver = userInteraction.getSolver();
        List<Coordinate> path = solver.solve(maze, start, finish);

        userInteraction.showMaze(maze, path);

    }

}
