package bean;

import bean.Produit;
import bean.Reception;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:24")
@StaticMetamodel(ReceptionItem.class)
public class ReceptionItem_ { 

    public static volatile SingularAttribute<ReceptionItem, BigDecimal> qte;
    public static volatile SingularAttribute<ReceptionItem, Produit> produit;
    public static volatile SingularAttribute<ReceptionItem, Long> id;
    public static volatile SingularAttribute<ReceptionItem, Reception> reception;
    public static volatile SingularAttribute<ReceptionItem, BigDecimal> qteAvoir;

}