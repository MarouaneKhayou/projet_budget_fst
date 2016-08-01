/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DemandeEntreeStock;
import bean.DemandeEntreeStockItem;
import bean.EntiteProduit;
import bean.EntiteProduitItem;
import bean.Livraison;
import bean.Produit;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SONY
 */
@Stateless
public class LivraisonFacade extends AbstractFacade<Livraison> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @EJB
    EntiteProduitFacade entiteProduitFacade;
    @EJB
    EntiteProduitItemFacade entiteProduitItemFacade;
    @EJB
    DemandeEntreeStockItemFacade demandeEntreeStockItemFacade;
    @EJB
    DemandeEntreeStockFacade demandeEntreeStockFacade;
    @EJB
    StockFacade stockFacade;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LivraisonFacade() {
        super(Livraison.class);
    }

    public void livrer(EntiteProduitItem entitProduitItem, Long qte) {
        
        if (qte != null && qte > 0  ) {
            Livraison livraison = new Livraison();
            livraison.setQteLivre(qte);
            livraison.setDate(new Date());
            livraison.setEntiteProduitItem(entitProduitItem);
            edit(livraison);
            //update entiteproduititem
            entitProduitItem.setQteLivre(entitProduitItem.getQteLivre() + qte);
            if (entitProduitItem.getQteLivre() == entitProduitItem.getQteCommande()) {
                entitProduitItem.setLivred(true);
            }
            entiteProduitItemFacade.edit(entitProduitItem);
            //update entiteproduit
            EntiteProduit entiteProduit = entiteProduitFacade.find(entitProduitItem.getEntiteProduit().getId());
            entiteProduit.setQteLivre(entiteProduit.getQteLivre() + qte);
            if (entiteProduit.getQteCommande() == entiteProduit.getQteLivre()) {
                entiteProduit.setLivred(true);
            }
            entiteProduitFacade.edit(entiteProduit);
            //update demandentreeStockitem
            DemandeEntreeStockItem demandeEntreeStockItem = demandeEntreeStockItemFacade.find(entiteProduit.getDemandeEntreeStockItem().getId());
            Produit produit=demandeEntreeStockItem.getProduitItem().getProduit();
            demandeEntreeStockItem.setQteLivre(demandeEntreeStockItem.getQteLivre() + qte);
            if (demandeEntreeStockItem.getQteCommande() == demandeEntreeStockItem.getQteLivre()) {
                demandeEntreeStockItem.setLivred(true);

            }
            demandeEntreeStockItemFacade.edit(demandeEntreeStockItem);
            //update demandeentreenstock
            DemandeEntreeStock demandeEntreeStock = demandeEntreeStockFacade.find(demandeEntreeStockItem.getDemandeEntreeStock().getId());
            System.out.println("demande id" + demandeEntreeStock.getId());
            boolean test = demandeEntreeStockItemFacade.isLivred(demandeEntreeStock);
            System.out.println(demandeEntreeStock);
            if (test) {

                demandeEntreeStock.setLivred(true);
                demandeEntreeStockFacade.edit(demandeEntreeStock);
            }
            
            stockFacade.subtractFromStock(demandeEntreeStock, produit, qte);
            System.out.println("livraison  entiteproduit "+entitProduitItem);
            System.out.println("livraison  ebtiteAdmin "+entitProduitItem.getEntiteAdministratif());
            System.out.println("livraison   magasin laivarison " +entitProduitItem.getEntiteAdministratif().getMagasin());
            stockFacade.addTosTock(entitProduitItem.getEntiteAdministratif().getMagasin(), produit, qte);

        }
    }

}
