package controller.util;

import bean.BudgetEntiteAdministratifItem;
import bean.BudgetFaculte;
import bean.Compte;
import bean.CompteItem;
import bean.EntiteAdministratif;
import bean.Faculte;
import bean.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final SessionUtil instance = new SessionUtil();

    private SessionUtil() {
    }

    public static void attachUserToSession(User user) {
        Faculte myFaculte = user.getFaculte();
        myFaculte.getUsers().add(user);
        registerUser(user);
    }

    public static void registerUser(User user) {
        setAttribute("user", user);
    }

    public static void registerBudgetEntiteAdministratifItems(List<BudgetEntiteAdministratifItem> budgetEntiteAdministratifItems) {
        setAttribute("budgetEntiteAdministratifItems", budgetEntiteAdministratifItems);
    }

    public static void registerComptes(List<Compte> comptes) {
        setAttribute("comptes", comptes);
    }

    public static void registerCompteItems(List<CompteItem> compteItems) {
        setAttribute("compteItems", compteItems);
    }

    public static User getConnectedUser() {
        return (User) getAttribute("user");
    }

    public static Faculte getCurrentFaculte() {
        User user = getConnectedUser();
        if (user != null && user.getFaculte() != null) {
            return user.getFaculte();
        }
        return new Faculte();
    }

    public static List<EntiteAdministratif> getAllEntiteAdministratifs() {
        return getCurrentFaculte().getEntiteAdministratifs();
    }

    public static List<EntiteAdministratif> getCurrentEntiteAdministratifs() {
        List<EntiteAdministratif> entiteAdministratifs;
        User user = getConnectedUser();
        switch (user.getType()) {
            case 1:
            case 2:
            case 3:
            case 4:
                return getAllEntiteAdministratifs();
            case 5:
                entiteAdministratifs = new ArrayList<>();
                entiteAdministratifs.add(user.getEntiteAdministratif());
                return entiteAdministratifs;
            default:
                return null;
        }
    }

    public static EntiteAdministratif getCurrentEntiteAdministratif() {
        User user = getConnectedUser();
        if (user.getType() < 5) {
            return null; /// changee par administration
        } else {
            return getCurrentEntiteAdministratifs().get(0);
        }
    }

    public static List<Compte> getComptes() {
        return (List<Compte>) getAttribute("comptes");
    }

    public static List<CompteItem> getCurrentCompteItems() {
        return (List<CompteItem>) getAttribute("compteItems");
    }

    public static List<BudgetEntiteAdministratifItem> getCurrentBudgetEntiteAdministratifItem() {
        return (List<BudgetEntiteAdministratifItem>) getAttribute("budgetEntiteAdministratifItems");
    }

    public static List<BudgetFaculte> getCurrentBudgetFacultes() {
        return getCurrentFaculte().getBudgetFacultes();
    }

    public static List<String> getAnnees() {
        List<String> annees = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            String anneAffectation = 2015 + i + "";
            annees.add(anneAffectation);
        }
        return annees;
    }

    public static String getCurrentAnnee() {
        Date now = new Date();
        return Integer.toString(now.getYear() + 1900);
    }

    public static List<String> getTypeBudgets() {
        List<String> types = new ArrayList<>();
        types.add("fonctionnement et investissement");
        types.add("fonctionnement");
        types.add("investissement");
        return types;
    }

    public static SessionUtil getInstance() {
        return instance;
    }

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static void redirect(String pagePath) throws IOException {
        if (!pagePath.endsWith(".xhtml")) {
            pagePath += ".xhtml";
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect(pagePath);

    }

    private static boolean isContextOk(FacesContext fc) {
        boolean res = (fc != null
                && fc.getExternalContext() != null
                && fc.getExternalContext().getSession(false) != null);
        return res;
    }

    private static HttpSession getSession(FacesContext fc) {
        return (HttpSession) fc.getExternalContext().getSession(false);
    }

    public static Object getAttribute(String cle) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Object res = null;
        if (isContextOk(fc)) {
            res = getSession(fc).getAttribute(cle);
        }
        return res;
    }

    public static void setAttribute(String cle, Object valeur) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc != null && fc.getExternalContext() != null) {
            getSession(fc).setAttribute(cle, valeur);
        }
    }
}
