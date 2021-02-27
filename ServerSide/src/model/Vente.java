package model;


public class Vente {
    private int idVente;
    private String libelle;
    private float prix;
    private User proprietaire;
    private User encherisseur;
    private static int id = 0;

    public Vente(float prixBase, String libelle, User proprietaire) {
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

    public User getProprietaire() {
        return this.proprietaire;
    }

    public User getEncherisseur() {
        return this.encherisseur;
    }

    public void setPrix(float nouveauPrix) {
        this.prix = nouveauPrix;
    }

    public void setEncherisseur(User user) {
        this.encherisseur = user;
    }

    @Override
    public String toString() {
        String inter = "/";
        if (encherisseur != null) inter = encherisseur.getPseudo();
        return libelle + ", " + prix + ", " + proprietaire.getPseudo() + ", " + inter;
    }

    public String toStringHistorique() {
        return libelle + ", " + prix + ", " + proprietaire.getPseudo() + ", " + proprietaire.getAdressIP() + ", " + encherisseur.getPseudo() + ", " + encherisseur.getAdressIP();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vente)) return false;
        Vente vente = (Vente) o;
        return idVente == vente.idVente;
    }

}
