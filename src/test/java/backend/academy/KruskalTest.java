package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.generators.KruskalGenerator;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KruskalTest {

    @Test
    void generate() {
        final Generator generator = new KruskalGenerator();
        Maze maze = generator.generate(10, 10);
        //assertThat(maze).isEqualTo(new Maze(1, 1, new Cell[][]{}));
    }
}
