package server;

import model.Gestionnaire;

import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;


public class HandleServer extends Thread {

    private BufferedReader in;
    public PrintWriter out; //*
    private Socket socket;
    private Gestionnaire gestionnaire;
    private String name; //user
    DecimalFormat fmt = new DecimalFormat("#,##0.00#");

    /**
     * Le constructeur prend en paramètre la classe gestionnaire.
     * Cette classe contient toutes nos listes synchronisées ainsi
     * que nos différentes méthodes elles aussi synchronisées.
     *
     * @param socket
     * @param gestionnaire
     * @throws IOException
     */
    public HandleServer(Socket socket, Gestionnaire gestionnaire) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        this.gestionnaire = gestionnaire;
    }

    /**
     * Méthode qui gère tous les messages que peut recevoir le serveur au
     * travers des différentes étapes.
     *
     * @param param
     * @return String
     */
    private String messageClient(String param) {
        String msg = "";
        switch (param) {
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
            case "begin":
                msg = "Avez-vous un compte ? (Y/N)";
                break;
            case "connexion":
                msg = "Saisissez le pseudo de votre compte :";
                break;
            case "pseudoInexistant":
                msg = "❎ Compte inexistant, veuillez ressaisir votre pseudo";
                break;
            case "nouveauCompte":
                msg = "Veuillez entrer un pseudo s'il vous plaît : ";
                break;
            case "pseudoExistant":
                msg = "❎ Pseudo déjà existant, veuillez en entrer un autre";
                break;
            case "libelleVente":
                msg = "Saisissez le libelle de votre vente";
                break;
            case "prixBase":
                msg = "Saisissez le prix de base";
                break;
            case "errorFloat":
                msg = "❎ Saisie incorrect, veuillez ressaisir un float : ";
                break;
            case "idVenteEnchere":
                msg = "Saisissez le numéro de la vente : ";
                break;
            case "idVenteInexistant":
                msg = "❎ Numéro vente incorrect.";
                break;
            case "errorID":
                msg = "❎ Saisie incorrect, veuillez ressaisir un int : ";
                break;
            case "prorpietaireEgaleEnrechiseur":
                msg = "❎ Erreur : vous êtes le détenteur de cette vente, il vous est impossible d'enchérir.";
                break;
            case "prixEnchere":
                msg = "Saisissez le prix souhaité, il doit être égale ou supérieur à ";
                break;
            case "prixEnchereBas":
                msg = "❎ Prix insuffisant, veuillez en entrer un autre supérieur à ";
                break;
            case "enchereReussie":
                msg = "Enchère effectuée avec succès!";
                break;
            case "deconnexion":
                msg = "Vous pouvez quitter l'application. Aurevoir 👋";
                break;
            default:
                msg = "❎ Saisie incorrecte";
                break;
        }
        return msg;
    }

    private void connexion() throws IOException {
        String pseudo;
        Boolean first = true;
        do {
            if (first) {
                out.println(this.messageClient("connexion"));
                first = false;
            } else {
                out.println(this.messageClient("pseudoInexistant"));
            }
            pseudo = in.readLine();
        } while (!gestionnaire.connexionUser(pseudo, out));
        //Boucle jusqu'à avoir un pseudo existant
        this.name = pseudo;

        this.gestionnaire.newThread(name, this);
    }

    public void creationCompte() throws IOException {
        String pseudo;
        Boolean first = true; //first ittération ou pas
        do {
            if (first) {
                out.println(this.messageClient("nouveauCompte"));
                first = false;
            } else {
                out.println(this.messageClient("pseudoExistant"));
            }
            pseudo = in.readLine();
        } while (!gestionnaire.newUser(pseudo, this.socket.getInetAddress(), out)); //Boucle jusqu'à avoir un pseudo inexistant

        this.name = pseudo;

        this.gestionnaire.newThread(name, this);
    }

    public void creationVente() throws IOException {
        out.println(this.messageClient("libelleVente"));
        String libelle = in.readLine();
        out.println(this.messageClient("prixBase"));
        float prix;
        //Vérification de l'entrée d'un float.
        while (true) {
            try {
                prix = Float.parseFloat(in.readLine());
                break;
            } catch (Exception e) {
                out.println(this.messageClient("errorFloat"));
            }
        }
        String vente = gestionnaire.newVente(prix, libelle, this.name);
        out.println(vente);
    }

    public void enchere() throws IOException {
        out.println(gestionnaire.lesVentes());
        int reponse = 2;
        int id;
        do { // lecture d'un id vente correct
            if (reponse == 0) out.println(this.messageClient("idVenteInexistant"));
            else if (reponse == 1) out.println(this.messageClient("prorpietaireEgaleEnrechiseur"));

            out.println(this.messageClient("idVenteEnchere"));

            //Vérification de l'entrée d'un id.
            while (true) {
                try {
                    id = Integer.parseInt(in.readLine());
                    break;
                } catch (Exception e) {
                    out.println(this.messageClient("errorID"));
                }
            }
            reponse = gestionnaire.idVenteCorrect(id, this.name);
        } while (reponse != 2);

        float nouveauPrix;
        Boolean first = true;
        do { // lecture d'un prix suffisant
            if (first) {
                out.println(this.messageClient("prixEnchere") + fmt.format(gestionnaire.getPrix(id) * 1.1));
                first = false;
            } else
                out.println(this.messageClient("prixEnchereBas") + fmt.format(gestionnaire.getPrix(id) * 1.1));
            //Vérification de l'entrée d'un float.
            while (true) {
                try {
                    nouveauPrix = Float.parseFloat(in.readLine());
                    break;
                } catch (Exception e) {
                    out.println(this.messageClient("errorFloat"));
                }
            }


        } while (!gestionnaire.encherir(id, nouveauPrix, this.name));//màj du prix

        out.println(this.messageClient("enchereReussie"));
    }

    /**
     * Methode run de notre thread.
     * C'est ici que nous gérons tous les cas d'utilisations
     * de notre programme.
     */
    public void run() {
        try {
            while (true) { //boucle tant qu'il n'y pas de bonne réponse (y ou n)
                out.println(this.messageClient("begin"));
                String choixConnexion = in.readLine();
                if (choixConnexion.equalsIgnoreCase("y")) {
                    this.connexion();
                    break;
                } else if (choixConnexion.equalsIgnoreCase("n")) {
                    this.creationCompte();
                    break;
                } else {
                    out.println(this.messageClient("error"));
                }
            }
            out.println(this.gestionnaire.lesVentes());
            out.println(this.messageClient("menu"));

            while (true) {
                String command = in.readLine();
                switch (command) {
                    case "1":
                        this.creationVente();
                        break;
                    case "2":
                        out.println(gestionnaire.lesVentes());
                        break;
                    case "3":
                        this.enchere();
                        break;
                    case "4":
                        out.println(gestionnaire.historique());
                        break;
                    case "5":
                        out.println(this.messageClient("menu"));
                        break;
                    case "6":
                        out.println(this.messageClient("deconnexion"));
                        gestionnaire.removeThread(this.name);
                        this.socket.close();
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
