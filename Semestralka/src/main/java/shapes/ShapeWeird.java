package shapes;

import java.util.ArrayList;
import java.util.List;

public class ShapeWeird {

    private List<TetrisSquare> list;

    public ShapeWeird(int x, int y) {
        this.list = new ArrayList<>();

        this.list.add(new TetrisSquare(x, y, "red"));
        this.list.add(new TetrisSquare(x, y + 25, "red"));
        this.list.add(new TetrisSquare(x + 30, y + 25, "red"));
        this.list.add(new TetrisSquare(x + 30, y + 50, "red"));
    }
}
