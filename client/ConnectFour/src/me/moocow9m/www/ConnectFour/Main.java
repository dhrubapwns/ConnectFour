package me.moocow9m.www.ConnectFour;

import me.moocow9m.www.ConnectFour.connection.Connect;
import me.moocow9m.www.ConnectFour.connection.UserData;
import me.moocow9m.www.ConnectFour.drawing.Draw;
import me.moocow9m.www.ConnectFour.drawing.rectangleCreate;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static me.moocow9m.www.ConnectFour.drawing.drawShapes.drawShape;
import static me.moocow9m.www.ConnectFour.drawing.drawShapes.drawString;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    public static UserData user = new UserData();
    private Draw square;
    private Draw Username;
    private Draw Password;
    private Draw Login;
    private double mouseX, mouseY;
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private long window;
    private boolean boardDraw = false;
    private boolean login = true;
    private boolean conectionInfo = false;
    private String username = "";
    private String password = "";
    private boolean usernameOn = true;
    private boolean passwordOn = false;
    private boolean portOn = false;
    private boolean hostOn = false;
    private int Port = 30480;
    private String host = "";

    public static void main(String[] args) {
        new Main().run();
    }


    private void run() {
        try {
            Username = new rectangleCreate(250, 302, 14, 100, false);
            Username.setUp();
            Password = new rectangleCreate(250, 342, 14, 100, false);
            Password.setUp();
            Login = new rectangleCreate(250, 368, 20, 100, false);
            Login.setUp();
            try {
                host = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
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
                } else if (login && action == GLFW_RELEASE && glfwGetKeyName(key, scancode) != null && usernameOn) {
                    username = username + glfwGetKeyName(key, scancode);
                } else if (key == GLFW_KEY_BACKSPACE && username.length() > 0 && action == GLFW_RELEASE && usernameOn) {
                    username = username.substring(0, username.length() - 1);
                } else if (key == GLFW_KEY_SPACE && action == GLFW_RELEASE && usernameOn) {
                    username = username + " ";
                } else if (login && action == GLFW_RELEASE && glfwGetKeyName(key, scancode) != null && passwordOn) {
                    password = password + glfwGetKeyName(key, scancode);
                } else if (key == GLFW_KEY_BACKSPACE && password.length() > 0 && action == GLFW_RELEASE && passwordOn) {
                    password = password.substring(0, password.length() - 1);
                } else if (key == GLFW_KEY_SPACE && action == GLFW_RELEASE && passwordOn) {
                    password = password + " ";
                } else if (action == GLFW_RELEASE && glfwGetKeyName(key, scancode) != null && portOn) {
                    String temp = Port + "";
                    try {
                        Port = Integer.valueOf(temp + glfwGetKeyName(key, scancode));
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                } else if (key == GLFW_KEY_BACKSPACE && (Port + "").length() > 0 && action == GLFW_RELEASE && portOn) {
                    String temp = Port + "";
                    try {
                        Port = Integer.valueOf(temp.substring(0, temp.length() - 1));
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                } else if (action == GLFW_RELEASE && glfwGetKeyName(key, scancode) != null && hostOn) {
                    host = host + glfwGetKeyName(key, scancode);
                } else if (key == GLFW_KEY_BACKSPACE && host.length() > 0 && action == GLFW_RELEASE && hostOn) {
                    host = host.substring(0, host.length() - 1);
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
                    if ((tempX >= 250 && tempX <= 350) && tempY >= 348 && tempY <= 366 && conectionInfo) {
                        user.setPassword(password);
                        user.setUsername(username.toUpperCase());
                        Thread connect = new Connect(host, Port);
                        connect.start();
                    }
                    if ((tempX >= 250 && tempX <= 350) && tempY >= 327 && tempY <= 341 && conectionInfo) {
                        hostOn = false;
                        portOn = true;
                    }
                    if ((tempX >= 250 && tempX <= 350) && tempY >= 287 && tempY <= 301 && conectionInfo) {
                        portOn = false;
                        hostOn = true;
                    }
                    if ((tempX >= 250 && tempX <= 350) && tempY >= 348 && tempY <= 366 && login) {
                        conectionInfo = true;
                        login = false;
                        usernameOn = false;
                        passwordOn = false;
                    }
                    if ((tempX >= 250 && tempX <= 350) && tempY >= 327 && tempY <= 341 && login) {
                        usernameOn = false;
                        passwordOn = true;
                    }
                    if ((tempX >= 250 && tempX <= 350) && tempY >= 287 && tempY <= 301 && login) {
                        passwordOn = false;
                        usernameOn = true;
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
                drawString("Username:", 260, 280);
                if (usernameOn) {
                    glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                } else {
                    glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                }
                Username.draw();
                glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                drawString(username, 260, 300);
                drawString("Password:", 260, 320);
                if (passwordOn) {
                    glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                } else {
                    glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                }
                Password.draw();
                glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                String passwordTemp = "";
                for (int i = 0; i < password.length(); i++) {
                    passwordTemp = passwordTemp + "*";
                }
                drawString(passwordTemp, 260, 340);
                if ((mouseX >= 251 && mouseX <= 350) && mouseY >= 348 && mouseY <= 366) {
                    glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                } else {
                    glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                }
                Login.draw();
                drawString("Login", 272, 360);
                glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                drawShape(7, 20.0f, 15.0f, 100.0f, 100.0f);
            }
            if (boardDraw) {
                glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                square.draw();
            }
            if (conectionInfo) {
                if ((mouseX >= 251 && mouseX <= 350) && mouseY >= 348 && mouseY <= 366) {
                    glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                } else {
                    glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                }
                Login.draw();
                drawString("Login", 272, 360);
                drawString("Host:", 260, 280);
                if (hostOn) {
                    glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                } else {
                    glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                }
                Username.draw();
                glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                drawString(host, 260, 300);
                drawString("Port:", 260, 320);
                if (portOn) {
                    glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                } else {
                    glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                }
                Password.draw();
                drawString(Port + "", 260, 340);
            }
            glfwSwapBuffers(window);
        }
    }
}
