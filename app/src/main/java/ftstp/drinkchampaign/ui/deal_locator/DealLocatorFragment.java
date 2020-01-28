package ftstp.drinkchampaign.ui.deal_locator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ftstp.drinkchampaign.R;

public class DealLocatorFragment extends Fragment {
    //key for saved instance state
    String CUR_STORES = "save_these_stores";
    String SPIN_SORT = "sort_with_this";
    //full list of stores
    final ArrayList<Store> fullStoreList = new ArrayList<Store>();
    //stores in full list
    Store campusPantry = new Store("Campus Pantry","Natty Lite 12 pack","112 E Green St Suite B",1.2,4.6f,19.99);
    Store hollywoodLiquors = new Store("Hollywood Liquors","Red Stripe 6 pack","512 S Neil St A",2.7,4.2f,8.99);
    Store piccadillyLiquors = new Store("Piccadilly Liquors","Burnett's Vodka 1.75L","601 S !st St #101",2.1,4.3f,15.99);
    Store illiniPantry = new Store("Illini Pantry","Tito's Vodka 750ml","606 S 6th St",0.2,4.2f,20.99);
    Store bSpiritLLC = new Store("B Spirits LLC","Busch Lite 20 pack","306 W Main St",4.5,4.2f,24.99);
    Store shinersMoonshine = new Store("Shiners Moonshine","Dumplin Creek Moonshine","809 S Neil St",3.3,5.0f,11.99);
    Store friarTuck = new Store("Friar Tuck Beverage","Skiren Whiskey","1333 Savoy Plaze Ln",8.7,4.8f,79.99);
    Store universityFood = new Store("University Food and Liquor","Jack Daniel's Old No 7","211 W University Ave",3.6,3.6f,19.99);
    Store redLion = new Store("The Red Lion","Jagerbomb","211 E Green St",0.8,3.4f,4);
    Store joesBrewery = new Store("Joe's Brewery","Lunch Box","706 S 5th St",0.6,4.1f,5);
    Store illiniInn = new Store("Illini Inn","Tequila Sunrise","901 S 4th St",1.1,4.3f,4);
    Store brothersBar = new Store("Brothers Bar & Grill","Long Island Ice Tea","613 E Green St",0.4,4.0f,9);
    Store theHub = new Store("The Hub","Vodka Lemonade","601 S 1st #102",1.4,3.3f,3);
    //view model for fragment
    private DealLocatorViewModel dealLocatorViewModel;
    //adapter for recyclerview
    final DealLocatorAdaptor storeAdaptor = new DealLocatorAdaptor(fullStoreList);
    public Spinner sortSpinner;
    public RecyclerView recyclerStores;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //List of all of our stores
        fullStoreList.add(campusPantry);
        fullStoreList.add(hollywoodLiquors);
        fullStoreList.add(piccadillyLiquors);
        fullStoreList.add(illiniPantry);
        fullStoreList.add(bSpiritLLC);
        fullStoreList.add(shinersMoonshine);
        fullStoreList.add(friarTuck);
        fullStoreList.add(universityFood);
        fullStoreList.add(redLion);
        fullStoreList.add(joesBrewery);
        fullStoreList.add(illiniInn);
        fullStoreList.add(brothersBar);
        fullStoreList.add(theHub);
        //if the user was already on this page
        if(savedInstanceState != null){
            //get the list of store names that the user had been looking at
            ArrayList<String> list = savedInstanceState.getStringArrayList(CUR_STORES);
            //temp arraylist to hold results
            ArrayList<Store> options = new ArrayList<>();
            //loop through all names
            for(String str: list){
                //loop through all stores
                for(Store store: fullStoreList){
                    //if the name is in both lists (which it should be) get the store from the full list
                    if(store.getName().toLowerCase().contains(str.toLowerCase())){
                        //add store to temp list
                        options.add(store);
                    }
                }
            }
            //update adapter options
            storeAdaptor.changeOptions(options);
            //get sort style and sort adapter options
            String sortme = savedInstanceState.getString(SPIN_SORT);
            storeAdaptor.sortOptions(sortme);
        } else {
            //otherwise populate with all deals
            storeAdaptor.changeOptions(fullStoreList);
        }

        dealLocatorViewModel = ViewModelProviders.of(this).get(DealLocatorViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_deal_locator, container, false);

        //Creates and populates recyclerView
        recyclerStores = (RecyclerView)root.findViewById(R.id.store_list);
        recyclerStores.setAdapter(storeAdaptor);
        recyclerStores.setLayoutManager(new LinearLayoutManager(getContext()));

        sortSpinner = (Spinner)root.findViewById(R.id.spinner);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sortSelection = sortSpinner.getSelectedItem().toString();
                storeAdaptor.sortOptions(sortSelection);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
                return;
            }
        });
        //search text holder
        final SearchView userInput = (SearchView)root.findViewById(R.id.searchBar);
        //response to change in search text
        userInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //temp arraylist to hold search results
                ArrayList<Store> matchStores = new ArrayList<Store>();
                //search through all available deals
                for(Store storeSearch: fullStoreList){
                    //if the store name or deal type match the input text
                    if(storeSearch.getName().toLowerCase().contains(s.toLowerCase())
                            || storeSearch.getDeal().toLowerCase().contains(s.toLowerCase())){
                        //add the matching store to the temp arraylist
                        matchStores.add(storeSearch);
                    }
                }
                //update the options in the adapter
                storeAdaptor.changeOptions(matchStores);
                //update the order of the options in the adapter
                String sortSelection = sortSpinner.getSelectedItem().toString();
                storeAdaptor.sortOptions(sortSelection);
                //when submitted search for store/liquor
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //if user removes all text
                if(s == null || s.equals("")){
                    //update the options in the adapter
                    storeAdaptor.changeOptions(fullStoreList);
                    //update the order of the options in the adapter
                    String sortSelection = sortSpinner.getSelectedItem().toString();
                    storeAdaptor.sortOptions(sortSelection);
                }
                return false;
            }
        });
        //get the name of the image in the search view
        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        //get the image in the search view
        ImageView magImage = (ImageView) userInput.findViewById(magId);
        //make it work like a button
        magImage.setClickable(true);
        //when the user clicks the search image, complete their query
        magImage.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //complete the query
                userInput.setQuery(userInput.getQuery(),true);
            }
        });

        //return the inflated layout
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //save the current stores that the user is looking at
        outState.putStringArrayList(CUR_STORES,storeAdaptor.getStoreNames());
        outState.putString(SPIN_SORT,sortSpinner.getSelectedItem().toString());

        super.onSaveInstanceState(outState);
    }
}