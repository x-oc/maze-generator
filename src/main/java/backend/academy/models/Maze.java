package backend.academy.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Maze {

    private final int height;
    private final int width;
    @Setter
    private Cell[][] grid;

}
