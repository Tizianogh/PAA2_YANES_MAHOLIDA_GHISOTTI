package server;

import model.Gestionnaire;
import model.Vente;

import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class HandleServer extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private Gestionnaire gestionnaire;

    public HandleServer(Socket socket, Gestionnaire gestionnaire) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        this.gestionnaire = gestionnaire;
    }

    private String messageClient(String param) {
        String msg = "";
        switch (param) {
            case "pseudo":
                msg = "Veuillez entrer un pseudo s'il vous plaît : ";
                break;
            case "pseudoExistant":
                msg = "Pseudo déjà existant, veuillez en entrer un autre";
                break;
            case "menu":
                msg = "-----------------------------------------------------------------\n" +
                        "Veuillez saisir un chiffre correspondant au numéro de l'action : \n" +
                        "1- Créer une vente\n" +
                        "2- Afficher les ventes en cours\n" +
                        "3- Enchérir\n" +
                        "4- Historique des ventes terminees\n" +
                        "5- Menu\n" +
                        "6- Quitter";
                break;
            case "libelleVente":
                msg = "Saisissez le libelle de votre vente";
                break;
            case "prixBase":
                msg = "Saisissez le prix de base";
                break;
            default:
                msg = "Saisi incorrect";
                break;
        }
        return msg;
    }

    public void run() {
        try {
            Boolean first = true;
            String pseudo;
            do {
                if (first) {
                    out.println(this.messageClient("pseudo"));
                    first = false;
                } else {
                    out.println(this.messageClient("pseudoExistant"));
                }
                pseudo = in.readLine();
            } while (!gestionnaire.newUser(pseudo));

            this.setName(pseudo);

            out.println(this.messageClient("menu"));
            while (true) {
                String command = in.readLine();

                switch (command) {
                    case "1":
                        out.println(this.messageClient("libelleVente"));
                        String libelle = in.readLine();
                        out.println(this.messageClient("prixBase"));
                        int prix = Integer.parseInt(in.readLine());
                        Vente vente = gestionnaire.newVente(new Vente(prix, libelle, this.getName()));
                        out.println(vente);
                        break;
                    case "2":
                        out.println(gestionnaire.toString());
                        break;
                    case "5":
                        out.println(this.messageClient("menu"));
                        break;
                    default:
                        out.println(this.messageClient("error"));
                        break;
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
