package ftstp.drinkchampaign.ui.faq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ftstp.drinkchampaign.R;

public class FaqFragment extends Fragment {

    private FaqViewModel faqViewModel;

    public View root;
    public LinearLayout section1;
    public LinearLayout section2;
    public LinearLayout section3;
    public LinearLayout section4;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //assign viewmodel
        faqViewModel = ViewModelProviders.of(this).get(FaqViewModel.class);
        //inflate layout
        root = inflater.inflate(R.layout.fragment_faq, container, false);
        //assign and set listener for question section 1
        section1 = (LinearLayout) root.findViewById(R.id.section1);
        section1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the content for the current section
                LinearLayout layout = (LinearLayout) root.findViewById(R.id.section1_content);
                //get the image for the current section
                ImageView b1 = (ImageView) root.findViewById(R.id.button_section1);
                //change the image, visibility of content, and background color appropriately
                if(layout.getVisibility() == View.GONE) {
                    b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.cup_icon));
                    layout.setVisibility(View.VISIBLE);
                    section1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    layout.setVisibility(View.GONE);
                    b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.spilled_cup_icon));
                    section1.setBackgroundColor(0x00000000);
                }
            }
        });
        //assign and set listener for question section 2
        section2 = (LinearLayout) root.findViewById(R.id.section2);
        section2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the content for the current section
                LinearLayout layout = (LinearLayout) root.findViewById(R.id.section2_content);
                //get the image for the current section
                ImageView b2 = (ImageView) root.findViewById(R.id.button_section2);
                //change the image, visibility of content, and background color appropriately
                if(layout.getVisibility() == View.GONE) {
                    b2.setBackgroundDrawable(getResources().getDrawable(R.drawable.cup_icon));
                    layout.setVisibility(View.VISIBLE);
                    section2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    layout.setVisibility(View.GONE);
                    b2.setBackgroundDrawable(getResources().getDrawable(R.drawable.spilled_cup_icon));
                    section2.setBackgroundColor(0x00000000);
                }
            }
        });
        //assign and set listener for question section 3
        section3 = (LinearLayout) root.findViewById(R.id.section3);
        section3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the content for the current section
                TextView layout = (TextView) root.findViewById(R.id.section3_content);
                //get the image for the current section
                ImageView b3 = (ImageView) root.findViewById(R.id.button_section3);
                //change the image, visibility of content, and background color appropriately
                if(layout.getVisibility() == View.GONE) {
                    b3.setBackgroundDrawable(getResources().getDrawable(R.drawable.cup_icon));
                    layout.setVisibility(View.VISIBLE);
                    section3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    layout.setVisibility(View.GONE);
                    b3.setBackgroundDrawable(getResources().getDrawable(R.drawable.spilled_cup_icon));
                    section3.setBackgroundColor(0x00000000);
                }
            }
        });
        //assign and set listener for question section 4
        section4 = (LinearLayout) root.findViewById(R.id.section4);
        section4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the content for the current section
                TextView layout = (TextView) root.findViewById(R.id.section4_content);
                //get the image for the current section
                ImageView b4 = (ImageView) root.findViewById(R.id.button_section4);
                //change the image, visibility of content, and background color appropriately
                if(layout.getVisibility() == View.GONE) {
                    b4.setBackgroundDrawable(getResources().getDrawable(R.drawable.cup_icon));
                    layout.setVisibility(View.VISIBLE);
                    section4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    layout.setVisibility(View.GONE);
                    b4.setBackgroundDrawable(getResources().getDrawable(R.drawable.spilled_cup_icon));
                    section4.setBackgroundColor(0x00000000);
                }
            }
        });
        //return the inflated layout
        return root;
    }
}