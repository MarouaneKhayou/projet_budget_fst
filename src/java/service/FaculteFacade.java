/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Faculte;
import bean.User;
import controller.util.SessionUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class FaculteFacade extends AbstractFacade<Faculte> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @EJB
    EntiteAdministratifFacade entiteAdministratifFacade;
    @EJB
    BudgetFaculteFacade budgetFaculteFacade;

    public Faculte findByUser(User user) {
        Faculte faculte = null;
        try {
            if (user != null && user.getLogin() != null) {
                faculte = (Faculte) em.createQuery("SELECT f FROM Faculte f, User u WHERE u.faculte.id=f.id and u.login='" + user.getLogin() + "'").getSingleResult();
            }
        } catch (NoResultException e) {
            faculte = null;
        }
        return faculte;
    }

    public int initFaculteParams(Faculte faculte) {
        if (faculte != null && faculte.getId() != null) {
            faculte.setEntiteAdministratifs(entiteAdministratifFacade.findByFaculte(faculte));
            faculte.setBudgetFacultes(budgetFaculteFacade.findByAnneeAndFaculte(SessionUtil.getCurrentAnnee(), faculte));
            return 1;
        }
        return -1;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FaculteFacade() {
        super(Faculte.class);
    }

}
