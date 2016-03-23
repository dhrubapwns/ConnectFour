package me.moocow9m.www.ConnectFour.drawing;

import static org.lwjgl.opengl.GL11.*;

public class boardCreate extends abstractDraw {
    protected float size;

    public boardCreate(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public boardCreate() {
        this.x = this.y = this.size = 0;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void draw() {
        glBegin(GL_LINE_STRIP);
        float sizeEnd = size / 2;
        float xplus = x + sizeEnd;
        float xminus = x - sizeEnd;
        float yplus = y + sizeEnd;
        float yminus = y - sizeEnd;
        glVertex2f(xplus, yplus);
        glVertex2f(xplus, yminus);
        glVertex2f(xminus, yminus);
        glVertex2f(xminus, yplus);
        glVertex2f(xplus, yplus);
        glEnd();
    }
}