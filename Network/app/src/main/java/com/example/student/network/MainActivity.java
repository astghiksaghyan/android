package com.example.student.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterModel adapter;
    static  public List<CardViewModel> listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            listItems = new AsyncTaskRunner().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new AdapterModel(this, listItems, getSupportFragmentManager());

        recyclerView.setAdapter(adapter);




    }

    class AsyncTaskRunner extends AsyncTask<Void, Void,List<CardViewModel>> {
        HttpURLConnection connection = null;

        @Override
        protected List<CardViewModel> doInBackground(Void... strings) {

            try {
                List<CardViewModel> listItems = new ArrayList<>();
                URL url = new URL("https://jsonplaceholder.typicode.com/photos");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
                    JSONArray jsonArray = new JSONArray(String.valueOf(responseStrBuilder));


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonItem = (JSONObject) jsonArray.get(i);

                    String title_JOI = jsonItem.getString("title");
                    String url_JOI = jsonItem.getString("url");
                    String thumbnailUrl_JOI = jsonItem.getString("thumbnailUrl");
                    CardViewModel item = new CardViewModel(thumbnailUrl_JOI, title_JOI, url_JOI);
                    listItems.add(item);

                }
                return listItems;

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (null != connection) {
                    connection.disconnect();
                }
            }

//                URL url = new URL("https://jsonplaceholder.typicode.com/photos");
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                connection.getInputStream();
//                URL url = URL.parse("https://jsonplaceholder.typicode.com/photos");
            return null;
        }




    }


}
