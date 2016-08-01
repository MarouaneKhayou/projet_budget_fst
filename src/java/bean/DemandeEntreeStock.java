/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.Fournisseur;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class DemandeEntreeStock implements Serializable {

    @OneToMany(mappedBy = "demandeEntreeStock")
    private List<Reception> receptions;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int type; // AO, BC
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreation;
    private BigDecimal montantTotal;
    private BigDecimal tva;
    @OneToOne
    private Fournisseur fournisseur;
    @OneToMany(mappedBy = "demandeEntreeStock")
    private List<DemandeEntreeStockItem> demandeEntreeStockItems;

    private boolean terminee;

    private Boolean livred;

    public Boolean getLivred() {
        return livred;
    }

    public void setLivred(Boolean livred) {
        this.livred = livred;
    }

    public List<Reception> getReceptions() {
        return receptions;
    }

    public void setReceptions(List<Reception> receptions) {
        this.receptions = receptions;
    }

    public List<DemandeEntreeStockItem> getDemandeEntreeStockItems() {
        if (demandeEntreeStockItems == null) {
            demandeEntreeStockItems = new ArrayList<>();
        }
        return demandeEntreeStockItems;
    }

    public void setDemandeEntreeStockItems(List<DemandeEntreeStockItem> demandeEntreeStockItems) {
        this.demandeEntreeStockItems = demandeEntreeStockItems;
    }

    public boolean isTerminee() {
        return terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }

    public String getState() {
        if(terminee)
            return "Terminee";
        return "En cours";
    }

    public Long getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public BigDecimal getMontantTotal() {
        if (montantTotal == null) {
            montantTotal = new BigDecimal(0);
        }
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public BigDecimal getTva() {
        if (tva == null) {
            tva = new BigDecimal(0);
        }
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public Fournisseur getFournisseur() {
        if (fournisseur == null) {
            fournisseur = new Fournisseur();
        }
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DemandeEntreeStock)) {
            return false;
        }
        DemandeEntreeStock other = (DemandeEntreeStock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.DemandeEntreeStock[ id=" + id + " ]";
    }

}
