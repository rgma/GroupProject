public class Position {
    public boolean rightOpen;
    public boolean leftOpen;
    public boolean downOpen;
    public boolean upOpen;
    public int posX;
    public int posY;
    public boolean visited = false;
    public boolean hasCoin = false;

    public Position(boolean rightOpen, boolean leftOpen, boolean downOpen,
                    boolean upOpen) {
        this.downOpen = downOpen;
        this.rightOpen = rightOpen;
        this.upOpen = upOpen;
        this.leftOpen = leftOpen;
    }

    public boolean isRightOpen() {
        return rightOpen;
    }

    public boolean isLeftOpen() {
        return leftOpen;
    }

    public boolean isDownOpen() {
        return downOpen;
    }

    public boolean isUpOpen() {
        return upOpen;
    }


}