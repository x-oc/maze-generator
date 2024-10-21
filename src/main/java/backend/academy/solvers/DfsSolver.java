package backend.academy.solvers;

import backend.academy.models.Cell;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DfsSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate startArg, Coordinate endArg) {

        Coordinate start = transformCoordinate(startArg);
        Coordinate end = transformCoordinate(endArg);

        boolean[][] visited = new boolean[maze.grid().length][maze.grid()[0].length];
        Map<Coordinate, Coordinate> parent = new HashMap<>();

        if (dfs(maze, start, end, visited, parent)) {
            return reconstructPath(parent, end);
        }

        return new ArrayList<>();
    }

    private Coordinate transformCoordinate(Coordinate coordinate) {
        return new Coordinate(coordinate.row() * 2 - 1, coordinate.col() * 2 - 1);
    }

    private boolean dfs(Maze maze, Coordinate current, Coordinate end, boolean[][] visited,
                        Map<Coordinate, Coordinate> parent) {
        if (current.equals(end)) {
            return true;
        }

        visited[current.row()][current.col()] = true;

        List<Coordinate> neighbors = getNeighbors(maze, current);

        for (Coordinate neighbor : neighbors) {
            if (!visited[neighbor.row()][neighbor.col()]
                && maze.grid()[neighbor.row()][neighbor.col()].type() != Cell.Type.WALL) {
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

    @SuppressWarnings("PSC_PRESIZE_COLLECTIONS")
    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> parent, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;

        while (parent.get(current) != null) {
            path.add(current);
            current = parent.get(current);
        }

        path.add(current);
        Collections.reverse(path);

        return path;
    }
}
