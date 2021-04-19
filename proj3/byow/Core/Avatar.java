package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

public class Avatar implements Serializable {
    private int points = 0;
    private Position startpos;
    private Position pos;
    private Position door;
    private String commands;
    private TERenderer ter = new TERenderer();
    private TETile[][] world;
    private Boolean GameOver = false;

    public static final int GOAL = 2;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    /**
     * Constructor
     */
    public Avatar( ) {
        initialTER();
    }

    public void setWorld(TETile[][] world) {
        this.world = world;
        world[startpos.getX()][startpos.getY()] = Tileset.AVATAR;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void setStartpos(Position startpos) {
        this.startpos = startpos;
    }

    public void setDoor(Position door) {
        this.door = door;
    }

    public void initialTER() {
        ter.initialize(WIDTH, HEIGHT);
        // initialize tiles
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Add points to the avatar.
     */
    private void addPoints() {
        this.points += 1;
        // If points are more than 1, the locked door turn to be unlocked door.
        if (points == GOAL) {
            world[door.getX()][door.getY()] = Tileset.UNLOCKED_DOOR;
        }
    }

    /**
     * Move around with keyboard inputs.
     */
    private void move(String input) {
        int m; int n;
        int x = this.pos.getX();
        int y = this.pos.getY();
        switch (input) {
            case "W":
                m = 0;
                n = 1;
                break;
            case "S":
                m = 0;
                n = -1;
                break;
            case "A":
                m = -1;
                n = 0;
                break;
            case "D":
                m = 1;
                n = 0;
                break;
            default:
                m = 0;
                n = 0;
        }
        TETile nextTile =  world[x + m][y + n];
        if (nextTile.equals(Tileset.FLOOR)) {
            this.pos.changeAvatarPos(m, n, world);
        }  else if (nextTile.equals(Tileset.FLOWER) ){
            addPoints();
            this.pos.changeAvatarPos(m, n, world);
        } else if (nextTile.equals(Tileset.UNLOCKED_DOOR)) {
            //Game Over.
            GameOver = true;
        }
        // If nextTile is wall or locked_door, do nothing.
    }

    /**
     * Draw the current board.
     */
    private void drawBoard() {
        Font font = new Font("Monaco", Font.BOLD, 10);
        StdDraw.setFont(font);
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x, y);
            }
        }
        drawAddition();
    }

    /**
     * Draw additional message while playing the game.
     */
    private void drawAddition() {
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        String showMessage = "Score : " + Integer.toString(this.points);
        StdDraw.text(WIDTH/2, HEIGHT-2, showMessage);
        StdDraw.show();
    }

    public void drawStart() {
        // Draw starting message here!
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        String showMessage0 = "CS 61B";
        StdDraw.text(WIDTH/2, HEIGHT/2+1, showMessage0);
        String showMessage1 = "N: CREATE NEW WORLD";
        StdDraw.text(WIDTH/2, HEIGHT/2-1, showMessage1);
        StdDraw.show();
    }

    public void drawStartTwo() {
        StdDraw.clear(StdDraw.BLACK);
        String showMessage = "PLEASE INPUT YOUR SEED";
        StdDraw.text(WIDTH/2, HEIGHT/2, showMessage);
        StdDraw.show();
    }

    public void drawEnd() {
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        String showMessage = "YOU WIN!";
        StdDraw.text(WIDTH/2, HEIGHT/2, showMessage);
        StdDraw.show();
    }

    /**
     *
     */
    public void systemInput(String input) {
        String output = "";//Should output include :Q/L?
        String typed;
        char[] arrayInput = input.toCharArray();
        int  flag = 0;
        for (int i = 0; i < arrayInput.length; i += 1) {
            typed = Character.toString(arrayInput[i]);
            output += typed;
            switch (typed) {
                case ":":
                    flag = 1;
                    break;
                case "Q":
                    if (flag == 1) {
                        //save
                        //Should I return? Terminate the game.
                    }
                    //nothing happend
                    break;
                case "L":
                    //load
                    flag = 0;
                    break;
                default: //this should receive W A S D. If there's no such command, no reaction.
                    flag = 0;
                    move(typed);
            }
            if (GameOver) {
                //Draw the end pic.
                drawEnd();
                break;
            }
            drawBoard();
        }
    }

    /**
     * This method is currently wrong, don't know how to deal with :Q.
     */
    public void playerInput() {
        String output = "";//Should output include :Q/L?
        String typed;
        int  flag = 0;
        for (int i = 0; true; i += 1) {
            if (StdDraw.hasNextKeyTyped()) {
                typed = Character.toString(StdDraw.nextKeyTyped());
                output += typed;
                switch (typed) {
                    case ":":
                        flag = 1;
                        break;
                    case "Q":
                        if (flag == 1) {
                            //save
                            //Should I return? Terminate the game.
                        }
                        //nothing happend
                        break;
                    case "L":
                        //load
                        flag = 0;
                        break;
                    default: //this should receive W A S D. If there's no such command, no reaction.
                        flag = 0;
                        move(typed);
                }
            }
            if (GameOver) {
                //Draw the end pic.
                drawEnd();
                break;
            }
            drawBoard();
        }
    }

}
