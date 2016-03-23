package me.moocow9m.www.ConnectFour.drawing;

public interface Draw {
    float getX();

    void setX(float x);

    float getY();

    void setY(float y);

    void setLocation(float x, float y);

    void setUp();

    void destroy();

    void draw();
}
