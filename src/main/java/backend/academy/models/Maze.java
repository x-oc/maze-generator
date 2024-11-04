package backend.academy.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Maze {

    private final int height;
    private final int width;
    private Cell[][] grid;

}
