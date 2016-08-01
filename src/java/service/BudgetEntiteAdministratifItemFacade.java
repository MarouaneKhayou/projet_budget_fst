/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.BudgetEntiteAdministratifItem;
import bean.Compte;
import bean.CompteItem;
import bean.EntiteAdministratif;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class BudgetEntiteAdministratifItemFacade extends AbstractFacade<BudgetEntiteAdministratifItem> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    public BudgetEntiteAdministratifItem findByAnneeAndEntiteAdministratifAndCompte(String annee, EntiteAdministratif entiteAdministratif, Compte compte) {
        List<BudgetEntiteAdministratifItem> budgetEntiteAdministratifItems = findByCriteres(annee, entiteAdministratif, compte, null);
        return (!budgetEntiteAdministratifItems.isEmpty()) ? budgetEntiteAdministratifItems.get(0) : null;
    }

    public List<BudgetEntiteAdministratifItem> findByAnneeAndEntiteAdministratif(String annee, EntiteAdministratif entiteAdministratif) {
        return findByCriteres(annee, entiteAdministratif, null, null);
    }

    public List<BudgetEntiteAdministratifItem> findByAnnee(String annee) {
        return findByCriteres(annee, null, null, null);
    }

    public List<BudgetEntiteAdministratifItem> findByCriteres(String annee, EntiteAdministratif entiteAdministratif, Compte compte, CompteItem compteItem) {
        String query = "select beai from BudgetEntiteAdministratifItem beai where 1=1 ";
        if (annee != null && annee.equals("")) {
            query += " and beai.budgetFonctionnementEntiteAdministratif.annee ='" + annee + "' ";
        }
        if (entiteAdministratif != null) {
            query += " and beai.budgetFonctionnementEntiteAdministratif.entiteAdministratif.id =" + entiteAdministratif.getId();
        }
        if (compte != null) {
            query += " and beai.compteItem.compte.id =" + compte.getId();
        }
        if (compteItem != null) {
            query += " and and beai.compteItem.id=" + compteItem.getId();
        }
        return em.createQuery(query).getResultList();
    }

    public BudgetEntiteAdministratifItem prepareEntiteAdministrationItem(String annee, EntiteAdministratif entiteAdministratif, BudgetEntiteAdministratifItem budgetEntiteAdministratifItem) {

        return budgetEntiteAdministratifItem;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BudgetEntiteAdministratifItemFacade() {
        super(BudgetEntiteAdministratifItem.class);
    }

}
