package com.example.cookmaster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.cookmaster.classes.ApiRequest;

import java.util.Arrays;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ShopFragment extends Fragment {
    private Button testBtn;
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
        testBtn = view.findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiRequest = new ApiRequest();
                apiRequest.getUser("d");
                apiRequest.getProducts();
            }
        });


        return view;
    }

}
