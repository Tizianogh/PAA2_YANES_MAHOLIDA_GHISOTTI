package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    private static final int PORT = 9000;
    private int port;

    private MainServer(){
        this.port=PORT;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(PORT);

        while (true) {
            System.out.println("[SERVER] EN ATTENTE");
            Socket socketClient = socket.accept();
            System.out.println("[SERVER] NOUVEAU CLIENT \n");

            Thread server = new HandleServer(socketClient, new MainServer());

            server.start();
        }

    }
}
