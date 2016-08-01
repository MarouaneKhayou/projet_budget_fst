/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DemandeEntreeStock;
import bean.Reception;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class ReceptionFacade extends AbstractFacade<Reception> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceptionFacade() {
        super(Reception.class);
    }

    public Reception findOneREception(DemandeEntreeStock demandeEntreeStock) {
        List<Reception> receptions = findAll();

        for (Reception reception : receptions) {
            if (reception.getDemandeEntreeStock().getId() == demandeEntreeStock.getId()) {
                return reception;
            }
        }
        return null;

    }

}
