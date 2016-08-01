package bean;

import bean.DemandeEntreeStock;
import bean.EntiteProduit;
import bean.ProduitItem;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:24")
@StaticMetamodel(DemandeEntreeStockItem.class)
public class DemandeEntreeStockItem_ { 

    public static volatile SingularAttribute<DemandeEntreeStockItem, Boolean> livred;
    public static volatile SingularAttribute<DemandeEntreeStockItem, ProduitItem> produitItem;
    public static volatile SingularAttribute<DemandeEntreeStockItem, Long> qteCommande;
    public static volatile SingularAttribute<DemandeEntreeStockItem, DemandeEntreeStock> demandeEntreeStock;
    public static volatile SingularAttribute<DemandeEntreeStockItem, Double> montant;
    public static volatile SingularAttribute<DemandeEntreeStockItem, String> id;
    public static volatile SingularAttribute<DemandeEntreeStockItem, Long> qteLivre;
    public static volatile SingularAttribute<DemandeEntreeStockItem, EntiteProduit> entiteProduit;
    public static volatile SingularAttribute<DemandeEntreeStockItem, Long> qteRecu;

}