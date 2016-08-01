package bean;

import bean.BudgetFaculte;
import bean.EntiteAdministratif;
import bean.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:22")
@StaticMetamodel(Faculte.class)
public class Faculte_ { 

    public static volatile ListAttribute<Faculte, EntiteAdministratif> entiteAdministratifs;
    public static volatile ListAttribute<Faculte, BudgetFaculte> budgetFacultes;
    public static volatile SingularAttribute<Faculte, String> adresse;
    public static volatile SingularAttribute<Faculte, String> logo;
    public static volatile SingularAttribute<Faculte, Long> id;
    public static volatile SingularAttribute<Faculte, String> nom;
    public static volatile ListAttribute<Faculte, User> users;

}