package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.generators.KruskalGenerator;
import backend.academy.generators.PrimGenerator;
import backend.academy.solvers.DfsSolver;
import backend.academy.solvers.Solver;
import lombok.experimental.UtilityClass;
import java.util.List;

@UtilityClass
public class Main {
    public static void main(String[] args) {

        final Generator generator = new KruskalGenerator();
        Maze maze = generator.generate(20, 40);
        Renderer renderer = new MazeRenderer();
//        Solver solver = new DfsSolver();
//        List<Coordinate> solution = solver.solve(maze, new Coordinate(1 * 2 - 1, 1 * 2 - 1), new Coordinate(20 * 2 - 1, 40 * 2 - 1));
//        System.out.println(renderer.render(maze, solution));
    }
}
