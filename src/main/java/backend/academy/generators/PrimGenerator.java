package backend.academy.generators;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrimGenerator extends AbstractGenerator {
    @Override
    public Maze generate(int height, int width) {

        Cell[][] grid = createEmptyGrid(height * 2 + 1, width * 2 + 1);

        Set<Coordinate> visited = new HashSet<>();
        visited.add(new Coordinate(0, 0)); // Начальная вершина

        List<Coordinate> edges = getEdges(height, width);
        Collections.shuffle(edges, new SecureRandom());

        // Алгоритм Прима
        while (visited.size() < height * width) {
            Coordinate min = findMinWeightEdge(edges, visited, grid);
            if (min != null) {
                addNewEdge(min, grid, visited, edges);
            } else {
                // Если нет ребер, соединяющих посещенные и непосещенные вершины, то лабиринт сгенерирован
                break;
            }
        }

        return new Maze(height, width, grid);
    }

    // Найти ребро с минимальным весом, соединяющее посещенную вершину с непосещенной
    private Coordinate findMinWeightEdge(List<Coordinate> edges, Set<Coordinate> visited, Cell[][] grid) {
        Coordinate minEdge = null;
        for (Coordinate edge : edges) {
            int row = edge.row();
            int col = edge.col();
            if (row % 2 == 0) {
                if (visited.contains(new Coordinate((row - 1) / 2, col / 2))
                    != visited.contains(new Coordinate((row + 1) / 2, col / 2))) {
                    if (minEdge == null || compareEdges(edge, minEdge, grid, visited)) {
                        minEdge = edge;
                    }
                }
            } else {
                if (visited.contains(new Coordinate(row / 2, (col - 1) / 2))
                    != visited.contains(new Coordinate(row / 2, (col + 1) / 2))) {
                    if (minEdge == null || compareEdges(edge, minEdge, grid, visited)) {
                        minEdge = edge;
                    }
                }
            }
        }
        return minEdge;
    }

    // Добавить новое ребро в лабиринт
    private void addNewEdge(Coordinate edge, Cell[][] grid, Set<Coordinate> visited, List<Coordinate> edges) {
        int row = edge.row();
        int col = edge.col();
        if (row % 2 == 0) {
            grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
            visited.add(new Coordinate((row + 1) / 2, col / 2));
            visited.add(new Coordinate((row - 1) / 2, col / 2));
        } else {
            grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
            visited.add(new Coordinate(row / 2, (col + 1) / 2));
            visited.add(new Coordinate(row / 2, (col - 1) / 2));
        }
        edges.remove(edge);
    }

    private boolean compareEdges(Coordinate edge1, Coordinate edge2,
                                    Cell[][] grid, Set<Coordinate> visited) {
        int row1 = edge1.row();
        int col1 = edge1.col();
        int row2 = edge2.row();
        int col2 = edge2.col();

        // Выбор ребра с наименьшим количеством соседей у посещенных вершин
        int neighbors1 = getNeighbors(grid, visited, row1, col1);
        int neighbors2 = getNeighbors(grid, visited, row2, col2);
        return neighbors1 < neighbors2;
    }

    private int getNeighbors(Cell[][] grid, Set<Coordinate> visited, int row, int col) {
        int neighbors1;
        if (row % 2 == 0) {
            if (visited.contains(new Coordinate((row - 1) / 2, col / 2))) {
                neighbors1 = countNeighbors(grid[row - 1][col], grid, visited);
            } else {
                neighbors1 = countNeighbors(grid[row + 1][col], grid, visited);
            }
        } else {
            if (visited.contains(new Coordinate(row / 2, (col - 1) / 2))) {
                neighbors1 = countNeighbors(grid[row][col - 1], grid, visited);
            } else {
                neighbors1 = countNeighbors(grid[row][col + 1], grid, visited);
            }
        }
        return neighbors1;
    }

    private int countNeighbors(Cell cell, Cell[][] grid, Set<Coordinate> visited) {
        int count = 0;
        if (cell.row() + 2 < grid.length - 1 && visited.contains(
                new Coordinate((cell.row() + 2) / 2, cell.col() / 2))) {
            count++;
        }
        if (cell.row() - 2 > 0 && visited.contains(new Coordinate((cell.row() - 2) / 2, cell.col() / 2))) {
            count++;
        }
        if (cell.col() + 2 < grid[0].length - 1 && visited.contains(
                new Coordinate(cell.row() / 2, (cell.col() + 2) / 2))) {
            count++;
        }
        if (cell.col() - 2 > 0 && visited.contains(new Coordinate(cell.row() / 2, (cell.col() - 2) / 2))) {
            count++;
        }
        return count;
    }
}
