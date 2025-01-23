package matus;

import java.util.Random;


public class Tetromino {

    private TetrominoType tetrominoType; // Current type of tetromino
    private final int[][] coords; // Current coordinates of the tetromino
    private final int[][][] tetrominoTable; // Table of tetromino shapes

    /**
     * Constructor a matus.Tetromino object with default coordinates and shapes.
     * Initializes the coordinates array and the tetromino table that defines
     * the shapes of the tetrominoes. The default shape is set to NO_BLOCK.
     */
    public Tetromino() {
        this.coords = new int[4][2];
        this.tetrominoTable = new int[][][]{
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}}, // NO_BLOCK
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}, // Z_SHAPE
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}}, // S_SHAPE
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}}, // I_SHAPE
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}}, // T_SHAPE
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, // O_SHAPE
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}}, // L_SHAPE
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}} // J_SHAPE
        };
        this.setShape(TetrominoType.NO_BLOCK);
    }

    /**
     * Sets the shape of the tetromino to the specified matus.TetrominoType.
     * This method updates the coordinates of the tetromino based on the
     * shape defined in the tetromino table.
     *
     * @param tetromino the matus.TetrominoType to set for this tetromino.
     */
    public void setShape(TetrominoType tetromino) {
        for (int i = 0; i < this.coords.length; i++) {
            System.arraycopy(this.tetrominoTable[tetromino.ordinal()][i], 0, this.coords[i], 0, this.coords[i].length);
        }
        this.tetrominoType = tetromino;
    }

    /**
     * Sets a random shape for the tetromino from the available shapes.
     * This method generates a random integer to select a shape from the
     * matus.TetrominoType enum, ensuring that the shape is not NO_BLOCK.
     */
    public void setRandomShape() {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1; // Randomly select a shape (1 to 7)
        this.setShape(TetrominoType.values()[x]);
    }

    /**
     * Gets the current shape of the tetromino.
     *
     * @return the matus.TetrominoType of the current tetromino.
     */
    public TetrominoType getShape() {
        return this.tetrominoType;
    }

    /**
     * Sets the x-coordinate of a specific block in the tetromino.
     *
     * @param index the index of the block to set.
     * @param x the x-coordinate to set for the specified block.
     */
    public void setX(int index, int x) {
        this.coords[index][0] = x;
    }

    /**
     * Sets the y-coordinate of a specific block in the tetromino.
     *
     * @param index the index of the block to set.
     * @param y the y-coordinate to set for the specified block.
     */
    public void setY(int index, int y) {
        this.coords[index][1] = y;
    }

    /**
     * Gets the x-coordinate of a specific block in the tetromino.
     *
     * @param index the index of the block to retrieve.
     * @return the x-coordinate of the specified block.
     */
    public int getX(int index) {
        return this.coords[index][0];
    }

    /**
     * Gets the y-coordinate of a specific block in the tetromino.
     *
     * @param index the index of the block to retrieve.
     * @return the y-coordinate of the specified block.
     */
    public int getY(int index) {
        return this.coords[index][1];
    }


    /**
     * Gets the minimum y-coordinate among all blocks in the tetromino.
     * This method is useful for determining the lowest point of the tetromino.
     *
     * @return the minimum y-coordinate of the tetromino.
     */
    public int getMinY() {
        int temp = 0;
        for (int[] coord : this.coords) {
            temp = Math.min(temp, coord[1]);
        }
        return temp;
    }

    /**
     * Rotates the tetromino 90 degrees to the right.
     * If the tetromino is of type O_SHAPE, it remains unchanged as it is symmetrical.
     * A new matus.Tetromino object is created to represent the rotated shape.
     *
     * @return a new matus.Tetromino object representing the rotated shape.
     */
    public Tetromino rotateRight() {
        if (this.tetrominoType == TetrominoType.O_SHAPE) {
            return this;
        }
        Tetromino ret = new Tetromino();
        ret.tetrominoType = this.tetrominoType;

        for (int i = 0; i < this.coords.length; i++) {
            ret.setX(i, -this.getY(i)); // Set new x based on the old y
            ret.setY(i, this.getX(i)); // Set new y based on the old x
        }
        return ret;
    }
}
