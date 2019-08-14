/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.zz.managed;

import fit5192.zz.repository.UserRepository;
import fit5192.zz.repository.entities.User_;
import java.util.List;
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
 * @author Zheng R
 */
/*
要实现功能：addUsersearchUsers deleteUser detail search（转到具体的info界面）进行修改
1 需要form inputText 得到搜索要求
2 需要一个dataTable遍历搜索的结果
3 通过三个按钮完成 deleteUser detail 要求的部分
 */
@ManagedBean
@Named(value = "usersManagedBean")
@SessionScoped
public class UsersManagedBean {

    @EJB
    private UserRepository userRepository;

    private List<User_> users;

    private User_ user;
    
    private String inputId;

    private String inputEmail;

    private String inputNickname;

    private String inputPassword;

    private String inputLevel;

    private String inputLastName;

    private String inputFirstName;

    private String inputAddress;

    private String inputPhone;

    public UsersManagedBean() {
        //why need the user
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        IndexManagedBean app = (IndexManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "indexManagedBean");
        user = app.getUser();
    }

    @PostConstruct
    public void initTable() {
        try {
            users = userRepository.getAllUsers();
            System.err.print("是不是没取出数据"+users.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser() {
        User_ user = createNewUser();
        try {
            userRepository.addUser(user);
        } catch (Exception ex) {
            Logger.getLogger(UsersManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        users.add(user);
        try {
            users = userRepository.getAllUsers();
        } catch (Exception ex) {
            Logger.getLogger(UsersManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void searchUser(){
        User_ user = createNewUser();
        List<User_> serchedUsers = userRepository.searchUserByAnyAttribute(user);
        this.users=serchedUsers;
        //return serchedUsers;
    }
    
    public void deleteUser(User_ user){
        try {
            userRepository.removeUserById(user.getId());
            users.remove(user);
            users = userRepository.getAllUsers();
        } catch (Exception ex) {
            Logger.getLogger(UsersManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void viewAllUsers() {
        try {
            this.users = userRepository.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void goToDetail(User_ user){}
    
     public User_ createNewUser(){
        User_ user = new User_();
//        if(this.inputId.length()>0){
//            user.setId(Integer.parseInt(inputId));
//        }
        if(this.inputEmail.length()>0){
            user.setEmail(inputEmail);
        }
        if(this.inputNickname.length()>0){
            user.setNickname(inputNickname);
        }
        if(this.inputPassword.length()>0){
            user.setPassword(inputPassword);
        }
        if(this.inputLevel.length()>0){
            user.setLevel(Integer.parseInt(inputLevel));
        }
        if(this.inputLastName.length()>0){
            user.setLastName(inputLastName);
        }
        if(this.inputFirstName.length()>0){
            user.setFirstName(inputFirstName);
        }
        if(this.inputAddress.length()>0){
            user.setAddress(inputAddress);
        }
        if(this.inputPhone.length()>0){
            user.setPhone(inputPhone);
        }
        return user;
    }

    public List<User_> getUsers() {
        return users;
    }

    public void setUsers(List<User_> users) {
        this.users = users;
    }

    public User_ getUser() {
        return user;
    }

    public void setUser(User_ user) {
        this.user = user;
    }

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public String getInputEmail() {
        return inputEmail;
    }

    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }

    public String getInputNickname() {
        return inputNickname;
    }

    public void setInputNickname(String inputNickname) {
        this.inputNickname = inputNickname;
    }

    public String getInputPassword() {
        return inputPassword;
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword = inputPassword;
    }

    public String getInputLevel() {
        return inputLevel;
    }

    public void setInputLevel(String inputLevel) {
        this.inputLevel = inputLevel;
    }

    public String getInputLastName() {
        return inputLastName;
    }

    public void setInputLastName(String inputLastName) {
        this.inputLastName = inputLastName;
    }

    public String getInputFirstName() {
        return inputFirstName;
    }

    public void setInputFirstName(String inputFirstName) {
        this.inputFirstName = inputFirstName;
    }

    public String getInputAddress() {
        return inputAddress;
    }

    public void setInputAddress(String inputAddress) {
        this.inputAddress = inputAddress;
    }

    public String getInputPhone() {
        return inputPhone;
    }

    public void setInputPhone(String inputPhone) {
        this.inputPhone = inputPhone;
    }
     
    
}
