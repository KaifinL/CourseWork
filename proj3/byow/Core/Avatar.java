package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Avatar implements Serializable {
    private long seedNum;
    private int points = 0;
    private int originpoints = 0;
    private TETile appearance = Tileset.AVATAR;
    private Position startpos;
    private Position pos;
    private Position door;
    private String commands;
    private TERenderer ter = new TERenderer();
    private TETile[][] world;
    private TETile[][] backupworld;
    private Boolean gameOver = false;
    private TETile floor = Tileset.FLOOR;
    private TETile flower = Tileset.FLOWER;
    private TETile unlockedDoor = Tileset.UNLOCKED_DOOR;
    private TETile BLUEFLOWER = Tileset.BLUEFLOWER;
    private boolean poisoned = false;
    private boolean originstate = false;
    private boolean dark = false;
    private boolean origindark = false;

    public static final int GOAL = 2;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    /**
     * Constructor
     */
    public Avatar() {
        initialTER();
    }

    public Position getPos() {
        return pos;
    }

    public void setWorld(TETile[][] world) {
        this.world = world;
        world[startpos.getX()][startpos.getY()] = appearance;
        setBackupworld();
    }

    public void setSeedNum(long seedNum) {
        this.seedNum = seedNum;
    }

    public void setAppearance() {
        drawAppearance();
        String option;
        for (int i = 0; true; i += 1) {
            if (StdDraw.hasNextKeyTyped()) {
                option = Character.toString(StdDraw.nextKeyTyped());
                switch (option) {
                    case "1" -> this.appearance = Tileset.WATER;
                    case "2" -> this.appearance = Tileset.SAND;
                    case "3" -> this.appearance = Tileset.MOUNTAIN;
                    case "4" -> this.appearance = Tileset.TREE;
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

    public void setBackupworld() {
        backupworld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                backupworld[x][y] = world[x][y];
            }
        }
    }

    public void setOriginstate() {
        originstate = poisoned;
    }

    public void setOriginpoints() {
        originpoints = points;
    }

    public void setOrigindark() {
        origindark = dark;
    }


    public TETile[][] getWorld() {
        return this.world;
    }

    public void initialTER() {
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
            world[door.getX()][door.getY()] = unlockedDoor;
        }
    }
    private void minusPoints() {
        this.points -= 1;
        if (points < GOAL) {
            world[door.getX()][door.getY()] = Tileset.LOCKED_DOOR;
        }
    }


    /**
     * Move around with keyboard inputs.
     */
    private void move(String input) {
        int m; int n;
        int x = this.pos.getX();
        int y = this.pos.getY();
        if (poisoned) {
            switch (input) {
                case "W": m = 0;n = -1;break;
                case "S": m = 0;n = 1;break;
                case "A": m = 1;n = 0;break;
                case "D": m = -1;n = 0;break;
                default: m = 0; n = 0;break;
            }
        } else {
            switch (input) {
                case "W": m = 0;n = 1;break;
                case "S": m = 0;n = -1;break;
                case "A": m = -1;n = 0;break;
                case "D": m = 1;n = 0;break;
                default: m = 0; n = 0;break;
            }
        }
        TETile nextTile =  world[x + m][y + n];
        if (nextTile.equals(floor)) {
            this.pos.changeAvatarPos(m, n, world, floor, appearance);
        }  else if (nextTile.equals(flower) ){
            addPoints();
            this.pos.changeAvatarPos(m, n, world, floor, appearance);
        } else if (nextTile.equals(unlockedDoor)) {
            if (points > 9) {
                gameOver = true;
                return;
            }
            solvepuzzle();
            gameOver = true;
        } else if (nextTile.equals(BLUEFLOWER)) {
            poisoned = !poisoned;
            minusPoints();
            this.pos.changeAvatarPos(m, n, world, floor, appearance);
        }
    }

    /**
     * Solve a math problem to win the game.
     */
    private void solvepuzzle() {
        int a = (int)(1+Math.random()*10);
        int b  = (int)(1+Math.random()*10);
        int c  = (int)(1+Math.random()*10);
        double x = ((double)c - (double)b)/(double)a;
        float  answer =( float )(Math.round(x * 10 ))/ 10;
        StdDraw.clear(new Color(0, 0, 0));
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        String showMessage = Integer.toString(a) + "x + " +
                Integer.toString(b) + " = " + Integer.toString(c);
        StdDraw.text(WIDTH/2, HEIGHT/2+5, showMessage);
        StdDraw.show();

        String stranswer = Float.toString(answer);
        String typed;
        String input = "";
        for (int i = 0; true; i += 1) {
            if (StdDraw.hasNextKeyTyped()) {
                typed = Character.toString(StdDraw.nextKeyTyped());
                input += typed;
                StdDraw.clear(new Color(0, 0, 0));
                StdDraw.text(WIDTH/2, HEIGHT/2+5, showMessage);
                StdDraw.text(WIDTH/2, HEIGHT/2, input);
                StdDraw.show();
                if (input.equals(stranswer)) {
                    StdDraw.pause(300);
                    break;
                } else if (input.length()==stranswer.length()) {
                    StdDraw.clear(new Color(0, 0, 0));
                    StdDraw.text(WIDTH/2, HEIGHT/2+5, "Wrong Answer, Try again!");
                    StdDraw.show();
                    input = "";
                }
            }
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
     * Draw the dark board(Player turns off the light).
     */
    private void drawDarkBoard() {
        Font font = new Font("Monaco", Font.BOLD, 10);
        StdDraw.setFont(font);
        StdDraw.clear(new Color(0, 0, 0));
        int x0 = max(pos.getX()-4, 0);
        int x1 = min(pos.getX()+4, WIDTH);
        int y0 = max(pos.getY()-4, 0);
        int y1 = min(pos.getY()+4, HEIGHT);
        for (int x = x0; x < x1; x += 1) {
            for (int y = y0; y < y1; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x, y);
            }
        }
        drawAddition();
    }

    public void drawMouse(int mouseX, int mouseY) {
        mouseX = min(mouseX, WIDTH-1);
        mouseX = max(mouseX, 0);
        mouseY = min(mouseY, HEIGHT-1);
        mouseY = max(mouseY, 0);
        String mouseMessage = world[mouseX][mouseY].description();
        StdDraw.text(mouseX, mouseY, mouseMessage);
        StdDraw.show();
    }

    /**
     * Draw additional message while playing the game.
     */
    private void drawAddition() {
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        String showMessage = "Score : " + Integer.toString(this.points);
        StdDraw.text(5, HEIGHT-5, showMessage);
        String poisonedMessage;
        if (poisoned) {
            poisonedMessage = "Poisoned";
        } else {
            poisonedMessage = "Healthy";
        }
        StdDraw.text(5, HEIGHT-7, poisonedMessage);
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        drawMouse(mouseX, mouseY);
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
        ter.initialize(WIDTH, HEIGHT);
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
        StdDraw.text(WIDTH/2, HEIGHT/2-6, "Replay (R)");
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
            typed = typed.toUpperCase();
            switch (typed) {
                case ":":
                    flag = 1;
                    break;
                case "Q":
                    if (flag == 1) {
                        this.commands = output;
                        SaveLoad.save(this);
                        flag = 2;
                    }
                    break;
                case "C":
                    flag = 0;
                    Skill c = new Skill(this, world, WIDTH, HEIGHT, seedNum);
                    c.chiselNewWorld();
                    world = c.getWorld();
                    minusPoints();
                    break;
                default:
                    flag = 0;
                    move(typed);
            }
            output += typed;
            if (flag == 2) {
                break;
            }
            if (gameOver) {
                break;
            }
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
                typed = typed.toUpperCase();
                switch (typed) {
                    case ":":
                        flag = 1;
                        break;
                    case "Q":
                        if (flag == 1) {
                            this.commands = output;
                            SaveLoad.save(this);
                            flag = 2;
                        }
                        break;
                    case "C":
                        flag = 0;
                        Skill c = new Skill(this, world, WIDTH, HEIGHT, seedNum);
                        c.chiselNewWorld();
                        world = c.getWorld();
                        minusPoints();
                        break;
                    case "T":
                        dark = !dark;
                        break;
                    default:
                        flag = 0;
                        move(typed);
                }
                output += typed;
            }
            if (flag == 2) {
                break;
            }
            if (gameOver) {
                drawEnd();
                break;
            }
            if (dark) {
                drawDarkBoard();
            } else {
                drawBoard();
            }
        }
    }

    /**
     * The ability for the user to “replay” their most recent save,
     * visually displaying all of the actions taken since the last time a new world was created.
     */
    public void replay() {
        dark = origindark;
        poisoned = originstate;
        points = originpoints;
        world[pos.getX()][pos.getY()] = floor;
        pos = new Position(startpos.getX(),startpos.getY(),startpos.getDirection());
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = backupworld[x][y];
            }
        }
        String typed;
        char[] arrayInput = commands.toCharArray();
        for (int i = 0; i < arrayInput.length; i += 1) {
            typed = Character.toString(arrayInput[i]);
            switch (typed) {
                case ":":
                    break;
                case "C":
                    Skill c = new Skill(this, world, WIDTH, HEIGHT, seedNum);
                    c.chiselNewWorld();
                    world = c.getWorld();
                    minusPoints();
                    break;
                case "T":
                    dark = !dark;
                    break;
                default:
                    move(typed);
            }
            if (gameOver) {
                drawEnd();
                break;
            }
            if (dark) {
                drawDarkBoard();
            } else {
                drawBoard();
            }
            StdDraw.pause(400);
        }
        startpos = new Position(pos.getX(),pos.getY(), pos.getDirection());
    }
}
