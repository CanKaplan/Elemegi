package com.Elemegi.Elemegi.Controller;

import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.Model.Order;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.Model.User;

import java.security.PublicKey;
import java.util.List;

public class MainManager {
    private List<User> users;
    private List<Product> products;
    private List<Comment> comments;
    private List<Order> orders;

    private final static MainManager instance = new MainManager();

    public static MainManager getInstance() {
        return instance;
    }

    public static void setUserProperties(String email) {
        //check email get id set properties
    }

    public void openLoginPanel() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void createUser(String name, int roleType, String password, String email, String phoneNumber, String address, int age) {
        User user = new User(name, roleType, password, email, phoneNumber, address, age);
        users.add(user);
        // Bu oluşturulan user database e eklenmeli
        //Id olayı halledilmesi lazım
    }

    public void addProduct(String productName, long productID, long userID, List<String> labels, List<String> image, double price, int deliverTime) {
        Product product = new Product(productName, 0, userID, labels, image, price, deliverTime);
        products.add(product);
        // Bu oluşturulan user database e eklenmeli
    }

    public Product deleteProduct(long productID) {
        int i = 0;
        for (; i < products.size() && products.get(i).getProductID() != productID; i++) {
        }
        if(i < products.size()) {
            Product deletedProduct = products.get(i);
            products.remove(i);
            //Database
            return deletedProduct;
        }
        else
            return null;
    }
    public User deleteUser(long userID){
        int i = 0;
        for (; i < users.size() && users.get(i).getID() != userID; i++) {
        }
        if(i < users.size()) {
            User deleteduser = users.get(i);
            users.remove(i);
            //Database
            return deleteduser;
        }
        else
            return null;
    }
    public void giveOrder(long productID, long userID,int amount, String productName,double price,String description, int deliverTime){
        Order order = new Order(0,productID,userID,amount,productName,price);
        order.setDescription(description);
        order.setRemainingTime(deliverTime);
        orders.add(order);
    }
    public Order cancelOrder(long orderID){
        int i = 0;
        for (; i < orders.size() && orders.get(i).getOrderID() != orderID; i++) {
        }
        if(i < orders.size()) {
            Order canceledorder = orders.get(i);
            orders.remove(i);
            //Database
            return canceledorder;
        }
        else
            return null;
    }
}
