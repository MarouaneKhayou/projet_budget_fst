package bean;

import bean.DemandeEntreeStockItem;
import bean.Fournisseur;
import bean.Reception;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-31T01:29:23")
@StaticMetamodel(DemandeEntreeStock.class)
public class DemandeEntreeStock_ { 

    public static volatile SingularAttribute<DemandeEntreeStock, Date> dateCreation;
    public static volatile SingularAttribute<DemandeEntreeStock, Boolean> livred;
    public static volatile ListAttribute<DemandeEntreeStock, Reception> receptions;
    public static volatile SingularAttribute<DemandeEntreeStock, BigDecimal> montantTotal;
    public static volatile SingularAttribute<DemandeEntreeStock, Boolean> terminee;
    public static volatile ListAttribute<DemandeEntreeStock, DemandeEntreeStockItem> demandeEntreeStockItems;
    public static volatile SingularAttribute<DemandeEntreeStock, Fournisseur> fournisseur;
    public static volatile SingularAttribute<DemandeEntreeStock, Long> id;
    public static volatile SingularAttribute<DemandeEntreeStock, Integer> type;
    public static volatile SingularAttribute<DemandeEntreeStock, BigDecimal> tva;

}