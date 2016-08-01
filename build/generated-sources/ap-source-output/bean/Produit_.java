package bean;

import bean.CategorieProduit;
import bean.UniteMesure;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:23")
@StaticMetamodel(Produit.class)
public class Produit_ { 

    public static volatile SingularAttribute<Produit, UniteMesure> uniteMesure;
    public static volatile SingularAttribute<Produit, CategorieProduit> categorieProduit;
    public static volatile SingularAttribute<Produit, Double> prix;
    public static volatile SingularAttribute<Produit, String> libelle;
    public static volatile SingularAttribute<Produit, String> description;
    public static volatile SingularAttribute<Produit, Long> id;

}