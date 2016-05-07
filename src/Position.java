public class Position {
    public boolean rightOpen;
    public boolean leftOpen;
    public boolean downOpen;
    public boolean upOpen;

    public Position(boolean rightOpen, boolean leftOpen, boolean downOpen,
                    boolean upOpen) {
        this.downOpen = downOpen;
        this.rightOpen = rightOpen;
        this.upOpen = upOpen;
        this.leftOpen = leftOpen;
    }
}
