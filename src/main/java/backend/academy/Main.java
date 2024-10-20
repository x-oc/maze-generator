package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.generators.PrimGenerator;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {

        final Generator generator = new PrimGenerator();
        Maze maze = generator.generate(20, 100);
        Renderer renderer = new MazeRenderer();
        System.out.println(renderer.render(maze));
    }
}
