package model;

import server.HandleServer;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class Gestionnaire {

    /**
     * La liste "ventes" s'occupent de stocker et synchronisé
     * tous les objets des enchères
     * La liste users est une "base de données" avec tous les pseudos
     * des threads lors d'une session. Nous utilisons cette liste pour
     * effectuer la vérification qu'un utilisateur est unique ou s'il
     * souhaite se connecter avec son compte déjà existant.
     */
    ConcurrentLinkedQueue<Vente> ventes = new ConcurrentLinkedQueue<Vente>();
    ConcurrentSkipListSet<String> users = new ConcurrentSkipListSet<String>();
    ConcurrentSkipListMap<String, HandleServer> mapThreads = new ConcurrentSkipListMap<>();

    /**
     * Cette méthode sert à rajouter une vente dans la liste synchronisé "ventes"
     *
     * @param vente
     * @return String
     */
    public synchronized String newVente(Vente vente) {
        this.ventes.add(vente);
        return "L'objet suivant : " + vente.getLibelle() + " a bien été mis en vente";
    }

    /**
     * Méthode permettant d'afficher la liste des objets actuellement en enchère.
     *
     * @return String
     */
    public synchronized String toString() {
        String chaine = "";
        for (Vente v : ventes) {
            chaine = chaine + v + "\n";
        }
        return "Liste des ventes :\nIntitulé, meilleure offre, vendeur, meilleur enchérisseur\n" + chaine;
    }

    /**
     * Méthode permettant de vérifier que le pseudo entré par
     * l'utilisateur n'est pas déjà utilisé. Si ce n'est pas le cas,
     * alors on autorise son utilisation et on l'ajoute à liste
     * synchronisé "users" pour pouvoir tester avec les futurs
     * utilisateurs
     *
     * @param pseudo
     * @return Boolean
     */
    public synchronized Boolean newUser(String pseudo) {
        if (users.contains(pseudo)) {
            return false;
        } else {
            users.add(pseudo);
            return true;
        }
    }

    public synchronized Boolean connexionUser(String pseudo) {
        if (users.contains(pseudo)) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized void newThread(String pseudo, HandleServer thread) {
        HandleServer ancienThread = mapThreads.get(pseudo);

        if (ancienThread != null) {
            mapThreads.remove(pseudo);
            ancienThread.stop();
        }
        mapThreads.put(pseudo, thread);
    }
}
