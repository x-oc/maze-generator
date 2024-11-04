package backend.academy.solvers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter public enum SolverType {
    BFS("Bfs"),
    DFS("Dfs");

    private final String namePresentation;

    SolverType(String namePresentation) {
        this.namePresentation = namePresentation;
    }

    public static List<String> getAllNames() {
        return Arrays.stream(SolverType.values())
            .map(SolverType::namePresentation)
            .collect(Collectors.toList());
    }
}
