package backend.academy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Maze {

    private final int height;
    private final int width;
    private final Cell[][] grid;

}
