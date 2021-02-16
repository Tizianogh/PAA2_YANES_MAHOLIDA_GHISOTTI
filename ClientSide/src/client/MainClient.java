package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainClient extends Thread {
    private static final int SERVER_PORT = 9000;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println(fromServer.readLine());
        System.out.println("> ");
        String pseudo = keyboard.readLine();
        out.println(pseudo);

        while (true) {
            do {
                System.out.println(fromServer.readLine());
            } while (fromServer.ready());

            System.out.println("> ");
            String command = keyboard.readLine();

            if (command.equals("quit")) {
                break;
            }
            out.println(command);


        }
        socket.close();
    }
}