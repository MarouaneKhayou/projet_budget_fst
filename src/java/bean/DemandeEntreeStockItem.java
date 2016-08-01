/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author AIMAD
 */
@Entity
public class DemandeEntreeStockItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @OneToOne
    private ProduitItem produitItem;

    private Long qteCommande;
    private Long qteRecu;
    private Double montant;
    @ManyToOne
    private DemandeEntreeStock demandeEntreeStock;

    @OneToOne
    private EntiteProduit entiteProduit;

    private Long qteLivre;
    private Boolean livred;

    public Long getQteLivre() {
        return qteLivre;
    }

    public void setQteLivre(Long qteLivre) {
        this.qteLivre = qteLivre;
    }

    public Boolean getLivred() {
        return livred;
    }

    public void setLivred(Boolean livred) {
        this.livred = livred;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntiteProduit getEntiteProduit() {
        return entiteProduit;
    }

    public void setEntiteProduit(EntiteProduit entiteProduit) {
        this.entiteProduit = entiteProduit;
    }

    public ProduitItem getProduitItem() {
        if (produitItem == null) {
            produitItem = new ProduitItem();
        }
        return produitItem;
    }

    public void setProduitItem(ProduitItem produitItem) {
        this.produitItem = produitItem;
    }

    public DemandeEntreeStock getDemandeEntreeStock() {
        if (demandeEntreeStock == null) {
            demandeEntreeStock = new DemandeEntreeStock();
        }
        return demandeEntreeStock;
    }

    public void setDemandeEntreeStock(DemandeEntreeStock demandeEntreeStock) {
        this.demandeEntreeStock = demandeEntreeStock;
    }

    public Long getQteCommande() {
        return qteCommande;
    }

    public void setQteCommande(Long qteCommande) {
        this.qteCommande = qteCommande;
    }

    public Long getQteRecu() {
        return qteRecu;
    }

    public void setQteRecu(Long qteRecu) {
        this.qteRecu = qteRecu;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DemandeEntreeStockItem)) {
            return false;
        }
        DemandeEntreeStockItem other = (DemandeEntreeStockItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.DemandeEntreeStockItem[ id=" + id + " ]";
    }

}
