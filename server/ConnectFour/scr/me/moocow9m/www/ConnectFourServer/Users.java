package me.moocow9m.www.ConnectFourServer;

import me.moocow9m.www.ConnectFour.connection.GameData;
import me.moocow9m.www.ConnectFour.connection.UserData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * This object handles the execution for a single user.
 */
public class Users {
    private static final int USER_THROTTLE = 200;
    private Socket socket;
    private boolean connected;
    private Inport inport;

    /**
     * Creates a new Client User with the socket from the newly connected client.
     *
     * @param newSocket The socket from the connected client.
     */
    public Users(Socket newSocket) {
        // Set properties
        socket = newSocket;
        connected = true;
        // Get input
        inport = new Inport();
        inport.start();
    }

    /**
     * Gets the connection status of this user.
     *
     * @return If this user is still connected.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Purges this user from connection.
     */
    public void purge() {
        // Close everything
        try {
            connected = false;
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not purge " + socket + ".");
        }
    }

    /**
     * Returns the String representation of this user.
     *
     * @return A string representation.
     */
    public String toString() {
        return socket.toString();
    }

    /**
     * Handles all incoming data from this user.
     */
    private class Inport extends Thread {
        private ObjectInputStream in;

        public void run() {
            // Open the InputStream
            try {
                UserData userData = new UserData();
                GameData gameData = new GameData();
                in = new ObjectInputStream(socket.getInputStream());
                System.out.println(in.readObject().getClass().getName());
                if (in.readObject().getClass().getName().equals("me.moocow9m.www.ConnectFour.connection.UserData")) {
                    userData = (UserData) in.readObject();
                } else {
                    gameData = (GameData) in.readObject();
                }
                if (gameData != null) {

                } else {
                    if (userData.getVersion() != 0.01) {
                        purge();
                    }
                }
            } catch (Exception e) {
                System.out.println("Could not get input stream from " + toString());
                e.printStackTrace();
                return;
            }
            // Announce
            System.out.println(socket + " has connected input.");
            // Enter process loop
            while (true) {
                // Sleep
                try {
                    Thread.sleep(USER_THROTTLE);
                } catch (Exception e) {
                    System.out.println(toString() + " has input interrupted.");
                }
            }
        }
    }
}
