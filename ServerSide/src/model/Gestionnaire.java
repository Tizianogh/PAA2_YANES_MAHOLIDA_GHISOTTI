package model;

import server.HandleServer;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
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
    ConcurrentSkipListMap<Integer, Vente> historique = new ConcurrentSkipListMap<>();
    ConcurrentSkipListSet<String> users = new ConcurrentSkipListSet<String>();
    ConcurrentSkipListMap<String, HandleServer> mapThreads = new ConcurrentSkipListMap<>();
    Timer timer = new Timer();

    /**
     *
     * Cette méthode sert à rajouter une vente dans la liste synchronisée "ventes"
     *
     * @param prix
     * @param libelle
     * @param pseudo
     * @return
     */
    public synchronized String newVente(float prix, String libelle, String pseudo) {
        Vente vente = new Vente(prix, libelle, pseudo);
        timer.schedule(new VenteASupprimer(vente), 30000);
        this.ventes.put(vente.getId(), vente);
        return "La vente suivante : " + vente.getLibelle() + ", a bien été mise en vente";
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
    public synchronized String historique() {
        String chaine = "";
        for (Map.Entry v : historique.entrySet()){
            chaine = chaine + v + "\n";
        }
        return "Historique des ventes :\nId, intitulé, meilleure offre, vendeur, meilleur enchérisseur\n" + chaine;
    }

    public synchronized Boolean idVenteExistant(int id) {

        return ventes.containsKey(id);
    }

    public synchronized float getPrix(int id) {

        return ventes.get(id).getPrix();
    }

    /**
     *
     *  @param id     = id de la vente en question
     * @param pseudo = le pseudo de la thread en cours, càd celui de l'enchérisseur
     * @return 0 if id inexistant,  1 if proprietaire = encherisseur, 2 else (idVente correct)
     */
    public synchronized int idVenteCorrect(int id, String pseudo) {
        if (!ventes.containsKey(id)) return 0;
        else if (pseudo.equals(ventes.get(id).getProprietaire())) return 1;
        else return 2;
    }

    /**
     *
     * Met à jour le prix si le prix est suffisant
     *
     * @param id
     * @param nouveauPrix
     * @param pseudo
     * @return true if prix suffisant, false else
     */
    public synchronized Boolean encherir(int id, float nouveauPrix, String pseudo) {
        if (this.getPrix(id) * 1.1 > nouveauPrix) {
            return false;
        } else {
            Vente v = ventes.get(id);
            if (!v.getEncherisseur().isEmpty())
                mapThreads.get(v.getEncherisseur()).out.println(
                        "["+pseudo+
                                "] vient d'enchérir ["+nouveauPrix+
                                "] sur la vente de ["+v.getLibelle()+"]."
                );
            v.setPrix(nouveauPrix);
            v.setEncherisseur(pseudo);
            return true;
        }
    }

    /**
     *
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
     *
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
     *
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

    public class VenteASupprimer extends TimerTask {
        private Vente  vente;
        VenteASupprimer(Vente vente) {

            this.vente = vente;
        }

        @Override
        public void run() {
            if ( ! vente.getEncherisseur().isEmpty()) {
                System.out.println(vente.getEncherisseur());
                historique.put(vente.getId(),vente);
                mapThreads.get(vente.getProprietaire()).out.println("Votre vente de ["+vente.getLibelle()+"] a été remportée par ["+vente.getEncherisseur()+"] à ["+vente.getPrix()+"].");
                mapThreads.get(vente.getEncherisseur()).out.println("Félicitations ! vous rempotrer la vente de ["+vente.getLibelle()+"] à ["+vente.getPrix()+"].");
            } else {
                mapThreads.get(vente.getProprietaire()).out.println("Votre vente de ["+vente.getLibelle()+"] est terminée sans avoir trouvé preneur."); //*
            }
            ventes.remove(vente.getId());
        }
    }
}
