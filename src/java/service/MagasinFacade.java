/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Magasin;
import bean.Produit;
import bean.Stock;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class MagasinFacade extends AbstractFacade<Magasin> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;
    @EJB
    private service.StockFacade stockFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MagasinFacade() {
        super(Magasin.class);
    }

    public void insertProduct(Magasin magasin, Produit produit, BigDecimal qte) {
        boolean found = false;

        for (Stock stock : magasin.getStocks()) {
            if (stock.getProduit().equals(produit)) {
                stock.setQte(stock.getQte().add(qte));
                getStockFacade().edit(stock);
                found = true;
                break;
            }
        }

        if (!found) {
            Stock stock = new Stock();
            stock.setMagasin(magasin);
            stock.setProduit(produit);
            stock.setQte(qte);
            getStockFacade().create(stock);
        }

    }

    public StockFacade getStockFacade() {
        if (stockFacade == null) {
            stockFacade = new StockFacade();
        }
        return stockFacade;
    }

    public void setStockFacade(StockFacade stockFacade) {
        this.stockFacade = stockFacade;
    }

}
