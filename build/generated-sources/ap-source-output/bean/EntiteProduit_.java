package bean;

import bean.DemandeEntreeStockItem;
import bean.EntiteProduitItem;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:22")
@StaticMetamodel(EntiteProduit.class)
public class EntiteProduit_ { 

    public static volatile ListAttribute<EntiteProduit, EntiteProduitItem> entiteProduitItems;
    public static volatile SingularAttribute<EntiteProduit, Boolean> livred;
    public static volatile SingularAttribute<EntiteProduit, Long> qteCommande;
    public static volatile SingularAttribute<EntiteProduit, Long> id;
    public static volatile SingularAttribute<EntiteProduit, Long> qteLivre;
    public static volatile SingularAttribute<EntiteProduit, DemandeEntreeStockItem> demandeEntreeStockItem;
    public static volatile SingularAttribute<EntiteProduit, Long> qteRecu;

}