package bean;

import bean.DemandeEntreeStock;
import bean.Magasin;
import bean.ReceptionItem;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:24")
@StaticMetamodel(Reception.class)
public class Reception_ { 

    public static volatile SingularAttribute<Reception, Date> date;
    public static volatile ListAttribute<Reception, ReceptionItem> receptionItems;
    public static volatile SingularAttribute<Reception, DemandeEntreeStock> demandeEntreeStock;
    public static volatile SingularAttribute<Reception, Long> id;
    public static volatile SingularAttribute<Reception, Magasin> magasin;

}