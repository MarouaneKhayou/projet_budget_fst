package bean;

import bean.EntiteAdministratif;
import bean.EntiteProduit;
import bean.Livraison;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:22")
@StaticMetamodel(EntiteProduitItem.class)
public class EntiteProduitItem_ { 

    public static volatile SingularAttribute<EntiteProduitItem, Boolean> livred;
    public static volatile ListAttribute<EntiteProduitItem, Livraison> livraisons;
    public static volatile SingularAttribute<EntiteProduitItem, Long> qteCommande;
    public static volatile SingularAttribute<EntiteProduitItem, EntiteAdministratif> entiteAdministratif;
    public static volatile SingularAttribute<EntiteProduitItem, Long> id;
    public static volatile SingularAttribute<EntiteProduitItem, Long> qteLivre;
    public static volatile SingularAttribute<EntiteProduitItem, EntiteProduit> entiteProduit;
    public static volatile SingularAttribute<EntiteProduitItem, Long> qteRecu;

}