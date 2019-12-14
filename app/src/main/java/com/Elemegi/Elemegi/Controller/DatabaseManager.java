package com.Elemegi.Elemegi.Controller;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DatabaseManager extends Activity {
    private final static DatabaseManager instance = new DatabaseManager();

    public boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() ){
            return true;
        }
        return false;
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    public String checkUser(String email, String password){
        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "password=" + password + "&email=" + email;
        backgroundTask.setTaskContent("checkUser.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String checkUserRegistered(String email){
        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "email=" + email;
        backgroundTask.setTaskContent("checkUserRegistered.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String registerUser(String name, String surname, String email, String password, String roleType){
        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "name=" + name + "&surname=" + surname + "&type=" + roleType + "&password=" + password + "&email=" + email;
        backgroundTask.setTaskContent("register.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String getMyOrders(long ID){
        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "ID=" + ID;
        backgroundTask.setTaskContent("order.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String getFavourites(long ID){
        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "ID=" + ID;
        backgroundTask.setTaskContent("showFavourite.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String addFavourite(long userID, long productID){
        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "ID=" + userID + "&ID=" + productID;
        backgroundTask.setTaskContent("addfavourite.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String getProducts(String label1,String label2,String label3){
        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "label1=" + label1 + "&label2=" + label2 + "&label3=" + label3;
        backgroundTask.setTaskContent("register.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String updateProfile(String image, String name, String email, String phone, String password, String address){
        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "image=" + image + "&name=" + name + "&email=" + email + "&phone=" + phone + "&password=" + password + "&address=" + address;
        backgroundTask.setTaskContent("updateProfile.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String createHomePageSliderProducts(long id) {

        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "u_ID=" + id;
        backgroundTask.setTaskContent("homePageSlider.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String createHomePageProducts(long id) {

        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "u_ID=" + id;
        backgroundTask.setTaskContent("homePageProducts.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String createMyProductsPage(long id) {

        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "u_ID=" + id;
        backgroundTask.setTaskContent("homePageProducer.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    public String createMyOrdersPage(long id) {

        BackgroundTask backgroundTask = new BackgroundTask();
        String data_string = "u_ID=" + id;
        backgroundTask.setTaskContent("????????.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;

    }

    public String addProductPage(long id, String[] images, String nameString, String descriptionString, String deliveryTimeString, String priceString, List<String> labels) {
        BackgroundTask backgroundTask = new BackgroundTask();
        String[] tempImage = new String[3];
        for(int i = 0; i < 3; i++){
            if(images[i] != ""){
                tempImage[i] = images[i];
            }
            else{
                tempImage[i] = "";
            }
        }
        String data_string = "u_ID=" + id + "&p_name" + nameString + "&description" + descriptionString + "&price" + priceString + "&deliveryTime" + deliveryTimeString
                + "&image" + tempImage[0] + "," + tempImage[1] + "," + tempImage[2] + "&label" + labels.get(0) + "," + labels.get(1) + ","
                + labels.get(2) + "," + labels.get(3) + "," + labels.get(4);
        backgroundTask.setTaskContent("addproduct.php",data_string);
        String result = "";
        try {
            result = backgroundTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("XXXXXXXXXXX",result);
        return result;
    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {

        private String add_info_url;
        private String queryPhp;

        @Override
        protected void onPreExecute() {
            add_info_url = "https://elemegiapp.000webhostapp.com/" + queryPhp;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String jsonString = "";
               try {
                   URL url = new URL(add_info_url);
                   HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                   httpURLConnection.setRequestMethod("POST");
                   httpURLConnection.setDoInput(true);
                   httpURLConnection.setDoOutput(true);
                   StringBuilder response;

                   try (BufferedReader in = new BufferedReader(
                           new InputStreamReader(httpURLConnection.getInputStream()))) {

                       String line;
                       response = new StringBuilder();
                       Log.d("sadasdasdasd","bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                       while ((line = in.readLine()) != null) {
                           response.append(line);
                       }
                   }
                   jsonString = response.toString();
               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
            return jsonString;

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result){

        }


        public void setTaskContent(String queryPhp, String data_string) {
            this.queryPhp = queryPhp + "?" + data_string;
        }
    }

}
