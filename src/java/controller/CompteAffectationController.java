package controller;

import bean.BudgetEntiteAdministratif;
import bean.BudgetEntiteAdministratifItem;
import bean.Compte;
import bean.CompteItem;
import bean.EntiteAdministratif;
import controller.util.JsfUtil;
import controller.util.SessionUtil;
import service.CompteFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.BudgetEntiteAdministratifFacade;
import service.BudgetEntiteAdministratifItemFacade;
import service.CompteItemFacade;

@Named("compteAffectationController")
@ViewScoped
public class CompteAffectationController implements Serializable {

    @EJB
    private CompteItemFacade compteItemFacade;
    @EJB
    private service.BudgetEntiteAdministratifItemFacade budgetEntiteAdministratifItemFacade;
    @EJB
    private service.BudgetEntiteAdministratifFacade budgetEntiteAdministratifFacade;

    private List<String> annees;
    private List<EntiteAdministratif> entiteAdministrartifs;
    private List<BudgetEntiteAdministratif> budgetFonctionnementFacultes;
    private List<BudgetEntiteAdministratif> budgetInvestissementFacultes;
    private List<Compte> comptes;
    private List<BudgetEntiteAdministratifItem> budgetEntiteAdministratifItems;
    private List<CompteItem> compteItems;

    private String selectedAnnee = "";
    private EntiteAdministratif selectedEntiteAdministratif;
    private BudgetEntiteAdministratif selectedBudgetFonctionnement;
    private BudgetEntiteAdministratif selectedBudgetInvestissement;
    private CompteItem selectedCompteItem;
    private Compte selectedCompte;
    private BudgetEntiteAdministratifItem budgetEntiteAdministratifItem;
    private int typeAffectation;

    public void onBtnSelect() {
        compteItems = compteItemFacade.findByEntiteAdministratifAndAnnee(selectedEntiteAdministratif, selectedAnnee);
        budgetEntiteAdministratifFacade.updateBudgetEntiteAdministratif(selectedBudgetFonctionnement, selectedBudgetInvestissement, selectedAnnee, selectedEntiteAdministratif);
    }

    public void onBtnAjout() {
        int code = compteItemFacade.addCompteItem(selectedCompte, selectedCompteItem, selectedBudgetFonctionnement, selectedBudgetInvestissement, compteItems, typeAffectation);
        compteItemFacade.errorAddCompteItem(code);
    }

    public void onBtnValider() {
        compteItemFacade.saveCompteItem(compteItems, selectedBudgetFonctionnement, selectedBudgetInvestissement);
    }

    public void onBtnAnnuler() {

    }

    public List<String> getAnnees() {
        if (annees == null) {
            annees = SessionUtil.getAnnees();
        }
        return annees;
    }

    public List<EntiteAdministratif> getEntiteAdministrartifs() {
        if (entiteAdministrartifs == null) {
            entiteAdministrartifs = SessionUtil.getAllEntiteAdministratifs();
        }
        return entiteAdministrartifs;
    }

    public List<BudgetEntiteAdministratif> getBudgetFonctionnementFacultes() {
        if (budgetFonctionnementFacultes == null) {
            budgetFonctionnementFacultes = new ArrayList<>();
        }
        return budgetFonctionnementFacultes;
    }

    public List<BudgetEntiteAdministratif> getBudgetInvestissementFacultes() {
        if (budgetInvestissementFacultes == null) {
            budgetInvestissementFacultes = new ArrayList<>();
        }
        return budgetInvestissementFacultes;
    }

    public List<Compte> getComptes() {
        if (comptes == null) {
            comptes = SessionUtil.getComptes();
        }
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public List<CompteItem> getCompteItems() {
        if (compteItems == null) {
            compteItems = new ArrayList<>();
        }
        return compteItems;
    }

    public String getSelectedAnnee() {
        return selectedAnnee;
    }

    public void setSelectedAnnee(String selectedAnnee) {
        this.selectedAnnee = selectedAnnee;
    }

    public EntiteAdministratif getSelectedEntiteAdministratif() {
        return selectedEntiteAdministratif;
    }

    public void setSelectedEntiteAdministratif(EntiteAdministratif selectedEntiteAdministratif) {
        this.selectedEntiteAdministratif = selectedEntiteAdministratif;
    }

    public BudgetEntiteAdministratif getSelectedBudgetFonctionnement() {
        return selectedBudgetFonctionnement;
    }

    public void setSelectedBudgetFonctionnement(BudgetEntiteAdministratif selectedBudgetFonctionnement) {
        this.selectedBudgetFonctionnement = selectedBudgetFonctionnement;
    }

    public BudgetEntiteAdministratif getSelectedBudgetInvestissement() {
        return selectedBudgetInvestissement;
    }

    public void setSelectedBudgetInvestissement(BudgetEntiteAdministratif selectedBudgetInvestissement) {
        this.selectedBudgetInvestissement = selectedBudgetInvestissement;
    }

    public CompteItem getSelectedCompteItem() {
        return selectedCompteItem;
    }

    public void setSelectedCompteItem(CompteItem selectedCompteItem) {
        this.selectedCompteItem = selectedCompteItem;
    }

    public Compte getSelectedCompte() {
        return selectedCompte;
    }

    public void setSelectedCompte(Compte selectedCompte) {
        this.selectedCompte = selectedCompte;
    }

    public BudgetEntiteAdministratifItem getBudgetEntiteAdministratifItem() {
        return budgetEntiteAdministratifItem;
    }

    public void setBudgetEntiteAdministratifItem(BudgetEntiteAdministratifItem budgetEntiteAdministratifItem) {
        this.budgetEntiteAdministratifItem = budgetEntiteAdministratifItem;
    }

    public int getTypeAffectation() {
        return typeAffectation;
    }

    public void setTypeAffectation(int typeAffectation) {
        this.typeAffectation = typeAffectation;
    }

}
