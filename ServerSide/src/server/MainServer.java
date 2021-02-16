package server;

import model.Gestionnaire;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    private static final int PORT = 9000;

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(PORT);
        Gestionnaire enchere = new Gestionnaire();

        while (true) {
            System.out.println("[SERVER] EN ATTENTE");
            Socket socketClient = socket.accept();
            System.out.println("[SERVER] NOUVEAU CLIENT");

            Thread server = new HandleServer(socketClient, enchere);

            server.start();
        }

    }
}
