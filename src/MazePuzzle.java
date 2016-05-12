import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazePuzzle {
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    public static final int MAZE_SIZE = 10;
    public static Position[][] maze = new Position[MAZE_SIZE][MAZE_SIZE];

    public static void main (String args[]) {
        maze = generateMaze();
       //playGame();
        System.out.println("Game Over");
        new Maze(maze);
    }

    //Generates a maze using randomised DFS. The starting and exit points are
    //always top left and bottom right respectively
    public static Position[][] generateMaze() {
        List<Position> positionList = new ArrayList<>();
        Direction direction;
        Position currPos;

        //Initalise maze positions
        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                maze[i][j] = new Position(false, false, false, false);
                maze[i][j].posX = i;
                maze[i][j].posY = j;
            }
        }

        //Choose starting position as top left
        currPos = maze[0][0];
        currPos.visited = true;
        positionList.add(currPos);

        //Choose a random direction with an unvisited position
        direction = chooseRandUnvisitedNeigh(currPos);

        //Add right neighbour
        if (direction == Direction.RIGHT) {
            maze[0][0].rightOpen = true;
            maze[0][1].leftOpen = true;
            positionList.add(0, maze[0][1]);
        //Add bottom neighbour
        } else if (direction == Direction.DOWN) {
            maze[0][0].downOpen = true;
            maze[1][0].upOpen = true;
            positionList.add(0, maze[1][0]);
        }

        while (!positionList.isEmpty()) {
            currPos = positionList.get(0);
            currPos.visited = true;
            direction = chooseRandUnvisitedNeigh(currPos);

            if (direction == Direction.UP) {
                maze[currPos.posX][currPos.posY].upOpen = true;
                maze[currPos.posX - 1][currPos.posY].downOpen = true;
                positionList.add(0, maze[currPos.posX - 1][currPos.posY]);
            } else if (direction == Direction.LEFT) {
                maze[currPos.posX][currPos.posY].leftOpen = true;
                maze[currPos.posX][currPos.posY - 1].rightOpen = true;
                positionList.add(0, maze[currPos.posX][currPos.posY - 1]);
            } else if (direction == Direction.RIGHT) {
                maze[currPos.posX][currPos.posY].rightOpen = true;
                maze[currPos.posX][currPos.posY + 1].leftOpen = true;
                positionList.add(0, maze[currPos.posX][currPos.posY + 1]);
            } else if (direction == Direction.DOWN) {
                maze[currPos.posX][currPos.posY].downOpen = true;
                maze[currPos.posX + 1][currPos.posY].upOpen = true;
                positionList.add(0, maze[currPos.posX + 1][currPos.posY]);
            } else {
                positionList.remove(currPos);
            }
        }

        //Create exit position in bottom right
        maze[MAZE_SIZE-1][MAZE_SIZE-1].downOpen = true;

        return maze;
    }

    //Returns the direction of a random unvisited neighbour of given position
    public static Direction chooseRandUnvisitedNeigh (Position currPos) {
        int rand;
        int directionArrayCount = 0;
        Random randGen = new Random();
        Direction direction = null;
        Direction directionArray[] = new Direction[4];

        //check top
        if (currPos.posX > 0 && !maze[currPos.posX-1][currPos.posY].visited) {
            directionArray[directionArrayCount] = Direction.UP;
            directionArrayCount++;
        }
        //check bottom
        if (currPos.posX < (MAZE_SIZE - 1) && !maze[currPos.posX+1][currPos.posY].visited) {
            directionArray[directionArrayCount] = Direction.DOWN;
            directionArrayCount++;
        }
        //check left
        if (currPos.posY > 0 && !maze[currPos.posX][currPos.posY-1].visited) {
            directionArray[directionArrayCount] = Direction.LEFT;
            directionArrayCount++;
        }
        //check bottom
        if (currPos.posY < (MAZE_SIZE - 1) && !maze[currPos.posX][currPos.posY+1].visited) {
            directionArray[directionArrayCount] = Direction.RIGHT;
            directionArrayCount++;
        }
        //Randomly choose from an array of valid choices
        if (directionArrayCount != 0) {
            rand = randGen.nextInt(directionArrayCount);
            direction = directionArray[rand];
        }

        return direction;
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

            if (direction == Direction.UP) {
                if (maze[x][y].upOpen) {
                    playerLocation[0] = playerLocation[0] - 1;
                }
            } else if (direction == Direction.DOWN) {
                if (maze[x][y].downOpen) {
                    playerLocation[0] = playerLocation[0] + 1;
                }
            } else if (direction == Direction.LEFT) {
                if (maze[x][y].leftOpen) {
                    playerLocation[1] = playerLocation[1] - 1;
                }
            } else if (direction == Direction.RIGHT) {
                if (maze[x][y].rightOpen) {
                    playerLocation[1] = playerLocation[1] + 1;
                }
            }
        } else {
            System.out.println("Error: maze is not initialised in function movePlayer");
        }

        return playerLocation;
    }

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
                //Wall blocking movement left
                if (!maze[i][j].leftOpen) {
                    System.out.print("| ");
                } else {
                    System.out.print("  ");
                }
                //Player is at current position
                if (playerLocation[0] == i && playerLocation[1] == j) {
                    System.out.print("X ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("|");

            //Print bottom wall
            for (int k = 0; k < MAZE_SIZE; k++) {
                //Wall blocking movement down
                if (!maze[i][k].downOpen) {
                    System.out.print("+---");
                } else {
                    System.out.print("+   ");
                }
            }
            System.out.println("+");
        }
    }
}