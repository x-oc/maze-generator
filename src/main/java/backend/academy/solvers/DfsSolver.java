package backend.academy.solvers;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.*;

public class DfsSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {

        start = new Coordinate(start.row() * 2 - 1, start.col() * 2 - 1);
        end = new Coordinate(end.row() * 2 - 1, end.col() * 2 - 1);

        if (maze.grid()[start.row()][start.col()].type() == Cell.Type.WALL ||
            maze.grid()[end.row()][end.col()].type() == Cell.Type.WALL) {
            return Collections.emptyList();
        }

        boolean[][] visited = new boolean[maze.grid().length][maze.grid()[0].length];
        Map<Coordinate, Coordinate> parent = new HashMap<>();

        if (dfs(maze, start, end, visited, parent)) {
            return reconstructPath(parent, end);
        }

        return Collections.emptyList();
    }

    private boolean dfs(Maze maze, Coordinate current, Coordinate end, boolean[][] visited, Map<Coordinate, Coordinate> parent) {
        if (current.equals(end)) {
            return true;
        }

        visited[current.row()][current.col()] = true;

        List<Coordinate> neighbors = getNeighbors(maze, current);

        for (Coordinate neighbor : neighbors) {
            if (!visited[neighbor.row()][neighbor.col()] && maze.grid()[neighbor.row()][neighbor.col()].type() != Cell.Type.WALL) {
                parent.put(neighbor, current);
                if (dfs(maze, neighbor, end, visited, parent)) {
                    return true;
                }
            }
        }

        return false;
    }

    private List<Coordinate> getNeighbors(Maze maze, Coordinate current) {
        List<Coordinate> neighbors = new ArrayList<>();

        if (current.row() > 0) {
            neighbors.add(new Coordinate(current.row() - 1, current.col()));
        }
        if (current.row() < maze.grid().length - 1) {
            neighbors.add(new Coordinate(current.row() + 1, current.col()));
        }
        if (current.col() > 0) {
            neighbors.add(new Coordinate(current.row(), current.col() - 1));
        }
        if (current.col() < maze.grid()[0].length - 1) {
            neighbors.add(new Coordinate(current.row(), current.col() + 1));
        }

        return neighbors;
    }

    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> parent, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;

        while (parent.containsKey(current)) {
            path.add(current);
            current = parent.get(current);
        }

        path.add(current);
        Collections.reverse(path);

        return path;
    }
}
