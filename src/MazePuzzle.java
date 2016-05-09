import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class MazePuzzle {
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    public static final int MAZE_SIZE = 10;
    public static Position[][] maze;

    public static void main (String args[]) {
        maze = generateMaze(Difficulty.EASY);
        playGame();
        System.out.println("Game Over");
    }

    //used to test player movement and board printing
    public static Position[][] testMaze() {
        Position[][] maze = new Position[MAZE_SIZE][MAZE_SIZE];
        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                maze[i][j] = new Position(false, false, false, false);//true, true, true, true);
            }
        }
        int i = 1;
        for (int j = 0; j < MAZE_SIZE; j++) {
            maze[i][j].rightOpen = true;
            maze[i][j].leftOpen = true;
        }

        int j = 1;
        for (i = 0; i < MAZE_SIZE; i++) {
            maze[i][j].downOpen = true;
            maze[i][j].upOpen = true;
        }

        j = 4;
        for (i = 0; i < MAZE_SIZE; i++) {
            maze[i][j].downOpen = true;
            maze[i][j].upOpen = true;
        }

        return maze;
    }

    //todo currently does not always generate a solvable maze
    //has bug with generating walls between positions for the top row
     public static Position[][] generateMaze(Difficulty difficulty) {
        Position[][] maze = new Position[MAZE_SIZE][MAZE_SIZE];

        //Randomly choose where wall should be
        if (difficulty == Difficulty.EASY) {
            Random randomGen = new Random();
            int rand;

            for (int i = 0; i < MAZE_SIZE; i++) {
                for (int j = 0; j < MAZE_SIZE; j++) {
                    //Initialise maze position
                    maze[i][j] = new Position(false, false, false, false);

                    if (i == 0 || j == 0) {
                        maze[i][j] = new Position(true, true, true, true);
                        //Top border position
                        if (i == 0) {
                            maze[i][j].upOpen = false;
                            if (j == (MAZE_SIZE - 1)) {
                                maze[i][j].rightOpen = false;
                            } else {
                                rand = randomGen.nextInt(10);
                                if (rand < 6) {
                                    maze[i][j].rightOpen = false;
                                }
                            }
                            if (j != 0) {
                                maze[i][j].leftOpen = maze[i][j-1].rightOpen;
                            }
                        }
                        //Left border position
                        if (j == 0) {
                            maze[i][j].leftOpen = false;
                            if (i == (MAZE_SIZE - 1)) {
                                maze[i][j].downOpen = false;
                            } else {
                                rand = randomGen.nextInt(10);
                                if (rand < 6) {
                                    maze[i][j].downOpen = false;
                                }
                            }
                            if (i != 0) {
                                maze[i][j].upOpen = maze[i-1][j].downOpen;
                            }
                        } else {
                            rand = randomGen.nextInt(10);
                            if (rand < 6) {
                                maze[i][j].rightOpen = false;
                            }
                        }
                    } else {
                        //Copy from left and up neighbours
                        maze[i][j].leftOpen = maze[i][j-1].rightOpen;
                        maze[i][j].upOpen = maze[i-1][j].downOpen;

                        //Bottom border position
                        if (i == (MAZE_SIZE - 1)) {
                            maze[i][j].downOpen = false;
                        //Randomly choose if there are walls
                        } else {
                            rand = randomGen.nextInt(10);
                            if (rand < 6) {
                                maze[i][j].downOpen = true;
                            }
                        }

                        //Right border position
                        if (j == (MAZE_SIZE - 1)) {
                            maze[i][j].rightOpen = false;
                        //Randomly choose if there are walls
                        } else {
                            rand = randomGen.nextInt(10);
                            if (rand < 6) {
                                maze[i][j].rightOpen = true;
                            }

                        }
                    }
                }
            }

            //Create an exit from the maze
            maze[MAZE_SIZE-1][MAZE_SIZE-1].downOpen = true;

        } else if (difficulty == Difficulty.MEDIUM) {
            //todo
        } else if (difficulty == Difficulty.HARD){
            //todo
        }

        return maze;
    }

    public static void playGame () {
        int[] playerLocation = new int[2]; //x,y co-ord
        boolean running = true;
        String userInputLine = "";
        //todo read input without needing to press enter after every command
        BufferedReader userInput =
                new BufferedReader(new InputStreamReader(System.in));

        //Player starting location
        playerLocation[0] = 0;
        playerLocation[1] = 0;

        while (running) {
            printMaze(playerLocation);
            try {
                userInputLine = userInput.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: could not read user's input");
            }

            //Move up
            if (userInputLine.contains("w")) {
                movePlayer(playerLocation, Direction.UP);
            //Move left
            } else if (userInputLine.contains(("a"))) {
                movePlayer(playerLocation, Direction.LEFT);
            //Move down
            } else if (userInputLine.contains(("s"))) {
                movePlayer(playerLocation, Direction.DOWN);
            //Move right
            } else if (userInputLine.contains(("d"))) {
                movePlayer(playerLocation, Direction.RIGHT);
            }

            //Player has left the board and won the game
            if (playerLocation[0] >= MAZE_SIZE || playerLocation[1] >= MAZE_SIZE) {
                running = false;
            }
        }
    }

    public static int[] movePlayer (int[] playerLocation,
                                           Direction direction) {
        if (maze != null) {
            int x = playerLocation[0];
            int y = playerLocation[1];

            if (direction == direction.UP) {
                if (maze[x][y].upOpen) {
                    playerLocation[0] = playerLocation[0] - 1;
                }
            } else if (direction == direction.DOWN) {
                if (maze[x][y].downOpen) {
                    playerLocation[0] = playerLocation[0] + 1;
                }
            } else if (direction == direction.LEFT) {
                if (maze[x][y].leftOpen) {
                    playerLocation[1] = playerLocation[1] - 1;
                }
            } else if (direction == direction.RIGHT) {
                if (maze[x][y].rightOpen) {
                    playerLocation[1] = playerLocation[1] + 1;
                }
            }
        } else {
            //todo error
        }

        return playerLocation;
    }

    //seems to work fine
    public static void printMaze(int[] playerLocation) {

        //Print top border
        for (int k = 0; k < MAZE_SIZE; k++) {
            if (k == 0) {
                System.out.print("+   ");
            } else {
                System.out.print("+---");
            }

        }
        System.out.println("+");

        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                //There is a wall blocking movement left
                if (maze[i][j].leftOpen == false) {
                    System.out.print("| ");
                } else {
                    System.out.print("  ");
                }
                //Player is at current position
                if (playerLocation[0] == i && playerLocation[1] == j) { //todo check this works
                    System.out.print("X ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("|");

            //Print bottom wall
            for (int k = 0; k < MAZE_SIZE; k++) {
                //Check there is a wall blocking movement down
                if (maze[i][k].downOpen == false) {
                    System.out.print("+---");
                } else {
                    System.out.print("+   ");
                }
            }
            System.out.println("+");
        }
    }
}
