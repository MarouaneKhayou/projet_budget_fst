package bean;

import bean.DemandeEntreeStockItem;
import bean.ExpressionBesoin;
import bean.Produit;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:22")
@StaticMetamodel(ProduitItem.class)
public class ProduitItem_ { 

    public static volatile SingularAttribute<ProduitItem, ExpressionBesoin> expressionBesoin;
    public static volatile SingularAttribute<ProduitItem, Integer> qteConfirm;
    public static volatile SingularAttribute<ProduitItem, Produit> produit;
    public static volatile SingularAttribute<ProduitItem, Integer> qteDemande;
    public static volatile SingularAttribute<ProduitItem, Long> id;
    public static volatile SingularAttribute<ProduitItem, DemandeEntreeStockItem> demandeEntreeStockItem;

}