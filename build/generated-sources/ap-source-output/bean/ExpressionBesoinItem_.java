package bean;

import bean.CategorieProduit;
import bean.ExpressionBesoin;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:22")
@StaticMetamodel(ExpressionBesoinItem.class)
public class ExpressionBesoinItem_ { 

    public static volatile SingularAttribute<ExpressionBesoinItem, CategorieProduit> categorieProduit;
    public static volatile SingularAttribute<ExpressionBesoinItem, ExpressionBesoin> expressionBesoin;
    public static volatile SingularAttribute<ExpressionBesoinItem, String> description;
    public static volatile SingularAttribute<ExpressionBesoinItem, Long> qteDemande;
    public static volatile SingularAttribute<ExpressionBesoinItem, Long> id;

}