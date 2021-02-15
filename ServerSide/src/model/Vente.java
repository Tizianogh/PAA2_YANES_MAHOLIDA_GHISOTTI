package model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Vente {
    private String proprietaire;
    private UUID idVente;
    private String libelle;
    private float prix;

    public Vente(float prixBase, String libelle, String proprietaire) {
        this.idVente = UUID.randomUUID();
        this.prix = prix;
        this.libelle = libelle;
        this.proprietaire = proprietaire;
    }

    public UUID getIdVente() {
        return idVente;
    }

    public String getLibelle() {
        return libelle;
    }

    public float getPrix() {
        return prix;
    }

    private void setPrix(float nouveauPrix) {
        if (nouveauPrix > this.prix) {
            this.prix = nouveauPrix;
        } else {
            System.out.println("Le prix est en dessous de la mise autorisée");
        }
    }

    @Override
    public String toString() {
        return "Vente{" +
                "proprietaire='" + proprietaire + '\'' +
                ", idVente=" + idVente +
                ", libelle='" + libelle + '\'' +
                ", prix=" + prix +
                '}';
    }
}
