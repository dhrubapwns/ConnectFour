package me.moocow9m.www.ConnectFour;

import me.moocow9m.www.ConnectFour.drawing.Draw;
import me.moocow9m.www.ConnectFour.drawing.boardCreate;
import me.moocow9m.www.ConnectFour.drawing.rectangleCreate;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static me.moocow9m.www.ConnectFour.drawing.drawShapes.drawShape;
import static me.moocow9m.www.ConnectFour.drawing.drawShapes.drawString;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    private Draw square;
    private Draw rectangle;
    private double mouseX, mouseY;
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private long window;
    private boolean boardDraw = false;
    private boolean login = true;
    private String username = "";
    private String password = "";
    private boolean usernameOn = false;
    private boolean passwordOn = false;

    public static void main(String[] args) {
        new Main().run();
    }


    private void run() {
        try {
            rectangle = new rectangleCreate(250, 302, 14, 100, false);
            rectangle.setUp();
            init();
            loop();
            glfwDestroyWindow(window);
            keyCallback.free();
            mouseButtonCallback.free();
            cursorPosCallback.free();
            if (square != null) {
                square.destroy();
            }
        } finally {
            glfwTerminate();
            errorCallback.free();
        }
    }

    private void init() {
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        if (glfwInit() != GLFW_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        int WIDTH = 600;
        int HEIGHT = 600;

        window = glfwCreateWindow(WIDTH, HEIGHT, "Connect Four Start Page", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, GLFW_TRUE);
                } else if (login && action == GLFW_RELEASE && glfwGetKeyName(key, scancode) != null) {
                    username = username + glfwGetKeyName(key, scancode);
                } else if (key == GLFW_KEY_BACKSPACE && username.length() > 0 && action == GLFW_RELEASE) {
                    username = username.substring(0, username.length() - 1);
                } else if (key == GLFW_KEY_SPACE && action == GLFW_RELEASE) {
                    username = username + " ";
                }
            }
        });
        mouseX = mouseY = 0.0;
        glfwSetMouseButtonCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long l, int i, int i1, int i2) {
                if (i == 0 && i1 == 1) {
                    System.out.println("Left Click Detected " + mouseX + " " + mouseY);
                    double tempX = mouseX;

                    double tempY = mouseY;
                    if ((tempX >= 300 && tempX <= 400) && tempY >= 300 && tempY <= 400) {
                        square = new boardCreate(300.0f, 300.0f, 50.0f);
                        square.setUp();
                        boardDraw = true;
                        login = false;
                    }
                }
            }
        });
        glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long l, double v, double v1) {
                mouseX = v;
                mouseY = v1;
            }
        });
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - WIDTH) / 2,
                (vidmode.height() - HEIGHT) / 2
        );
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 600, 600, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        while (glfwWindowShouldClose(window) == GLFW_FALSE) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClear(GL_COLOR_BUFFER_BIT);

            if (login) {
                glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                drawString("Username:", 260, 280);
                rectangle.draw();
                drawString(username, 260, 300);
                drawString("Password:", 260, 320);
                drawString("Password will not show up", 200, 340);
                drawString("Login", 272, 360);
                drawShape(7, 20.0f, 15.0f, 100.0f, 100.0f);
            }
            if (boardDraw) {
                glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                square.draw();
            }
            glfwSwapBuffers(window);
        }
    }
}
