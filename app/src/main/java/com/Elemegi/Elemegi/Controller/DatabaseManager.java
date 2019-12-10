package com.Elemegi.Elemegi.Controller;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

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

    public boolean checkUser(String email, String password){
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.setTaskContent("GET","checkUser.php");
        //backgroundTask.execute(,);

        return false;
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {

        private String add_info_url;
        private String queryPhp;
        private String methodType;
        private InputStream is = null;

        @Override
        protected void onPreExecute() {
            add_info_url = "https://elemegiapp.000webhostapp.com/" + queryPhp;
        }

        @Override
        protected String doInBackground(String... args) {
            String nameToAdd = args[0];
            String emailToAdd = args[1];
            String mobileToAdd = args[2];
            String jsonString = "";
            if(methodType.equals("POST")){
                try {
                    URL url = new URL(add_info_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data_string = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(nameToAdd,"UTF-8")+"&"+
                            URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(emailToAdd,"UTF-8")+"&"+
                            URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobileToAdd,"UTF-8");

                    bufferedWriter.write(data_string);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return "One colum inserted";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return null;
            }else{
                try {
                    URL url = new URL(add_info_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    is = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    jsonString = sb.toString();

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return  jsonString;
            }

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }


        public void setTaskContent(String methodType, String queryPhp) {
            this.queryPhp = queryPhp;
            this.methodType = methodType;
        }
    }

}
