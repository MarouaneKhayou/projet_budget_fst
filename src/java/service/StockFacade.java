/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DemandeEntreeStock;
import bean.Magasin;
import bean.Produit;
import bean.Reception;
import bean.Stock;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class StockFacade extends AbstractFacade<Stock> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @EJB
    ReceptionFacade receptionFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StockFacade() {
        super(Stock.class);
    }

    public Stock getProduitStockfromMagasin(Magasin magasin, Produit produit) {
        System.out.println("magasin " + magasin);
        System.out.println("produit " + produit);
        List<Stock> stocks = findAll();
        for (Stock stock : stocks) {
            if (stock.getMagasin().getId() == magasin.getId() && stock.getProduit().getId() == produit.getId()) {
                return stock;
            }
        }

        return null;
    }

    public void subtractFromStock(Stock stock, Long qte) {
        System.out.println("sub2");
        stock.setQte(stock.getQte().subtract(new BigDecimal(qte)));
        System.out.println("stock2 " + stock);
        edit(stock);
    }

    public void subtractFromStock(DemandeEntreeStock demandeEntreeStock, Produit produit, Long qte) {
        System.out.println("substract");
        Reception reception = getReceptionFacade().findOneREception(demandeEntreeStock);
        //return null
        System.out.println("reception " + reception);

        if (reception == null) {
            System.out.println("RECEPTION NULL");
        }

        Magasin magasin = reception.getMagasin();

        System.out.println("magasin " + magasin);

        Stock stock = getProduitStockfromMagasin(magasin, produit);
        System.out.println("stock " + stock);
        subtractFromStock(stock, qte);
    }

    public void addTOStock(Stock stock, Long qte) {
        System.out.println("addToStock2 " + stock);
        stock.setQte(stock.getQte().add(new BigDecimal(qte)));
        edit(stock);
    }

    public void addTosTock(Magasin magasin, Produit produit, Long qte) {

        System.out.println("addtoStock");
        System.out.println("magasin " + magasin);
        System.out.println("produit " + produit);
        Stock stock = getProduitStockfromMagasin(magasin, produit);
        System.out.println("stock Test " + stock);
        if (stock != null) {
            addTOStock(stock, qte);

        } else {
            stock = new Stock();
            stock.setMagasin(magasin);
            stock.setProduit(produit);
            stock.setQte(new BigDecimal(qte));
            edit(stock);

        }

    }

    public ReceptionFacade getReceptionFacade() {
        if (receptionFacade == null) {
            receptionFacade = new ReceptionFacade();
        }
        return receptionFacade;
    }

    public void setReceptionFacade(ReceptionFacade receptionFacade) {
        this.receptionFacade = receptionFacade;
    }

}
