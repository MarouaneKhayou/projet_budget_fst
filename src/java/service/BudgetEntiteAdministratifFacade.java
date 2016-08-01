/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.BudgetEntiteAdministratif;
import bean.BudgetFaculte;
import bean.EntiteAdministratif;
import bean.Faculte;
import controller.util.CalculUtil;
import controller.util.JsfUtil;
import controller.util.SessionUtil;
import java.util.List;
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
public class BudgetEntiteAdministratifFacade extends AbstractFacade<BudgetEntiteAdministratif> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @EJB
    BudgetFaculteFacade budgetFaculteFacade;
    @EJB
    BudgetEntiteAdministratifItemFacade budgetEntiteAdministratifItemFacade;
    @EJB
    CompteItemFacade compteItemFacade;

    public List<BudgetEntiteAdministratif> findBudgetFonctionnementByEntiteAdmin(EntiteAdministratif entiteAdministratif) {
        return findByEntiteAdmin(entiteAdministratif, 1);
    }

    public BudgetEntiteAdministratif findBudgetFonctionnementByEntiteAdmin(String annee, EntiteAdministratif entiteAdministratif) {
        List<BudgetEntiteAdministratif> budgetEntiteAdministratifs = findByEntiteAdmin(annee, entiteAdministratif, 1);
        return (!budgetEntiteAdministratifs.isEmpty()) ? budgetEntiteAdministratifs.get(0) : null;
    }

    public BudgetEntiteAdministratif findBudgetInvestissementByEntiteAdmin(String annee, EntiteAdministratif entiteAdministratif) {
        List<BudgetEntiteAdministratif> budgetEntiteAdministratifs = findByEntiteAdmin(annee, entiteAdministratif, 2);
        return (!budgetEntiteAdministratifs.isEmpty()) ? budgetEntiteAdministratifs.get(0) : null;
    }

    public List<BudgetEntiteAdministratif> findBudgeInvestissementByEntiteAdmin(String annee, EntiteAdministratif entiteAdministratif) {
        return findByEntiteAdmin(annee, entiteAdministratif, 2);
    }

    public List<BudgetEntiteAdministratif> findByEntiteAdmin(String annee, EntiteAdministratif entiteAdministratif, int type) {
        return findByCriteres(null, annee, entiteAdministratif, type, -1);
    }

    public List<BudgetEntiteAdministratif> findByEntiteAdmin(EntiteAdministratif entiteAdministratif, int type) {
        return findByCriteres(null, null, null, type, type);
    }

    public List<BudgetEntiteAdministratif> findBudgetInvestissementByAnnee(String annee) {
        return findByCriteres(SessionUtil.getCurrentFaculte(), annee, null, 2, -1);
    }

    public List<BudgetEntiteAdministratif> findBudgetFonctionnementByAnnee(String annee) {
        return findByCriteres(SessionUtil.getCurrentFaculte(), annee, null, 1, -1);
    }

    public List<BudgetEntiteAdministratif> findByAnnee(String annee, int type) {
        return findByCriteres(SessionUtil.getCurrentFaculte(), annee, null, type, -1);
    }

    public List<BudgetEntiteAdministratif> findAllBudgetInvestissement() {
        return findByFaculte(SessionUtil.getCurrentFaculte(), 2);
    }

    public List<BudgetEntiteAdministratif> findAllBudgetFonctionnement() {
        return findByFaculte(SessionUtil.getCurrentFaculte(), 1);
    }

    public List<BudgetEntiteAdministratif> findByFaculte(Faculte faculte, int type) {
        return findByCriteres(faculte, null, null, type, -1);
    }

    public List<BudgetEntiteAdministratif> findByCriteres(Faculte faculte, String annee, EntiteAdministratif entiteAdministratif, int type, int signe) {
        String query = "select bea from BudgetEntiteAdministratif bea where 1=1 ";
        if (faculte != null) {
            query += " and bea.entiteAdministratif.faculte.id=" + faculte.getId();
        }
        if (annee != null || !annee.equals("")) {
            query += " and bea.annee = '" + annee + "' ";
        }
        if (entiteAdministratif != null) {
            query += " and bea.entiteAdministratif.id =" + entiteAdministratif.getId();
        }
        if (type != -1) {
            query += " and bea.type =" + type;
        }
        if (signe == 1) {
            query += " and bea.dateSignature !='null'";
        } else if (signe == 0) {
            query += " and bea.dateSignature ='null'";
        }
        System.out.println("Haaaa query = " + query);
        return em.createQuery(query).getResultList();
    }

    public boolean affectationTermine(List<BudgetEntiteAdministratif> budgetFonctionnements, List<BudgetEntiteAdministratif> budgetInvestissements, List<EntiteAdministratif> entiteAdministratifs) {
        if (budgetFonctionnements != null && budgetInvestissements != null) {
            if (budgetFonctionnements.size() == budgetInvestissements.size() && budgetInvestissements.size() == entiteAdministratifs.size()) {
                return true;
            }
        }
        return false;
    }

    public int addBudgetEntiteAdministratif(
            String selectedAnnee,
            EntiteAdministratif selectedEntiteAdministratif,
            BudgetEntiteAdministratif selectedBudgetFonctionnement,
            BudgetEntiteAdministratif selectedBudgetInvestissement,
            List<BudgetEntiteAdministratif> budgetFonctionnements,
            List<BudgetEntiteAdministratif> budgetInvestissements) {

        if (selectedAnnee == null || selectedAnnee.equals("") && selectedEntiteAdministratif == null || selectedBudgetFonctionnement.getEntiteAdministratif() == null || selectedBudgetFonctionnement.getAnnee() == null || selectedBudgetFonctionnement.getAnnee().equals("")) {
            return -6;
        }

        int v1 = CalculUtil.prepareAddingBudget(selectedBudgetFonctionnement);
        int v2 = CalculUtil.prepareAddingBudget(selectedBudgetInvestissement);
        if (v1 == 1 && v2 == 1) {
            if (!existe(selectedBudgetInvestissement, budgetInvestissements) || !existe(selectedBudgetFonctionnement, budgetFonctionnements)) {
                budgetFonctionnements.add(selectedBudgetFonctionnement);
                budgetInvestissements.add(selectedBudgetInvestissement);
                return 1;
            } else {
                return -5; // budget existe deja
            }

        } else if (v1 != 1) {
            return -1; // budget fonctionnement invalid
        } else if (v2 != 1) {
            return -1; // budget invistissement invalid
        }
        return 0;
    }

    public int addBudgetFaculte(BudgetFaculte budgetFaculte, List<BudgetEntiteAdministratif> budgetInvestissement, List<BudgetEntiteAdministratif> budgetFonctionnement) {
        if (budgetFaculte != null && budgetInvestissement != null && budgetFonctionnement != null) {
            budgetFaculteFacade.edit(budgetFaculte);
            saveList(budgetFonctionnement);
            saveList(budgetInvestissement);
            return 1;
        }
        return -1;
    }

    public void saveList(List<BudgetEntiteAdministratif> budgetEntiteAdministratifs) {
        for (int i = 0; i < budgetEntiteAdministratifs.size(); i++) {
            if (budgetEntiteAdministratifs.get(i).getId() == -1) {
                budgetEntiteAdministratifs.get(i).setId(null);
                edit(budgetEntiteAdministratifs.get(i));
            }
        }
    }

    public void remBudgetEntiteAdministratif(List<String> trash, int index, List<BudgetEntiteAdministratif> budgetFonctionnements, List<BudgetEntiteAdministratif> budgetInvestissements) {
        if (budgetFonctionnements.get(index).getId() == -1 || budgetInvestissements.get(index).getId() == -1) {
            trash.add(budgetFonctionnements.get(index).getId() + "");
            trash.add(budgetInvestissements.get(index).getId() + "");
        }
        budgetFonctionnements.remove(index);
        budgetInvestissements.remove(index);
    }

    public void delete(List<String> trash) {
        String id = "";
        for (String idBudget : trash) {
            id += idBudget + ",";
        }
        em.createQuery("delete bea from BudgetEntiteAdministratif bea where id in" + "(" + id + "0)").executeUpdate();
    }

    public boolean existe(BudgetEntiteAdministratif budgetEntiteAdministratif, List<BudgetEntiteAdministratif> budgetEntiteAdministratifs) {
        for (BudgetEntiteAdministratif b : budgetEntiteAdministratifs) {
            if (b.getEntiteAdministratif().equals(budgetEntiteAdministratif.getEntiteAdministratif())) {
                return true;
            }
        }
        return false;
    }
    
    public void updateBudgetEntiteAdministratif(
            BudgetEntiteAdministratif selectedBudgetFonctionnement,
            BudgetEntiteAdministratif selectedBudgetInvestissement,
            String selectedAnnee,
            EntiteAdministratif selectedEntiteAdministratif)
    {
        selectedBudgetFonctionnement = findBudgetFonctionnementByEntiteAdmin(selectedAnnee, selectedEntiteAdministratif);
        selectedBudgetInvestissement = findBudgetInvestissementByEntiteAdmin(selectedAnnee, selectedEntiteAdministratif);
        if(selectedBudgetFonctionnement == null || selectedBudgetInvestissement == null){
            JsfUtil.addSuccessMessage(controller.util.Error.AFFECTATION_COMPTE_BUDGET_EA_NOT_FOUND);
        }
    }

    public void errorAddBudgetEntiteAdministratif(int code) {
        switch (code) {
            case 1:
                JsfUtil.addSuccessMessage(controller.util.Error.AFFECTATION_BUDGET_ADDING_SUCCESS);
                break;
            case -1:
                JsfUtil.addErrorMessage(controller.util.Error.CALC_BUDGET_INVALIDE);
                break;
            case -4:
                JsfUtil.addErrorMessage(controller.util.Error.AFFECTATION_BUDGET_TERMINER);
                break;
            case -5:
                JsfUtil.addErrorMessage(controller.util.Error.AFFECTATION_BUDGET_EXISTE);
                break;
            case -6:
                JsfUtil.addErrorMessage(controller.util.Error.AFFECTATION_BUDGET_NO_SELECTION);
                break;

        }
    }

    public void errorAddingBudgetFaculte(int code) {
        switch (code) {
            case 1:
                JsfUtil.addSuccessMessage(controller.util.Error.AFFECTATION_BUDGET_ADDING_SUCCESS);
                break;
        }
    }

    public Long getMaxId() {
        Long maxId;
        try {
            maxId = (Long) em.createQuery("select max(bea.id) from BudgetEntiteAdministratif bea ").getSingleResult();
        } catch (NoResultException e) {
            maxId = 0l;
        }
        return maxId;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BudgetEntiteAdministratifFacade() {
        super(BudgetEntiteAdministratif.class);
    }

}
