/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.EntiteAdministratif;
import bean.ExpressionBesoin;
import bean.Faculte;
import bean.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class EntiteAdministratifFacade extends AbstractFacade<EntiteAdministratif> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    public EntiteAdministratif findByExpressionBesoin(ExpressionBesoin expressionBesoin) {
        EntiteAdministratif entiteAdministratif = null;
        try {
            entiteAdministratif = (EntiteAdministratif) em.createQuery("select ea from EntiteAdministratif ea where ea.id = (select beai.budgetEntiteAdministratif.id from BudgetEntiteAdministratifItem beai where beai.compteItem.id = " + expressionBesoin.getId() + " ) ").getSingleResult();
        } catch (NoResultException e) {
            entiteAdministratif = null;
        }
        return entiteAdministratif;
    }

    public List<EntiteAdministratif> findByFaculte(Faculte faculte) {
        if (faculte != null && faculte.getId() != null) {
            String query = "SELECT ea FROM EntiteAdministratif ea WHERE ea.faculte.id =" + faculte.getId();
            return em.createQuery(query).getResultList();
        }
        return null;
    }

    public EntiteAdministratif findByUser(User user) {
        EntiteAdministratif entiteAdministratif = null;
        try {
            if (user != null && user.getLogin() != null) {
                entiteAdministratif = (EntiteAdministratif) em.createQuery("SELECT ea FROM EntiteAdministratif ea WHERE ea.chef.login ='" + user.getLogin() + "'").getSingleResult();
            }
        } catch (NoResultException e) {
            entiteAdministratif = null;
        }
        return new EntiteAdministratif();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntiteAdministratifFacade() {
        super(EntiteAdministratif.class);
    }

    public EntiteAdministratif getAdminEntite() {
        return (EntiteAdministratif) em.createQuery("SELECT EA FROM EntiteAdministratif AS EA WHERE EA.type=1").getSingleResult();
    } 

}
