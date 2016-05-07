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

    public static void main (String args[]) {
        Position[][] currentMaze = generateMaze(Difficulty.EASY);
        playGame(currentMaze);
    }

    //todo currently does not generate a solvable maze
    //bug in left column, not properly generating wall blocks
    public static Position[][] generateMaze(Difficulty difficulty) {
        Position[][] maze = new Position[MAZE_SIZE][MAZE_SIZE];

        //Randomly chose where wall should be
        if (difficulty == Difficulty.EASY) {
            Random randomGen = new Random();
            int rand;

            for (int i = 0; i < MAZE_SIZE; i++) {
                for (int j = 0; j < MAZE_SIZE; j++) {
                    //Initialise maze position
                    maze[i][j] = new Position(false, false, false, false);

                    //Border position
                    if (i == 0 || i == (MAZE_SIZE - 1)|| j == 0 || j == (MAZE_SIZE - 1)) {
                        //Top border position
                        if (i == 0) {
                            maze[i][j].upOpen = false;
                        }
                        //Bottom border position
                        if (i == (MAZE_SIZE - 1)) {
                            maze[i][j].downOpen = false;
                        }
                        //Left border position
                        if (j == 0) {
                            maze[i][j].leftOpen = false;

                        }
                        //Right border position
                        if (j == (MAZE_SIZE - 1)) {
                            maze[i][j].rightOpen = false;
                        }
                    } else {
                        //Copy from left and up neighbours
                        maze[i][j].leftOpen = maze[i-1][j].rightOpen;
                        maze[i][j].upOpen = maze[i-1][j].downOpen;

                        //Randomly choose if there are walls to the right and below
                        rand = randomGen.nextInt(10);
                        if (rand < 7) {
                            maze[i][j].rightOpen = true;
                        }
                        rand = randomGen.nextInt(10);
                        if (rand < 7) {
                            maze[i][j].downOpen = true;
                        }
                    }
                }
            }
        } else if (difficulty == Difficulty.MEDIUM) {
            //todo
        } else if (difficulty == Difficulty.HARD){
            //todo
        }

        return maze;
    }

    public static void playGame (Position[][] maze) {
        int[] playerLocation = new int[2]; //x,y co-ord
        boolean running = true;
        String userInputLine = "";
        BufferedReader userInput =
                new BufferedReader(new InputStreamReader(System.in));

        //Player starting location
        playerLocation[0] = 2;
        playerLocation[1] = 1;

        while (running) {
            printMaze(playerLocation, maze);
            try {
                userInputLine = userInput.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: could not read user's input");
            }

            //Move up
            if (userInputLine.contains("w")) {
                movePlayer(maze, playerLocation, Direction.UP);
            //Move left
            } else if (userInputLine.contains(("a"))) {
                movePlayer(maze, playerLocation, Direction.LEFT);
            //Move down
            } else if (userInputLine.contains(("s"))) {
                movePlayer(maze, playerLocation, Direction.DOWN);
            //Move right
            } else if (userInputLine.contains(("d"))) {
                movePlayer(maze, playerLocation, Direction.RIGHT);
            }
        }
    }

    public static int[] movePlayer (Position[][] maze, int[] playerLocation,
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

    //todo not working, fix infinite loop and display problem
    public static void printMaze(int[] playerLocation, Position[][] maze) {

        for (int i = 0; i < MAZE_SIZE; i++) {
            //Print top border
            if (i == 0) {
                for (int k = 0; k < MAZE_SIZE; k++) {
                    System.out.print("+---");
                }
                System.out.println("+");
            } else {
                for (int k = 0; k < MAZE_SIZE; k++) {
                    //Check there is a wall blocking movement up
                    if (maze[i][k].upOpen == false) {
                        System.out.print("+---");
                    } else {
                        System.out.print("+   ");
                    }
                }
                System.out.println("+");
            }
            for (int j = 0; j < MAZE_SIZE; j++) {
                //Print right border
                if (j == (MAZE_SIZE - 1)) {
                    System.out.print("| ");
                    //Player is at current position
                    if (playerLocation[0] == i && playerLocation[1] == j) { //todo check this works
                        System.out.println("X |");
                    } else {
                        System.out.println("  |");
                    }
                //Print left border
                } else if (j == 0) {
                    System.out.print("| ");
                    //Player is at current position
                    if (playerLocation[0] == i && playerLocation[1] == j) { //todo check this works
                        System.out.print("X ");
                    } else {
                        System.out.print("  ");
                    }
                } else {
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
            }
        }
        //Print bottom border
        for (int k = 0; k < MAZE_SIZE; k++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }
}
