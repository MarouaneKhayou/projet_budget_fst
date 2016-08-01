/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author AIMAD
 */
@Entity
public class EntiteProduitItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long qteCommande;
    private Long qteRecu;
    @ManyToOne
    private EntiteProduit entiteProduit;
    @OneToOne
    private EntiteAdministratif entiteAdministratif;

    @OneToMany(mappedBy = "entiteProduitItem")
    private List<Livraison> livraisons;

    private Boolean livred;
    private Long qteLivre;

    public List<Livraison> getLivraisons() {
        return livraisons;
    }

    public void setLivraisons(List<Livraison> livraisons) {
        this.livraisons = livraisons;
    }

    public Boolean getLivred() {
        return livred;
    }

    public void setLivred(Boolean livred) {
        this.livred = livred;
    }

    public Long getQteLivre() {
        return qteLivre;
    }

    public void setQteLivre(Long qteLivre) {
        this.qteLivre = qteLivre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EntiteProduit getEntiteProduit() {
        if (entiteAdministratif == null) {
            entiteAdministratif = new EntiteAdministratif();
        }
        return entiteProduit;
    }

    public void setEntiteProduit(EntiteProduit entiteProduit) {
        this.entiteProduit = entiteProduit;
    }

    public EntiteAdministratif getEntiteAdministratif() {
        return entiteAdministratif;
    }

    public void setEntiteAdministratif(EntiteAdministratif entiteAdministratif) {
        this.entiteAdministratif = entiteAdministratif;
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
        if (!(object instanceof EntiteProduitItem)) {
            return false;
        }
        EntiteProduitItem other = (EntiteProduitItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.EntiteProduitItem[ id=" + id + " ]";
    }

}
