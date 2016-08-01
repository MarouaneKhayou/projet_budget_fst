package bean;

import bean.EntiteAdministratif;
import bean.Faculte;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:24")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, EntiteAdministratif> entiteAdministratif;
    public static volatile SingularAttribute<User, String> adresse;
    public static volatile SingularAttribute<User, String> tel;
    public static volatile SingularAttribute<User, Faculte> faculte;
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, Integer> type;
    public static volatile SingularAttribute<User, String> nom;
    public static volatile SingularAttribute<User, String> prenom;
    public static volatile SingularAttribute<User, String> email;

}