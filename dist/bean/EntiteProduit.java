/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class EntiteProduit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long qteCommande;
    private Long qteRecu;
    @OneToMany(mappedBy = "entiteProduit")
    private List<EntiteProduitItem> entiteProduitItems;
    @OneToOne
    private DemandeEntreeStockItem demandeEntreeStockItem;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<EntiteProduitItem> getEntiteProduitItems() {
        if (entiteProduitItems == null) {
            entiteProduitItems = new ArrayList<>();
        }
        return entiteProduitItems;
    }

    public void setEntiteProduitItems(List<EntiteProduitItem> entiteProduitItems) {
        this.entiteProduitItems = entiteProduitItems;
    }

    public DemandeEntreeStockItem getDemandeEntreeStockItem() {
        if (demandeEntreeStockItem == null) {
            demandeEntreeStockItem = new DemandeEntreeStockItem();
        }
        return demandeEntreeStockItem;
    }

    public void setDemandeEntreeStockItem(DemandeEntreeStockItem demandeEntreeStockItem) {
        this.demandeEntreeStockItem = demandeEntreeStockItem;
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
        if (!(object instanceof EntiteProduit)) {
            return false;
        }
        EntiteProduit other = (EntiteProduit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.EntiteProduit[ id=" + id + " ]";
    }

}
