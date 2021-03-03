package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainClient {
    private static final int SERVER_PORT = 9000;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        System.out.println(fromServer.readLine());
        String pseudo = keyboard.readLine();
        out.println(pseudo);

        Thread sendToServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String command = null;
                    try {
                        command = keyboard.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println(command);
                }
            }
        });
        sendToServer.start();

        Thread receiveFromServer = new Thread(new Runnable() {
            String msg;

            @Override
            public void run() {
                try {
                    msg = fromServer.readLine();

                    while (msg != null) {
                        System.out.println(msg);

                        msg = fromServer.readLine();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        receiveFromServer.start();
    }
}
