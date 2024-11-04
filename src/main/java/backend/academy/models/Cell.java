package backend.academy.models;

import lombok.Getter;

public record Cell(int row, int col, Type type) {

    @Getter public enum Type {
        WALL('#'),
        PASSAGE(' '),
        COIN('o'),
        SAND('~'),
        START('S'),
        FINISH('F');

        private final char display;

        Type(char display) {
            this.display = display;
        }
    }
}
