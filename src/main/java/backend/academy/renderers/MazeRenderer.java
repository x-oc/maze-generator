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
                sb.append(getChar(type));
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
                    sb.append(getChar(type));
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private char getChar(Cell.Type type) {
        char ans = '-';
        if (type == Cell.Type.WALL) {
            ans = '#';
        } else if (type == Cell.Type.PASSAGE) {
            ans = ' ';
        } else if (type == Cell.Type.COIN) {
            ans = 'o';
        } else if (type == Cell.Type.SAND) {
            ans = '~';
        } else if (type == Cell.Type.START) {
            ans = 'S';
        } else if (type == Cell.Type.FINISH) {
            ans = 'F';
        }
        return ans;
    }
}
