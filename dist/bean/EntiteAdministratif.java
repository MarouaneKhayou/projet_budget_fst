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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author AIMAD
 */
@Entity
public class EntiteAdministratif implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String logo;
    private int type; //que ca soit un {Laboratoite, Departement , Administration ...}
    private Double creditOuvert;  //ce qui reste du budget Affecter 
    @OneToMany(mappedBy = "entiteAdministratif")
    private List<BudgetEntiteAdministratif> budgetEntiteAdministratifs; // 3la 7ssab l3imaan
    @ManyToOne
    private Faculte faculte;
    @OneToOne
    private User chef;
    @OneToOne
    private Magasin magasin;

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }
   
    public List<BudgetEntiteAdministratif> getBudgetEntiteAdministratifs() {
        if(budgetEntiteAdministratifs == null){
            budgetEntiteAdministratifs = new ArrayList<>();
        }
        return budgetEntiteAdministratifs;
    }

    public void setBudgetEntiteAdministratifs(List<BudgetEntiteAdministratif> budgetEntiteAdministratifs) {
        this.budgetEntiteAdministratifs = budgetEntiteAdministratifs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Double getCreditOuvert() {
        if (creditOuvert == null) {
            creditOuvert = new Double(0);
        }
        return creditOuvert;
    }

    public void setCreditOuvert(Double sommeInterieur) {
        this.creditOuvert = sommeInterieur;
    }

    public Faculte getFaculte() {
        if (faculte == null) {
            faculte = new Faculte();
        }
        return faculte;
    }

    public void setFaculte(Faculte faculte) {
        this.faculte = faculte;
    }

    public User getChef() {
        if (chef == null) {
            chef = new User();
        }
        return chef;
    }

    public void setChef(User chef) {
        this.chef = chef;
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
        if (!(object instanceof EntiteAdministratif)) {
            return false;
        }
        EntiteAdministratif other = (EntiteAdministratif) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "bean.EntiteAdministratif[ id=" + id + " ]";
        return this.nom;
    }

}
