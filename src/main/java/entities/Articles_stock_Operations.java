package entities;

import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;




import models.ArticlesStock;
import models.HibernateUtil;

@ManagedBean(name="ArticlesStockDao")
public class Articles_stock_Operations {

	    private static Transaction transObj;
		private static Session sessionObj = HibernateUtil.getSessionFactory().openSession();
		  
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {  //pour afficher les messages d'erreurs , Avertissement , succés
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	public void insert_stock(ArticlesStock articlesStock) {  //insérer dans la table  articlesStock
		   try {
		            transObj = sessionObj.beginTransaction();
		            sessionObj.save(articlesStock);
		            addMessage(FacesMessage.SEVERITY_INFO, "Insertion d'article", "Article  "+ articlesStock.getCodeArt()+"  Inserted");
		   } catch (Exception exceptionObj) {
		            exceptionObj.printStackTrace();
		            addMessage(FacesMessage.SEVERITY_ERROR, "Code Art", "Article  existant , impossible de l'inserer à nouveau");
		   } finally {
		            transObj.commit();
		        }
	 }


	
	public void update_Stock(int codeArt,ArticlesStock articlesStock) { //Mise à jour d'article  numero *codeArt*
		  try {
	            transObj = sessionObj.beginTransaction();
	            ArticlesStock code = (ArticlesStock)sessionObj.load(ArticlesStock.class, new Integer(codeArt));
	            code.setQteArt(articlesStock.getQteArt());
	           	code.setNomArt(articlesStock.getNomArt());
	            code.setDescArt(articlesStock.getDescArt());
	            code.setPrixArt(articlesStock.getPrixArt());
	            addMessage(FacesMessage.SEVERITY_INFO, "Mise à jour d'article", "Article  "+ codeArt+"  Updated");
	        } catch(Exception exceptionObj){
	            exceptionObj.printStackTrace();
	            addMessage(FacesMessage.SEVERITY_INFO, "Mise à jour d'article", "Article  mis à jour avec succés");
	        } finally {
	            transObj.commit();
	        }
	    }
		
	

	public void delete_Stock(int codeArt,ArticlesStock articlesStock) { //suppression d'article numero *codeArt*
		 try {
	            transObj = sessionObj.beginTransaction();
	            @SuppressWarnings("deprecation")
				ArticlesStock code = (ArticlesStock)sessionObj.load(ArticlesStock.class, new Integer(codeArt));
	            sessionObj.delete(code);
	            addMessage(FacesMessage.SEVERITY_WARN, "Suppression d'article", "Article  "+ codeArt+"  deleted");
	        } catch (Exception exceptionObj) {
	            exceptionObj.printStackTrace();
	            addMessage(FacesMessage.SEVERITY_ERROR, "Suppression d'article", "Article inexistant");
	        } finally {
	            transObj.commit();
	        }
	}
		
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public List < ArticlesStock > getstock() { // passer les valeurs du table articlesStock à notre view Articles_Stock.xhtml

	        Transaction transaction = null;
	        List < ArticlesStock > stocklist = null;
	        try {
	            // start a transaction
	            transaction = sessionObj.beginTransaction();
	            // get an user object

	            stocklist = sessionObj.createQuery("from articles_stock").list();
	            System.out.print(stocklist);

	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	              if (transaction != null) {
	                    transaction.rollback();
	                 }
	           e.printStackTrace();
	        }
	        return stocklist;
	    }
	
	

	public Object total_article_stock() {  //Calculer le nombre d'articles en stock
	    transObj = sessionObj.beginTransaction();
        Query query = sessionObj.createQuery("select count(codeArt) from articles_stock");
        query.uniqueResult();
        sessionObj.getTransaction().commit();
	    return query.uniqueResult();	
	}    
	
	public Object total_qte_stock() { //Calculer la quantité d'articles en stock
	    transObj = sessionObj.beginTransaction();
        Query query = sessionObj.createQuery("select sum(qteArt) from articles_stock");
        query.uniqueResult();
        sessionObj.getTransaction().commit();
	    return query.uniqueResult();	
	}  
	
	public Object total_prix_stock() { //Calculer la valeur d'articles en stock
	    transObj = sessionObj.beginTransaction();
        Query query = sessionObj.createQuery("select sum(prixArt) from articles_stock");
        query.uniqueResult();
        sessionObj.getTransaction().commit();
	    return query.uniqueResult();	
	}
}  
		   







	
	
	
		
	



