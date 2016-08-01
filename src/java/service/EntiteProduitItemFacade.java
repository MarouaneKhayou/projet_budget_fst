/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.EntiteProduit;
import bean.EntiteProduitItem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class EntiteProduitItemFacade extends AbstractFacade<EntiteProduitItem> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntiteProduitItemFacade() {
        super(EntiteProduitItem.class);
    }

    public List<EntiteProduitItem> getEntitProduitItembyentiteProduit(EntiteProduit entiteProduit) {

        return em.createQuery("SELECT epi  FROM EntiteProduitItem epi WHERE epi.entiteProduit.id= :idep   ").setParameter("idep", entiteProduit.getId()).getResultList();
    }

}
