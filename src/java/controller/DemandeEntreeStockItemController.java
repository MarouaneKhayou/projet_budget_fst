package controller;

import bean.DemandeEntreeStockItem;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.DemandeEntreeStockItemFacade;

import java.io.Serializable;
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

@Named("demandeEntreeStockItemController")
@SessionScoped
public class DemandeEntreeStockItemController implements Serializable {

    @EJB
    private service.DemandeEntreeStockItemFacade ejbFacade;
    private List<DemandeEntreeStockItem> items = null;
    private DemandeEntreeStockItem selected;

    public DemandeEntreeStockItemController() {
    }

    public DemandeEntreeStockItem getSelected() {
        return selected;
    }

    public void setSelected(DemandeEntreeStockItem selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DemandeEntreeStockItemFacade getFacade() {
        return ejbFacade;
    }

    public DemandeEntreeStockItem prepareCreate() {
        selected = new DemandeEntreeStockItem();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DemandeEntreeStockItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DemandeEntreeStockItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DemandeEntreeStockItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DemandeEntreeStockItem> getItems() {
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

    public DemandeEntreeStockItem getDemandeEntreeStockItem(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<DemandeEntreeStockItem> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DemandeEntreeStockItem> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DemandeEntreeStockItem.class)
    public static class DemandeEntreeStockItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DemandeEntreeStockItemController controller = (DemandeEntreeStockItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "demandeEntreeStockItemController");
            return controller.getDemandeEntreeStockItem(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DemandeEntreeStockItem) {
                DemandeEntreeStockItem o = (DemandeEntreeStockItem) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DemandeEntreeStockItem.class.getName()});
                return null;
            }
        }

    }

}
