package bean;

import bean.User;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:24")
@StaticMetamodel(Budget.class)
public class Budget_ { 

    public static volatile SingularAttribute<Budget, BigDecimal> relicatPaye;
    public static volatile SingularAttribute<Budget, Date> dateSignature;
    public static volatile SingularAttribute<Budget, User> responsableValidation;
    public static volatile SingularAttribute<Budget, User> responsableAffectation;
    public static volatile SingularAttribute<Budget, String> annee;
    public static volatile SingularAttribute<Budget, Integer> type;
    public static volatile SingularAttribute<Budget, Double> creditOuvert;
    public static volatile SingularAttribute<Budget, BigDecimal> relicatEengage;
    public static volatile SingularAttribute<Budget, BigDecimal> montantAffecte;
    public static volatile SingularAttribute<Budget, Date> dateAffectation;
    public static volatile SingularAttribute<Budget, Long> id;
    public static volatile SingularAttribute<Budget, BigDecimal> montantEngage;
    public static volatile SingularAttribute<Budget, Date> dateValidation;
    public static volatile SingularAttribute<Budget, String> commentaire;
    public static volatile SingularAttribute<Budget, BigDecimal> montantPaye;

}