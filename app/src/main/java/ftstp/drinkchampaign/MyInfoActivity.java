package ftstp.drinkchampaign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyInfoActivity extends AppCompatActivity {

    public String USER_WEIGHT = "get_weight";
    public String USER_R = "get_r_value";
    public String USER_RADIO_ID = "get_radio_button_id";
    public String USER_INFO = "get_info";
    public String USER_UNITS = "get_units";
    SharedPreferences userPrefs;
    SharedPreferences.Editor editor;
    private Button saveButton;

    EditText weightEdit;
    RadioGroup radioGroup;
    Spinner unitSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        //get user preferences and editor for changes
        userPrefs = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        editor = userPrefs.edit();

        double weight = userPrefs.getFloat(USER_WEIGHT,0f);
        int radioButtonId = userPrefs.getInt(USER_RADIO_ID,-1);
        int unitPosition = userPrefs.getInt(USER_UNITS,-1);


        weightEdit = (EditText) findViewById(R.id.weight_input);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        unitSelector = (Spinner) findViewById(R.id.unit_select);

        RadioButton radioButtonF = findViewById(R.id.radio_female);
        RadioButton radioButtonM = findViewById(R.id.radio_male);
        RadioButton radioButtonO = findViewById(R.id.radio_other);

        if(weight != 0.0){
            weightEdit.setText(String.format("%.2f", weight));
        }

        if(unitPosition != -1){
            unitSelector.setSelection(unitPosition);
        }

        if(radioButtonId == R.id.radio_female){
            radioButtonF.setChecked(true);
        } else if(radioButtonId == R.id.radio_male){
            radioButtonM.setChecked(true);
        } else if(radioButtonId != -1){
            radioButtonO.setChecked(true);
        }

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //bool to see if they are ready to go back to home
                boolean goHome = true;
                //if no input, notify they must input number
                if(weightEdit.getText() == null || weightEdit.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"You must complete all inputs", Toast.LENGTH_SHORT);
                    goHome = false;
                } else {
                    //get weight number input
                    double weightInput = Double.parseDouble(weightEdit.getText().toString());
                    //update shared weight preferences
                    editor.putFloat(USER_WEIGHT, (float)weightInput);

                    int units = unitSelector.getSelectedItemPosition();
                    editor.putInt(USER_UNITS,units);
                }
                //get sex input
                int checkedId = radioGroup.getCheckedRadioButtonId();
                //if no input, notify they must select option
                if(checkedId == -1){
                    Toast.makeText(getBaseContext(),"You must complete all inputs", Toast.LENGTH_SHORT);
                    goHome = false;
                } else {
                    //else continue
                    //update shared sex preferences with number based on selection
                    if (checkedId == R.id.radio_female || checkedId == R.id.radio_other) {
                        editor.putFloat(USER_R, 0.0055f /*weight_input*/);
                    } else {
                        editor.putFloat(USER_R, 0.0068f/*weight_input*/);
                    }

                    editor.putInt(USER_RADIO_ID,checkedId);
                }
                //apply any changes that were made
                editor.apply();
                if(goHome){
                    //start the main activity
                    Intent intent = new Intent();
                    intent.setClass(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }
    /**
     * Adds the home button to the app bar
     * @param menu
     * @return - true if successful
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        //make a menu inflater
        MenuInflater inflater = getMenuInflater();
        //inflate it if it's present
        inflater.inflate(R.menu.drink_tracker_menu, menu);
        getSupportActionBar().setTitle("");
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles the action to be performed if the user presses the my info button in the app bar
     * @param item - item selected (there is only the my info button
     * @return - true if successful
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //check which menu item was pressed
        switch (item.getItemId()) {
            //if it is the only one in the menu
            case R.id.action_home:
                //start the main activity
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            //otherwise... do something else?
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
