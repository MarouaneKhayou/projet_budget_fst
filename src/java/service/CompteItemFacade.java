/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.BudgetEntiteAdministratif;
import bean.BudgetEntiteAdministratifItem;
import bean.Compte;
import bean.CompteItem;
import bean.EntiteAdministratif;
import controller.util.CalculUtil;
import controller.util.JsfUtil;
import java.util.ArrayList;
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
public class CompteItemFacade extends AbstractFacade<CompteItem> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @EJB
    BudgetEntiteAdministratifItemFacade budgetEntiteAdministratifItemFacade;
    @EJB
    BudgetEntiteAdministratifFacade budgetEntiteAdministratifFacade;
    
    

    public List<CompteItem> findByEntiteAdministratifAndAnnee(EntiteAdministratif entiteAdministratif, String annee) {
        if (entiteAdministratif == null || annee.equals("") || annee == null) {
            JsfUtil.addErrorMessage(controller.util.Error.AFFECTATION_BUDGET_NO_SELECTION);
            return null;
        }

        List<BudgetEntiteAdministratifItem> budgetEntiteAdministratifItems = budgetEntiteAdministratifItemFacade.findByAnneeAndEntiteAdministratif(annee, entiteAdministratif);
        if (budgetEntiteAdministratifItems.isEmpty()) {
            return null;
        }

        String idList = getCompteItemId(budgetEntiteAdministratifItems);
        System.out.println("select ci from CompteItem ci where ci.id in (" + idList + idList.charAt(0) + ")");
        return em.createQuery("select ci from CompteItem ci where ci.id in (" + idList + idList.charAt(0) + ")").getResultList();
    }

    private String getCompteItemId(List<BudgetEntiteAdministratifItem> budgetEntiteAdministratifItems) {
        String beaiId = "";
        for (BudgetEntiteAdministratifItem budgetEntiteAdministratifItem : budgetEntiteAdministratifItems) {
            beaiId = budgetEntiteAdministratifItem.getCompteItem().getId() + ",";
        }
        return beaiId;
    }

    public void errorSelection() {

    }

    public CompteItem findByBudgetEntiteAdministratifItem(BudgetEntiteAdministratifItem budgetEntiteAdministratifItem) {
        List<CompteItem> compteItems = em.createQuery("select ci from CompteItem ci where ci.id=" + budgetEntiteAdministratifItem.getCompteItem().getId()).getResultList();
        return (!compteItems.isEmpty()) ? compteItems.get(0) : null;
    }

    public CompteItem findByAnneeCompteEntiteAdministratif(String annee, Compte compte, EntiteAdministratif entiteAdministration) {
        BudgetEntiteAdministratifItem budgetEntiteAdministratifItem = budgetEntiteAdministratifItemFacade.findByAnneeAndEntiteAdministratifAndCompte(annee, entiteAdministration, compte);
        return findByBudgetEntiteAdministratifItem(budgetEntiteAdministratifItem);
    }

    public int addCompteItem(
            Compte selectedCompte,
            CompteItem selectedCompteItem,
            BudgetEntiteAdministratif selectedBudgetFonctionnement,
            BudgetEntiteAdministratif selectedBudgetInvestissement,
            List<CompteItem> compteItems,
            int type) {

        if (selectedCompte == null) {
            return -5;
        }

        selectedCompteItem.setCompte(selectedCompte);
        int code = CalculUtil.prepareAffectationCompteItem(selectedCompteItem, selectedBudgetFonctionnement, selectedBudgetInvestissement, type);
        if (code == 1) {
            compteItems.add(selectedCompteItem);
        }
        return code;
    }

    public void saveCompteItem(List<CompteItem> compteItems, BudgetEntiteAdministratif budgetFonctionnement, BudgetEntiteAdministratif budgetInvestissement) {
        prepareBudgets(compteItems, budgetFonctionnement, budgetInvestissement);
        for (CompteItem ci : compteItems) {
            prepareBudgetEntiteAdministratifItem(ci, budgetFonctionnement, budgetInvestissement);
            edit(ci);
        }
    }

    public void prepareBudgetEntiteAdministratifItem(CompteItem compteItem, BudgetEntiteAdministratif budgetFonctionnement, BudgetEntiteAdministratif budgetInvestissement) {
        BudgetEntiteAdministratifItem budgetEntiteAdministratifItem = new BudgetEntiteAdministratifItem();
        budgetEntiteAdministratifItem.setCompteItem(compteItem);
        budgetEntiteAdministratifItem.setBudgetFonctionnementEntiteAdministratif(budgetFonctionnement);
        budgetEntiteAdministratifItem.setBudgetInvestissementEntiteAdministratif(budgetInvestissement);
        budgetEntiteAdministratifItemFacade.edit(budgetEntiteAdministratifItem);
    }

    public void prepareBudgets(List<CompteItem> compteItems, BudgetEntiteAdministratif budgetFonctionnement, BudgetEntiteAdministratif budgetInvestissement) {
        budgetFonctionnement.setMontantEngage(CalculUtil.sigmaFonctionnementCompteItem(compteItems));
        budgetInvestissement.setMontantEngage(CalculUtil.sigmaInvestissementCompteItem(compteItems));
        budgetEntiteAdministratifFacade.edit(budgetInvestissement);
        budgetEntiteAdministratifFacade.edit(budgetFonctionnement);
    }

    public void prerapreEngage(BudgetEntiteAdministratif budgetEntiteAdministratif, CompteItem compteItem, int type) {
        if (type == 1) {
            budgetEntiteAdministratif.setMontantEngage(compteItem.getMontantAffecteFonctionnement());
        } else {
            budgetEntiteAdministratif.setMontantEngage(compteItem.getMontantAffecteInvestissement());
        }
    }

    public void errorAddCompteItem(int code) {
        switch (code) {
            case 1:
                JsfUtil.addSuccessMessage(controller.util.Error.AFFECTATION_COMPTE_ADDING_SUCCESS);
                break;
            case -1:
                JsfUtil.addErrorMessage(controller.util.Error.CALC_BUDGET_INVALIDE);
                break;
            case -4:
                JsfUtil.addErrorMessage(controller.util.Error.AFFECTATION_COMPTE_MONTANTMAX_DEPASSER);
                break;
            case -3:
                JsfUtil.addErrorMessage(controller.util.Error.AFFECTATION_BUDGET_INVESTISSEMENT_INSUFF);
                break;
            case -2:
                JsfUtil.addErrorMessage(controller.util.Error.AFFECTATION_BUDGET_FONCTIONNEMENT_INSUFF);
                break;
            case -5:
                JsfUtil.addErrorMessage(controller.util.Error.AFFECTATION_COMPTE_SELECTION);
                break;
            case -6:
                JsfUtil.addErrorMessage(controller.util.Error.AFFECTATION_COMPTE_BUDGET_NOT_FOUND);
                break;
            case -7:
                JsfUtil.addErrorMessage(controller.util.Error.SELECTION_NO_ANNEE);
                break;
        }
    }

    public int nbreCompteAffecte(String annee, EntiteAdministratif entiteAdministratif) {
        return (int) em.createQuery("select count(beai) from BudgetEntiteAdministratifItem beai where beai.budgetFonctionnementEntiteAdministratif.annee='" + annee + "' and beai.budgetFonctionnementEntiteAdministratif.entiteAdministratif.id=" + entiteAdministratif.getId()).getSingleResult();
    }

    public int nbreCompteNonAffecte(String annee, EntiteAdministratif entiteAdministratif) {
        return (int) em.createQuery("select count(c)-count(beai) from BudgetEntiteAdministratifItem beai, Compte c where beai.budgetFonctionnementEntiteAdministratif.annee='" + annee + "' and beai.budgetFonctionnementEntiteAdministratif.entiteAdministratif.id=" + entiteAdministratif.getId()).getSingleResult();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompteItemFacade() {
        super(CompteItem.class);
    }

}
