/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CompteItem;
import bean.ExpressionBesoin;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class ExpressionBesoinFacade extends AbstractFacade<ExpressionBesoin> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;
    
    
    public List<ExpressionBesoin> findByCompteItem(CompteItem compteItem){
        return em.createQuery("select eb from ExpressionBesoin eb where eb.compteItem.id="+compteItem.getId()).getResultList();
    }
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExpressionBesoinFacade() {
        super(ExpressionBesoin.class);
    }
    
}
