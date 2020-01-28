package ftstp.drinkchampaign.ui.drink_tracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ftstp.drinkchampaign.MyInfoActivity;
import ftstp.drinkchampaign.R;

import static android.content.Context.MODE_PRIVATE;

public class DrinkTrackerFragment extends Fragment {

    private DrinkTrackerViewModel drinkTrackerViewModel;
    private TextView drink_counter;
    private TextView bac_counter;
    private ImageButton add_button;
    private Button remove_button;

    ArrayList<Drink> drinks = new ArrayList<>();

    int counter = 0;
    double bac = 0.00;
    double alcohol_consumed = 0.0;

    public String USER_WEIGHT = "get_weight";
    public String USER_R = "get_r_value";
    public String USER_INFO = "get_info";
    public String USER_UNITS = "get_units";
    public String DRINK_TIMES = "get_drink_list";
    SharedPreferences userPrefs;

    public View root;

    double weight;
    double r;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        drinkTrackerViewModel =
                ViewModelProviders.of(this).get(DrinkTrackerViewModel.class);
        root = inflater.inflate(R.layout.fragment_drink_tracker, container, false);
        //get shared preferences for calculations
        userPrefs = getContext().getSharedPreferences(USER_INFO, MODE_PRIVATE);
        //get units of weight for conversion
        int unitPosition = userPrefs.getInt(USER_UNITS,-1);
        //get weight for bac calculation
        weight = userPrefs.getFloat(USER_WEIGHT,0f);
        //convert weight based on units, assume lbs if no input
        if(unitPosition == 1){
            weight *= 1000;
        } else {
            weight *= 454;
        }
        //get r value for bac calculation
        r = userPrefs.getFloat(USER_R,0f);

        //get drink times from saved state
        Set<String> drinkList = userPrefs.getStringSet(DRINK_TIMES,null);
        //add new drink with time listed for each in set
        if(drinkList != null){
            //loop through set
            for(String str: drinkList){
                //create new drink
                Drink oldDrink = new Drink();
                //set time to old drink time
                oldDrink.setTime(str);
                //add to drink list
                drinks.add(oldDrink);
            }
        }
        //update alcohol consumed and number of drinks
        updateBACVariables();
        //get and set number of drinks info
        drink_counter = root.findViewById(R.id.drink_counter);
        drink_counter.setText(String.format("%d", counter));
        //get and set bac tracking info
        bac_counter = root.findViewById(R.id.bac_num);
        //if weight and r are equal to zero
        if(weight == 0.0 || r == 0.0){
            //set bac text view invisible
            bac_counter.setVisibility(View.GONE);
            //get info text view and set visible
            TextView infoText = root.findViewById(R.id.input_notif);
            infoText.setVisibility(View.VISIBLE);
            //get info button
            Button goInfoButton = root.findViewById(R.id.go_to_info_button);
            goInfoButton.setVisibility(View.VISIBLE);
            //set up button to take user to my info page
            goInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), MyInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else {
            bac = (alcohol_consumed / (weight * r));
        }
        //fill bac
        bac_counter.setText(String.format("%.3f", bac));

        final ImageView cup_image = root.findViewById(R.id.cup_image);
        final TextView x_view = root.findViewById(R.id.x_view);
        final LinearLayout bac_layout = (LinearLayout) root.findViewById(R.id.bac_box);

        //get the button to add a new drink
        add_button = root.findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //add a new drink
                drinks.add(new Drink());
                //update alcohol consumed and number of drinks
                updateBACVariables();
                //display new number of drinks
                drink_counter.setText(String.valueOf(counter));
                if(weight != 0.0) {
                    //calculate bac
                    bac = (alcohol_consumed / (weight * r));
                    //set colors based on bac
                    int color = ResourcesCompat.getColor(getResources(), R.color.colorGreen, null);
                    if (bac >= 0.080) {
                        //set text color to red
                        color = ResourcesCompat.getColor(getResources(), R.color.colorRed, null);
                        //set remove button color neutral, otherwise it's too much red
                        remove_button.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorText2, null));
                    } else if (bac >= 0.055) {
                        //set text color to orange
                        color = ResourcesCompat.getColor(getResources(), R.color.colorOrange, null);
                        //set remove button color to red
                        remove_button.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorRed, null));
                    } else {
                        //set remove button color to red
                        remove_button.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorRed, null));
                    }
                    cup_image.setColorFilter(color);
                    x_view.setTextColor(color);
                    drink_counter.setTextColor(color);
                    add_button.setColorFilter(color);
                    bac_layout.setBackgroundColor(color);
                    //display new bac
                    bac_counter.setText(String.format("%.3f", bac));
                }
                // Code here executes on main thread after user presses button
            }
        });
        //get the button to remove the last drink added
        remove_button = root.findViewById(R.id.remove_button);
        remove_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(drinks.size() > 0) {
                    //remove last drink
                    drinks.remove(counter - 1);
                    //update alcohol consumed and number of drinks
                    updateBACVariables();
                    //display new number of drinks
                    drink_counter.setText(String.valueOf(counter));
                    if (weight != 0.0) {
                        //calculate bac
                        bac = (alcohol_consumed / (weight * r));
                        //set colors based on bac
                        int color = ResourcesCompat.getColor(getResources(), R.color.colorGreen, null);
                        if (bac >= 0.080) {
                            //set text color to red
                            color = ResourcesCompat.getColor(getResources(), R.color.colorRed, null);
                            //set remove button color to a neutral color, otherwise it's too much red
                            remove_button.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorText2, null));
                        } else if (bac >= 0.055) {
                            //set text color to orange
                            color = ResourcesCompat.getColor(getResources(), R.color.colorOrange, null);
                            //set remove button color to red
                            remove_button.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorRed, null));
                        } else {
                            //set remove button color to red
                            remove_button.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorRed, null));
                        }
                        cup_image.setColorFilter(color);
                        x_view.setTextColor(color);
                        drink_counter.setTextColor(color);
                        add_button.setColorFilter(color);
                        bac_layout.setBackgroundColor(color);
                        //display new bac
                        bac_counter.setText(String.format("%.3f", bac));
                    }
                }
            }
        });

        return root;
    }

    protected void updateBACVariables(){
        //update alcohol consumed
        alcohol_consumed = 0.0;
        for(int i = 0; i < drinks.size(); i++){
            //get the drink at the current position
            Drink d = drinks.get(i);
            boolean downtime = (i == 0);
            //if there is any alcohol that has not been diffused
            double curGrams = d.getGramsAlcohol(downtime);
            if(curGrams >= 0.0){
                //add to amount consumed
                alcohol_consumed += curGrams;
            } else {
                //otherwise remove the drink
                drinks.remove(i);
                i--;
            }
        }
        //update number of drinks
        counter = drinks.size();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //create a new set to hold times of all drinks
        Set<String> drinkTimes = new HashSet<>();
        //loop through the drinks
        for(int i = 0; i < drinks.size(); i++){
            Drink d = drinks.get(i);
            boolean downtime = (i == 0);
            //if there is any alcohol that has not been diffused
            if(d.getGramsAlcohol(downtime) > 0.0) {
                //add the time to the set
                drinkTimes.add(d.getTimeString());
            }
        }
        //get the editor
        SharedPreferences.Editor editor = userPrefs.edit();
        //add the new set to the preferences
        editor.putStringSet(DRINK_TIMES,drinkTimes);
        //apply any changes
        editor.apply();

        super.onSaveInstanceState(outState);
    }
}