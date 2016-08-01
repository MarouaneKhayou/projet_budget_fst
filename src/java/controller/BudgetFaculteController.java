package controller;

import bean.BudgetEntiteAdministratif;
import bean.BudgetFaculte;
import bean.EntiteAdministratif;
import controller.util.CalculUtil;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import service.BudgetFaculteFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

@Named("budgetFaculteController")
@SessionScoped
public class BudgetFaculteController implements Serializable {

    @EJB
    private service.BudgetFaculteFacade ejbFacade;
    private List<BudgetFaculte> items = null;
    private BudgetFaculte selected;
    
    

    @EJB
    private service.BudgetEntiteAdministratifFacade budgetEntiteAdministratifFacade;
    @EJB
    private service.BudgetFacade budgetFacade;
    
    

    private List<String> annees;
    private List<EntiteAdministratif> entiteAdministrartifs;
    private List<BudgetFaculte> budgetFacultes;
    private List<BudgetEntiteAdministratif> budgetFonctionnementFacultes;
    private List<BudgetEntiteAdministratif> budgetInvestissementFacultes;

    private BudgetFaculte selectedBudgetFaculte;
    private BudgetEntiteAdministratif selectedBudgetFonctionnement;
    private BudgetEntiteAdministratif selectedBudgetInvestissement;
    private String selectedAnnee = "";
    private EntiteAdministratif selectedEntiteAdministratif;
    
    private List<String> trash = new ArrayList<>();

  
    public void onBtnSelect() {
        if (selectedAnnee != null) {
            budgetFonctionnementFacultes = budgetEntiteAdministratifFacade.findBudgetFonctionnementByAnnee(selectedAnnee);
            budgetInvestissementFacultes = budgetEntiteAdministratifFacade.findBudgetInvestissementByAnnee(selectedAnnee);
        }
    }

    public void onBtnAjouter() {
        prepareBudgetEntiteAdministratifs();
        prepareBudgetEntiteAdministratifs();
        int code = budgetEntiteAdministratifFacade.addBudgetEntiteAdministratif(selectedAnnee, selectedEntiteAdministratif, selectedBudgetFonctionnement, selectedBudgetInvestissement, budgetFonctionnementFacultes, budgetInvestissementFacultes);
        budgetEntiteAdministratifFacade.errorAddBudgetEntiteAdministratif(code);
        freeSelection();
    }

    public void onBtnValider() {
        prepareBudgetFaculte();
        int code = budgetEntiteAdministratifFacade.addBudgetFaculte(selectedBudgetFaculte, budgetInvestissementFacultes, budgetFonctionnementFacultes);
        budgetEntiteAdministratifFacade.errorAddingBudgetFaculte(code);
        if (!trash.isEmpty()) {
            budgetEntiteAdministratifFacade.delete(trash);
        }
        freeAll();
    }

    public void onBtnAnnuler() {
        freeAll();
    }

    public void onBtnRemove(int index) {
        budgetEntiteAdministratifFacade.remBudgetEntiteAdministratif(trash, index, budgetFonctionnementFacultes, budgetInvestissementFacultes);
    }

    public BigDecimal getMontantInvestissement(int index) {
        return budgetInvestissementFacultes.get(index).getMontantAffecte();
    }

    private void prepareBudgetEntiteAdministratifs() {
        prepareBudgetEntiteAdministratif(selectedBudgetFonctionnement, 1);
        prepareBudgetEntiteAdministratif(selectedBudgetInvestissement, 2);
    }

    private void prepareBudgetEntiteAdministratif(BudgetEntiteAdministratif budgetEntiteAdministratif, int type) {
        budgetEntiteAdministratif.setId(new Long(-1));
        budgetEntiteAdministratif.setAnnee(selectedAnnee);
        budgetEntiteAdministratif.setDateAffectation(new Date());
        budgetEntiteAdministratif.setDateValidation(new Date());
        budgetEntiteAdministratif.setEntiteAdministratif(selectedEntiteAdministratif);
        budgetEntiteAdministratif.setResponsableAffectation(SessionUtil.getConnectedUser());
        budgetEntiteAdministratif.setResponsableValidation(SessionUtil.getConnectedUser());
        budgetEntiteAdministratif.setType(type);
    }

    private void prepareBudgetFaculte() {
        getSelectedBudgetFaculte();
        selectedBudgetFaculte.setDateAffectation(new Date());
        selectedBudgetFaculte.setDateValidation(new Date());
        selectedBudgetFaculte.setResponsableAffectation(SessionUtil.getConnectedUser());
        selectedBudgetFaculte.setResponsableValidation(SessionUtil.getConnectedUser());
        selectedBudgetFaculte.setAnnee(selectedAnnee);
        System.out.println("sigma : " + CalculUtil.sigmaBudgetEntiteAdmin(budgetFonctionnementFacultes, budgetInvestissementFacultes));
        selectedBudgetFaculte.setMontantAffecte(CalculUtil.sigmaBudgetEntiteAdmin(budgetFonctionnementFacultes, budgetInvestissementFacultes));
    }

    private void freeAll() {
        selectedAnnee = null;
        selectedEntiteAdministratif = null;
        selectedBudgetFaculte = null;
        selectedBudgetFonctionnement = null;
        selectedBudgetInvestissement = null;
        budgetFonctionnementFacultes = null;
        budgetInvestissementFacultes = null;
        trash.clear();
    }

    private void freeSelection() {
        selectedEntiteAdministratif = null;
        selectedBudgetFaculte = null;
        selectedBudgetFonctionnement = null;
        selectedBudgetInvestissement = null;
    }

    public String getEtat(BudgetEntiteAdministratif budgetEntiteAdministratif) {
        return budgetFacade.StrEtat(budgetEntiteAdministratif);
    }

    public boolean isItSigne(int index) {
        return budgetFonctionnementFacultes.get(index).getDateSignature() != null;
    }

    public boolean isItDeletable(int index) {
        return isItSigne(index) || budgetFonctionnementFacultes.get(index).getId() == -1;
    }
    
     public List<String> getAnnees() {
        annees = SessionUtil.getAnnees();
        return annees;
    }

    public List<EntiteAdministratif> getEntiteAdministrartifs() {
        entiteAdministrartifs = SessionUtil.getAllEntiteAdministratifs();
        return entiteAdministrartifs;
    }


    public List<BudgetEntiteAdministratif> getBudgetFonctionnementFacultes() {
        getBudgetInvestissementFacultes();
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

    public BudgetFaculte getSelectedBudgetFaculte() {
        if (selectedBudgetFaculte == null) {
            selectedBudgetFaculte = new BudgetFaculte();
        }
        return selectedBudgetFaculte;
    }

    public void setSelectedBudgetFaculte(BudgetFaculte selectedBudgetFaculte) {
        this.selectedBudgetFaculte = selectedBudgetFaculte;
    }

    public BudgetEntiteAdministratif getSelectedBudgetFonctionnement() {
        if (selectedBudgetFonctionnement == null) {
            selectedBudgetFonctionnement = new BudgetEntiteAdministratif();
        }
        return selectedBudgetFonctionnement;
    }

    public void setSelectedBudgetFonctionnement(BudgetEntiteAdministratif selectedBudgetFonctionnement) {
        this.selectedBudgetFonctionnement = selectedBudgetFonctionnement;
    }

    public BudgetEntiteAdministratif getSelectedBudgetInvestissement() {
        if (selectedBudgetInvestissement == null) {
            selectedBudgetInvestissement = new BudgetEntiteAdministratif();
        }
        return selectedBudgetInvestissement;
    }

    public void setSelectedBudgetInvestissement(BudgetEntiteAdministratif selectedBudgetInvestissement) {
        this.selectedBudgetInvestissement = selectedBudgetInvestissement;
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

    public BudgetFaculteController() {
    }

    public BudgetFaculte getSelected() {
        return selected;
    }

    public void setSelected(BudgetFaculte selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BudgetFaculteFacade getFacade() {
        return ejbFacade;
    }

    public BudgetFaculte prepareCreate() {
        selected = new BudgetFaculte();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BudgetFaculteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BudgetFaculteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BudgetFaculteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BudgetFaculte> getItems() {
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

    public BudgetFaculte getBudgetFaculte(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<BudgetFaculte> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BudgetFaculte> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BudgetFaculte.class)
    public static class BudgetFaculteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BudgetFaculteController controller = (BudgetFaculteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "budgetFaculteController");
            return controller.getBudgetFaculte(getKey(value));
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
            if (object instanceof BudgetFaculte) {
                BudgetFaculte o = (BudgetFaculte) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BudgetFaculte.class.getName()});
                return null;
            }
        }

    }

}
