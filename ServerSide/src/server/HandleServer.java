package server;

import model.ListeDesVentes;
import model.Vente;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class HandleServer extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private ListeDesVentes enchere;

    public HandleServer(Socket socket, ListeDesVentes enchere) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        this.enchere = enchere;
    }

    String menu = "test";

    private String messageClient(int param) {
        String msg = "";
        switch (param) {
            case 0:
                msg = "Veuillez entrer un pseudo s'il vous plaît : ";
                break;
            case 1:
                msg =   "-----------------------------------------------------------------\n" +
                        "Veuillez saisir un chiffre correspondant au numéro de l'action : \n" +
                        "1- Créer une vente\n" +
                        "2- Afficher les ventes en cours\n" +
                        "3- Enchérir\n" +
                        "4- Historique des ventes terminees\n" +
                        "5- Quitter";
                break;
        }
        return msg;
    }

    public void run() {
        try {
            out.println(this.messageClient(0));
            String pseudo = in.readLine();

            while (true) {
                out.println(this.messageClient(1));
                String command = in.readLine();

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
