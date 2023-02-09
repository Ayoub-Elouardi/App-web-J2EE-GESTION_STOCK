package models;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.mapping.List;

import entities.Articles_stock_Operations;


@Entity (name = "articles_stock")
@Table(name = "articles_stock", catalog = "g_stock")
@ManagedBean(name="ArticlesStock")
public class ArticlesStock<articles_stock_list> implements java.io.Serializable {

	private int codeArt;
	private int qteArt;
	private String nomArt;
	private String descArt;
	private int prixArt;
    public static Articles_stock_Operations dbObj;
	public ArticlesStock() {
	}

	public ArticlesStock(int codeArt, int qteArt, String nomArt, String descArt, int prixArt) {
		this.codeArt = codeArt;
		this.qteArt = qteArt;
		this.nomArt = nomArt;
		this.descArt = descArt;
		this.prixArt = prixArt;
	}

	@Id

	@Column(name = "codeArt", unique = true, nullable = false)
	public int getCodeArt() {
		return this.codeArt;
	}

	public void setCodeArt(int codeArt) {
		this.codeArt = codeArt;
	}

	@Column(name = "qteArt", nullable = false)
	public int getQteArt() {
		return this.qteArt;
	}

	public void setQteArt(int qteArt) {
		this.qteArt = qteArt;
	}

	@Column(name = "nomArt", nullable = false, length = 20)
	public String getNomArt() {
		return this.nomArt;
	}

	public void setNomArt(String nomArt) {
		this.nomArt = nomArt;
	}

	@Column(name = "descArt", nullable = false, length = 200)
	public String getDescArt() {
		return this.descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}

	@Column(name = "prixArt", nullable = false)
	public int getPrixArt() {
		return this.prixArt;
	}

	public void setPrixArt(int prixArt) {
		this.prixArt = prixArt;
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {  //pour afficher les messages d'erreurs , Avertissement , succés
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	
	public void insert() {    //controle du formulaire et passer les valeurs au fonction insert_stock en aArticles_Stock_Operations
		if(getCodeArt()<=0) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Code Art", "Article  code  must be positive");
		}else {
			if(getQteArt()<=0) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Quantité Article", "Quantité Article  must be positive");
			}else {
				if(getNomArt()=="") {
					addMessage(FacesMessage.SEVERITY_ERROR, "Nom Article", "Champ vide");
				}
				else {
					if(getPrixArt()<=0) {
						addMessage(FacesMessage.SEVERITY_ERROR, "Prix Article", "Prix Article  must be positive");
					}else {
						if(getDescArt()=="") {
							addMessage(FacesMessage.SEVERITY_ERROR, "Description Article", "Champ vide");
						}else {
							 dbObj = new Articles_stock_Operations();
							 dbObj.insert_stock(this);
						}
					}
				}
			}
	   
		}
	
	}

	 public void deleteid(int codeArt) { //passer les valeurs au fonction delete_stock en Articles_Stock_Operations
	        dbObj = new Articles_stock_Operations();
	        dbObj.delete_Stock(codeArt, this);
	    }
	 
	    public void update() { //passer les valeurs au fonction update_stock en Articles_Stock_Operations
	       dbObj = new Articles_stock_Operations();    
	       dbObj.update_Stock(codeArt, this);
	    }			
	    public String Statut(int qte) { //passer les valeurs au fonction
		      String stat="";
	    	if(qte>1 && qte<10) {
		    	   stat= "Stock faible";
		       }
		       else {
		    	   if(qte<=0) {
		    		   stat= "En rupture";
		    	   }
		    	   else {
		    		   stat="En Stock";
		    	   }
		       }
			return stat;
		    }	
	    
   public String severity(int qte) { //passer les valeurs au fonction
    String stat="";
	if(qte>1 && qte<10) {
  	   stat= "warning";
     }
     else {
  	   if(qte<=0) {
  		   stat= "danger";
  	   }
  	   else {
  		   stat="success";
  	   }
     }
	return stat;
  }	
}
	       
	
	 


