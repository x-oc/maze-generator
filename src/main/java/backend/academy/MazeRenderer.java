package backend.academy;

import java.util.List;

public class MazeRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.height() * 2 + 1; row++) {
            for (int col = 0; col < maze.width() * 2 + 1; col++) {
                sb.append(maze.grid()[row][col].type() == Cell.Type.WALL ? "#" : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                if (path.contains(new Coordinate(row, col))) {
                    sb.append("O"); // Используем "O" для обозначения пути
                } else {
                    sb.append(maze.grid()[row][col].type() == Cell.Type.WALL ? "#" : " ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
