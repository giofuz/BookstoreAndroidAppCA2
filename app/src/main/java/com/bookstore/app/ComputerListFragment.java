package com.bookstore.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.bookstore.app.com.bookstore.app.http.HttpClient;
import com.bookstore.app.com.bookstore.app.http.HttpException;
import com.bookstore.app.com.bookstore.app.http.HttpRequest;
import com.bookstore.app.com.bookstore.app.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ComputerListFragment extends ListFragment {
    private final String API_URL = "http://192.168.1.8/ComputerStoreWebApp/api/";
    private final String TAG = "ComputerStoreWebApp";

    private List<Computer> mComputers;
    private ComputerListAdapter mListAdapter;

    private class HttpRequestTask extends AsyncTask<HttpRequest, Integer, HttpResponse> {

        @Override
        public HttpResponse doInBackground(HttpRequest... requests) {
            HttpClient client;
            HttpRequest request;
            HttpResponse response = null;

            client = new HttpClient();
            request = requests[0];
            try {
                response = client.execute(request);
            }
            catch (HttpException e) {
                String errorMessage = "Error downloading book list: " + e.getMessage();
                Log.d(TAG, "HttpClient: " + errorMessage);
            }

            return response;
        }

        @Override
        public void onPostExecute(HttpResponse response) {
            onHttpResponse(response);
        }
    }
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        String urlString = null;
        URI uri;
        HttpRequest request;
        HttpRequestTask t;

        try {
            urlString = API_URL + "/computers";
            uri = new URI(urlString);

            request = new HttpRequest("GET", uri);
            t = new HttpRequestTask();
            t.execute(request);
        }
        catch (URISyntaxException e) {
            String errorMessage = "Error parsing uri (" + urlString + "): " + e.getMessage();
            Log.d(TAG, "HttpClient: " + errorMessage);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListAdapter != null) {
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search: {
                Toast.makeText(getActivity(), "Search selected", Toast.LENGTH_LONG).show();
                return false;
            }
            case R.id.action_new: {
                Toast.makeText(getActivity(), "Add selected", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this.getActivity(), ComputerActivity.class);
                startActivity(i);

                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Computer b = (Computer)(getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), ComputerActivity.class);
        i.putExtra(ComputerActivity.EXTRA_COMPUTER_ID, b.getId());
        startActivity(i);
    }
    
    public void onHttpResponse(HttpResponse response) {
		JSONArray jsonArray;
        JSONObject jsonObject;
        Computer computer;

        if (response != null) {
            if (response.getStatus() == 200) {
                String body = response.getBody();
                try {
                    jsonArray = new JSONArray(body);
                    mComputers = new ArrayList<Computer>();
                    for (int i = 0; i != jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String make = jsonObject.getString("make");
                        String model = jsonObject.getString("model");
                        String os = jsonObject.getString("os");
                        String quantity = jsonObject.getString("quantity");
                        double price = jsonObject.getDouble("price");
                        int studentId = jsonObject.getInt("student_id");

                        computer = new Computer(id, make, model, os, quantity, price, studentId);

                        mComputers.add(computer);
                    }
                }
                catch (JSONException e) {
                    String message = "Error retrieving books: " + e.getMessage();
                    Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
                }

                CompterStore store = CompterStore.getInstance();
                store.setBooks(mComputers);

                mListAdapter = new ComputerListAdapter(getActivity(), mComputers);

                setListAdapter(mListAdapter);
            }
            else {
                Log.d(TAG, "Http Response: " + response.getStatus() + " " + response.getDescription());
            }
        }
    }
}
