package backend.academy.solvers;

public class SolverFactory {

    private SolverFactory() {}

    public static Solver createSolver(SolverType type) {
        return switch (type) {
            case BFS -> new BfsSolver();
            case DFS -> new DfsSolver();
        };
    }
}
