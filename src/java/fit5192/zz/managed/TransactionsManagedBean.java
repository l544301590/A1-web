/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.zz.managed;

import fit5192.zz.repository.TransactionRepository;
import fit5192.zz.repository.entities.Transaction_;
import fit5192.zz.repository.entities.User_;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author dylan
 */
@ManagedBean
@Named(value = "transactionsManagedBean")
@SessionScoped
public class TransactionsManagedBean implements Serializable {

    private List<Transaction_> transactions;
    private User_ user;
    
    @EJB
    private TransactionRepository transactionRepository;
    
    public TransactionsManagedBean() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        IndexManagedBean app = (IndexManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "indexManagedBean");
        user = app.getUser();
        
        // TODO getTransactionByUserId
    }

    public List<Transaction_> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction_> transactions) {
        this.transactions = transactions;
    }
    
}
