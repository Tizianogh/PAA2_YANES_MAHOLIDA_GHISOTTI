package server;

import model.Gestionnaire;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadServer extends Thread {
    private ServerSocket socket;
    private Gestionnaire gestionnaire;
    private int port;

    ThreadServer(ServerSocket socket, Gestionnaire gestionnaire, int port) {
        this.socket = socket;
        this.gestionnaire = gestionnaire;
        this.port = port;
    }

    public void run() {
        while (true) {
            Thread server = null;
            Socket socketClient = null;
            try {
                socketClient = socket.accept();
                server = new HandleServer(socketClient, gestionnaire);
                System.out.println("\n⚠ Un nouveau client vient de se connecter.. \n"+
                        "Saisissez 1 pour voir les ventes en cours, 2 pour l'historique des ventes : ");
                System.out.print("> ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            server.start();
        }
    }
}
