package me.moocow9m.www.ConnectFour.drawing;

public abstract class abstractDraw implements Draw {
    protected float x;
    protected float y;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void setUp();

    public abstract void destroy();

    public abstract void draw();
}
