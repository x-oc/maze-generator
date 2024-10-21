package backend.academy.models;

public record Cell(int row, int col, Type type) {

    public enum Type { WALL, PASSAGE, COIN, SAND, START, FINISH }

}
