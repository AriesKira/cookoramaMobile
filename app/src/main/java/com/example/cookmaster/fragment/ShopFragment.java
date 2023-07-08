package com.example.cookmaster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cookmaster.classes.ApiRequest;
import com.example.cookmaster.classes.ProductAdapter;
import com.example.cookmaster.classes.ShopItem;
import com.example.cookmaster.interfaces.ProductsCallback;
import com.google.android.material.internal.TextWatcherAdapter;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;


public class ShopFragment extends Fragment {
    private LottieAnimationView loadingAnimation;
    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;
    private List<ShopItem> productsList = new ArrayList<>();


    private ApiRequest apiRequest = new ApiRequest();
    private Spinner generalFilters;
    private EditText searchBar;
    private TextView noItemsFoundText;
    private List<String> generalFiltersList = Arrays.asList("Newest", "Oldest", "Price ⬆️", "Price ⬇️︎");
    private ArrayAdapter<String> generalAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        this.generalFilters = view.findViewById(R.id.ShopGeneralFiltersSpinner);
        this.loadingAnimation = view.findViewById(R.id.animationView);
        this.searchBar = view.findViewById(R.id.shopSearchBar);
        this.noItemsFoundText = view.findViewById(R.id.noItemsFound);

        generalAdapter = new ArrayAdapter<>(getContext(), R.layout.shop_filter_spinners, generalFiltersList);
        generalAdapter.setDropDownViewResource(R.layout.shop_filter_spinners);
        generalFilters.setAdapter(generalAdapter);

        generalFilters.setSelection(0);

        productsRecyclerView = view.findViewById(R.id.shopRecyclerView);

        loadingAnimation.setVisibility(View.VISIBLE);

        apiRequest.getProducts(new ProductsCallback() {


            @Override
            public void onProductsReceived(List<ShopItem> products) {
                loadingAnimation.setVisibility(View.GONE);
                productsList.clear();
                productsList.addAll(products);
                productAdapter = new ProductAdapter(productsList);
                productsRecyclerView.setAdapter(productAdapter);

                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                productsRecyclerView.setLayoutManager(layoutManager);

            }

            @Override
            public void onProductsFailure(String errorMessage) {
                loadingAnimation.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Erreur : " + errorMessage, Toast.LENGTH_SHORT).show();
            }


        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Pas besoin de cette méthode pour votre cas
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    productsRecyclerView.setVisibility(View.VISIBLE);
                    apiRequest.getProducts(new ProductsCallback() {
                        @Override
                        public void onProductsReceived(List<ShopItem> products) {
                            loadingAnimation.setVisibility(View.GONE);
                            productsList.clear();
                            productsList.addAll(products);
                            productAdapter = new ProductAdapter(productsList);
                            productsRecyclerView.setAdapter(productAdapter);

                            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                            productsRecyclerView.setLayoutManager(layoutManager);

                        }

                        @Override
                        public void onProductsFailure(String errorMessage) {
                            loadingAnimation.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Erreur : " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // La barre de recherche contient du texte, effectuer la recherche normalement
                    apiRequest.searchProducts(s.toString(), new ProductsCallback() {
                        @Override
                        public void onProductsReceived(List<ShopItem> products) {
                            productsList.clear();
                            productsList.addAll(products);
                            productAdapter.notifyDataSetChanged();

                            if (productsList.isEmpty()) {
                                // Aucun résultat trouvé, afficher un message "Aucun résultat" et masquer la RecyclerView
                                noItemsFoundText.setVisibility(View.VISIBLE);
                                productsRecyclerView.setVisibility(View.GONE);
                            } else {
                                // Des résultats trouvés, afficher la RecyclerView normalement
                                noItemsFoundText.setVisibility(View.GONE);
                                productsRecyclerView.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onProductsFailure(String errorMessage) {
                            // Gérer les erreurs de recherche de produits
                            Toast.makeText(getContext(), "Erreur : " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Pas besoin de cette méthode pour votre cas
            }
        });



        return view;
    }


}
