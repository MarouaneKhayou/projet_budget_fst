package bean;

import bean.Compte;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:23")
@StaticMetamodel(CompteItem.class)
public class CompteItem_ { 

    public static volatile SingularAttribute<CompteItem, BigDecimal> montantAffecteInvestissement;
    public static volatile SingularAttribute<CompteItem, BigDecimal> montantAffecte;
    public static volatile SingularAttribute<CompteItem, Long> id;
    public static volatile SingularAttribute<CompteItem, BigDecimal> montantEngage;
    public static volatile SingularAttribute<CompteItem, BigDecimal> montantAffecteFonctionnement;
    public static volatile SingularAttribute<CompteItem, BigDecimal> montantPaye;
    public static volatile SingularAttribute<CompteItem, Compte> compte;

}