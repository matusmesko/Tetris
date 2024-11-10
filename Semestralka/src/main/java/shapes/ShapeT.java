package shapes;

import java.util.ArrayList;
import java.util.List;

public class ShapeT {
    private List<TetrisSquare> list;

    public ShapeT(int x, int y) {
        this.list = new ArrayList<>();

        this.list.add(new TetrisSquare(x, y, "magenta"));
        this.list.add(new TetrisSquare(x + 30, y, "magenta"));
        this.list.add(new TetrisSquare(x + 60, y, "magenta"));
        this.list.add(new TetrisSquare(x + 30, y + 28, "magenta"));
    }
}
