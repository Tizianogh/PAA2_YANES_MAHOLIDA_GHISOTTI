package model;

import server.HandleServer;

import java.util.Map;
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
    ConcurrentSkipListMap<Integer, Vente> ventes = new ConcurrentSkipListMap<>();
    ConcurrentSkipListSet<String> users = new ConcurrentSkipListSet<String>();
    ConcurrentSkipListMap<String, HandleServer> mapThreads = new ConcurrentSkipListMap<>();

    /**
     * Cette méthode sert à rajouter une vente dans la liste synchronisé "ventes"
     *
     * @param vente
     * @return String
     */
    public synchronized String newVente(Vente vente) {
        this.ventes.put(vente.getId(), vente);
        return "L'objet suivant : " + vente.getLibelle() + " a bien été mis en vente";
    }

    /**
     * Méthode permettant d'afficher la liste des objets actuellement en enchère.
     *
     * @return String
     */
    public synchronized String lesVentes() {
        String chaine = "";
        for (Map.Entry v : ventes.entrySet()) {
            chaine = chaine + v + "\n";
        }
        return "Liste des ventes :\nId, intitulé, meilleure offre, vendeur, meilleur enchérisseur\n" + chaine;
    }

    public synchronized Boolean idVenteExistant(int id) {
        return ventes.containsKey(id);
    }

    public synchronized float getPrix(int id) {
        return ventes.get(id).getPrix();
    }

    public synchronized Boolean encherir(int id, float nouveauPrix, String pseudo) {
        if (this.getPrix(id) * 1.1 > nouveauPrix) {
            return false;
        } else {
            ventes.get(id).setPrix(nouveauPrix);
            ventes.get(id).setEncherisseur(pseudo);
            return true;
        }
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

    /**
     * @param pseudo
     * @return True if pseudo exist, false else
     */
    public synchronized Boolean connexionUser(String pseudo) {
        if (users.contains(pseudo)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * On récupère la thread et son pseudo attitré
     * On stock dans ancienThread la thread qui a le même pseudo
     * Si elle existe, on la supprime de la map et on la stop
     * puis on stock la nouvelle thread dans la map
     *
     * @param pseudo
     * @param thread
     */
    public synchronized void newThread(String pseudo, HandleServer thread) {
        HandleServer ancienThread = mapThreads.get(pseudo);

        if (ancienThread != null) {
            mapThreads.remove(pseudo);
            ancienThread.stop();
        }
        mapThreads.put(pseudo, thread);
    }

}
