/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.math.BigDecimal;
import bean.Budget;
import bean.BudgetEntiteAdministratif;
import bean.Compte;
import bean.CompteItem;
import java.util.List;

/**
 *
 * @author AIMAD
 */
public class CalculUtil {

    public static boolean isItLibre(Double montant, Double montantMax, Double sigmaMontant) {
        return isItLibre(montantMax - sigmaMontant, montant);
    }

    public static boolean isItLibre(BigDecimal montantLibre, BigDecimal montant) {
        return montantLibre.compareTo(montant) >= 0;
    }

    public static boolean isItLibre(Double montantLibre, Double montant) {
        return (montantLibre - montant) >= 0;
    }

    public static boolean equals(BigDecimal val1, BigDecimal val2) {
        return val1.compareTo(val2) == 0;
    }

    public static boolean isItStrictNegatif(BigDecimal val) {
        return val.compareTo(new BigDecimal(0)) < 0;
    }

    public static boolean isItPositif(BigDecimal val) {
        return !isItStrictNegatif(val);
    }

    public static boolean isItStrictPostitif(BigDecimal val) {
        return val.compareTo(new BigDecimal(0)) > 0;
    }

    public static boolean isItPositif(Double val) {
        return !isItStrictNegatif(val);
    }

    public static boolean isItStrictPostitif(Double val) {
        return val > 0;
    }

    public static boolean isItStrictNegatif(Double val) {
        return val < 0;
    }

    public static boolean isItNegatif(Double montantAffecte) {
        return montantAffecte <= 0;
    }

    public static boolean isItNegatif(BigDecimal val) {
        return !isItStrictPostitif(val);
    }

    public static BigDecimal sigmaBudget(List<Budget> budgets1, List<Budget> budgets2) {
        BigDecimal sigma = new BigDecimal(0);
        sigma = sigma.add(sigmaBudget(budgets1));
        sigma = sigma.add(sigmaBudget(budgets2));
        return sigma;
    }

    public static BigDecimal sigmaBudgetEntiteAdmin(List<BudgetEntiteAdministratif> budgets1, List<BudgetEntiteAdministratif> budgets2) {
        BigDecimal sigma = new BigDecimal(0);
        sigma = sigma.add(sigmaBudgetEntiteAdmin(budgets1));
        sigma = sigma.add(sigmaBudgetEntiteAdmin(budgets2));
        return sigma;
    }

    public static BigDecimal sigmaBudgetEntiteAdmin(List<BudgetEntiteAdministratif> budgets) {
        BigDecimal sigma = new BigDecimal(0);
        for (Budget b : budgets) {
            sigma = sigma.add(b.getMontantAffecte());
        }
        return sigma;
    }

    public static BigDecimal sigmaBudget(List<Budget> budgets) {
        BigDecimal sigma = new BigDecimal(0);
        for (Budget b : budgets) {
            sigma.add(b.getMontantAffecte());
        }
        return sigma;
    }

    public static BigDecimal sigmaBudget(BigDecimal val, List<Budget> budgets) {
        BigDecimal sigma = new BigDecimal(0);
        sigma.add(val);
        for (Budget b : budgets) {
            sigma.add(b.getMontantAffecte());
        }
        return sigma;
    }

    public static int prepareAddingBudget(Budget budget) {
        if (isItNegatif(budget.getMontantAffecte())) {
            return -1; // montant invalid
        } else {
            return 1;
        }
    }

    public static int prepareAddingBudget(BigDecimal montant) {
        if (isItNegatif(montant)) {
            return -1; // montant invalid
        } else {
            return 1;
        }
    }

    public static int prepareAffectationCompte(CompteItem compteItem, BudgetEntiteAdministratif budgetFonctionnement, BudgetEntiteAdministratif budgetInvestissement) {
        BigDecimal montantMax = compteItem.getCompte().getMontantMax();
        if (isItNegatif(compteItem.getMontantAffecteInvestissement()) || isItNegatif(compteItem.getMontantAffecteInvestissement())) {
            return -1;
        }
        if (compteItem.getMontantAffecteFonctionnement().add(compteItem.getMontantAffecteInvestissement()).compareTo(new BigDecimal(0)) == 0) {
            return -1;
        }
        if (!isItLibre(budgetFonctionnement.getMontantAffecte().subtract(budgetFonctionnement.getMontantEngage()), budgetFonctionnement.getMontantAffecte())) {
            return -4; // montant fonctionnement insufisant
        }
        if (!isItLibre(budgetInvestissement.getMontantAffecte().subtract(budgetInvestissement.getMontantEngage()), budgetInvestissement.getMontantAffecte())) {
            return -3; // montant investissement insuffisant
        }
        if (isItLibre(montantMax, compteItem.getMontantAffecte())) {
            return 1;
        } else {
            return -5; // montant max depasse
        }
    }

    public static int prepareAffectationCompteItem(
            CompteItem compteItem,
            BudgetEntiteAdministratif budgetFonctionnement,
            BudgetEntiteAdministratif budgetInvestissement,
            int type) {
        BigDecimal montantMax = compteItem.getCompte().getMontantMax();

        if (isItNegatif(compteItem.getMontantAffecteFonctionnement()) && isItNegatif(compteItem.getMontantAffecteInvestissement())) {
            return -1; // montant invalide
        }

        if (type == 1) {

            if (isItNegatif(compteItem.getMontantAffecteFonctionnement()) || isItNegatif(compteItem.getMontantAffecteInvestissement())) {
                return -1; // montant invalide
            }
            if (!isItLibre(budgetFonctionnement.getMontantAffecte().subtract(budgetFonctionnement.getMontantEngage()), compteItem.getMontantAffecteFonctionnement())) {
                return -2; /// budget fonctionnement insuff
            }
            if (!isItLibre(budgetInvestissement.getMontantAffecte().subtract(budgetInvestissement.getMontantEngage()), compteItem.getMontantAffecteInvestissement())) {
                return -3; /// budget investissement insuff
            }
        }
        if (type == 2) {
            if (!isItLibre(budgetFonctionnement.getMontantAffecte().subtract(budgetFonctionnement.getMontantEngage()), compteItem.getMontantAffecteFonctionnement())) {
                return -2; /// budget fonctionnement insuff
            }
            compteItem.setMontantAffecteInvestissement(new BigDecimal(0));
        }
        if (type == 3) {
            if (!isItLibre(budgetInvestissement.getMontantAffecte().subtract(budgetInvestissement.getMontantEngage()), compteItem.getMontantAffecteInvestissement())) {
                return -3; /// budget investissement insuff
            }
            compteItem.setMontantAffecteFonctionnement(new BigDecimal(0));
        }
        if(!isItLibre(montantMax, compteItem.getMontantAffecteFonctionnement().add(compteItem.getMontantAffecteInvestissement()))){
            return -4; // montant max dépasseé
        }
        compteItem.setMontantAffecte(compteItem.getMontantAffecteFonctionnement().add(compteItem.getMontantAffecteInvestissement()));
        
        return 1;
    }

    public static int prepareAffectationBudget(Budget newBudget, List<Budget> budgets, Budget budgetSource) {
        if (isItStrictPostitif(budgetSource.getMontantAffecte())) {
            return -1; // budget source 0
        } else {
            if (prepareAddingBudget(newBudget) == -1) {
                return -2; // montant invalid
            }
            if (!isItLibre(budgetSource.getMontantAffecte(), sigmaBudget(newBudget.getMontantAffecte(), budgets))) {
                return -3; // montant libre depassé
            }

            return 1;
        }
    }

    public static BigDecimal sigmaFonctionnementCompteItem(List<CompteItem> compteItems) {
        BigDecimal sigma = new BigDecimal(0);
        for (CompteItem ci : compteItems) {
            sigma = sigma.add(ci.getMontantAffecteFonctionnement());
        }
        return sigma;
    }

    public static BigDecimal sigmaInvestissementCompteItem(List<CompteItem> compteItems) {
        BigDecimal sigma = new BigDecimal(0);
        for (CompteItem ci : compteItems) {
            sigma = sigma.add(ci.getMontantAffecteInvestissement());
        }
        return sigma;
    }

}
