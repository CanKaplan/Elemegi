package com.Elemegi.Elemegi.Controller;

import android.util.Log;

import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.Model.Order;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainManager {

    //Lists that keep data of the model package's layers
    private User currentUser;
    private List<User> users;

    private List<Comment> comments;
    private Order[] myOrders;
    public List<String> createdLabels =new ArrayList<>();
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
        name = name.replaceAll(" ","%20");
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

    public List<Order> getMyOrders(long id, int k) {
        List<Order> myOrders = new ArrayList<>();
        String myProductString = DatabaseManager.getInstance().createMyOrdersPage(id,k);
        List<List<String>> converted = converter(myProductString);
        int numberOfProducts = converted.size();
        if(k == 0){
            for (int i = 0; i < numberOfProducts &&  converted.get(i) != null; i++) {
                myOrders.add(i, new Order(Long.parseLong(converted.get(i).get(1)),converted.get(i).get(2),converted.get(i).get(0),0L,"","","",Double.parseDouble(converted.get(i).get(3)),Integer.parseInt(converted.get(i).get(5)),converted.get(i).get(4)));
            }
        }
        else{
            for (int i = 0; i < numberOfProducts &&  converted.get(i) != null; i++) {
                myOrders.add(i, new Order(Long.parseLong(converted.get(i).get(0)),converted.get(i).get(6),"",0L,converted.get(i).get(1),converted.get(i).get(3),converted.get(i).get(2),0,Integer.parseInt(converted.get(i).get(5)),converted.get(i).get(4)));
            }
        }
        return myOrders;
    }

    public void generateLabels(String images) {


        final JSONObject tempJson = jsonMaker(images);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    createJSONPostRequest(tempJson);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public JSONObject jsonMaker(String imageString){

        JSONObject imageJson = new JSONObject();
        try {
            imageJson.put("content", imageString);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject temp = new JSONObject();
        try {
            temp.put("type", "WEB_DETECTION");
            temp.put("maxResults", 20);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONArray featuresJsonArray = new JSONArray();
        featuresJsonArray.put(temp);

        JSONObject temp2 = new JSONObject();
        try {
            temp2.put("image", imageJson);
            temp2.put("features", featuresJsonArray);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray requestsJsonArray= new JSONArray();
        requestsJsonArray.put(temp2);

        JSONObject temp3 = new JSONObject();
        try {
            temp3.put("requests", requestsJsonArray);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String jsonStr = temp3.toString();
        Log.i("JSON",jsonStr);
        return temp3;
    }

    private void createJSONPostRequest(JSONObject jsonFile) throws IOException, JSONException {
        URL url = new URL("https://vision.googleapis.com/v1/images:annotate?key=AIzaSyBPEr098bfnM9MuTfPk98QrBn0DflVGHvo");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(jsonFile.toString());
        wr.flush();

        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            List<String> newList = new ArrayList<>();
            JSONObject newObj = new JSONObject(sb.toString());
            JSONObject new2Obj = new JSONObject(newObj.getJSONArray("responses").get(0).toString());
            JSONObject new3Obj = new JSONObject(new2Obj.getJSONObject("webDetection").toString());

            JSONArray tempArray = new3Obj.getJSONArray("webEntities");
            for (int i = 0; i < tempArray.length(); i++) {
                try {
                    String temp = tempArray.getJSONObject(i).getString("description");
                    newList.add(temp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            createdLabels = newList;
        } else {
            Log.i("bbbbb",con.getResponseMessage());
        }
    }

    public Long addProduct(String images, String nameString, String descriptionString, String deliveryTimeString, String priceString, List<String> labels) {
        nameString = nameString.replaceAll(" ","%20");
        descriptionString = descriptionString.replaceAll(" ","%20");

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
        commentAddString = commentAddString.replaceAll(" ","%20");
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

        descriptionString = descriptionString.replaceAll(" ","%20");
        nameString = nameString.replaceAll(" ","%20");

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

    public List<Product> getMyFavs(long ıd) {
        List<Product> myProducts = new ArrayList<>();
        String myProductString = DatabaseManager.getInstance().createMyFavPage(ıd);
        List<List<String>> converted = converter(myProductString);
        int numberOfProducts = converted.size();
        for (int i = 0; i < numberOfProducts &&  converted.get(i) != null; i++) {
            myProducts.add(i, new Product(converted.get(i).get(0), Long.parseLong(converted.get(i).get(1)), getCurrentUser().getName(), null, converted.get(i).get(2), converted.get(i).get(3), 0, Double.parseDouble(converted.get(i).get(4)), Integer.parseInt(converted.get(i).get(5)), null,0L));
        }
        return myProducts;

    }

    public List<Product> searchLabelsFromDatabase(List<String> labels) {
        List<Product> searchedProducts = new ArrayList<>();
        StringBuilder searchString = new StringBuilder();
        for (int i = 0; i < labels.size() - 1 ; i++) {
            searchString.append(labels.get(i)).append(",");
        }
        searchString.append(labels.get(labels.size()-1));
        String searchModifiedString = searchString.toString();
        searchModifiedString = searchModifiedString.replaceAll(" ","%20");
        String myProductString = DatabaseManager.getInstance().searchLabelsFromDatabase(searchModifiedString);
        List<List<String>> converted = converter(myProductString);
        int numberOfProducts = converted.size();
        for (int i = 0; i < numberOfProducts &&  converted.get(i) != null; i++) {
            searchedProducts.add(i, new Product(converted.get(i).get(1),Long.parseLong(converted.get(i).get(0)),"",null,converted.get(i).get(5),converted.get(i).get(2),0,Double.parseDouble(converted.get(i).get(3)),Integer.parseInt(converted.get(i).get(4)),null,0L));
        }
        return searchedProducts;
    }

    public List<Product> searchProduct(String searchString) {
        searchString = searchString.replaceAll(" ","%20");
        List<Product> searchedProducts = new ArrayList<>();
        String myProductString = DatabaseManager.getInstance().searchProduct(searchString);
        List<List<String>> converted = converter(myProductString);
        int numberOfProducts = converted.size();
        for (int i = 0; i < numberOfProducts &&  converted.get(i) != null; i++) {
            searchedProducts.add(i, new Product(converted.get(i).get(0), Long.parseLong(converted.get(i).get(1)), getCurrentUser().getName(), null, converted.get(i).get(2), converted.get(i).get(3), 0, Double.parseDouble(converted.get(i).get(4)), Integer.parseInt(converted.get(i).get(5)), null,0L));
        }
        return searchedProducts;
    }

    public void giveOrder(long productID, long id, String note) {
        note = note.replaceAll(" ","%20");
        DatabaseManager.getInstance().giveOrder(productID,id,note);
    }

    public void giveRate(long productID, long id, int rati2) {
        DatabaseManager.getInstance().giveRate(productID,id,rati2);
    }

    public boolean isRated(long productID, long id) {
        String res = DatabaseManager.getInstance().isRated(productID,id);
        if(res.equals("TRUE"))
            return true;
        return false;
    }
}
