package backend.academy;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.solvers.DfsSolver;
import backend.academy.solvers.Solver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;

public class DfsTest {
    @Test
    void noWaySolve() {
        Maze maze = new Maze(
            5, 5, new Cell[][] {
            { new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.PASSAGE), new Cell(0, 2, Cell.Type.WALL), new Cell(0, 3, Cell.Type.PASSAGE), new Cell(0, 4, Cell.Type.WALL) },
            { new Cell(1, 0, Cell.Type.PASSAGE), new Cell(1, 1, Cell.Type.WALL), new Cell(1, 2, Cell.Type.PASSAGE), new Cell(1, 3, Cell.Type.WALL), new Cell(1, 4, Cell.Type.PASSAGE) },
            { new Cell(2, 0, Cell.Type.WALL), new Cell(2, 1, Cell.Type.PASSAGE), new Cell(2, 2, Cell.Type.WALL), new Cell(2, 3, Cell.Type.PASSAGE), new Cell(2, 4, Cell.Type.WALL) },
            { new Cell(3, 0, Cell.Type.PASSAGE), new Cell(3, 1, Cell.Type.WALL), new Cell(3, 2, Cell.Type.PASSAGE), new Cell(3, 3, Cell.Type.WALL), new Cell(3, 4, Cell.Type.PASSAGE) },
            { new Cell(4, 0, Cell.Type.WALL), new Cell(4, 1, Cell.Type.PASSAGE), new Cell(4, 2, Cell.Type.WALL), new Cell(4, 3, Cell.Type.PASSAGE), new Cell(4, 4, Cell.Type.WALL) }
        });
        Solver solver = new DfsSolver();
        List<Coordinate> solution = solver.solve(maze, new Coordinate(2, 1), new Coordinate(2, 3));
        List<Coordinate> correctSolution = Collections.emptyList();
        Assertions.assertThat(solution).isEqualTo(correctSolution);
    }
}
