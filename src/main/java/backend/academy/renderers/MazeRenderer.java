package backend.academy.renderers;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.List;

public class MazeRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.height() * 2 + 1; row++) {
            for (int col = 0; col < maze.width() * 2 + 1; col++) {
                Cell.Type type = maze.grid()[row][col].type();
                sb.append(type.display());
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.height() * 2 + 1; row++) {
            for (int col = 0; col < maze.width() * 2 + 1; col++) {
                Cell.Type type = maze.grid()[row][col].type();
                if (path.contains(new Coordinate(row, col))) {
                    sb.append('.'); // Используем '.' для обозначения пути
                } else {
                    sb.append(type.display());
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
