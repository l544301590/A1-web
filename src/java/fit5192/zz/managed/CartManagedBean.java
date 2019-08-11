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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Named(value = "cartManagedBean")
@ApplicationScoped
public class CartManagedBean implements Serializable {

    @EJB
    private TransactionRepository transactionRepository;

    private Map<Product, Integer> cart;  // store product with corresponding quantity
    private List<Product> cartList;  // store product only
    private User_ user;

    public CartManagedBean() {
        this.cart = new HashMap<>();
        this.cartList = new ArrayList<>();
        
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        IndexManagedBean app = (IndexManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "indexManagedBean");
        this.user = app.getUser();
    }

    public String order() {
        Date date = new Date();
        System.out.println(date.toString());
        
        Transaction_ transaction = new Transaction_(date, cartList, user);

        try {
            transactionRepository.addTransaction(transaction);
            return "transactions";
        } catch (Exception e) {
            e.printStackTrace();
            return "cart";
        }
    }
    
    public void addToCart(Product product) {
        if (this.cart.containsKey(product)) {
            this.cart.put(product, this.cart.get(product) + 1);
        } else {
            this.cart.put(product, 1);
            this.cartList.add(product);
        }
    }
    
    public void addNum(Product product) {
        this.cart.put(product, this.cart.get(product) + 1);
    }
    
    public void subNum(Product product) {
        int currentNum = this.cart.get(product);
        this.cart.put(product, currentNum - 1);
        if (currentNum == 0) {
            this.cart.remove(product);
            this.cartList.remove(product);
        }
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

    public List<Product> getCartList() {
        return cartList;
    }

    public void setCartList(List<Product> cartList) {
        this.cartList = cartList;
    }

    
}
