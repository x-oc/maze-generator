package backend.academy;

import java.util.List;

public class MazeRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.grid().length; row++) {
            for (int col = 0; col < maze.grid()[0].length; col++) {
                Cell.Type type = maze.grid()[row][col].type();
                if (type == Cell.Type.WALL) sb.append("#");
                else if (type == Cell.Type.PASSAGE) sb.append(" ");
                else if (type == Cell.Type.COIN) sb.append("o");
                else if (type == Cell.Type.SAND) sb.append("~");
                else if (type == Cell.Type.START) sb.append("S");
                else if (type == Cell.Type.FINISH) sb.append("F");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.grid().length; row++) {
            for (int col = 0; col < maze.grid()[0].length; col++) {
                Cell.Type type = maze.grid()[row][col].type();
                if (path.contains(new Coordinate(row, col))) sb.append("+"); // Используем "+" для обозначения пути
                else if (type == Cell.Type.WALL) sb.append("#");
                else if (type == Cell.Type.PASSAGE) sb.append(" ");
                else if (type == Cell.Type.COIN) sb.append("o");
                else if (type == Cell.Type.SAND) sb.append("~");
                else if (type == Cell.Type.START) sb.append("S");
                else if (type == Cell.Type.FINISH) sb.append("F");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
