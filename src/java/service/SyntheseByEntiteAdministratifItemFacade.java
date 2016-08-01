/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.SyntheseByEntiteAdministratifItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class SyntheseByEntiteAdministratifItemFacade extends AbstractFacade<SyntheseByEntiteAdministratifItem> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SyntheseByEntiteAdministratifItemFacade() {
        super(SyntheseByEntiteAdministratifItem.class);
    }
    
}
