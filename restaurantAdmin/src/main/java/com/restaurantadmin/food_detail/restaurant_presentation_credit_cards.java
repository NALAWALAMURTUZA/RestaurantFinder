package com.restaurantadmin.food_detail;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.restaurantadmin.Global.Global_variable;
import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class restaurant_presentation_credit_cards extends BaseAdapter
{
	private Activity _activity;
    private JSONArray _array_creditcard;
    private static LayoutInflater inflater=null;
    public static List<String>  selected_paymentmethod; 
     
    
    public restaurant_presentation_credit_cards(Activity activity,JSONArray array_creditcard) throws JSONException 
	{
		// TODO Auto-generated constructor stub
		this._activity=activity;
		this._array_creditcard=array_creditcard;
		try {
			System.out.println("selected_paymentmethod"+Global_variable.logindata.getJSONObject("restaurant_details").getString("accepted_credit_cards").toString());		
		    selected_paymentmethod =  new ArrayList<String>(Arrays.asList(Global_variable.logindata.getJSONObject("restaurant_details").getString("accepted_credit_cards").split("\\s*,\\s*")));
			System.out.println("animalsArray"+selected_paymentmethod.size());
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
	}
    
	public int getCount() {
		// TODO Auto-generated method stub
		return this._array_creditcard.length();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}


	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		
		String name_en=null;
		try {
			name_en = _array_creditcard.getJSONObject(position).getString("credit_cards_name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 View vi=convertView;
	        if(convertView==null)
	        {
	            vi = inflater.inflate(R.layout.activity_rowfile_service, null);
	        }
	        TextView tv_services_name = (TextView)vi.findViewById(R.id.rf_admin_tv_services_name);
	        CheckBox cb_services = (CheckBox)vi.findViewById(R.id.rf_admin_services_checkmark);
	        cb_services.setChecked(false);
	        
			System.out.println("selected_paymentmethod"+selected_paymentmethod);
			System.out.println("selected_paymentmethod_size"+selected_paymentmethod.size());
			tv_services_name.setText(name_en);
		    for(int i=0;i<selected_paymentmethod.size();i++)
		    {
		    	if(selected_paymentmethod.get(i).toString().equalsIgnoreCase(tv_services_name.getText().toString()))
				{
						cb_services.setChecked(true);
				
						System.out.println("!!!!murtu"+i+"=="+"position"+position+"--"+tv_services_name.getText().toString()+"===="+selected_paymentmethod.get(i).toString());
				}
			}
			
	       
	        cb_services.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (((CheckBox) v).isChecked())
					{
						try {
							System.out.println("Pozition_true"+_array_creditcard.getJSONObject(position).getString("credit_cards_name"));
							selected_paymentmethod.add(_array_creditcard.getJSONObject(position).getString("credit_cards_name"));
							System.out.println("selected_paymentmethod"+selected_paymentmethod);
							selected_paymentmethod=removeDuplicates((ArrayList<String>) selected_paymentmethod);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					else
					{
						try {
							System.out.println("Pozition_false"+_array_creditcard.getJSONObject(position).getString("credit_cards_name"));
							selected_paymentmethod.remove(_array_creditcard.getJSONObject(position).getString("credit_cards_name"));
							System.out.println("selected_paymentmethod"+selected_paymentmethod);
							selected_paymentmethod=removeDuplicates((ArrayList<String>) selected_paymentmethod);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			});
	        
	        return vi;
	
	}
	static ArrayList<String> removeDuplicates(ArrayList<String> list) {

		// Store unique items in result.
		ArrayList<String> result = new ArrayList<String>();

		// Record encountered Strings in HashSet.
		HashSet<String> set = new HashSet<String>();

		// Loop over argument list.
		for (String item : list) {

		    // If String is not in set, add it to the list and the set.
		    if (!set.contains(item)) {
			result.add(item);
			set.add(item);
		    }
		}
		return result;
	    }

}

