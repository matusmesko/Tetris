package shapes;

import tvary.Stvorec;

import java.util.ArrayList;
import java.util.List;

public class ShapeSquare {

    private List<TetrisSquare> list;

    public ShapeSquare(int x, int y) {
        this.list = new ArrayList<>();
        list.add(new TetrisSquare(x, y, "yellow"));
        list.add(new TetrisSquare(x + 30, y, "yellow"));
        list.add(new TetrisSquare(x, y + 25, "yellow"));
        list.add(new TetrisSquare(x + 30, y + 25, "yellow"));
    }
}
