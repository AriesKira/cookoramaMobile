package com.example.cookmaster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cookmaster.classes.ApiRequest;
import com.example.cookmaster.classes.ProductAdapter;
import com.example.cookmaster.classes.ShopItem;
import com.example.cookmaster.interfaces.ProductsCallback;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;


public class ShopFragment extends Fragment {
    private Button testBtn;

    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;
    private List<ShopItem> productsList = new ArrayList<>();


    private ApiRequest apiRequest;
    private Spinner generalFilters;
    private Spinner itemsFilters;

    private List<String> generalFiltersList = Arrays.asList("Newest", "Oldest", "Price ⬆️", "Price ⬇️︎");
    private ArrayAdapter<String> generalAdapter;

    private List<String> itemsFiltersList = Arrays.asList("Kitchen Equipment", "Kitchens to Rent");
    private ArrayAdapter<String> itemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        generalFilters = view.findViewById(R.id.ShopGeneralFiltersSpinner);
        itemsFilters = view.findViewById(R.id.ShopItemsFiltersSpinner);

        generalAdapter = new ArrayAdapter<>(getContext(), R.layout.shop_filter_spinners, generalFiltersList);
        generalAdapter.setDropDownViewResource(R.layout.shop_filter_spinners);
        generalFilters.setAdapter(generalAdapter);

        itemsAdapter = new ArrayAdapter<>(getContext(), R.layout.shop_filter_spinners, itemsFiltersList);
        itemsAdapter.setDropDownViewResource(R.layout.shop_filter_spinners);
        itemsFilters.setAdapter(itemsAdapter);

        generalFilters.setSelection(0);
        itemsFilters.setSelection(0);

        productsRecyclerView = view.findViewById(R.id.shopRecyclerView);

        apiRequest = new ApiRequest();
        apiRequest.getProducts(new ProductsCallback() {


            @Override
            public void onProductsReceived(List<ShopItem> products) {
                productsList.clear();
                productsList.addAll(products);
                productAdapter = new ProductAdapter(productsList);
                productsRecyclerView.setAdapter(productAdapter);

                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                productsRecyclerView.setLayoutManager(layoutManager);

            }

            @Override
            public void onProductsFailure(String errorMessage) {
                Toast.makeText(getContext(), "Erreur : " + errorMessage, Toast.LENGTH_SHORT).show();
            }


        });

        return view;
    }


}
