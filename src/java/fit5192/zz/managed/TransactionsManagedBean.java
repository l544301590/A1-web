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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author dylan
 */
@ManagedBean
@Named(value = "transactionsManagedBean")
@ApplicationScoped
public class TransactionsManagedBean implements Serializable {

    private List<Transaction_> transactions;
    private User_ user;
    
    private String inputId;
    private String inputDate;
    
    @EJB
    private TransactionRepository transactionRepository;
    
    public TransactionsManagedBean() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        IndexManagedBean app = (IndexManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "indexManagedBean");
        user = app.getUser();
    }
    
    @PostConstruct
    public void initTransactions() {
        transactions = transactionRepository.SearchTransactionsByUserId(user.getId());
//        try {
//            transactions = transactionRepository.getAllTransactions();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void searchTransaction() {
        Transaction_ transaction = new Transaction_();
        if (inputId == "") {
            transaction.setId(Integer.parseInt(inputId));
        }
        if (inputDate == "") {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                transaction.setDate(sdf.parse(inputDate));
            } catch (Exception e) {
                return;
            }
        }
        transactions = transactionRepository.SerachTransactionByAnyAttribute(transaction);
    }
    
    public void viewAllTransactions() {
        this.initTransactions();
    }
    
    public List<Transaction_> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction_> transactions) {
        this.transactions = transactions;
    }

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }
    
}
