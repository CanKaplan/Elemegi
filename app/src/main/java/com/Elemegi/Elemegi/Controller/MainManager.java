package com.Elemegi.Elemegi.Controller;

import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.Model.Order;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.Model.User;

import java.util.List;

public class MainManager {

    //Lists that keep data of the model package's layers
    private User currentUser;
    private List<User> users;
    private Product[] myProducts;
    private Product[] sliderProducts;
    private Product[] bottomProducts;
    private List<Comment> comments;
    private Order[] myOrders;

    //defined managers that will be controlled by MainManager
    private SearchManager searchManager;

    private final static MainManager instance = new MainManager();

    public static MainManager getInstance() {
        return instance;
    }


    public List<User> getUsers() { return users; }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Order[] getOrders() {
        return myOrders;
    }

    public void setOrders(Order[] orders) {
        this.myOrders= orders;
    }

    public Product[] getSliderProducts() {
        return sliderProducts;
    }

    public void setSliderProducts(Product[] sliderProducts) {
        this.sliderProducts = sliderProducts;
    }

    public Product[] getBottomProducts() {
        return bottomProducts;
    }

    public void setBottomProducts(Product[] bottomProducts) {
        this.bottomProducts = bottomProducts;
    }

    public boolean checkUser(String email, String password){
        String result = DatabaseManager.getInstance().checkUser(email, password);
        if (result.equals("false")){
            return false;
        }
        //currentUser = new User("can kaplan","Customer","can123456","cankaplan1007@gmail.com",null,null);
        //RESULTTAN SANA USER STRING İ DONCEK ONLA currentUser ı setle **********************************************
        return true;
    }

    public void registerUser(String name, String surname,String roleType, String email, String password) {

        checkUserEmail(email);
        DatabaseManager.getInstance().registerUser(name,surname,email, password, roleType);
        //databaseManager.setUserID(email,password);
    }
    /*
        public void addProduct(String productName, long productID, long userID, List<String> labels, List<Base64> image, double price, int deliverTime) {
            Product product = new Product(productName, 0, userID, labels, image, price, deliverTime);
            //products.add(product);
            // Bu oluşturulan product database e eklenmeli
            //DatabaseManager.getInstance().createProductlistTable();
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
    public void giveOrder(long orderID, long productID, long userID, String productName,double price,String description, Date givenDate){
        Order order = new Order(orderID,productID,userID,givenDate,productName,price);
        //order.setDescription(description);
        //order.setRemainingTime(deliverTime);
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
*/
    //setPassword Methodu
    public void setPassword(String email, String pass){
        //DatabaseManager.getInstance().changePassword(email, pass);
    }

    public boolean checkUserEmail(String email) {
        if(DatabaseManager.getInstance().checkUserRegistered(email).equals("FALSE")){
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Product[] createHomePageSliderContent(long id) { // KARASIZIM BURDA PRODUCT ÇEKİP BURDA EŞİTLEYİP Mİ KULLANICAZ YOKSA DATABASEDEN RESİM Mİ ALICAZ

        String sliderProductsString = DatabaseManager.getInstance().createHomePageSliderProducts(id);
        // BURDAKİ STRINGLERI PRODUCT TİPİİNE ÇEVİR VE SLİDERPRODUCTS YAP  3 tane olacak
        return sliderProducts;
    }
    public Product[] createHomePageImages(long id) {
        String bottomProductsString = DatabaseManager.getInstance().createHomePageProducts(id);
        // BURDAKİ STRINGLERI PRODUCT TİPİİNE ÇEVİR VE BOTTOMPRODUCTS YAP  18 tane olacak
        return bottomProducts;
    }

    public Product[] getMyProducts(long id) {
        String myProductString = DatabaseManager.getInstance().createMyProductsPage(id);
        //BURDAKİ STRING SANA PRODUCTLARI DÖNDÜRCEK ONLARI PRODUCT ARRAYINE ÇEVİR
        return myProducts; // bu sadece producer kullanılıyorsa kullanılıcak.
    }

    public Order[] getMyOrders(long id) {
        String myOrdersString = DatabaseManager.getInstance().createMyOrdersPage(id);
        //BURDAKİ STRING SANA ORDERLARI DÖNDÜRCEK ONLARI ORDER ARRAYINE ÇEVİR
        return myOrders;
    }

    public List<String> generateLabels(String[] images) {
        List<String> generatedStrings = null;
        return generatedStrings;
    }

    public Product addProduct(String[] images, String nameString, String descriptionString, String deliveryTimeString, String priceString, List<String> labels) {
        Product currentProduct = null;
        String addProductString = DatabaseManager.getInstance().addProductPage(currentUser.getID(),images,nameString,descriptionString,deliveryTimeString,priceString,labels);
        //Burada sana product bilgilerini dönecek bunla product page oluştur
        return currentProduct;
    }


    //remember me local file set method

    //get 3 product for homepage

    //get 18 product for homepage favourite

    //
}
