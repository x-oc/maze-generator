package backend.academy.generators;

// Фабричный класс для создания генераторов
public class MazeGeneratorFactory {

    private MazeGeneratorFactory() {}

    public static MazeGenerator createGenerator(MazeGeneratorType type) {
        return switch (type) {
            case KRUSKAL -> new KruskalGenerator();
            case PRIM -> new PrimGenerator();
            // ... другие типы генераторов
        };
    }
}
