package backend.academy.generators;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenerator implements MazeGenerator {

    protected Cell[][] createEmptyGrid(int height, int width) {
        Cell[][] grid = new Cell[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if ((row & 1) == 1 && (col & 1) == 1) {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                } else {
                    grid[row][col] = new Cell(row, col, Cell.Type.WALL);
                }
            }
        }
        return  grid;
    }

    // Создание списка ребер
    protected List<Coordinate> getEdges(int height, int width) {
        List<Coordinate> edges = new ArrayList<>();
        for (int row = 1; row < height * 2; row += 2) {
            for (int col = 1; col < width * 2; col += 2) {
                if (row + 1 < height * 2) {
                    edges.add(new Coordinate(row + 1, col));
                }
                if (col + 1 < width * 2) {
                    edges.add(new Coordinate(row, col + 1));
                }
            }
        }
        return edges;
    }

}
