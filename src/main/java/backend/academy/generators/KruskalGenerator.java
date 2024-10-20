package backend.academy.generators;

import backend.academy.Cell;
import backend.academy.Maze;
import org.apache.commons.math3.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;

public class KruskalGenerator implements Generator {

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

        Integer[][] ids = new Integer[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                ids[row][col] = (Math.max(height, width) * 2 + 1) * row + col;
            }
        }

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

        // Алгоритм Краскала
        for (Pair<Integer, Integer> edge : edges) {

            int row = edge.getFirst();
            int col = edge.getSecond();
            if (row % 2 == 0) {
                if (!Objects.equals(ids[(row - 1) / 2][col / 2], ids[(row + 1) / 2][col / 2])) {
                    join(row - 1, row + 1, col, col, grid, ids);
                }
            } else {
                if (!Objects.equals(ids[row / 2][(col - 1) / 2], ids[row / 2][(col + 1) / 2])) {
                    join(row, row, col - 1, col + 1, grid, ids);
                }
            }
        }

        return new Maze(height, width, grid);
    }

    private void join(int row1, int row2, int col1, int col2, Cell[][] grid, Integer[][] ids) {
        Integer targetId = ids[row2 / 2][col2 / 2];
        Integer newId = ids[row1 / 2][col1 / 2];

        Queue<Cell> queue = new LinkedList<>(Collections.singletonList(
            grid[(row1 + row2) / 2][(col1 + col2) / 2]));
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            grid[cell.row()][cell.col()] = new Cell(cell.row(), cell.col(), Cell.Type.PASSAGE);
            if (cell.row() % 2 == 0) {
                if (Objects.equals(ids[(cell.row() + 1) / 2][cell.col() / 2], targetId)) {
                    ids[(cell.row() + 1) / 2][cell.col() / 2] = newId;
                    queue.addAll(getNeighbors(grid[cell.row() + 1][cell.col()], targetId, grid, ids));
                } else {
                    ids[(cell.row() - 1) / 2][cell.col() / 2] = newId;
                    queue.addAll(getNeighbors(grid[cell.row() - 1][cell.col()], targetId, grid, ids));
                }
            } else {
                if (Objects.equals(ids[cell.row() / 2][(cell.col() + 1) / 2], targetId)) {
                    ids[cell.row() / 2][(cell.col() + 1) / 2] = newId;
                    queue.addAll(getNeighbors(grid[cell.row()][cell.col() + 1], targetId, grid, ids));
                } else {
                    ids[cell.row() / 2][(cell.col() - 1) / 2] = newId;
                    queue.addAll(getNeighbors(grid[cell.row()][cell.col() - 1], targetId, grid, ids));
                }
            }
        }
    }

    private List<Cell> getNeighbors(Cell cell, int targetId, Cell[][] grid, Integer[][] ids) {
        List<Cell> neighbors = new ArrayList<>();

        for (Pair<Integer, Integer> i : new Pair[] {new Pair<>(cell.row() + 2, cell.col()),
                                                    new Pair<>(cell.row() - 2, cell.col()),
                                                    new Pair<>(cell.row(), cell.col() + 2),
                                                    new Pair<>(cell.row(), cell.col() - 2)}) {
            if (i.getFirst() > 0 && i.getSecond() > 0 &&
                    i.getFirst() < grid.length - 1 && i.getSecond() < grid[0].length - 1 &&
                    Objects.equals(ids[i.getFirst() / 2][i.getSecond() / 2], targetId) &&
                    grid[(i.getFirst() + cell.row()) / 2][(i.getSecond() + cell.col()) / 2].type() == Cell.Type.PASSAGE) {
                neighbors.add(grid[(i.getFirst() + cell.row()) / 2][(i.getSecond() + cell.col()) / 2]);
            }
        }

        return neighbors;
    }
}
