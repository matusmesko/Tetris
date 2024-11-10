package shapes;

import java.util.ArrayList;
import java.util.List;

public class ShapeStraight {

    private List<TetrisSquare> list;

    public ShapeStraight(int x, int y) {
        this.list = new ArrayList<>();
        list.add(new TetrisSquare(x, y, "blue"));
        list.add(new TetrisSquare(x + 30, y, "blue"));
        list.add(new TetrisSquare(x + 60, y, "blue"));
        list.add(new TetrisSquare(x + 90, y, "blue"));
    }
}
