package ftstp.drinkchampaign.ui.deal_locator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ftstp.drinkchampaign.R;

public class DealLocatorAdaptor extends RecyclerView.Adapter<DealLocatorAdaptor.DealLocatorViewHolder> {
    private List<Store> mStore;

    public DealLocatorAdaptor(List<Store> stores){
        mStore=stores;
    }

    @NonNull
    @Override
    public DealLocatorAdaptor.DealLocatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //get the context and make an inflater
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //make the view
        View storeView = inflater.inflate(R.layout.store_layout,parent,false);
        //create and return the viewholder
        DealLocatorViewHolder viewHolder = new DealLocatorViewHolder(storeView);
        return viewHolder;
    }

    /**
     * Sets all necessary components for each item in the layout
     * @param holder - holds the currently visible items in the recyclerview
     * @param position - states the place in the holder this should be getting the info from
     */
    @Override
    public void onBindViewHolder(@NonNull DealLocatorAdaptor.DealLocatorViewHolder holder, int position) {
        //get the current store
        Store thisStore = mStore.get(position);
        //set the text for the store name
        TextView storeName = holder.getNameT();
        storeName.setText(thisStore.getName());
        //set the text for the deal name
        TextView dealName = holder.getDealT();
        dealName.setText(thisStore.getDeal());
        //set the text for the address
        TextView addressName = holder.getAddressT();
        addressName.setText(thisStore.getAddress());
        //set the text for the distance
        TextView storeDistance = holder.getDistanceT();
        storeDistance.setText(String.valueOf(thisStore.getDistance())+"mi");
        //set the amount of stars filled in for the rating
        RatingBar storeRating = holder.getRatingT();
        storeRating.setRating(thisStore.getRating());
        //set the text for the deal cost
        TextView storeCost = holder.getCostT();
        storeCost.setText("$"+String.valueOf(thisStore.getCost()));
    }

    /**
     * Get number of items in the adapter
     * @return - int number of stores
     */
    @Override
    public int getItemCount() {
        return mStore.size();
    }

    /**
     * Get the type of layout used by the item in the given position
     * @param position - place in the holder to look at
     * @return - int layout used
     */
    @Override
    public int getItemViewType(int position) {
        return R.layout.store_layout;
    }

    /**
     * Replaces and updates data set
     * @param curStores - data to replace current arraylist of stores
     */
    public void changeOptions(ArrayList<Store> curStores){
        //replace the data
        mStore = curStores;
        //update the viewholder
        notifyDataSetChanged();
    }

    /**
     * Allows other classes to pass in a string to sort all options and update data set
     *
     * @param sortSelection - the string defining which way to sort the options
     */
    public void sortOptions(String sortSelection){
        //check the sort type
        if(sortSelection.equals("Distance")) {
            //sort by nearest
            Collections.sort(mStore, Store.DistanceComp());
        } else if(sortSelection.equals("Popularity")){
            //sort by most popular
            Collections.sort(mStore, Store.RatingComp());
        } else if(sortSelection.equals("Price")){
            //sort by cheapest
            Collections.sort(mStore, Store.CostComp());
        } else {
            return;
        }
        //update viewholder
        notifyDataSetChanged();
    }

    /**
     * Collects and returns all store names of stores in adapter as Strings
     * Used in onSaveInstanceState so we don't need to use a Parceable
     * @return - ArrayList of all store names as Strings
     */
    public ArrayList<String> getStoreNames(){
        ArrayList<String> list = new ArrayList<>();
        for(Store store: mStore){
            list.add(store.getName());
        }
        return list;
    }

    /**
     * Class to define what goes into the view for each option in the adapter
     */
    public class DealLocatorViewHolder extends RecyclerView.ViewHolder {
        /**
         * Items in the store_layout
         */
        private TextView storeNameTextView;
        private TextView dealNameTextView;
        private TextView storeAddressTextView;
        private TextView storeDistanceTextView;
        private RatingBar storeRatingRatingBar;
        private TextView storeCostTextView;

        /**
         * Constructor
         * @param storeView - the view to populate
         */
        public DealLocatorViewHolder(View storeView){
            super(storeView);
            storeNameTextView = (TextView)storeView.findViewById(R.id.store_name);
            dealNameTextView = (TextView)storeView.findViewById(R.id.deal_name);
            storeAddressTextView = (TextView)storeView.findViewById(R.id.address);
            storeDistanceTextView = (TextView)storeView.findViewById(R.id.distance);
            storeRatingRatingBar = (RatingBar)storeView.findViewById(R.id.popularity_bar);
            storeCostTextView = (TextView)storeView.findViewById(R.id.price);
        }

        /**
         * Getters for each item in the layout
         * @return
         */
        public TextView getNameT() {return storeNameTextView;}
        public TextView getDealT() {return dealNameTextView;}
        public TextView getAddressT() {return storeAddressTextView;}
        public TextView getDistanceT() {return storeDistanceTextView;}
        public RatingBar getRatingT() {return storeRatingRatingBar;}
        public TextView getCostT() {return storeCostTextView;}
    }
}
