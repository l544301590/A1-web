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
@SessionScoped
public class ProductsManagedBean {

    @EJB
    private ProductRepository productRepository;

    private List<Product> products;
    private User_ user;

    private String inputProductId;
    private String inputProductName;
    private String inputProductCategory;
    private String inputProductArea;
    private String inputProductPrice;
    private String inputProductInventory;
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
            Product product = new Product(
                    inputProductName, 
                    Integer.parseInt(inputProductCategory), 
                    inputProductArea, 
                    Float.parseFloat(inputProductPrice), 
                    Integer.parseInt(inputProductInventory)
            );
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
        Product product = new Product();
        if (this.inputProductName.length() > 0) {
            product.setName(inputProductName);
        }
        if (this.inputProductCategory.length() > 0) {
            product.setCategory(Integer.parseInt(inputProductCategory));
        }
        if (this.inputProductArea.length() > 0) {
            product.setArea(inputProductArea);
        }
        if (this.inputProductPrice.length() > 0) {
            product.setPrice(Float.parseFloat(inputProductPrice));
        }
        if (this.inputProductInventory.length() > 0) {
            product.setPrice(Integer.parseInt(inputProductInventory));
        }
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

    public String getInputProductName() {
        return inputProductName;
    }

    public void setInputProductName(String inputProductName) {
        this.inputProductName = inputProductName;
    }

    public String getInputProductArea() {
        return inputProductArea;
    }

    public void setInputProductArea(String inputProductArea) {
        this.inputProductArea = inputProductArea;
    }

    public String getInputProductDescription() {
        return inputProductDescription;
    }

    public void setInputProductDescription(String inputProductDescription) {
        this.inputProductDescription = inputProductDescription;
    }

    public String getInputProductId() {
        return inputProductId;
    }

    public void setInputProductId(String inputProductId) {
        this.inputProductId = inputProductId;
    }

    public String getInputProductCategory() {
        return inputProductCategory;
    }

    public void setInputProductCategory(String inputProductCategory) {
        this.inputProductCategory = inputProductCategory;
    }

    public String getInputProductPrice() {
        return inputProductPrice;
    }

    public void setInputProductPrice(String inputProductPrice) {
        this.inputProductPrice = inputProductPrice;
    }

    public String getInputProductInventory() {
        return inputProductInventory;
    }

    public void setInputProductInventory(String inputProductInventory) {
        this.inputProductInventory = inputProductInventory;
    }
    
}
