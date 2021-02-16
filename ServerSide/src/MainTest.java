import model.ListeDesVentes;
import model.Vente;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class MainTest {

    public static void main(String[] args) {
        ListeDesVentes ventes = new ListeDesVentes();

        ventes.newVente(new Vente(10,"testLibelle","Patrick"));
        ventes.newVente(new Vente(2,"testLibelle2","Patrick2"));
        ventes.newVente(new Vente(30,"testLibelle3","Patrick3"));
        ventes.newVente(new Vente(40,"testLibelle4","Patrick4"));

        System.out.println(ventes);
    }
}
