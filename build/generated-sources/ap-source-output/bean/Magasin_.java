package bean;

import bean.EntiteAdministratif;
import bean.Reception;
import bean.Stock;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:22")
@StaticMetamodel(Magasin.class)
public class Magasin_ { 

    public static volatile ListAttribute<Magasin, Reception> receptions;
    public static volatile SingularAttribute<Magasin, EntiteAdministratif> entiteAdministratif;
    public static volatile SingularAttribute<Magasin, Long> id;
    public static volatile ListAttribute<Magasin, Stock> stocks;

}