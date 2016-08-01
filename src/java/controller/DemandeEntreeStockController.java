package controller;

import bean.DemandeEntreeStock;
import bean.DemandeEntreeStockItem;
import bean.EntiteAdministratif;
import bean.EntiteProduitItem;
import bean.Magasin;
import bean.Produit;
import bean.ProduitItem;
import bean.Reception;
import bean.ReceptionItem;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.Message;
import controller.util.MessageManager;
import controller.util.SessionUtil;
import service.DemandeEntreeStockFacade;

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
import service.DemandeEntreeStockItemFacade;
import service.EntiteAdministratifFacade;
import service.MagasinFacade;
import service.ProduitItemFacade;
import service.ReceptionFacade;
import service.ReceptionItemFacade;

@Named("demandeEntreeStockController")
@SessionScoped
public class DemandeEntreeStockController implements Serializable {

    @EJB
    private service.DemandeEntreeStockFacade ejbFacade;
    
    @EJB
    private service.DemandeEntreeStockItemFacade demandeEntreeStockItemFacade;
    @EJB
    private service.ProduitItemFacade produitItemFacade;
    @EJB
    private service.EntiteAdministratifFacade entiteAdministratifFacade;
    @EJB
    private service.MagasinFacade magasinFacade;
    @EJB
    private service.ReceptionFacade receptionFacade;
    @EJB
    private service.ReceptionItemFacade receptionItemFacade;

    private List<DemandeEntreeStock> items = null;
    private DemandeEntreeStock selected;

    private DemandeEntreeStockItem demandeEntreeStockItem;

    private EntiteProduitItem entiteProduitItem;
    private Date date;
    private int quantite;

    private Produit produit;
    private List<Produit> produits;

    private Magasin magasin;

    private Reception reception;
    private ReceptionItem receptionItem;
    private List<ReceptionItem> receptionItems;

    private Long temporaryId = new Long(0);

    public void affecter() {
        if (getReceptionItems().isEmpty()) {
            Message msg = MessageManager.createWarnMessage(0, "Vieullez ajouter des items!!");
            MessageManager.showMessage(msg);
        } else {
            reception = new Reception();
            int id = getFacade().getSequence();

            reception.setId(new Long(id));
            reception.setDate(date);
            reception.setDemandeEntreeStock(selected);
            reception.setMagasin(magasin);
            
            getReceptionFacade().create(reception);

            getFacade().incrementSequence();

            for (ReceptionItem ri : receptionItems) {
                ri.setId(null);
                ri.setReception(reception);
                getReceptionItemFacade().create(ri);
                updateDemandeItem(ri.getProduit(), ri.getQte());
                getMagasinFacade().insertProduct(magasin, ri.getProduit(), ri.getQte());
            }
            annuler();
            Message msg = MessageManager.createWarnMessage(0, "Operation effectuee avec success !");
            MessageManager.showMessage(msg);
        }

    }

    private void updateDemandeItem(Produit produit, BigDecimal quantite) {
        for (DemandeEntreeStockItem desi : selected.getDemandeEntreeStockItems()) {
            if (desi.getProduitItem().getProduit().equals(produit)) {

                desi.setQteRecu(quantite.add(new BigDecimal(desi.getQteRecu())).longValue());

                getDemandeEntreeStockItemFacade().edit(desi);
            }
        }
    }

    public void addItem() {
        System.out.println("Ajouuuuuut");
        if (produit == null) {
            Message msg = MessageManager.createWarnMessage(0, "Vieullez selectionner un Produit !!");
            MessageManager.showMessage(msg);
        } else {
            ReceptionItem item = new ReceptionItem();
            item.setId(++temporaryId);
            item.setProduit(produit);
            produits.remove(produit);
            item.setQte(new BigDecimal(quantite));
            getReceptionItems().add(item);
            System.out.println("Item : " + item);
            System.out.println("RecItem -> " + receptionItems.size());
            System.out.println("Maachi Nullll");
        }

    }

    public void annuler() {
        reception = null;
        receptionItems = null;
        temporaryId = new Long(0);
    }

    public Reception getReception() {
        if (reception == null) {
            reception = new Reception();
        }
        return reception;
    }

    public void setReception(Reception reception) {
        this.reception = reception;
    }

    public ReceptionItem getReceptionItem() {
        if (receptionItem == null) {
            receptionItem = new ReceptionItem();
        }
        return receptionItem;
    }

    public void setReceptionItem(ReceptionItem receptionItem) {
        this.receptionItem = receptionItem;
    }

    public List<ReceptionItem> getReceptionItems() {
        if (receptionItems == null) {
            receptionItems = new ArrayList<>();
        }
        return receptionItems;
    }

    public void setReceptionItems(List<ReceptionItem> receptionItems) {
        this.receptionItems = receptionItems;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Magasin getMagasin() {
        EntiteAdministratif ea = getEntiteAdministratifFacade().getAdminEntite();
        System.out.println("Ha lenitite :: " + ea);
        magasin = ea.getMagasin();
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Date getDate() {
        if (date == null) {
            date = new Date();
        }
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public void setAvailableProduits() {
        System.out.println("LOCKING FOR PRODUCTS IN DATABASE !!!");
        System.out.println("HAHA :: " + selected.getDemandeEntreeStockItems().size());
        List<Produit> res = new ArrayList<>();
        for (DemandeEntreeStockItem desi : selected.getDemandeEntreeStockItems()) {
            System.out.println("Product : n " + desi.getProduitItem().getProduit());
            res.add(desi.getProduitItem().getProduit());
        }
        produits = res;
    }

    public DemandeEntreeStockItemFacade getDemandeEntreeStockItemFacade() {
        return demandeEntreeStockItemFacade;
    }

    public void setDemandeEntreeStockItemFacade(DemandeEntreeStockItemFacade demandeEntreeStockItemFacade) {
        this.demandeEntreeStockItemFacade = demandeEntreeStockItemFacade;
    }

    public List<Produit> getProduits() {
        //setAvailableProduits();
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public EntiteProduitItem getEntiteProduitItem() {
        if (entiteProduitItem == null) {
            entiteProduitItem = new EntiteProduitItem();
        }
        return entiteProduitItem;
    }

    public void setEntiteProduitItem(EntiteProduitItem entiteProduitItem) {
        this.entiteProduitItem = entiteProduitItem;
    }

    public DemandeEntreeStockItem getDemandeEntreeStockItem() {
        if (demandeEntreeStockItem == null) {
            demandeEntreeStockItem = new DemandeEntreeStockItem();
        }
        return demandeEntreeStockItem;
    }

    public void setDemandeEntreeStockItem(DemandeEntreeStockItem demandeEntreeStockItem) {
        this.demandeEntreeStockItem = demandeEntreeStockItem;
    }

    public DemandeEntreeStockController() {
    }

    public DemandeEntreeStock getSelected() {
        if (selected == null) {
            selected = new DemandeEntreeStock();
        }

        return selected;
    }

    public MagasinFacade getMagasinFacade() {
        if (magasinFacade == null) {
            magasinFacade = new MagasinFacade();
        }
        return magasinFacade;
    }

    public void setMagasinFacade(MagasinFacade magasinFacade) {
        this.magasinFacade = magasinFacade;
    }

    public EntiteAdministratifFacade getEntiteAdministratifFacade() {
        if (entiteAdministratifFacade == null) {
            entiteAdministratifFacade = new EntiteAdministratifFacade();
        }
        return entiteAdministratifFacade;
    }

    public void setEntiteAdministratifFacade(EntiteAdministratifFacade entiteAdministratifFacade) {
        this.entiteAdministratifFacade = entiteAdministratifFacade;
    }

    public ProduitItemFacade getProduitItemFacade() {
        if (produitItemFacade == null) {
            System.out.println("FACADE IS NULL");
            produitItemFacade = new ProduitItemFacade();
        }
        return produitItemFacade;
    }

    public void setProduitItemFacade(ProduitItemFacade produitItemFacade) {
        this.produitItemFacade = produitItemFacade;
    }

    public void setSelected(DemandeEntreeStock selected) {
        setDemandeEntreeStockItem(new DemandeEntreeStockItem());
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DemandeEntreeStockFacade getFacade() {
        if (ejbFacade == null) {
            ejbFacade = new DemandeEntreeStockFacade();
        }
        return ejbFacade;
    }

    public ReceptionFacade getReceptionFacade() {
        if (receptionFacade == null) {
            receptionFacade = new ReceptionFacade();
        }

        return receptionFacade;
    }

    public void setReceptionFacade(ReceptionFacade receptionFacade) {
        this.receptionFacade = receptionFacade;
    }

    public ReceptionItemFacade getReceptionItemFacade() {
        if (receptionItemFacade == null) {
            receptionItemFacade = new ReceptionItemFacade();
        }
        return receptionItemFacade;
    }

    public void setReceptionItemFacade(ReceptionItemFacade receptionItemFacade) {
        this.receptionItemFacade = receptionItemFacade;
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
        items = getFacade().findAll();

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
            DemandeEntreeStockController controller = (DemandeEntreeStockController) facesContext.getApplication().getELResolver().
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

}
