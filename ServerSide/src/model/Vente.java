package model;

import java.util.UUID;

public class Vente {
    private String proprietaire;
    private UUID idVente;
    private String libelle;
    private int prix;
    private String encherisseur;

    public Vente(int prixBase, String libelle, String proprietaire) {
        this.encherisseur = encherisseur;
        this.idVente = UUID.randomUUID();
        this.prix = prixBase;
        this.libelle = libelle;
        this.proprietaire = proprietaire;
        this.encherisseur = null;
    }

    public UUID getIdVente() {
        return idVente;
    }

    public String getLibelle() {
        return libelle;
    }

    public int getPrix() {
        return prix;
    }

    private void setPrix(int nouveauPrix) {
        if (nouveauPrix > this.prix) {
            this.prix = nouveauPrix;
        } else {
            System.out.println("Le prix est en dessous de la mise autorisée");
        }
    }

    @Override
    public String toString() {
        String inter = "/";
        if (encherisseur != null) inter = encherisseur;
        return libelle + ", " + prix + ", " + proprietaire + ", " + inter;
    }
}
