package backend.academy;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {

        GameManager gameManager = new GameManager();
        gameManager.start();

    }
}

/*
20
100
80
1
1
20
Kruskal
Bfs

*/
