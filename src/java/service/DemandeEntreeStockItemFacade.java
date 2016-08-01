/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DemandeEntreeStock;
import bean.DemandeEntreeStockItem;
import bean.EntiteProduitItem;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class DemandeEntreeStockItemFacade extends AbstractFacade<DemandeEntreeStockItem> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DemandeEntreeStockItemFacade() {
        super(DemandeEntreeStockItem.class);
    }

    public List<DemandeEntreeStockItem> getdemandeitemByDemande(DemandeEntreeStock demandeEntreeStock) {

        return em.createQuery("SELECT desi  FROM DemandeEntreeStockItem desi WHERE desi.demandeEntreeStock.id= :identifiant").setParameter("identifiant", demandeEntreeStock.getId()).getResultList();
    }

    public List<DemandeEntreeStockItem> getdemandeitemByDemandeusingFindAll(DemandeEntreeStock demandeEntreeStock) {
        List<DemandeEntreeStockItem> demandeEntreeStockItems = findAll();
        List<DemandeEntreeStockItem> items = new ArrayList<>();

        for (DemandeEntreeStockItem loadeddemandeitem : demandeEntreeStockItems) {
            if (loadeddemandeitem.getDemandeEntreeStock().getId() == demandeEntreeStock.getId()) {
                items.add(loadeddemandeitem);

            }

        }
        return items;
    }

    public List<Long> getQuantitiesLivraison(EntiteProduitItem entiteProduitItem) {

        List<Long> longs = new ArrayList<>();
        DemandeEntreeStockItem demandeEntreeStockItem = find(entiteProduitItem.getEntiteProduit().getDemandeEntreeStockItem().getId());

        Long restaLDeSItem = demandeEntreeStockItem.getQteRecu() - demandeEntreeStockItem.getQteLivre();
        Long restaalEP = entiteProduitItem.getQteCommande() - entiteProduitItem.getQteLivre();

        for (int i = 1; i <= restaLDeSItem && i <= restaalEP; i++) {
            longs.add(new Long(i));

        }

        System.out.println(longs.size());
        return longs;
    }

    public boolean isLivred(DemandeEntreeStock demandeEntreeStock) {
        System.out.println("id= " + demandeEntreeStock.getId());
        List<DemandeEntreeStockItem> demandeEntreeStockItems = null;
        String requete = "SELECT desi  FROM DemandeEntreeStockItem desi WHERE desi.demandeEntreeStock.id= :identifiant and desi.livred= :valboolean ";
        demandeEntreeStockItems = em.createQuery(requete).setParameter("identifiant", demandeEntreeStock.getId()).setParameter("valboolean", Boolean.FALSE).getResultList();
        if (demandeEntreeStockItems == null || demandeEntreeStockItems.size() == 0) {
            return true;
        }
        return false;

    }

}
