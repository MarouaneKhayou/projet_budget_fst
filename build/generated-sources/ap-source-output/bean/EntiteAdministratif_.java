package bean;

import bean.BudgetEntiteAdministratif;
import bean.Faculte;
import bean.Magasin;
import bean.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:26")
@StaticMetamodel(EntiteAdministratif.class)
public class EntiteAdministratif_ { 

    public static volatile SingularAttribute<EntiteAdministratif, Double> creditOuvert;
    public static volatile SingularAttribute<EntiteAdministratif, User> chef;
    public static volatile SingularAttribute<EntiteAdministratif, String> logo;
    public static volatile SingularAttribute<EntiteAdministratif, Long> id;
    public static volatile ListAttribute<EntiteAdministratif, BudgetEntiteAdministratif> budgetEntiteAdministratifs;
    public static volatile SingularAttribute<EntiteAdministratif, Faculte> faculte;
    public static volatile SingularAttribute<EntiteAdministratif, Integer> type;
    public static volatile SingularAttribute<EntiteAdministratif, Magasin> magasin;
    public static volatile SingularAttribute<EntiteAdministratif, String> nom;

}