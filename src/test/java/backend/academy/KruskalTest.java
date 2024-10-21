package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.generators.KruskalGenerator;
import backend.academy.models.Cell;
import backend.academy.models.Maze;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KruskalTest {

    @Test
    void generate() {
        final Generator generator = new KruskalGenerator();
        Maze maze = generator.generate(1, 2);
        Maze correctMaze = new Maze(3, 5, new Cell[][]{new Cell[]{new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.WALL), new Cell(0, 2, Cell.Type.WALL), new Cell(0, 3, Cell.Type.WALL), new Cell(0, 4, Cell.Type.WALL)},
            new Cell[]{new Cell(1, 0, Cell.Type.WALL), new Cell(1, 1, Cell.Type.PASSAGE), new Cell(1, 2, Cell.Type.PASSAGE), new Cell(1, 3, Cell.Type.PASSAGE), new Cell(1, 4, Cell.Type.WALL)},
            new Cell[]{new Cell(2, 0, Cell.Type.WALL), new Cell(2, 1, Cell.Type.WALL), new Cell(2, 2, Cell.Type.WALL), new Cell(2, 3, Cell.Type.WALL), new Cell(2, 4, Cell.Type.WALL)},
        });
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                assertThat(maze.grid()[i][j]).isEqualTo(correctMaze.grid()[i][j]);
            }
        }
    }
}
