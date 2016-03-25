package me.moocow9m.www.ConnectFour.connection;

import me.moocow9m.www.ConnectFour.Main;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connect extends Thread {
    private static Socket socket;
    private static ObjectOutputStream output;
    private String host;
    private int port;

    public Connect(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        connect();
    }

    void connect() {
        try {
            socket = new Socket(host, port);
            socket.setKeepAlive(true);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(Main.user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
