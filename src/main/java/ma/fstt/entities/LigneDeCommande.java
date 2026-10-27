
package ma.fstt.entities;

import java.math.BigDecimal;

public class LigneDeCommande {
    private int idLigne;
    private Commande commande;
    private Produit produit;
    private int quantite;
    private BigDecimal prixUnitaire;

    public LigneDeCommande() {
    }

    public LigneDeCommande(int idLigne, Commande commande,
                           Produit produit, int quantite,
                           BigDecimal prixUnitaire) {
        this.idLigne = idLigne;
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public int getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getSousTotal() {
        return prixUnitaire.multiply(
                BigDecimal.valueOf(quantite)
        );
    }
}
