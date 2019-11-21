package com.queens.delivery.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import com.queens.delivery.R;
import com.queens.delivery.adapters.HomeAdapter;
import com.queens.delivery.models.NewsItem;
import com.queens.delivery.models.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeAdapter hAdapter;
    private List<NewsItem> mData;
    private List<Orders> mOrders;
    private FloatingActionButton fabSwitcher;
    private boolean isDark = false;
    private ConstraintLayout rootLayout;
    private EditText searchInput;
    private CharSequence search="";

    private static final String URL_AWAITING_ORDERs = "https://delivery.queensclassycollections.com/api/member/get_await.php";



    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("HOME");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fabSwitcher = view.findViewById(R.id.fab_switcher);
        rootLayout = view.findViewById(R.id.root_layout);
        searchInput = view.findViewById(R.id.search_input);
        recyclerView = view.findViewById(R.id.news_rv);
        mData = new ArrayList<>();
        mOrders = new ArrayList<>();

        // load theme state

        isDark = getThemeStatePref();
        if(isDark) {
            // dark theme is on

            searchInput.setBackgroundResource(R.drawable.search_input_dark_style);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));

        }
        else
        {
            // light theme is on
            searchInput.setBackgroundResource(R.drawable.search_input_style);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));

        }



        // fill list news with data
        // just for testing purpose i will fill the news list with random data
        // you may get your data from an api / firebase or sqlite database ...
        /*mData.add(new NewsItem("OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","6 july 1994",R.drawable.user));
        mData.add(new NewsItem("I love Programming And Design","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,","6 july 1994",R.drawable.circul6));
        mData.add(new NewsItem("My first trip to Thailand story ","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","6 july 1994",R.drawable.uservoyager));
        mData.add(new NewsItem("After Facebook Messenger, Viber now gets...","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,","6 july 1994",R.drawable.useillust));
        mData.add(new NewsItem("Isometric Design Grid Concept","lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit","6 july 1994",R.drawable.circul6));
        mData.add(new NewsItem("Android R Design Concept 4K","lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit ","6 july 1994",R.drawable.user));
        mData.add(new NewsItem("OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","6 july 1994",R.drawable.user));
        mData.add(new NewsItem("I love Programming And Design","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,","6 july 1994",R.drawable.circul6));
        mData.add(new NewsItem("My first trip to Thailand story ","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","6 july 1994",R.drawable.uservoyager));
        mData.add(new NewsItem("After Facebook Messenger, Viber now gets...","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,","6 july 1994",R.drawable.useillust));
        mData.add(new NewsItem("Isometric Design Grid Concept","lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit","6 july 1994",R.drawable.circul6));
        mData.add(new NewsItem("Android R Design Concept 4K","lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit ","6 july 1994",R.drawable.user));
        mData.add(new NewsItem("OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","6 july 1994",R.drawable.user));
        mData.add(new NewsItem("I love Programming And Design","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,","6 july 1994",R.drawable.circul6));
        mData.add(new NewsItem("My first trip to Thailand story ","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","6 july 1994",R.drawable.uservoyager));
        mData.add(new NewsItem("After Facebook Messenger, Viber now gets...","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,","6 july 1994",R.drawable.useillust));
        mData.add(new NewsItem("Isometric Design Grid Concept","lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit","6 july 1994",R.drawable.circul6));
        mData.add(new NewsItem("Android R Design Concept 4K","lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit ","6 july 1994",R.drawable.user));*/



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_AWAITING_ORDERs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject product = array.getJSONObject(i);
                        mOrders.add(new Orders(
                                product.getInt("id"),
                                product.getInt("bill_no"),
                                product.getString("customer_address"),
                                product.getString("customer_phone"),
                                product.getString("date")
                        ));
                    }
                    hAdapter = new HomeAdapter(getContext(),mData,isDark);
                    recyclerView.setAdapter(hAdapter);
                    fabSwitcher.setOnClickListener(v -> {
                        isDark = !isDark ;
                        if (isDark) {

                            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                            searchInput.setBackgroundResource(R.drawable.search_input_dark_style);

                        }
                        else {
                            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            searchInput.setBackgroundResource(R.drawable.search_input_style);
                        }

                        hAdapter = new HomeAdapter(getContext(),mData,isDark);
                        if (!search.toString().isEmpty()){

                            hAdapter.getFilter().filter(search);

                        }
                        recyclerView.setAdapter(hAdapter);
                        saveThemeStatePref(isDark);
                    });



                    searchInput.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            hAdapter.getFilter().filter(s);
                            search = s;
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {super.onDetach();}

    private void saveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark",isDark);
        editor.commit();
    }

    private boolean getThemeStatePref () {

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark",false) ;
        return isDark;

    }

    private void loadOrders(){

    }
}
