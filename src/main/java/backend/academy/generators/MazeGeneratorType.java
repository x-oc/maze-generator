package backend.academy.generators;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter public enum MazeGeneratorType {

    KRUSKAL("Kruskal"),
    PRIM("Prim");

    private final String namePresentation;

    MazeGeneratorType(String namePresentation) {
        this.namePresentation = namePresentation;
    }

    public static List<String> getAllNames() {
        return Arrays.stream(MazeGeneratorType.values())
            .map(MazeGeneratorType::namePresentation)
            .collect(Collectors.toList());
    }
}
