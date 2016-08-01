/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Budget;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class BudgetFacade extends AbstractFacade<Budget> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;
    @EJB
    private UserFacade userFacade;

    public int checkEtat(Budget budget) {
        if (budget.getResponsableValidation() != null && budget.getDateSignature() != null) {
            return 1; // vailder + signé
        } else if (budget.getResponsableValidation() != null) {
            return 2;
        } else if (budget.getDateSignature() != null) {
            return 3;
        } else {
            return -1;
        }
    }

    public String StrEtat(Budget budget) {
        switch (checkEtat(budget)) {
            case 1:
                return "Valide " + "\nSigné";
            case 2:
                return "Valide " + "\npas encors signé !";
            default:
                return "En cours de validation..";
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BudgetFacade() {
        super(Budget.class);
    }

}
