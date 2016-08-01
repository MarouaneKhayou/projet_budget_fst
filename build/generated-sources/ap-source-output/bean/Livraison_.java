package bean;

import bean.EntiteProduitItem;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:23")
@StaticMetamodel(Livraison.class)
public class Livraison_ { 

    public static volatile SingularAttribute<Livraison, Date> date;
    public static volatile SingularAttribute<Livraison, EntiteProduitItem> entiteProduitItem;
    public static volatile SingularAttribute<Livraison, Long> id;
    public static volatile SingularAttribute<Livraison, Long> qteLivre;

}