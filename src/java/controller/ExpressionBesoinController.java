package controller;

import bean.CategorieProduit;
import bean.Compte;
import bean.CompteItem;
import bean.EntiteAdministratif;
import bean.ExpressionBesoin;
import bean.ExpressionBesoinItem;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import service.ExpressionBesoinFacade;

import java.io.Serializable;
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

@Named("expressionBesoinController")
@SessionScoped
public class ExpressionBesoinController implements Serializable {

    @EJB
    private service.ExpressionBesoinFacade ejbFacade;
    @EJB
    private service.CompteItemFacade compteItemFacade;

    
    
    private List<ExpressionBesoin> items = null;
    private ExpressionBesoin selected;
    
    private List<String> annees;
    private List<EntiteAdministratif> entiteAdministratifs;
    private List<Compte> comptes;
    private List<CompteItem> compteItems;
    private List<CategorieProduit> categorieProduits;
    private List<ExpressionBesoinItem> expressionBesoinItems;
    private List<ExpressionBesoin> expressionBesoins;
    
    private String selectedAnnee;
    private EntiteAdministratif selectedEntiteAdministratif;
    private Compte selectedCompte;
    private CompteItem selectedCompteItem;
    
    private ExpressionBesoin selectedExpressionBesoin;
    private ExpressionBesoinItem selectedExpressionBesoinItem;
    
    
    public void onBtnSelect(){
        compteItems = compteItemFacade.findByEntiteAdministratifAndAnnee(selectedEntiteAdministratif, selectedAnnee);
    }
    
    public void onBtnExpressionBesoinList(int index){
        expressionBesoins = ejbFacade.findByCompteItem(compteItems.get(index));
    }
   
    public List<String> getAnnees() {
        if(annees == null){
            annees = SessionUtil.getAnnees();
        }
        return annees;
    }

    public List<EntiteAdministratif> getEntiteAdministratifs() {
        if(entiteAdministratifs == null){
            entiteAdministratifs = SessionUtil.getCurrentEntiteAdministratifs();
        }
        return entiteAdministratifs;
    }


    public List<Compte> getComptes() {
        if(comptes == null){
            comptes = SessionUtil.getComptes();
        }
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public List<CategorieProduit> getCategorieProduits() {
        if(categorieProduits == null){
            categorieProduits = new ArrayList<>();
        }
        return categorieProduits;
    }

    public void setCategorieProduits(List<CategorieProduit> categorieProduits) {
        this.categorieProduits = categorieProduits;
    }

    public List<ExpressionBesoinItem> getExpressionBesoinItems() {
        if(expressionBesoinItems == null){
            expressionBesoinItems = new ArrayList<>();
        }
        return expressionBesoinItems;
    }

    public void setExpressionBesoinItems(List<ExpressionBesoinItem> expressionBesoinItems) {
        this.expressionBesoinItems = expressionBesoinItems;
    }

    public List<ExpressionBesoin> getExpressionBesoins() {
        if(expressionBesoins == null){
            expressionBesoins = new ArrayList<>();
        }
        return expressionBesoins;
    }

    public List<CompteItem> getCompteItems() {
        if(compteItems == null){
            compteItems = new ArrayList<>();
        }
        return compteItems;
    }

    public void setCompteItems(List<CompteItem> compteItems) {
        this.compteItems = compteItems;
    }

    public void setExpressionBesoins(List<ExpressionBesoin> expressionBesoins) {
        this.expressionBesoins = expressionBesoins;
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

    public Compte getSelectedCompte() {
        return selectedCompte;
    }

    public void setSelectedCompte(Compte selectedCompte) {
        this.selectedCompte = selectedCompte;
    }

    public CompteItem getSelectedCompteItem() {
        return selectedCompteItem;
    }

    public void setSelectedCompteItem(CompteItem selectedCompteItem) {
        this.selectedCompteItem = selectedCompteItem;
    }

    public ExpressionBesoin getSelectedExpressionBesoin() {
        return selectedExpressionBesoin;
    }

    public void setSelectedExpressionBesoin(ExpressionBesoin selectedExpressionBesoin) {
        this.selectedExpressionBesoin = selectedExpressionBesoin;
    }

    public ExpressionBesoinItem getSelectedExpressionBesoinItem() {
        return selectedExpressionBesoinItem;
    }

    public void setSelectedExpressionBesoinItem(ExpressionBesoinItem selectedExpressionBesoinItem) {
        this.selectedExpressionBesoinItem = selectedExpressionBesoinItem;
    }
    
    

    public ExpressionBesoinController() {
    }

    public ExpressionBesoin getSelected() {
        return selected;
    }

    public void setSelected(ExpressionBesoin selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ExpressionBesoinFacade getFacade() {
        return ejbFacade;
    }

    public ExpressionBesoin prepareCreate() {
        selected = new ExpressionBesoin();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ExpressionBesoinCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExpressionBesoinUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ExpressionBesoinDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ExpressionBesoin> getItems() {
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

    public ExpressionBesoin getExpressionBesoin(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ExpressionBesoin> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ExpressionBesoin> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ExpressionBesoin.class)
    public static class ExpressionBesoinControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExpressionBesoinController controller = (ExpressionBesoinController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "expressionBesoinController");
            return controller.getExpressionBesoin(getKey(value));
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
            if (object instanceof ExpressionBesoin) {
                ExpressionBesoin o = (ExpressionBesoin) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ExpressionBesoin.class.getName()});
                return null;
            }
        }

    }

}
