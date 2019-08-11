/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.zz.managed;

import fit5192.zz.repository.TransactionRepository;
import fit5192.zz.repository.entities.Product;
import fit5192.zz.repository.entities.Transaction_;
import fit5192.zz.repository.entities.User_;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dylan
 */
@ManagedBean
@Named(value = "transactionInfoManagedBean")
@SessionScoped
public class TransactionInfoManagedBean {

    @EJB
    private TransactionRepository transactionRepository;
    
    private int id;
    private Date date;
    private List<Product> products;
    private User_ user;
    
    public TransactionInfoManagedBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        String idString = request.getParameter("id");
        if (!idString.isEmpty()){
            this.id = Integer.parseInt(idString); 
        }
        
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        IndexManagedBean indexManagedBean = (IndexManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "indexManagedBean");
        this.user = indexManagedBean.getUser();
    }
    
    @PostConstruct
    public void initProductList() {
        try {
            Transaction_ transaction = transactionRepository.searchTransactionById(id);
            this.products = transaction.getProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
}
