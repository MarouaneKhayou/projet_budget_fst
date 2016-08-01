package controller;

import bean.BudgetEntiteAdministratif;
import bean.BudgetEntiteAdministratifItem;
import bean.Compte;
import bean.CompteItem;
import bean.EntiteAdministratif;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import service.CompteFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.CompteItemFacade;

@Named("compteController")
@SessionScoped
public class CompteController implements Serializable {

    @EJB
    private service.CompteFacade ejbFacade;
    private List<Compte> items = null;
    private Compte selected;

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

    public BigDecimal montantTotalAffecte(){
        return selectedCompteItem.getMontantAffecteFonctionnement().add(selectedCompteItem.getMontantAffecteInvestissement());
    }
    
    public List<String> getAnnees() {
        if (annees == null) {
            annees = SessionUtil.getAnnees();
        }
        return annees;
    }

    public List<EntiteAdministratif> getEntiteAdministrartifs() {
        if (entiteAdministrartifs == null) {
            System.out.println("tiiiiiit");
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
        if(selectedBudgetFonctionnement == null){
            selectedBudgetFonctionnement = new BudgetEntiteAdministratif();
        }
        return selectedBudgetFonctionnement;
    }

    public void setSelectedBudgetFonctionnement(BudgetEntiteAdministratif selectedBudgetFonctionnement) {
        this.selectedBudgetFonctionnement = selectedBudgetFonctionnement;
    }

    public BudgetEntiteAdministratif getSelectedBudgetInvestissement() {
        if(selectedBudgetInvestissement == null){
            selectedBudgetInvestissement = new BudgetEntiteAdministratif();
        }
        return selectedBudgetInvestissement;
    }

    public void setSelectedBudgetInvestissement(BudgetEntiteAdministratif selectedBudgetInvestissement) {
        this.selectedBudgetInvestissement = selectedBudgetInvestissement;
    }

    public CompteItem getSelectedCompteItem() {
        if(selectedCompteItem == null){
            selectedCompteItem = new CompteItem();
        }
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

    public CompteController() {
    }

    public Compte getSelected() {
        return selected;
    }

    public void setSelected(Compte selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CompteFacade getFacade() {
        return ejbFacade;
    }

    public Compte prepareCreate() {
        selected = new Compte();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CompteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CompteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CompteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Compte> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Compte getCompte(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Compte> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Compte> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Compte.class)
    public static class CompteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompteController controller = (CompteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "compteController");
            return controller.getCompte(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Compte) {
                Compte o = (Compte) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Compte.class.getName()});
                return null;
            }
        }

    }

}
