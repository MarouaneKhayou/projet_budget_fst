package bean;

import bean.CategorieProduit;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:22")
@StaticMetamodel(Compte.class)
public class Compte_ { 

    public static volatile SingularAttribute<Compte, BigDecimal> montantMax;
    public static volatile SingularAttribute<Compte, Long> id;
    public static volatile SingularAttribute<Compte, String> designation;
    public static volatile ListAttribute<Compte, CategorieProduit> categorieProduits;

}