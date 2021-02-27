package model;


public class Vente {
    private int idVente;
    private String libelle;
    private float prix;
    private String proprietaire;
    private String encherisseur;
    private static int id = 0;

    public Vente(float prixBase, String libelle, String proprietaire) {
        this.idVente = ++id;
        this.prix = prixBase;
        this.libelle = libelle;
        this.proprietaire = proprietaire;
        this.encherisseur = "";
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

    public String getProprietaire() {
        return this.proprietaire;
    }

    public String getEncherisseur() {
        return this.encherisseur;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vente)) return false;
        Vente vente = (Vente) o;
        return idVente == vente.idVente;
    }

}
