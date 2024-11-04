package backend.academy.generators;

import backend.academy.models.Maze;

public interface MazeGenerator {
    Maze generate(int height, int width);
}
