/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CompteItem implements Serializable {

    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @ManyToOne
    protected Compte compte;
    protected BigDecimal montantPaye;// li tpeyaa
    protected BigDecimal montantEngage; // lidayzin f commande b9ai matkhlsoch
    protected BigDecimal montantAffecte; // lkbir
    protected BigDecimal montantAffecteFonctionnement;
    protected BigDecimal montantAffecteInvestissement;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontantPaye() {
        if(montantPaye == null){
            montantPaye = new BigDecimal(0);
        }
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    public BigDecimal getMontantEngage() {
        if(montantEngage == null){
            montantEngage = new BigDecimal(0);
        }
        return montantEngage;
    }

    public void setMontantEngage(BigDecimal montantEngage) {
        this.montantEngage = montantEngage;
    }

    public BigDecimal getMontantAffecte() {
        if(montantAffecte == null){
            montantAffecte = new BigDecimal(0);
        }
        return montantAffecte;
    }

    public void setMontantAffecte(BigDecimal montantAffecte) {
        this.montantAffecte = montantAffecte;
    }

    public Compte getCompte() {
        if(compte == null){
            compte = new Compte();
        }
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public BigDecimal getMontantAffecteFonctionnement() {
        if(montantAffecteFonctionnement == null){
            montantAffecteFonctionnement = new BigDecimal(0);
        }
        return montantAffecteFonctionnement;
    }

    public void setMontantAffecteFonctionnement(BigDecimal montantAffecteFonctionnement) {
        this.montantAffecteFonctionnement = montantAffecteFonctionnement;
    }

    public BigDecimal getMontantAffecteInvestissement() {
        if(montantAffecteInvestissement == null){
            montantAffecteInvestissement = new BigDecimal(0);
        }
        return montantAffecteInvestissement;
    }

    public void setMontantAffecteInvestissement(BigDecimal montantAffecteInvestissement) {
        this.montantAffecteInvestissement = montantAffecteInvestissement;
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
        if (!(object instanceof CompteItem)) {
            return false;
        }
        CompteItem other = (CompteItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.CompteItem[ id=" + id + " ]";
    }

}
