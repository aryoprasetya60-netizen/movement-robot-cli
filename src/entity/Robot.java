package entity;

public class Robot {
    private String direction;
    private int x;
    private int y;

    public Robot(String direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public String getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
