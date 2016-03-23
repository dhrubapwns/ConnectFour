package me.moocow9m.www.ConnectFour.drawing;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by ava on 3/19/2016.
 */
public class rectangleCreate extends abstractDraw {
    protected float height;
    protected float width;
    protected boolean fill;

    public rectangleCreate(float x, float y, float height, float width, boolean fill) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.fill = fill;
    }

    public rectangleCreate() {
        this.x = this.y = 0;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void draw() {
        glBegin(GL_POINTS);
        glVertex2f(x, y);
        for (float i = x; i < x + width; i++) {
            glVertex2f(i, y);
        }
        for (float i = y; i > y - height; i--) {
            glVertex2f(x, i);
        }
        for (float i = x; i < x + width; i++) {
            glVertex2f(i, y - height);
        }
        for (float i = y; i > y - height; i--) {
            glVertex2f(x + width, i);
        }
        glVertex2f(x, y);
        glEnd();
    }
}