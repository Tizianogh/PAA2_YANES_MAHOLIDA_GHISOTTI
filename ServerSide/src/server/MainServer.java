package server;

import model.Gestionnaire;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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

            Scanner sc = new Scanner(System.in);
            System.out.println("Saisissez 1 pour voir les ventes en cours, 2 pour l'historique des ventes : ");
            if (sc.hasNextInt()) {
                int choix = sc.nextInt();
                if (choix == 1) {
                    System.out.println(enchere.lesVentes());
                } else if (choix == 2) {
                    System.out.println(enchere.historique());
                } else {
                    System.out.println("Saisie incorrecte");
                }
            } else{
                System.out.println("Saisie incorrecte");
            }
        }
    }
}
