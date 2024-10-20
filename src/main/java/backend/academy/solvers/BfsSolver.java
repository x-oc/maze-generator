package backend.academy.solvers;

import backend.academy.Cell;
import backend.academy.Coordinate;
import backend.academy.Maze;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BfsSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (maze.grid()[start.row()][start.col()].type() == Cell.Type.WALL ||
            maze.grid()[end.row()][end.col()].type() == Cell.Type.WALL) {
            return Collections.emptyList();
        }

        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);

        // Предшествующие вершины
        Coordinate[][] path = new Coordinate[maze.grid().length][maze.grid()[0].length];
        // Посещённые вершины
        boolean[][] visited = new boolean[maze.grid().length][maze.grid()[0].length];
        visited[start.row()][start.col()] = true;

        // Поиск в ширину
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            // Проверка на достижение конечной вершины
            if (current.row() == end.row() && current.col() == end.col()) {
                return reconstructPath(path, end);
            }

            // Поиск соседей
            List<Coordinate> neighbors = getNeighbors(current, maze);
            for (Coordinate neighbor : neighbors) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    queue.add(neighbor);
                    visited[neighbor.row()][neighbor.col()] = true;
                    path[neighbor.row()][neighbor.col()] = current;
                }
            }
        }

        // Если конечная вершина не найдена, возвращается пустой список
        return new ArrayList<>();
    }

    private List<Coordinate> getNeighbors(Coordinate coordinate, Maze maze) {
        List<Coordinate> neighbors = new ArrayList<>();

        if (coordinate.row() > 0 && maze.grid()[coordinate.row() - 1][coordinate.col()].type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(coordinate.row() - 1, coordinate.col()));
        }
        if (coordinate.row() < maze.grid().length - 1 && maze.grid()[coordinate.row() + 1][coordinate.col()].type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(coordinate.row() + 1, coordinate.col()));
        }
        if (coordinate.col() > 0 && maze.grid()[coordinate.row()][coordinate.col() - 1].type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(coordinate.row(), coordinate.col() - 1));
        }
        if (coordinate.col() < maze.grid()[0].length - 1 && maze.grid()[coordinate.row()][coordinate.col() + 1].type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(coordinate.row(), coordinate.col() + 1));
        }

        return neighbors;
    }

    // Функция для восстановления пути из массива предшественников
    private List<Coordinate> reconstructPath(Coordinate[][] predecessors, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;

        while (current != null) {
            path.add(current);
            current = predecessors[current.row()][current.col()];
        }

        // Путь нужно пройти в обратном порядке
        Collections.reverse(path);

        return path;
    }
}
