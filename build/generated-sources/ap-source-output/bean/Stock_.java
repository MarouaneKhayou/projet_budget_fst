package bean;

import bean.Magasin;
import bean.Produit;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:23")
@StaticMetamodel(Stock.class)
public class Stock_ { 

    public static volatile SingularAttribute<Stock, BigDecimal> qte;
    public static volatile SingularAttribute<Stock, Produit> produit;
    public static volatile SingularAttribute<Stock, BigDecimal> qteDeffectueux;
    public static volatile SingularAttribute<Stock, Long> id;
    public static volatile SingularAttribute<Stock, Magasin> magasin;

}