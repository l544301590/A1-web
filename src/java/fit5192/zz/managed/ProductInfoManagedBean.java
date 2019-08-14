/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.zz.managed;

import fit5192.zz.repository.ProductRepository;
import fit5192.zz.repository.RatingRepository;
import fit5192.zz.repository.entities.Product;
import fit5192.zz.repository.entities.Rating;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dylan
 */
@ManagedBean
@Named(value = "productInfoManagedBean")
@SessionScoped
public class ProductInfoManagedBean {

    @EJB
    private ProductRepository productRepository;
    @EJB
    private RatingRepository ratingRepository;

    private int id;
    private String name;  // full name, including category name
//    private String imgPath;
    private int category;
    private String area;
    private float price;  // unit price
    private int inventory;
    private String description;
    private List<Rating> ratings;

    private String inputRating;
    private String inputComment;

    private Product currentProduct;

    public ProductInfoManagedBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String idString = request.getParameter("id");
        if (!idString.isEmpty()) {
            this.id = Integer.parseInt(idString);
        }
    }

    @PostConstruct
    public void initProductInfoAndRatings() {
        initProductInfo();;
        initRatings();
    }

    public void initProductInfo() {
        try {
            Product product = productRepository.searchProductById(id);
            this.name = product.getName();
            this.category = product.getCategory();
            this.area = product.getArea();
            this.price = product.getPrice();
            this.inventory = product.getInventory();
            this.description = product.getDescription();
            this.ratings = product.getRatings();
            this.currentProduct = product;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void initRatings() {
        try {
            ratings = ratingRepository.searchRatingsByProductId(currentProduct.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        CartManagedBean cartManagedBean = (CartManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "cartManagedBean");
        cartManagedBean.addToCart(currentProduct);
    }

    public String addComment() {
        if (inputRating.length() > 0) {
            ELContext context = FacesContext.getCurrentInstance().getELContext();
            IndexManagedBean indexManagedBean = (IndexManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "indexManagedBean");
            Rating rating = new Rating(Integer.parseInt(inputRating), inputComment, currentProduct, indexManagedBean.getUser());
            try {
                ratingRepository.addRating(rating);
                initRatings();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "productinfo";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getInputRating() {
        return inputRating;
    }

    public void setInputRating(String inputRating) {
        this.inputRating = inputRating;
    }

    public String getInputComment() {
        return inputComment;
    }

    public void setInputComment(String inputComment) {
        this.inputComment = inputComment;
    }

}
