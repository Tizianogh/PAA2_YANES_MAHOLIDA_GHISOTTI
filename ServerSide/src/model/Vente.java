package model;

import java.util.UUID;

public class Vente {
    private String proprietaire;
    private String libelle;
    private float prix;
    private String encherisseur;
    private int idVente;
    private static int id = 0;

    public Vente(float prixBase, String libelle, String proprietaire) {
        this.idVente = ++id;
        this.prix = prixBase;
        this.libelle = libelle;
        this.proprietaire = proprietaire;
        this.encherisseur = null;
    }

    public int getId() {
        return this.idVente;
    }

    public String getLibelle() {
        return libelle;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float nouveauPrix) {
        this.prix = nouveauPrix;
    }

    public void setEncherisseur(String pseudo) {
        this.encherisseur = pseudo;
    }

    @Override
    public String toString() {
        String inter = "/";
        if (encherisseur != null) inter = encherisseur;
        return libelle + ", " + prix + ", " + proprietaire + ", " + inter;
    }
}
