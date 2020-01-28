package ftstp.drinkchampaign.ui.deal_locator;


import java.util.Comparator;

public class Store{
    private String storeName;
    private String dealName;
    private String storeAddress;
    private double storeDistance;
    private float storeRating;
    private double storeCost;
    //add list of liquors to each store. Maybe with price for sorting

    public Store(String name, String deal, String address, double distance, float rating, double cost){
        storeName=name;
        dealName=deal;
        storeAddress=address;
        storeDistance=distance;
        storeRating=rating;
        storeCost=cost;
    }

    public String getName() {
        return storeName;
    }
    public String getDeal() {
        return dealName;
    }
    public String getAddress() {
        return storeAddress;
    }
    public double getDistance() {
        return storeDistance;
    }
    public float getRating() {
        return storeRating;
    }
    public double getCost() {
        return storeCost;
    }

    static Comparator<Store> DistanceComp(){
        return new Comparator<Store>() {
            @Override
            public int compare(Store store, Store store2) {
                if (store.getDistance() - store2.getDistance() >= 0) {
                    return 1;
                }
                return -1;
            }
        };
    }

    static Comparator<Store> RatingComp(){
        return new Comparator<Store>() {
            @Override
            public int compare(Store store, Store store2) {
                if (store.getRating() - store2.getRating() >= 0) {
                    return -1;
                }
                return 1;
            }
        };
    }

    static Comparator<Store> CostComp(){
        return new Comparator<Store>() {
            @Override
            public int compare(Store store, Store store2) {
                if (store.getCost() - store2.getCost() >= 0) {
                    return 1;
                }
                return -1;
            }
        };
    }
}


