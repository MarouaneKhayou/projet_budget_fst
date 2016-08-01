package bean;

import bean.CompteItem;
import bean.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:23")
@StaticMetamodel(ExpressionBesoin.class)
public class ExpressionBesoin_ { 

    public static volatile SingularAttribute<ExpressionBesoin, Date> dateCreation;
    public static volatile SingularAttribute<ExpressionBesoin, Long> total;
    public static volatile SingularAttribute<ExpressionBesoin, CompteItem> compteItem;
    public static volatile SingularAttribute<ExpressionBesoin, Double> montantTotal;
    public static volatile SingularAttribute<ExpressionBesoin, Long> id;
    public static volatile SingularAttribute<ExpressionBesoin, User> demandeur;

}