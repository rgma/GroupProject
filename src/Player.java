import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    int x;
    int y;
    int xTile;
    int yTile;
    Image image;
    Image[] front = new Image[2];
    Image[] back = new Image[2];
    Image[] right = new Image[2];
    Image[] left = new Image[2];
    Image win;
    Image lose;
    Image get;
    String face;

    public Player(int playerNumber) throws IOException {
        x = 0;
        y = 0;
        xTile = 0;
        yTile = 0;
        create(playerNumber);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXTile() {
        return xTile;
    }

    public int getYTile() {
        return yTile;
    }

    public Image getImage() {
        return image;
    }

    public void changeFront() {
        if (image == front[0]) {
            image = front[1];
        } else {
            image = front[0];
        }
        face = "front";
    }

    public void changeBack() {
        if (image == back[0]) {
            image = back[1];
        } else {
            image = back[0];
        }
        face = "back";
    }

    public void changeLeft() {
        if (image == left[0] || image == right[0]) {
            image = left[1];
        } else {
            image = left[0];
        }
        face = "left";
    }

    public void changeRight() {
        if (image == right[0] || image == left[0]) {
            image = right[1];
        } else {
            image = right[0];
        }
        face = "right";
    }

    public void changeX(int x, int tileSize) {
        this.x = this.x + x;
        this.xTile = this.xTile + (x / tileSize);
    }

    public void changeY(int y, int tileSize) {
        this.y = this.y + y;
        this.yTile = this.yTile + (y / tileSize);
    }

    public String getFace() {
        return face;
    }

    public void changeWin() {
        image = win;
    }

    public void create(int playerNum) throws IOException {
        String player;
        if (playerNum == 1) {
            player = "NESS";
        } else {
            player = "PAULA";
        }
        front[0] = ImageIO.read(new File("src/images/"+player+"/forwardwalk1.png"));
        front[1] = ImageIO.read(new File("src/images/"+player+"/forwardwalk2.png"));
        back[0] = ImageIO.read(new File("src/images/"+player+"/upwalk1.png"));
        back[1] = ImageIO.read(new File("src/images/"+player+"/upwalk2.png"));
        right[0] = ImageIO.read(new File("src/images/"+player+"/rightstand.png"));
        right[1] = ImageIO.read(new File("src/images/"+player+"/rightwalk2.png"));
        left[0] = ImageIO.read(new File("src/images/"+player+"/leftstand.png"));
        left[1] = ImageIO.read(new File("src/images/"+player+"/leftwalk2.png"));
        win = ImageIO.read(new File("src/images/"+player+"/jump.png"));

        image = front[0];
        face = "front";
    }

    public void changeStarting2(int tileSize, int rowCols) {
        xTile = rowCols - 1;
        yTile = 0;
        x = x + ((rowCols - 1) * tileSize);
        y = 0;
    }

}
