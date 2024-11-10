package shapes;

import java.util.ArrayList;
import java.util.List;

public class ShapeL {

    private List<TetrisSquare> list;

    public ShapeL(int x, int y) {
        this.list = new ArrayList<>();

        list.add(new TetrisSquare(x, y, "green"));
        list.add(new TetrisSquare(x, y + 25, "green"));
        list.add(new TetrisSquare(x, y + 50, "green"));
        list.add(new TetrisSquare(x + 30, y + 50, "green"));
    }
}
