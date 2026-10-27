
package ma.fstt.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int idCommande;
    private LocalDateTime dateCommande;
    private Client client;
    private List<LigneDeCommande> lignes;

    public Commande() {
        this.dateCommande = LocalDateTime.now();
        this.lignes = new ArrayList<>();
    }

    public Commande(int idCommande, LocalDateTime dateCommande,
                    Client client) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.client = client;
        this.lignes = new ArrayList<>();
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LigneDeCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneDeCommande> lignes) {
        this.lignes = lignes;
    }

    public void ajouterLigne(LigneDeCommande ligne) {
        this.lignes.add(ligne);
    }
}
