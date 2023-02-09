package entities;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

import models.ArticlesApprovisionnement;
import models.HibernateUtil;

@ManagedBean(name="ArticlesApprDao")
public class Articles_Approvisionnement_Operations {

	 private static Transaction transObj;
	 private PDFOptions pdfOpt;
		private static Session sessionObj = HibernateUtil.getSessionFactory().openSession();
		  
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) { //pour afficher les messages d'erreurs , Avertissement , succés
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	public void insert_Appr(ArticlesApprovisionnement articlesAppr) { //insérer dans la table  ArticlesApprovisionnement
		 try {
		      transObj = sessionObj.beginTransaction();
		      sessionObj.save(articlesAppr);
		      addMessage(FacesMessage.SEVERITY_INFO, "Insertion d'article", "Article  "+ articlesAppr.getCodeArt()+"  Inserted");
		  } catch (Exception exceptionObj) {
		      exceptionObj.printStackTrace();
		      addMessage(FacesMessage.SEVERITY_ERROR, "Code Art", "Article  existant , impossible de l'inserer à nouveau");
		  } finally {
		      transObj.commit();
		  }
    }

    public void update_Appr(int codeArt,ArticlesApprovisionnement articlesAppr) { //mise à jour d'article numéro *codeArt*
		  try {
			  Date today = new Date();
	            transObj = sessionObj.beginTransaction();
	            ArticlesApprovisionnement code = (ArticlesApprovisionnement)sessionObj.load(ArticlesApprovisionnement.class, new Integer(codeArt));
	            code.setQteCommande(articlesAppr.getQteCommande());
	            if(articlesAppr.getDatePrevueLivraison()!=null) {
	            	code.setDatePrevueLivraison(articlesAppr.getDatePrevueLivraison());
	            }
	            addMessage(FacesMessage.SEVERITY_INFO, "Mise à jour d'article", "Article  "+ codeArt+"  Updated");
	  	   } catch(Exception exceptionObj){
	           exceptionObj.printStackTrace();
	           addMessage(FacesMessage.SEVERITY_INFO, "Mise à jour d'article", "Article  mis à jour avec succés");
	       } finally {
	           transObj.commit();
	       }
	 }
		
	 public void delete_Appr(int codeArt,ArticlesApprovisionnement articlesAppr) { //Suppression d'article numéro *codeArt*
		 try {
	            transObj = sessionObj.beginTransaction();
	            @SuppressWarnings("deprecation")
	            ArticlesApprovisionnement code = (ArticlesApprovisionnement)sessionObj.load(ArticlesApprovisionnement.class, new Integer(codeArt));
	            sessionObj.delete(code);
	            addMessage(FacesMessage.SEVERITY_WARN, "Suppression d'article", "Article  "+ codeArt+"  deleted");
		 } catch (Exception exceptionObj) {
	            exceptionObj.printStackTrace();
	            addMessage(FacesMessage.SEVERITY_ERROR, "Suppression d'article", "Article inexistant");
	     } finally {
	            transObj.commit();
	     }
	}
		
	
	@SuppressWarnings("unchecked")
	public List < ArticlesApprovisionnement > getAppr() { // passer les valeurs du table ArticlesApprovisionnement à notre view Articles_Approvisionnement.xhtml
            Transaction transaction = null;
	        List < ArticlesApprovisionnement > ApprList = null;
	        try {
	            // start a transaction
	            transaction = sessionObj.beginTransaction();
	            // get an user object
                ApprList = sessionObj.createQuery("from articles_approvisionnement").list();
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	             e.printStackTrace();
	        }
	        return ApprList;
	    }
	
	public Object total_article_Appr() { //Calculer le nombre d'articles en approvisionnement
	    transObj = sessionObj.beginTransaction();
        Query query = sessionObj.createQuery("select count(codeArt) from articles_approvisionnement");
        query.uniqueResult();
        sessionObj.getTransaction().commit();
	    return query.uniqueResult();	
	}    
	
	public Object total_qte_Appr() { //Calculer la quantité d'articles en approvisionnement
	    transObj = sessionObj.beginTransaction();
        Query query = sessionObj.createQuery("select sum(qteCommande) from articles_approvisionnement");
        query.uniqueResult();
        sessionObj.getTransaction().commit();
	    return query.uniqueResult();	
	}  
}
