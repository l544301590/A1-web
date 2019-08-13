/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.zz.managed;

import fit5192.zz.repository.UserRepository;
import fit5192.zz.repository.entities.User_;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Zheng Ru
 */
@ManagedBean
@Named(value = "userInfoManagedBean")
@SessionScoped
public class UserInfoManagedBean {
    @EJB
    private UserRepository userRepository;
    
    private String updateUserInfoMessage = "The User Information";
    
    private int id;
    private String email;
    private String nickname;
    private String password ;
    private int level;
    private String lastName;
    private String firstName;
    private String address;
    private String phone;
    
    private User_ user;

    public UserInfoManagedBean() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        IndexManagedBean app = (IndexManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "indexManagedBean");
        user = app.getUser();
        System.err.println("11111111111111111111111");
        this.id = user.getId();
        System.err.println(this.id);
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.level = user.getLevel();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.address = user.getAddress();
        this.phone = user.getPhone();
    }

    //1 show the info of current user
    //2 get the userInfo changed
    //3 user the userRepository.upadate the user
    //所以不应该有两个user变量 刷新什么

    public String updateUser(){
        User_ user = new User_(nickname,lastName,firstName,address,phone);//这里是不是应该用this.user 方便页面的自动更行
        try {
            userRepository.updateUser(user);
        } catch (Exception ex) {
            this.updateUserInfoMessage = ex.getMessage();
        }
        this.updateUserInfoMessage = "Update successfully";
        return "userinfo";
    }
    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLevel() {
        return level;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User_ getUser() {
        return user;
    }

    public void setUser(User_ user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUpdateUserInfoMessage() {
        return updateUserInfoMessage;
    }

    public void setUpdateUserInfoMessage(String updateUserInfoMessage) {
        this.updateUserInfoMessage = updateUserInfoMessage;
    }
    
    
    
}
