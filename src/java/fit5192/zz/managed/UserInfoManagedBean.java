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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zheng Ru
 */
@ManagedBean
@Named(value = "userInfoManagedBean")
@RequestScoped
public class UserInfoManagedBean {

    @EJB
    private UserRepository userRepository;

    private String updateUserInfoMessage = "The User Information";

    private String id;
    private String email;
    private String nickname;
    private String password;
    private String level;
    private String lastName;
    private String firstName;
    private String address;
    private String phone;

    private User_ user;

    public UserInfoManagedBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String idString = request.getParameter("id");
        System.out.println("Id为空？" + idString);
        this.id = idString;
    }

    @PostConstruct
    public void initUser() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        IndexManagedBean app = (IndexManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "indexManagedBean");
        this.user = app.getUser();
        try {
            this.user = userRepository.searchUserById(Integer.parseInt(this.id));
//                System.out.println("找出来的user" + user);
        } catch (Exception ex) {
            Logger.getLogger(UserInfoManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.id = String.valueOf(user.getId());

        this.email = user.getEmail();

        this.nickname = user.getNickname();

        this.password = user.getPassword();

        this.level = String.valueOf(user.getLevel());

        this.lastName = user.getLastName();

        this.firstName = user.getFirstName();

        this.address = user.getAddress();

        this.phone = user.getPhone();
    }

//1 show the info of current user
//2 get the userInfo changed
//3 user the userRepository.upadate the user
//所以不应该有两个user变量 刷新什么
    public String updateUser() {
        User_ user = createNewUser();

        if (user.getLevel() < 3) {
            if (isEmpty(user.getLastName())) {
                this.updateUserInfoMessage = "Need to fill in Lastname to upgrade";
            }
            if (isEmpty(user.getFirstName())) {
                this.updateUserInfoMessage = "Need to fill in Firstname to upgrade";
            }
            if (isEmpty(user.getAddress())) {
                this.updateUserInfoMessage = "Need to fill in Address to upgrade";
            }
            if (isEmpty(user.getPhone())) {
                this.updateUserInfoMessage = "Need to fill in Phone to upgrade";
            }
            return "userinfo";
        }
        try {
            userRepository.updateUser(user);

        } catch (Exception ex) {
            Logger.getLogger(UserInfoManagedBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        this.updateUserInfoMessage = "Update successfully";
        return "userinfo";
    }

    public User_ createNewUser() {
        User_ user = new User_();
        if (this.id.length() > 0) {
            user.setId(Integer.parseInt(id));
        }
        if (this.email.length() > 0) {
            user.setEmail(email);
        }
        if (this.nickname.length() > 0) {
            user.setNickname(nickname);
        }
        if (this.password.length() > 0) {
            user.setPassword(password);
        }
        if (this.level.length() > 0) {
            user.setLevel(Integer.parseInt(level));
        }
        if (this.lastName.length() > 0) {
            user.setLastName(lastName);
        }
        if (this.firstName.length() > 0) {
            user.setFirstName(firstName);
        }
        if (this.address.length() > 0) {
            user.setAddress(address);
        }
        if (this.phone.length() > 0) {
            user.setPhone(phone);
        }
        return user;
    }

    public static boolean isEmpty(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str == " ") {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
