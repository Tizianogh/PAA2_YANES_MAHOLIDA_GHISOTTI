package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class ListeDesVentes {
    private ArrayList<Vente> ventes = new ArrayList<>();

    public synchronized void newVente(Vente vente) {
        this.ventes.add(vente);
        Collections.synchronizedCollection(ventes);
    }

    public synchronized String toString() {
        String chaine = "";
        int id = 0;
        for (Vente v : ventes) {
            chaine = chaine + (id++) + "- " + v + "\n";

        }
        return chaine;
    }
}
