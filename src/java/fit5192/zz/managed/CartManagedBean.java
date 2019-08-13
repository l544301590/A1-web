/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.zz.managed;

import fit5192.zz.repository.ProductRepository;
import fit5192.zz.repository.TransactionRepository;
import fit5192.zz.repository.UserRepository;
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
    @EJB
    private ProductRepository productRepository;
    @EJB
    private UserRepository userRepository;

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
//        Date now =new Date();
//        Product pro=new Product("strawberry",2,"berry",10,21);
//        List<Product> proList=new ArrayList<Product>();
//        proList.add(pro);
//        User_ user1=new User_("aaa@qq.com","12345678f.123");
//        //Rating rating=new Rating(5,pro,user);
//        Transaction_ tran = new Transaction_(now,proList,user1);
//        try {
////            userRepository.addUser(user1);
////            productRepository.addProduct(pro);
//            transactionRepository.addTransaction(tran);
//            //ratingRepository.addRating(rating);
//           // productRepository.searchTest();
//            
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return "";
        Date date = new Date();
//        List<Product> ps = new ArrayList<>();
//        for (Product product : cartList) {
//            try{
//                Product p = productRepository.searchProductById(product.getId());
//                ps.add(p);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
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

    public User_ getUser() {
        return user;
    }

    
}
