package com.rf_user.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.rf.restaurant_user.R;

public class ViewHolder {

	public TextView categories_address,size_of_food_textview,spicy_level_txv;
    public TextView categories_name,SR_txv,quantity_txv,parent_index;
    public ImageView imageview;
    public LinearLayout ly_quantity;
    public RatingBar categories_rate;
    public Spinner spicy_level_spinner;
    public Button plus_btn,minus_btn;
    /******* child ******/
    public TextView subcats_textview,size_of_subfood_textview,subspicy_level_textview
           ,sub_quantity_text,sub_sr_txv,sub_child_index;
    public Spinner subspicy_leval_spinner;
    public ImageView sub_photo_imageview;
    public Button sub_plus_button,sub_minus_button;
    public ViewHolder(View v) {
    	/******** Group *********/
        this.categories_name = (TextView)v.findViewById(R.id.txv_categories_name);
        this.quantity_txv = (TextView)v.findViewById(R.id.txv_quantity);
        this.ly_quantity = (LinearLayout)v.findViewById(R.id.ly_add_mins);
        this.categories_address = (TextView)v.findViewById(R.id.txv_categories_details);
        this.size_of_food_textview = (TextView)v.findViewById(R.id.txv_categories_size);
        this.spicy_level_txv = (TextView)v.findViewById(R.id.txv_categories_spicylevel);
        this.SR_txv = (TextView)v.findViewById(R.id.txv_sr);
        this.categories_rate = (RatingBar)v.findViewById(R.id.rate_categoriesratingbar);
        this.imageview = (ImageView)v.findViewById(R.id.img_categories_image);
        this.plus_btn = (Button)v.findViewById(R.id.btn_Add);
        this.minus_btn = (Button)v.findViewById(R.id.btn_minus);
        this.spicy_level_spinner = (Spinner)v.findViewById(R.id.spinner_spicy_level);
        this.parent_index = (TextView)v.findViewById(R.id.parent_index);
        /******** Child ************/
        
        this.sub_photo_imageview = (ImageView)v.findViewById(R.id.sub_photo_imageview);
        this.subcats_textview = (TextView)v.findViewById(R.id.subcats__name_textview);
        this.sub_sr_txv = (TextView)v.findViewById(R.id.sub_sr_txv);
        this.sub_quantity_text = (TextView)v.findViewById(R.id.sub_quantity_text);
        this.subspicy_level_textview = (TextView)v.findViewById(R.id.subspicy_level_textview);
        this.size_of_subfood_textview = (TextView)v.findViewById(R.id.size_of_subfood_textview);
        this.sub_plus_button = (Button)v.findViewById(R.id.sub_plus_button);
        this.sub_minus_button = (Button)v.findViewById(R.id.sub_minus_button);
        this.subspicy_leval_spinner = (Spinner)v.findViewById(R.id.subspicy_leval_spinner);
        this.sub_child_index = (TextView)v.findViewById(R.id.sub_child_index);
    }
}
