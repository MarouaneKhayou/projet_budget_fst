package bean;

import bean.Compte;
import bean.Produit;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:24")
@StaticMetamodel(CategorieProduit.class)
public class CategorieProduit_ { 

    public static volatile ListAttribute<CategorieProduit, Produit> produits;
    public static volatile SingularAttribute<CategorieProduit, String> libelle;
    public static volatile SingularAttribute<CategorieProduit, Long> id;
    public static volatile SingularAttribute<CategorieProduit, Compte> compte;

}