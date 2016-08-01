/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.BudgetFaculte;
import bean.Faculte;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class BudgetFaculteFacade extends AbstractFacade<BudgetFaculte> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;
    
     public List<BudgetFaculte> findByAnneeAndFaculte(String annee, Faculte faculte) {
        List<BudgetFaculte> budgetFacultes;
        budgetFacultes = findByCritere(faculte, annee);
        if (!budgetFacultes.isEmpty()) {
            return budgetFacultes;
        } else {
            return null;
        }
    }

    public List<BudgetFaculte> findByFaculte(Faculte faculte) {
        return findByCritere(faculte, null);
    }

    public List<BudgetFaculte> findByCritere(Faculte faculte, String annee) {
        List<BudgetFaculte> budgetFaculte = null;
        if (faculte != null && faculte.getId() != null) {
            String query = "SELECT bf FROM BudgetFaculte bf WHERE bf.faculte.id =" + faculte.getId();
            if (annee != null || !annee.equals("")) {
                query += " and bf.annee ='" + annee + "'";
            }
            budgetFaculte = em.createQuery(query).getResultList();
        }
        return budgetFaculte;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BudgetFaculteFacade() {
        super(BudgetFaculte.class);
    }
    
}
