/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Compte implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    private Long id; //le numero du compte d'etat 
    private String designation; // smia dl compte
    private BigDecimal montantMax;  // le montant a ne pas deppaser
    @OneToMany(mappedBy = "compte")
    private List<CategorieProduit> categorieProduits;
    
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public BigDecimal getMontantMax() {
        
        return montantMax;
    }

    public void setMontantMax(BigDecimal montantMax) {
        this.montantMax = montantMax;
    }

    public List<CategorieProduit> getCategorieProduits() {
        return categorieProduits;
    }

    public void setCategorieProduits(List<CategorieProduit> categorieProduits) {
        this.categorieProduits = categorieProduits;
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
        if (!(object instanceof Compte)) {
            return false;
        }
        Compte other = (Compte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.designation;
    }

}
