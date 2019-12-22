package com.Elemegi.Elemegi.Controller;

import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.Model.Order;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.Model.User;

import java.util.ArrayList;
import java.util.List;

public class MainManager {

    //Lists that keep data of the model package's layers
    private User currentUser;
    private List<User> users;

    private List<Comment> comments;
    private Order[] myOrders;

    //defined managers that will be controlled by MainManager
    private SearchManager searchManager;

    private final static MainManager instance = new MainManager();

    public static MainManager getInstance() {
        return instance;
    }


    public boolean checkUser(String email, String password){
        String result = DatabaseManager.getInstance().checkUser(email, password);
        if (result.equals("false")){
            return false;
        }
        List<List<String>> converted = converter(result);
        currentUser = new User(Long.parseLong(converted.get(0).get(0)),converted.get(0).get(1),converted.get(0).get(2),converted.get(0).get(3),converted.get(0).get(5),converted.get(0).get(7),converted.get(0).get(6));

        return true;
    }

    public void registerUser(String name, String surname,String roleType, String email, String password) {

        checkUserEmail(email);
        DatabaseManager.getInstance().registerUser(name,surname,email, password, roleType);
        //databaseManager.setUserID(email,password);
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

    public List<Product> createHomePageSliderContent(long id) {
        List<Product> sliderProducts = new ArrayList<>();
        String sliderProductsString = DatabaseManager.getInstance().createHomePageSliderProducts(id);
        List<List<String>> converted = converter(sliderProductsString);
        for(int i = 0 ; i < 3 ; i++){
            sliderProducts.add(i,new Product(converted.get(i).get(1),Long.parseLong(converted.get(i).get(0)) , converted.get(i).get(2), null,converted.get(i).get(6),null,0,Double.parseDouble(converted.get(i).get(4)),0,null,0L));
        }

        return sliderProducts;
    }
    public List<Product> createHomePageImages(long id) {
        List<Product> bottomProducts = new ArrayList<>();
        String bottomProductsString = DatabaseManager.getInstance().createHomePageProducts(id);
        List<List<String>> converted = converter(bottomProductsString);
        for(int i = 0 ; i < 18 &&  converted.get(i) != null  ; i++){
            bottomProducts.add(i,new Product(converted.get(i).get(1),Long.parseLong(converted.get(i).get(0)) , converted.get(i).get(2), null,converted.get(i).get(6),null,0,Double.parseDouble(converted.get(i).get(4)),0,null,0L));
        }
        return bottomProducts;
    }

    public List<Product> getMyProducts(long id) {
        List<Product> myProducts = new ArrayList<>();
        String myProductString = DatabaseManager.getInstance().createMyProductsPage(id);
        List<List<String>> converted = converter(myProductString);
        int numberOfProducts = converted.size();
        for (int i = 0; i < numberOfProducts &&  converted.get(i) != null; i++) {
            myProducts.add(i, new Product(converted.get(i).get(1), Long.parseLong(converted.get(i).get(2)), getCurrentUser().getName(), null, converted.get(i).get(0), converted.get(i).get(3), 0, Double.parseDouble(converted.get(i).get(4)), Integer.parseInt(converted.get(i).get(5)), null,0L));
        }
        return myProducts; // bu sadece producer kullanılıyorsa kullanılıcak.
    }

    public Order[] getMyOrders(long id) {
        String myOrdersString = DatabaseManager.getInstance().createMyOrdersPage(id);
        //BURDAKİ STRING SANA ORDERLARI DÖNDÜRCEK ONLARI ORDER ARRAYINE ÇEVİR
        return myOrders;
    }

    public List<String> generateLabels(String images) {
        List<String> generatedStrings = null;
        return generatedStrings;
    }

    public Long addProduct(String images, String nameString, String descriptionString, String deliveryTimeString, String priceString, List<String> labels) {
        String addProductString = DatabaseManager.getInstance().addProductPage(currentUser.getID(),nameString,descriptionString,deliveryTimeString,priceString,labels);
        int stringCount = images.length() / 3000;
        Long productID = Long.parseLong(addProductString);
        for (int i = 0; i < stringCount ; i++) {
            String tmpImage = images.substring(i*3000,(i+1)*3000);
            DatabaseManager.getInstance().imageAddition(productID,tmpImage);
        }
        DatabaseManager.getInstance().imageAddition(productID,images.substring((stringCount)*3000));
        return productID;
    }

    private List<List<String>> converter(String data){
        boolean firstEn = true;
        int column = 0;
        int row = 0;
        StringBuilder tmp = new StringBuilder();
        List<List<String>> listOfLists = new ArrayList<>();
        for(int i = 0; i< data.length() ; i++){
            char current = data.charAt(i);
            if(current == '['){
                listOfLists.add(new ArrayList<String>());
            }
            if(current == '"'){
                if(!firstEn){
                    firstEn = true;
                    listOfLists.get(row).add(column, tmp.toString());
                    tmp = new StringBuilder();
                    column++;
                }
                else
                    firstEn = false;
            }
            if(!firstEn && current != '"'){
                tmp.append(current);
            }
            if(current == 'n' && data.charAt(i+1) == 'u' && firstEn){
                listOfLists.get(row).add(column,"");
                column++;
            }
            if(current == ']'){
                row++;
                column = 0;
            }
        }
        return listOfLists;
    }

    private List<String> converter2(String data){
        int y = 0;
        StringBuilder tmp = new StringBuilder();
        List<String> list= new ArrayList<>();
        for(int i = 0; i< data.length() ; i++){
            if(data.charAt(i) != ','){
                tmp.append(data.charAt(i));
            }
            else{
                list.add(y, tmp.toString());
                y++;
                tmp = new StringBuilder();
            }
        }
        list.add(0,data);
        return  list;
    }


    public Product getProductInfo(long productID) {
        String productInfoString = DatabaseManager.getInstance().getProductInfo(productID);
        Product myProduct;
        List<List<String>> converted = converter(productInfoString);

        String myCommentsString = DatabaseManager.getInstance().getComments(productID);
        List<Comment> myComments = new ArrayList<>();
        List<List<String>> convertedComments = converter(myCommentsString);

        for (int i = 0; i < convertedComments.size(); i++) {
            myComments.add(i,new Comment(convertedComments.get(i).get(0),convertedComments.get(i).get(1)));
        }
        double temp;
        if(converted.get(0).get(8).equals("")){
            temp = 0;
        }
        else{
            temp = Double.parseDouble(converted.get(0).get(8));
        }
        myProduct = new Product(converted.get(0).get(1),Long.parseLong(converted.get(0).get(0)) , converted.get(0).get(2), null,converted.get(0).get(6),converted.get(0).get(3),temp,Double.parseDouble(converted.get(0).get(4)),Integer.parseInt(converted.get(0).get(5)),myComments,Long.parseLong(converted.get(0).get(7)));
        return myProduct;
    }

    public void updateFav(long productID, long id) {
        DatabaseManager.getInstance().updateFav(productID,id);
    }

    public boolean checkIfOrdered(long productID, long id) {
        String productCheckString = DatabaseManager.getInstance().checkIfOrdered(productID,id);
        if (productCheckString.equals("TRUE")){
            return true;
        }
        else
            return false;
    }

    public void sendComment(String commentAddString, long productID, long id) {
        DatabaseManager.getInstance().sendComment(commentAddString,productID,id);
    }

    public List<Comment> updateComments(long productID) {
        String myCommentsString = DatabaseManager.getInstance().getComments(productID);
        List<Comment> myComments = new ArrayList<>();
        List<List<String>> convertedComments = converter(myCommentsString);

        for (int i = 0; i < convertedComments.size(); i++) {
            myComments.add(i,new Comment(convertedComments.get(i).get(0),convertedComments.get(i).get(1)));
        }
        return myComments;
    }

    public boolean checkFav(long productID, long id) {
        String checkFavString = DatabaseManager.getInstance().checkFav(productID,id);
        if (checkFavString.equals("FAV")){
            return true;
        }
        else
            return false;
    }

    public void deleteProduct(long productID) {
        DatabaseManager.getInstance().deleteProduct(productID);
    }

    public void updateProduct(long productID, String nameString, String descriptionString, double price, int deliveryTime, String images) {

        descriptionString = descriptionString.replace(' ', '-');
        nameString = nameString.replace(' ', '-');

        int stringCount = images.length() / 3000;
        DatabaseManager.getInstance().updateProduct(productID,nameString,descriptionString,price,deliveryTime);
        for (int i = 0; i < stringCount ; i++) {
            String tmpImage = images.substring(i*3000,(i+1)*3000);
            DatabaseManager.getInstance().imageAddition(productID,tmpImage);
        }
        DatabaseManager.getInstance().imageAddition(productID,images.substring((stringCount)*3000));

    }

    public User getUserProfile(long producerID) {

        String producerInfoString = DatabaseManager.getInstance().getProducerInfo(producerID);
        User newUser;
        List<List<String>> converted = converter(producerInfoString);
        newUser = new User(producerID,converted.get(0).get(1),null,converted.get(0).get(3),converted.get(0).get(5),converted.get(0).get(7),converted.get(0).get(6));

        return newUser;
    }
}
