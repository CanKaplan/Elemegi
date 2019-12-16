package com.Elemegi.Elemegi.Controller;

import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.Model.Order;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.Model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainManager {

    //Lists that keep data of the model package's layers
    private User currentUser;
    private List<User> users;
    private List<Product> myProducts;
    private List<Product> sliderProducts = new ArrayList<>();
    private Product[] bottomProducts = new Product[18];
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

    public List<Product> getSliderProducts() {
        return sliderProducts;
    }

    public void setSliderProducts(List<Product> sliderProducts) {
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
        List<List<String>> converted = converter(result);
        currentUser = new User(converted.get(0).get(1),converted.get(0).get(2),converted.get(0).get(4),converted.get(0).get(3),converted.get(0).get(5),converted.get(0).get(7));

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

    public List<Product> createHomePageSliderContent(long id) { // KARASIZIM BURDA PRODUCT ÇEKİP BURDA EŞİTLEYİP Mİ KULLANICAZ YOKSA DATABASEDEN RESİM Mİ ALICAZ

        String sliderProductsString = DatabaseManager.getInstance().createHomePageSliderProducts(id);
        List<List<String>> converted = converter(sliderProductsString);
        for(int i = 0 ; i < 3 ; i++){
            sliderProducts.set(i,new Product(converted.get(i).get(1),Long.parseLong(converted.get(i).get(0)) , Long.parseLong(converted.get(i).get(2)), null,converted.get(i).get(6),null,0,Double.parseDouble(converted.get(i).get(4)),0,null));
        }

        return sliderProducts;
    }
    public Product[] createHomePageImages(long id) {
        String bottomProductsString = DatabaseManager.getInstance().createHomePageProducts(id);
        List<List<String>> converted = converter(bottomProductsString);
        for(int i = 0 ; i < 18 ; i++){
            bottomProducts[i] = new Product(converted.get(i).get(1),Long.parseLong(converted.get(i).get(0)) , Long.parseLong(converted.get(i).get(2)), null,converted.get(i).get(6),null,0,Double.parseDouble(converted.get(i).get(4)),0,null);
        }
        return bottomProducts;
    }

    public List<Product> getMyProducts(long id) {
        String myProductString = DatabaseManager.getInstance().createMyProductsPage(id);
        List<List<String>> converted = converter(myProductString);
        int numberOfProducts = converted.size();
        for (int i = 0; i < numberOfProducts ; i++){
            myProducts.set(i, new Product(converted.get(i).get(1),Long.parseLong(converted.get(i).get(0)) , Long.parseLong(converted.get(i).get(2)), null,converted.get(i).get(6),null,0,Double.parseDouble(converted.get(i).get(4)),0,null));
        }
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
    private List<List<String>> converter(String data){
        //List<String> inner = new ArrayList<String>();
        boolean firstEn = true;
        int column = 0;
        int row = 0;
        String tmp = "";
        List<List<String>> listOfLists = new ArrayList<>();
        for(int i = 0; i< data.length() ; i++){
            if(data.charAt(i) == '"'){
                if(!firstEn){
                    firstEn = true;
                    listOfLists.get(row).set(column,tmp);
                    column++;
                }
                else
                    firstEn = false;
            }
            if(firstEn){
                tmp = tmp + data.charAt(i);
            }
            if(data.charAt(i) == 'n' && firstEn){
                listOfLists.get(row).set(column,null);
                column++;
            }
            if(data.charAt(i) == ']'){
                row++;
            }
        }
        return listOfLists;
    }
}
