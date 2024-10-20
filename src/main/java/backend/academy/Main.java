package backend.academy;

import backend.academy.generators.Generator;
import backend.academy.generators.KruskalGenerator;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {

        final Generator generator = new KruskalGenerator();
        Maze maze = generator.generate(200, 200);
        Renderer renderer = new MazeRenderer();
        System.out.println(renderer.render(maze));
    }
}
