package backend.academy.generators;

import backend.academy.Cell;
import backend.academy.Maze;
import org.apache.commons.math3.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrimGenerator implements Generator {
    @Override
    public Maze generate(int height, int width) {

        Cell[][] grid = new Cell[height * 2 + 1][width * 2 + 1];
        for (int row = 0; row < height * 2 + 1; row++) {
            for (int col = 0; col < width * 2 + 1; col++) {
                if (row % 2 == 1 && col % 2 == 1) {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                } else {
                    grid[row][col] = new Cell(row, col, Cell.Type.WALL);
                }
            }
        }

        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        // Начальная вершина
        visited.add(new Pair<>(1, 1));

        // Создание списка ребер
        List<Pair<Integer, Integer>> edges = new ArrayList<>();
        for (int row = 1; row < height * 2; row += 2) {
            for (int col = 1; col < width * 2; col += 2) {
                if (row + 1 < height * 2) {
                    edges.add(new Pair<>(row + 1, col));
                }
                if (col + 1 < width * 2) {
                    edges.add(new Pair<>(row, col + 1));
                }
            }
        }

        Collections.shuffle(edges, new Random());

        // Алгоритм Прима
        while (visited.size() < height * width) {
            // Найти ребро с минимальным весом, соединяющее посещенную вершину с непосещенной
            Pair<Integer, Integer> minEdge = null;
            for (Pair<Integer, Integer> edge : edges) {
                int row = edge.getFirst();
                int col = edge.getSecond();
                if (row % 2 == 0) {
                    if (visited.contains(new Pair<>((row - 1) / 2, col / 2)) != visited.contains(new Pair<>((row + 1) / 2, col / 2))) {
                        if (minEdge == null || compareEdges(edge, minEdge, grid, visited)) {
                            minEdge = edge;
                        }
                    }
                } else {
                    if (visited.contains(new Pair<>(row / 2, (col - 1) / 2)) != visited.contains(new Pair<>(row / 2, (col + 1) / 2))) {
                        if (minEdge == null || compareEdges(edge, minEdge, grid, visited)) {
                            minEdge = edge;
                        }
                    }
                }
            }

            // Добавить новое ребро в лабиринт
            if (minEdge != null) {
                int row = minEdge.getFirst();
                int col = minEdge.getSecond();
                if (row % 2 == 0) {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                    visited.add(new Pair<>((row + 1) / 2, col / 2));
                    visited.add(new Pair<>((row - 1) / 2, col / 2));
                } else {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                    visited.add(new Pair<>(row / 2, (col + 1) / 2));
                    visited.add(new Pair<>(row / 2, (col - 1) / 2));
                }
                edges.remove(minEdge);
            } else {
                // Если нет ребер, соединяющих посещенные и непосещенные вершины, то лабиринт сгенерирован
                break;
            }
        }

        return new Maze(height, width, grid);
    }

    private boolean compareEdges(Pair<Integer, Integer> edge1, Pair<Integer, Integer> edge2, Cell[][] grid, Set<Pair<Integer, Integer>> visited) {
        int row1 = edge1.getFirst();
        int col1 = edge1.getSecond();
        int row2 = edge2.getFirst();
        int col2 = edge2.getSecond();

        int neighbors1;
        int neighbors2;
        // Выбор ребра с наименьшим количеством соседей у посещенных вершин
        if (row1 % 2 == 0) {
            if (visited.contains(new Pair<>((row1 - 1) / 2, col1 / 2))) {
                neighbors1 = countNeighbors(grid[row1 - 1][col1], grid, visited);
            } else {
                neighbors1 = countNeighbors(grid[row1 + 1][col1], grid, visited);
            }
        } else {
            if (visited.contains(new Pair<>(row1 / 2, (col1 - 1) / 2))) {
                neighbors1 = countNeighbors(grid[row1][col1 - 1], grid, visited);
            } else {
                neighbors1 = countNeighbors(grid[row1][col1 + 1], grid, visited);
            }
        }
        if (row2 % 2 == 0) {
            if (visited.contains(new Pair<>((row2 - 1) / 2, col2 / 2))) {
                neighbors2 = countNeighbors(grid[row2 - 1][col2], grid, visited);
            } else {
                neighbors2 = countNeighbors(grid[row2 + 1][col2], grid, visited);
            }
        } else {
            if (visited.contains(new Pair<>(row2 / 2, (col2 - 1) / 2))) {
                neighbors2 = countNeighbors(grid[row2][col2 - 1], grid, visited);
            } else {
                neighbors2 = countNeighbors(grid[row2][col2 + 1], grid, visited);
            }
        }
        return neighbors1 < neighbors2;
    }

    private int countNeighbors(Cell cell, Cell[][] grid, Set<Pair<Integer, Integer>> visited) {
        int count = 0;
        if (cell.row() + 2 < grid.length - 1 && visited.contains(new Pair<>((cell.row() + 2) / 2, cell.col() / 2))) {
            count++;
        }
        if (cell.row() - 2 > 0 && visited.contains(new Pair<>((cell.row() - 2) / 2, cell.col() / 2))) {
            count++;
        }
        if (cell.col() + 2 < grid[0].length - 1 && visited.contains(new Pair<>(cell.row() / 2, (cell.col() + 2) / 2))) {
            count++;
        }
        if (cell.col() - 2 > 0 && visited.contains(new Pair<>(cell.row() / 2, (cell.col() - 2) / 2))) {
            count++;
        }
        return count;
    }
}
