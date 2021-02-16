import model.Gestionnaire;
import model.Vente;

import java.util.Scanner;

public class MainTest {
    private static String messageClient(String param) {
        String msg = "";
        switch (param) {
            case "0":
                msg = "Veuillez entrer un pseudo s'il vous plaît : ";
                break;
            case "1":
                msg = "Pseudo déjà existant, veuillez en entrer un autre";
                break;
            case "2":
                msg = "-----------------------------------------------------------------\n" +
                        "Veuillez saisir un chiffre correspondant au numéro de l'action : \n" +
                        "1- Créer une vente\n" +
                        "2- Afficher les ventes en cours\n" +
                        "3- Enchérir\n" +
                        "4- Historique des ventes terminees\n" +
                        "5- Quitter";
                break;

            default:
                msg = "Saisi incorrect";
                break;
        }
        return msg;
    }

    public static void main(String[] args) {
        Gestionnaire ventes = new Gestionnaire();
        Scanner un = new Scanner(System.in);

        for (int z = 0; z < 3; z++) {
            Boolean first = true;
            String pseudo;
            int i = 0;
            do {
                i++;
                if (first) {
                    System.out.println(messageClient("0") + i);
                    first = false;
                } else {
                    System.out.println(messageClient("1") + i);
                }
                pseudo = un.next();
            } while (!ventes.newUser(pseudo));


            System.out.println("liste : " + ventes.toString());
        }
    }
}
