package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HandleServer extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private MainServer server;

    public HandleServer(Socket socket, MainServer server) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String command = in.readLine();

                if (command.equals("pseudo")) {
                    System.out.println(out.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
