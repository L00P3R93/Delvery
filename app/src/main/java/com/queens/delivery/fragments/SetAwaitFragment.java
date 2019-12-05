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
import android.widget.TextView;
import android.widget.ProgressBar;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

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
import com.queens.delivery.adapters.SetAwaitAdapter;
import com.queens.delivery.models.NewsItem;
import com.queens.delivery.models.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

import com.queens.delivery.R;
import com.queens.delivery.models.Products;


public class SetAwaitFragment extends Fragment {

    private RecyclerView recyclerView;
    private SetAwaitAdapter setAwaitAdapter;
    private List<Products> mProducts;
    private FloatingActionButton fabSwitcher;
    private boolean isDark = false;
    private ConstraintLayout rootLayout;

    private CharSequence search="";
    private ProgressBar progressBar;
    private int delv_id, order_id;
    SharedPreferences pref;

    private String URL_AWAITING_PRODUCTS = "";



    public SetAwaitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("PRODUCTS");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_await, container, false);
        fabSwitcher = view.findViewById(R.id.fab_switcher);
        rootLayout = view.findViewById(R.id.root_layout);

        recyclerView = view.findViewById(R.id.products_rv);
        progressBar = view.findViewById(R.id.progressBar);
        pref = getActivity().getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        mProducts = new ArrayList<>();
        delv_id = pref.getInt("delv_id",0);
        order_id = pref.getInt("order_id", 0);

        // load theme state
        isDark = getThemeStatePref();
        if(isDark) {
            // dark theme is on
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else{
            // light theme is on
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadProducts();

        fabSwitcher.setOnClickListener(v -> {
            isDark = !isDark ;
            if (isDark) {

                rootLayout.setBackgroundColor(getResources().getColor(R.color.black));


            }
            else {
                rootLayout.setBackgroundColor(getResources().getColor(R.color.white));

            }

            setAwaitAdapter = new SetAwaitAdapter(getContext(), mProducts,isDark);

            recyclerView.setAdapter(setAwaitAdapter);
            saveThemeStatePref(isDark);
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {super.onDetach();}

    private void saveThemeStatePref(boolean isDark) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark",isDark);
        editor.commit();
    }

    private boolean getThemeStatePref () {
        boolean isDark = pref.getBoolean("isDark",false) ;
        return isDark;

    }

    private void loadProducts(){
        URL_AWAITING_PRODUCTS = "https://delivery.queensclassycollections.com/api/member/set_await.php?order_id="+order_id;
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_AWAITING_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                         JSONObject products = array.getJSONObject(i);
                         mProducts.add(new Products(
                                 products.getInt("pid"),
                                 products.getString("name"),
                                 products.getString("image"),
                                 products.getString("quantity"),
                                 products.getString("price"),
                                 products.getString("sub_t")
                         ));
                    }
                    setAwaitAdapter = new SetAwaitAdapter(getContext(), mProducts,isDark);
                    recyclerView.setAdapter(setAwaitAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView(),"Error Loading Data",Snackbar.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

}
