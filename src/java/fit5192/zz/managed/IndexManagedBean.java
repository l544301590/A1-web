/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.zz.managed;

import fit5192.zz.repository.TransactionRepository;
import fit5192.zz.repository.UserRepository;
import fit5192.zz.repository.entities.Product;
import fit5192.zz.repository.entities.Transaction_;
import fit5192.zz.repository.entities.User_;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author dylan
 */
@ManagedBean
@Named(value = "indexManagedBean")
@ApplicationScoped
public class IndexManagedBean implements Serializable {

    @EJB
    private UserRepository userRepository;
    @EJB
    private TransactionRepository transactionRepository;

    private String email;
    private String password;
    private String password2;
    private String loginMessage = "Welcome! Please Login!";
    private String registerMessage = "Welcome! Please Register!";
    private User_ user;

    public IndexManagedBean() {
    }
    
    
    public String login() {
        String res = userRepository.login(new User_(email, password));
        try {
            int level = Integer.parseInt(res);
            user = userRepository.searchUserByEmail(email).get(0);  // store globally
            return "products";  // login successfully, jump to products.xhtml
        } catch (NumberFormatException e) {
            this.loginMessage = res;  // modify prompt message
        }
        return "index";  // jump to itself(index.xhtml)
    }

    public String register() {
        if (password.equals(password2)) {
            this.registerMessage = "Two passwords don't match";
            return "register";
        }
        String res = userRepository.register(new User_(email, password));
        try {
            int level = Integer.parseInt(res);
            user = userRepository.searchUserByEmail(email).get(0);  // store globally
            return "userinfo";
        } catch (Exception e) {
            this.registerMessage = res;
        }
        return "register";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    public String getRegisterMessage() {
        return registerMessage;
    }

    public void setRegisterMessage(String registerMessage) {
        this.registerMessage = registerMessage;
    }

    public User_ getUser() {
        return user;
    }

}
