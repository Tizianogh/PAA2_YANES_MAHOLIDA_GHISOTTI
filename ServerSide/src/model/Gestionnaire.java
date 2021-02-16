package model;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class Gestionnaire {

    ConcurrentLinkedQueue<Vente> ventes = new ConcurrentLinkedQueue<Vente>();
    ConcurrentSkipListSet<String> users = new ConcurrentSkipListSet<>();

    public synchronized Vente newVente(Vente vente) {
        this.ventes.add(vente);
        return vente;
    }


    public synchronized String toString() {
        String chaine = "";
        int id = 0;
        for (Vente v : ventes) {
            chaine = chaine + (id++) + "- " + v + "\n";
        }
        return chaine;
    }

    public synchronized Boolean newUser(String pseudo) {
        if (users.contains(pseudo)) {
            return false;
        } else {
            users.add(pseudo);
            return true;
        }
    }
}