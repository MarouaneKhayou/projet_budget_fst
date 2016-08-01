package controller;

import bean.DemandeEntreeStock;
import bean.DemandeEntreeStockItem;
import bean.EntiteProduitItem;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.DemandeEntreeStockFacade;

import java.io.Serializable;
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
import service.EntiteProduitItemFacade;
import service.LivraisonFacade;

@Named("livraisonController")
@SessionScoped
public class LivraisonController implements Serializable {

    @EJB
    private service.DemandeEntreeStockFacade ejbFacade;
    @EJB
    private service.DemandeEntreeStockItemFacade demandeEntreeStockItemFacade;
    @EJB
    private EntiteProduitItemFacade entiteProduitItemFacade;
    @EJB
    private LivraisonFacade livraisonFacade;
    private List<DemandeEntreeStock> items = null;
    private DemandeEntreeStock selected;
    private Date datedemande;
    private List<EntiteProduitItem> entiteProduitItems;
    private List<Long> quantites;
    private Long quantiteLivraison;
    private EntiteProduitItem entiteProduitItem;
    private DemandeEntreeStockItem demandeEntreeStockItem;
    private Long nullquantite;
    private DemandeEntreeStock demandeEntreeStockHelper;
    private int testFirstTime = 0;

    public void changequantites(EntiteProduitItem entiteProduitItem) {
        quantites = demandeEntreeStockItemFacade.getQuantitiesLivraison(entiteProduitItem);
     
        this.entiteProduitItem = entiteProduitItem;

    }

    public void livrer() {

        try {
            livraisonFacade.livrer(entiteProduitItem, quantiteLivraison);

            getdemandeitems(selected);

            getdemandeenteeStockItemDetails(this.demandeEntreeStockItem);

            quantiteLivraison = null;
           JsfUtil.addSuccessMessage("Livraison Effectuée");
        } catch (Exception e) {
            e.printStackTrace();
             JsfUtil.addErrorMessage("erreur");
            
        }

    }

    public boolean isdisabled(EntiteProduitItem entiteProduitItem) {
        Long qteCo = entiteProduitItem.getQteCommande();
        Long qteLi = entiteProduitItem.getQteLivre();
        Long qtepRecu = entiteProduitItem.getEntiteProduit().getDemandeEntreeStockItem().getQteRecu();
        Long qtepLivre = entiteProduitItem.getEntiteProduit().getDemandeEntreeStockItem().getQteLivre();

        if (qteCo == qteLi || qtepRecu == qtepLivre) {
            return true;
        } else {
            return false;
        }

    }

    public void getdemandeitems(DemandeEntreeStock demandeEntreeStock) {

        selected = ejbFacade.find(demandeEntreeStock.getId());
        selected.setDemandeEntreeStockItems(demandeEntreeStockItemFacade.getdemandeitemByDemande(selected));

        demandeEntreeStockHelper = selected;

       
        entiteProduitItems = null;

    }
 
    public void getdemandeenteeStockItemDetails(DemandeEntreeStockItem demandeEntreeStockItem) {

        this.demandeEntreeStockItem = demandeEntreeStockItem;
        this.entiteProduitItems = entiteProduitItemFacade.getEntitProduitItembyentiteProduit(demandeEntreeStockItem.getEntiteProduit());
      
    }

    public List<EntiteProduitItem> getEntiteProduitItems() {
        return entiteProduitItems;
    }

    public void setEntiteProduitItems(List<EntiteProduitItem> entiteProduitItems) {
        this.entiteProduitItems = entiteProduitItems;
    }

    public Date getDatedemande() {
        return datedemande;
    }

    public void setDatedemande(Date datedemande) {
        this.datedemande = datedemande;
    }

    public void dateChaged() {
        testFirstTime = 1;
        items = ejbFacade.findNotLivredDeamndebyDate(datedemande);

        datedemande = null;
        selected = null;
        entiteProduitItems = null;

    }

    public List<Long> getQuantites() {
        return quantites;
    }

    public void setQuantites(List<Long> quantites) {
        this.quantites = quantites;
    }

    public Long getQuantiteLivraison() {
        return quantiteLivraison;
    }

    public void setQuantiteLivraison(Long quantiteLivraison) {
        this.quantiteLivraison = quantiteLivraison;
    }

    public EntiteProduitItem getEntiteProduitItem() {
        return entiteProduitItem;
    }

    public void setEntiteProduitItem(EntiteProduitItem entiteProduitItem) {
        this.entiteProduitItem = entiteProduitItem;
    }

    public LivraisonController() {
    }

    public DemandeEntreeStock getSelected() {
        return selected;
    }

    public void setSelected(DemandeEntreeStock selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DemandeEntreeStockFacade getFacade() {
        return ejbFacade;
    }

    public DemandeEntreeStock prepareCreate() {
        selected = new DemandeEntreeStock();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DemandeEntreeStockCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DemandeEntreeStockUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DemandeEntreeStockDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DemandeEntreeStock> getItems() {

        if (testFirstTime == 0) {
           
            items = getFacade().findNotLivredDeamndebyDate(null);
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

    public DemandeEntreeStock getDemandeEntreeStock(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<DemandeEntreeStock> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DemandeEntreeStock> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DemandeEntreeStock.class)
    public static class DemandeEntreeStockControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LivraisonController controller = (LivraisonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "demandeEntreeStockController");
            return controller.getDemandeEntreeStock(getKey(value));
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
            if (object instanceof DemandeEntreeStock) {
                DemandeEntreeStock o = (DemandeEntreeStock) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DemandeEntreeStock.class.getName()});
                return null;
            }
        }

    }

    public DemandeEntreeStockItem getDemandeEntreeStockItem() {
        return demandeEntreeStockItem;
    }

    public void setDemandeEntreeStockItem(DemandeEntreeStockItem demandeEntreeStockItem) {
        this.demandeEntreeStockItem = demandeEntreeStockItem;
    }

    public Long getNullquantite() {
        return null;
    }

    public void setNullquantite(Long nullquantite) {
        this.nullquantite = nullquantite;
    }

    public DemandeEntreeStock getDemandeEntreeStockHelper() {
        return demandeEntreeStockHelper;
    }

    public void setDemandeEntreeStockHelper(DemandeEntreeStock demandeEntreeStockHelper) {
        this.demandeEntreeStockHelper = demandeEntreeStockHelper;
    }

    public int getTestFirstTime() {

        return testFirstTime;
    }

    public void setTestFirstTime(int testFirstTime) {
        this.testFirstTime = testFirstTime;
    }

}
