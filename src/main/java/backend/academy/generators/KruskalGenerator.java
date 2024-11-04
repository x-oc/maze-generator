package backend.academy.generators;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class KruskalGenerator extends AbstractGenerator {

    @Override
    public Maze generate(int height, int width) {

        Cell[][] grid = createEmptyGrid(height * 2 + 1, width * 2 + 1);

        int[][] ids = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                ids[row][col] = (Math.max(height, width) * 2 + 1) * row + col;
            }
        }

        List<Coordinate> edges = getEdges(height, width);
        Collections.shuffle(edges, new SecureRandom());

        // Алгоритм Краскала
        for (Coordinate edge : edges) {

            int row = edge.row();
            int col = edge.col();
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

    private void join(int rowUpper, int rowLower, int colLeft, int colRight, Cell[][] grid, int[][] ids) {
        int targetId = ids[rowLower / 2][colRight / 2];
        int newId = ids[rowUpper / 2][colLeft / 2];

        Queue<Cell> queue = new LinkedList<>(Collections.singletonList(
            grid[(rowUpper + rowLower) >>> 1][(colLeft + colRight) >>> 1]));
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

    private List<Cell> getNeighbors(Cell cell, int targetId, Cell[][] grid, int[][] ids) {
        List<Cell> rightNeighbors = new ArrayList<>();

        Coordinate[] neighbors = new Coordinate[]  {new Coordinate(cell.row() + 2, cell.col()),
                                                    new Coordinate(cell.row() - 2, cell.col()),
                                                    new Coordinate(cell.row(), cell.col() + 2),
                                                    new Coordinate(cell.row(), cell.col() - 2)};

        for (Coordinate i : neighbors) {
            if (isEdgeInGrid(grid, i) && areIdsEqual(ids, i, targetId) && isTherePassage(grid, i, cell)) {
                rightNeighbors.add(grid[(i.row() + cell.row()) >>> 1][(i.col() + cell.col()) >>> 1]);
            }
        }

        return rightNeighbors;
    }

    private static boolean isEdgeInGrid(Cell[][] grid, Coordinate edge) {
        return edge.row() > 0 && edge.col() > 0 && edge.row() < grid.length - 1 && edge.col() < grid[0].length - 1;
    }

    private static boolean areIdsEqual(int[][] ids, Coordinate edge, int id) {
        return Objects.equals(ids[edge.row() / 2][edge.col() / 2], id);
    }

    private static boolean isTherePassage(Cell[][] grid, Coordinate edge, Cell cell) {
        return grid[(edge.row() + cell.row()) >>> 1][(edge.col() + cell.col()) >>> 1].type() != Cell.Type.WALL;
    }
}
