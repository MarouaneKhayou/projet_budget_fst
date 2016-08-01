/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author AIMAD
 */
@Entity
public class Reception implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    DemandeEntreeStock demandeEntreeStock;

    @OneToMany(mappedBy = "reception")
    List<ReceptionItem> receptionItems;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date date;

    @ManyToOne
    Magasin magasin;

    public List<ReceptionItem> getReceptionItems() {
        return receptionItems;
    }

    public void setReceptionItems(List<ReceptionItem> receptionItems) {
        this.receptionItems = receptionItems;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandeEntreeStock getDemandeEntreeStock() {
        return demandeEntreeStock;
    }

    public void setDemandeEntreeStock(DemandeEntreeStock demandeEntreeStock) {
        this.demandeEntreeStock = demandeEntreeStock;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof Reception)) {
            return false;
        }
        Reception other = (Reception) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Reception[ id=" + id + " ]";
    }

}
