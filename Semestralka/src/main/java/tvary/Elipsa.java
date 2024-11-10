package tvary;

import java.awt.geom.Ellipse2D;

/**
 * tvary.Elipsa, s ktorým možno pohybovať a nakreslí sa na plátno.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Elipsa {
    private int osX;
    private int osY;
    private int lavyHornyX;
    private int lavyHornyY;
    private String farba;
    private boolean jeViditelny;
    
    /**
     * Vytvor novú elipsu preddefinovanej farby na preddefinovanej pozícii. 
     */
    public Elipsa() {
        this.osX = 60;
        this.osY = 30;
        this.lavyHornyX = 20;
        this.lavyHornyY = 60;
        this.farba = "blue";
        this.jeViditelny = false;
    }

    /**
     * (tvary.Elipsa) Zobraz sa.
     */
    public void zobraz() {
        this.jeViditelny = true;
        this.nakresli();
    }
    
    /**
     * (tvary.Elipsa) Skry sa.
     */
    public void skry() {
        this.zmaz();
        this.jeViditelny = false;
    }
    
    /**
     * (tvary.Elipsa) Posuň sa vpravo o pevnú dĺžku.
     */
    public void posunVpravo() {
        this.posunVodorovne(20);
    }

    /**
     * (tvary.Elipsa) Posuň sa vľavo o pevnú dĺžku.
     */
    public void posunVlavo() {
        this.posunVodorovne(-20);
    }

    /**
     * (tvary.Elipsa) Posuň sa hore o pevnú dĺžku.
     */
    public void posunHore() {
        this.posunZvisle(-20);
    }

    /**
     * (tvary.Elipsa) Posuň sa dole o pevnú dĺžku.
     */
    public void posunDole() {
        this.posunZvisle(20);
    }

    /**
     * (tvary.Kruh) Posuň sa vodorovne o dĺžku danú parametrom.
     */
    public void posunVodorovne(int vzdialenost) {
        this.zmaz();
        this.lavyHornyX += vzdialenost;
        this.nakresli();
    }

    /**
     * (tvary.Elipsa) Posuň sa zvisle o dĺžku danú parametrom.
     */
    public void posunZvisle(int vzdialenost) {
        this.zmaz();
        this.lavyHornyY += vzdialenost;
        this.nakresli();
    }

    /**
     * (tvary.Elipsa) Posuň sa pomaly vodorovne o dĺžku danú parametrom.
     */
    public void pomalyPosunVodorovne(int vzdialenost) {
        int delta;

        if (vzdialenost < 0) {
            delta = -1;
            vzdialenost = -vzdialenost;
        } else {
            delta = 1;
        }

        for (int i = 0; i < vzdialenost; i++) {
            this.lavyHornyX += delta;
            this.nakresli();
        }
    }

    /**
     * (tvary.Elipsa) Posuň sa pomaly zvisle o dĺžku danú parametrom.
     */
    public void pomalyPosunZvisle(int vzdialenost) {
        int delta;

        if (vzdialenost < 0) {
            delta = -1;
            vzdialenost = -vzdialenost;
        } else {
            delta = 1;
        }

        for (int i = 0; i < vzdialenost; i++) {
            this.lavyHornyY += delta;
            this.nakresli();
        }
    }

    /**
     * (tvary.Elipsa) Zmeň veľkosti osí na hodnoty dané parametrami.
     * Veľkosť musí byť nezáporné celé číslo. 
     */
    public void zmenOsi(int osX, int osY) {
        this.zmaz();
        this.osX = osX;
        this.osY = osY;
        this.nakresli();
    }

    /**
     * (tvary.Elipsa) Zmeň farbu na hodnotu danú parametrom.
     * Nazov farby musí byť po anglicky.
     * Možné farby sú tieto:
     * červená - "red"
     * žltá    - "yellow"
     * modrá   - "blue"
     * zelená  - "green"
     * fialová - "magenta"
     * čierna  - "black"
     * biela   - "white"
     * hnedá   - "brown"
     */
    public void zmenFarbu(String farba) {
        this.farba = farba;
        this.nakresli();
    }

    /*
     * Draw the circle with current specifications on screen.
     */
    private void nakresli() {
        if (this.jeViditelny) {
            Platno canvas = Platno.dajPlatno();
            canvas.draw(this, this.farba, new Ellipse2D.Double(this.lavyHornyX, this.lavyHornyY, 
                                                          this.osX, this.osY));
            canvas.wait(10);
        }
    }

    /*
     * Erase the circle on screen.
     */
    private void zmaz() {
        if (this.jeViditelny) {
            Platno canvas = Platno.dajPlatno();
            canvas.erase(this);
        }
    }
}
