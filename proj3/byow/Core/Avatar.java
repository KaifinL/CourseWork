package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

public class Avatar implements Serializable {
    private int points = 0;
    private TETile appearance = Tileset.AVATAR;
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
    public Avatar() {
        initialTER();
    }

    public void setWorld(TETile[][] world) {
        this.world = world;
        world[startpos.getX()][startpos.getY()] = appearance;
    }

    public void setAppearance() {
        drawAppearance();
        String option;
        for (int i = 0; true; i += 1) {
            if (StdDraw.hasNextKeyTyped()) {
                option = Character.toString(StdDraw.nextKeyTyped());
                switch (option) {
                    case "1":
                        this.appearance = Tileset.WATER;
                        break;
                    case "2":
                        this.appearance = Tileset.SAND;
                        break;
                    case "3":
                        this.appearance = Tileset.MOUNTAIN;
                        break;
                    case "4":
                        this.appearance = Tileset.TREE;
                        break;
                }
                break;
            }
        }
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
            this.pos.changeAvatarPos(m, n, world, appearance);
        }  else if (nextTile.equals(Tileset.FLOWER) ){
            addPoints();
            this.pos.changeAvatarPos(m, n, world, appearance);
        } else if (nextTile.equals(Tileset.UNLOCKED_DOOR)) {
            GameOver = true;
        }
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
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        String showMessage = "Score : " + Integer.toString(this.points);
        StdDraw.text(WIDTH/20, HEIGHT+8, showMessage);
        StdDraw.show();
    }

    /**
     * Draw appearance selection.
     */
    public void drawAppearance() {
        StdDraw.clear(StdDraw.BLACK);
        Font font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH/2, HEIGHT/2+2, "1: WATER");
        StdDraw.text(WIDTH/2, HEIGHT/2, "2: SAND");
        StdDraw.text(WIDTH/2, HEIGHT/2-2, "3: MOUNTAIN");
        StdDraw.text(WIDTH/2, HEIGHT/2-4, "4: TREE");
        StdDraw.show();
    }

    /**
     * Draw starting message if user uses keyboard.
     */
    public void drawStart() {
        StdDraw.clear(StdDraw.BLACK);
        Font font = new Font("Arial", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        String showMessage0 = "CS61B: THE GAME";
        StdDraw.text(WIDTH/2, HEIGHT/2+6, showMessage0);
        Font font1 = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font1);
        StdDraw.text(WIDTH/2, HEIGHT/2+2, "New Game (N)");
        StdDraw.text(WIDTH/2, HEIGHT/2, "Load Game (L)");
        StdDraw.text(WIDTH/2, HEIGHT/2-2, "Quit (Q)");
        StdDraw.text(WIDTH/2, HEIGHT/2-4, "Change Appearance (C)");
        StdDraw.show();
    }

    public void drawStartTwo() {
        StdDraw.clear(StdDraw.BLACK);
        String showMessage = "PLEASE INPUT YOUR SEED";
        StdDraw.text(WIDTH/2, HEIGHT/2, showMessage);
        StdDraw.show();
    }

    /**
     * Show the corresponding seed user inputs.
     */
    public void drawFrame(String s) {
        StdDraw.clear(StdDraw.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH/2, HEIGHT/2, s);
        StdDraw.show();
    }

    /**
     * Draw the end message when game over.
     */
    public void drawEnd() {
        StdDraw.clear(StdDraw.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        String showMessage = "YOU WIN!";
        StdDraw.text(WIDTH/2, HEIGHT/2+5, showMessage);
        StdDraw.show();
    }

    /**
     * Interact with InputString.
     * Don't support changing appearance in this mode.
     */
    public void systemInput(String input) {
        String output = "";
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
                        this.commands = output;
                        //save
                        //quit the game
                    }
                    break;
                case "L":
                    //load
                    flag = 0;
                    break;
                default:
                    flag = 0;
                    move(typed);
            }
            if (GameOver) {
                drawEnd();
                break;
            }
            drawBoard();
        }
    }

    /**
     * Interact with user in keyboard mode.
     */
    public void playerInput() {
        String output = "";
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
                            this.commands = output;
                            //save
                            //quit the game
                        }
                        break;
                    case "L":
                        //load
                        flag = 0;
                        break;
                    default:
                        flag = 0;
                        move(typed);
                }
            }
            if (GameOver) {
                drawEnd();
                break;
            }
            drawBoard();
        }
    }

    public TETile[][] getWorld() {
        return world;
    }

    /**
     * The ability for the user to “replay” their most recent save,
     * visually displaying all of the actions taken since the last time a new world was created.
     */
    public void replay() {
        systemInput(commands);
    }
}
