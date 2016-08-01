/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.BudgetEntiteAdministratifItem;
import bean.User;
import controller.util.SessionUtil;
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
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @EJB
    FaculteFacade faculteFacade;
    @EJB
    EntiteAdministratifFacade entiteAdministratifFacade;
    @EJB
    BudgetEntiteAdministratifItemFacade budgetEntiteAdministratifItemFacade;
    @EJB
    CompteFacade compteFacade;
    @EJB
    CompteItemFacade compteItemFacade;

    public void initBudgetEntiteAdministratifItems(User user) {
        List<BudgetEntiteAdministratifItem> budgetEntiteAdministratifItems = null;
        if (user.getType() < 5) {
            budgetEntiteAdministratifItems = budgetEntiteAdministratifItemFacade.findByAnnee(SessionUtil.getCurrentAnnee());
            if (!budgetEntiteAdministratifItems.isEmpty()) {
                SessionUtil.registerBudgetEntiteAdministratifItems(budgetEntiteAdministratifItems);
            }
        } else {
            budgetEntiteAdministratifItems = budgetEntiteAdministratifItemFacade.findByAnneeAndEntiteAdministratif(SessionUtil.getCurrentAnnee(), SessionUtil.getCurrentEntiteAdministratif());
            SessionUtil.registerBudgetEntiteAdministratifItems(budgetEntiteAdministratifItems);
        }
    }

    public void initComptes() {
        SessionUtil.registerComptes(compteFacade.findAll());
    }

    public void initCompteItems() {

    }

    public String getType(User user) {
        int type = user.getType();
        if (type == 1) {
            return "Doyen";
        } else if (type == 2) {
            return "VD";
        } else if (type == 3) {
            return "SG";
        } else if (type == 4) {
            return "Comptable";
        } else if (type == 5) {
            return "Chef Departement";
        } else {
            return null;
        }
    }

    public int seConnnecter(User user) {
        if (user == null || user.getLogin() == null) {
            return -1;
        } else {
            User loadedUser = find(user.getLogin());
            if (loadedUser == null) {
                return -2;
            } else if (!loadedUser.getPassword().equals(user.getPassword())) {
                return -3;
            } else {
                user.setFaculte(faculteFacade.findByUser(user));
                int type = loadedUser.getType();
                user.setType(type);
                if (type == 1) {

                }
                if (type == 2) {

                }
                if (type == 3) {

                }
                if (type == 4) {

                }
                if (type == 5) {
                    user.setEntiteAdministratif(entiteAdministratifFacade.findByUser(user));
                }
                return 1;
            }
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

}
