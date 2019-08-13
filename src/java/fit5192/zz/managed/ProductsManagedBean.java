/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.zz.managed;

import fit5192.zz.repository.ProductRepository;
import fit5192.zz.repository.entities.Product;
import fit5192.zz.repository.entities.User_;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author dylan
 */
@ManagedBean
@Named(value = "productsManagedBean")
@ApplicationScoped
public class ProductsManagedBean {

    @EJB
    private ProductRepository productRepository;

    private List<Product> products;
    private User_ user;

    private int inputProductId;
    private String inputProductName;
    private int inputProductCategory;
    private String inputProductArea;
    private float inputProductPrice;
    private int inputProductInventory;
    private String inputProductDescription;

    public ProductsManagedBean() {
//        this.products = productRepository.;
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        IndexManagedBean app = (IndexManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "indexManagedBean");
        user = app.getUser();
    }

    @PostConstruct
    public void initTable() {
        try {
            products = productRepository.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct() {
        try {
            Product product = new Product(inputProductName, inputProductCategory, inputProductArea, inputProductPrice, inputProductInventory);
            productRepository.addProduct(product);
            products.add(product);
            products = productRepository.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Product product) {
        try {
            productRepository.removeProductById(product.getId());
            products.remove(product);
            products = productRepository.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void updateProduct() {

    }

    public void searchProduct() {
        Product product = new Product(inputProductName, inputProductCategory, inputProductArea, inputProductPrice, inputProductInventory);
        this.products = productRepository.searchProductByAnyAttribute(product);
    }
    
    public void viewAllProducts() {
        try {
            this.products = productRepository.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart(Product product) {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        CartManagedBean cartManagedBean = (CartManagedBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(context, null, "cartManagedBean");
        cartManagedBean.addToCart(product);
    }

    public void goToDetail(Product product) {

    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getInputProductId() {
        return inputProductId;
    }

    public void setInputProductId(int inputProductId) {
        this.inputProductId = inputProductId;
    }

    public String getInputProductName() {
        return inputProductName;
    }

    public void setInputProductName(String inputProductName) {
        this.inputProductName = inputProductName;
    }

    public int getInputProductCategory() {
        return inputProductCategory;
    }

    public void setInputProductCategory(int inputProductCategory) {
        this.inputProductCategory = inputProductCategory;
    }

    public String getInputProductArea() {
        return inputProductArea;
    }

    public void setInputProductArea(String inputProductArea) {
        this.inputProductArea = inputProductArea;
    }

    public float getInputProductPrice() {
        return inputProductPrice;
    }

    public void setInputProductPrice(float inputProductPrice) {
        this.inputProductPrice = inputProductPrice;
    }

    public int getInputProductInventory() {
        return inputProductInventory;
    }

    public void setInputProductInventory(int inputProductInventory) {
        this.inputProductInventory = inputProductInventory;
    }

    public String getInputProductDescription() {
        return inputProductDescription;
    }

    public void setInputProductDescription(String inputProductDescription) {
        this.inputProductDescription = inputProductDescription;
    }
}
