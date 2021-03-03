package server;

import model.Gestionnaire;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class MainServer {
    private static final int PORT = 9000;

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(PORT);
        Gestionnaire enchere = new Gestionnaire();

        ThreadServer server = new ThreadServer(socket, enchere, PORT);

        server.start();

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Saisissez 1 pour voir les ventes en cours, 2 pour l'historique des ventes : ");
            System.out.print("> ");

            if (sc.hasNextInt()) {
                int choix = sc.nextInt();
                if (choix == 1) {
                    System.out.println("-----------------------");
                    System.out.println(enchere.lesVentes());
                    System.out.println("-----------------------");
                } else if (choix == 2) {
                    System.out.println("-----------------------");
                    System.out.println(enchere.historique());
                    System.out.println("-----------------------");
                } else {
                    System.out.println("Saisie incorrecte");
                }
            } else {
                System.out.println("Saisie incorrecte");
            }

        }
    }
}

