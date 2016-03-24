package me.moocow9m.www.ConnectFour.connection;

import java.net.Socket;
import java.io.PrintWriter;
public class Connect {
    private static Socket socket;
    private static PrintWriter printWriter;
    public static void connect(String host, int port)
    {
        try
        {
            socket = new Socket(host, port);
            socket.setKeepAlive(true);
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            printWriter.println("Hello Socket");
            printWriter.println("EYYYYYAAAAAAAA!!!!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
