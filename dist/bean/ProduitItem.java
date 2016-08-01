/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ProduitItem implements Serializable {

    @OneToOne(mappedBy = "produitItem")
    private DemandeEntreeStockItem demandeEntreeStockItem;

    @ManyToOne
    private ExpressionBesoin expressionBesoin;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Produit produit;
    private int qteDemande;
    private int qteConfirm;

    public DemandeEntreeStockItem getDemandeEntreeStockItem() {
        return demandeEntreeStockItem;
    }

    public void setDemandeEntreeStockItem(DemandeEntreeStockItem demandeEntreeStockItem) {
        this.demandeEntreeStockItem = demandeEntreeStockItem;
    }

    public int getQte() {
        return qteDemande;
    }

    public void setQteDemande(int qteDemande) {
        this.qteDemande = qteDemande;
    }

    public int getQteConfirm() {
        return qteConfirm;
    }

    public void setQteConfirm(int qteConfirm) {
        this.qteConfirm = qteConfirm;
    }

    public Produit getProduit() {
        if (produit == null) {
            produit = new Produit();
        }
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

}
