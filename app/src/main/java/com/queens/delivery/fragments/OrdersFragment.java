package com.queens.delivery.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;


import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import com.queens.delivery.R;
import com.queens.delivery.adapters.HomeAdapter;
import com.queens.delivery.models.Orders;
import com.queens.delivery.adapters.OrdersAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private OrdersAdapter eAdapter;
    private List<Orders> mOrders;
    private FloatingActionButton fabSwitcher;
    private boolean isDark = false;
    private ConstraintLayout rootLayout;
    private EditText searchInput;
    private CharSequence search="";
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipe;
    private int delv_id, order_id;
    SharedPreferences pref;

    private static final String URL_RIDERS_ORDERS = "https://delivery.queensclassycollections.com/api/member/riders_orders.php?rider_id=2";

    public OrdersFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("ORDERS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        fabSwitcher = view.findViewById(R.id.fab_switcher);
        rootLayout = view.findViewById(R.id.root_layout);
        searchInput = view.findViewById(R.id.search_input);
        recyclerView = view.findViewById(R.id.news_rv);
        progressBar = view.findViewById(R.id.progressBar);
        swipe= view.findViewById(R.id.swipeContainer);
        mOrders = new ArrayList<>();
        pref = getActivity().getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);

        swipe.setOnRefreshListener(this);
        swipe.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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







        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadOrders();
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

            eAdapter = new OrdersAdapter(getContext(),mOrders,isDark);
            if (!search.toString().isEmpty()){

                eAdapter.getFilter().filter(search);

            }
            recyclerView.setAdapter(eAdapter);
            saveThemeStatePref(isDark);
        });



        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                eAdapter.getFilter().filter(s);
                search = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    public void onRefresh(){
        eAdapter.clearAll();
        loadOrders();
        swipe.setRefreshing(false);
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

    private void loadOrders(){

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_RIDERS_ORDERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject product = array.getJSONObject(i);
                        mOrders.add(new Orders(
                                product.getInt("id"),
                                product.getInt("bill_id"),
                                product.getString("bill_no"),
                                product.getString("customer_address"),
                                product.getString("customer_phone"),
                                product.getString("date")
                        ));
                    }
                    eAdapter = new OrdersAdapter(getContext(),mOrders,isDark);
                    eAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            delv_id = mOrders.get(position).getId();
                            order_id = mOrders.get(position).getBillId();
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("delv_id", delv_id);
                            editor.putInt("order_id", order_id);
                            editor.commit();

                            Fragment fragment = new SetAwaitFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //Snackbar.make(getView(),delv_id+" "+order_id,Snackbar.LENGTH_LONG).show();
                        }
                    });
                    recyclerView.setAdapter(eAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView(), "Error Loading",Snackbar.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}
