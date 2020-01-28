package ftstp.drinkchampaign;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the base view
        setContentView(R.layout.activity_main);
        //get a controller for the bottom navigation stuff
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //adjusts the location of the bottom navigation for different orientations
        if(getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.nav_view), navController);
        } else if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE){
            NavigationUI.setupWithNavController((NavigationView) findViewById(R.id.nav_view2), navController);
        }
        getSupportActionBar().setTitle("");
    }

    /**
     * Adds the my info button to the app bar
     * @param menu
     * @return - true if successful
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        //make a menu inflater
        MenuInflater inflater = getMenuInflater();
        //inflate it if it's present
        inflater.inflate(R.menu.my_info_menu, menu);
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
            case R.id.action_my_info:
                //start the my info activity
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), MyInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            //otherwise... do something else?
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
