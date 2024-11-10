package shapes;

import tvary.Obdlznik;
import tvary.Stvorec;

public class TetrisSquare {

    private Stvorec stvorec;
    private Obdlznik top;
    private Obdlznik left;
    private Obdlznik right;
    private Obdlznik bottom;


    public TetrisSquare(int x, int y, String farba) {
        stvorec = new Stvorec();
        stvorec.pomalyPosunVodorovne(x - 60);
        stvorec.posunZvisle(y - 50);
        stvorec.zmenFarbu(farba);
        stvorec.zobraz();

        top = new Obdlznik();
        top.pomalyPosunVodorovne(x - 60);
        top.posunZvisle(y - 50);
        top.zmenFarbu("black");
        top.zmenStrany(30, 3);
        top.zobraz();

        left = new Obdlznik();
        left.pomalyPosunVodorovne(x - 60);
        left.posunZvisle(y - 50);
        left.zmenFarbu("black");
        left.zmenStrany(3, 30);
        left.zobraz();

        right = new Obdlznik();
        right.pomalyPosunVodorovne(x - 30);
        right.posunZvisle(y - 50);
        right.zmenFarbu("black");
        right.zmenStrany(3, 30);
        right.zobraz();

        bottom = new Obdlznik();
        bottom.pomalyPosunVodorovne(x - 60);
        bottom.posunZvisle(y - 23);
        bottom.zmenFarbu("black");
        bottom.zmenStrany(30, 3);
        bottom.zobraz();
    }
}
