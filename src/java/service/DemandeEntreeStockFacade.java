/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DemandeEntreeStock;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author AIMAD
 */
@Stateless
public class DemandeEntreeStockFacade extends AbstractFacade<DemandeEntreeStock> {

    @PersistenceContext(unitName = "projet_budget_facultePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DemandeEntreeStockFacade() {
        super(DemandeEntreeStock.class);
    }

    public int getSequence() {
        //int res = (int) em.createQuery("SELECT SEQ_COUNT FROM SEQUENCE SEQ WHERE SEQ.SEQ_NAME='SEQ_RECEPTION'").getSingleResult();
        int res = (int) em.createNativeQuery("SELECT SEQ_COUNT FROM SEQUENCE SEQ WHERE SEQ.SEQ_NAME='SEQ_RECEPTION'").getSingleResult();

        return ++res;
    }

    public void incrementSequence() {
        System.out.println("Updating sequence value");
        em.createNativeQuery("UPDATE sequence SET SEQ_COUNT = seq_count+1 WHERE seq_name='SEQ_RECEPTION';");
    }

    public List<DemandeEntreeStock> findNotLivredDeamndebyDate(Date date) {
        if (date != null) {
            java.sql.Date dateSql = new java.sql.Date(date.getTime());

            List<DemandeEntreeStock> res = em.createQuery("SELECT des  FROM DemandeEntreeStock des WHERE des.dateCreation= :dateTrim and des.livred= :valLivred ORDER BY des.dateCreation DESC").setParameter("dateTrim", dateSql).setParameter("valLivred", Boolean.FALSE).getResultList();

            return res;
        }
        return em.createQuery("SELECT des  FROM DemandeEntreeStock des WHERE  des.livred= :valLivred ORDER BY des.dateCreation DESC").setParameter("valLivred", Boolean.FALSE).getResultList();
    }

}
