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
import org.json.JSONObject;

public class restaurant_presentation_services extends BaseAdapter
{
	private Activity _activity;
    private JSONArray _array_services;
    private static LayoutInflater inflater=null;
    public static  JSONObject obj_services = new JSONObject() ;
    public static  JSONArray array_local_services = new JSONArray() ;
    public restaurant_presentation_services(Activity activity,JSONArray array_services) throws JSONException 
	{
		// TODO Auto-generated constructor stub
		this._activity=activity;
		this._array_services=array_services;
		 inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 for(int i=0;i<Global_variable.logindata.getJSONArray("services").length();i++)
		 {
			 obj_services.put(Global_variable.logindata.getJSONArray("services").getJSONObject(i).getString("service_id").toString(), Global_variable.logindata.getJSONArray("services").getJSONObject(i).getString("service_name").toString());
		 }
		 System.out.println("obj_services_main"+obj_services);
		 this.array_local_services=Global_variable.logindata.getJSONArray("services");
		 System.out.println("obj_services_main_array"+array_local_services);
		 
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return this._array_services.length();
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
		String service_id=null;
		
		try {
			name_en = _array_services.getJSONObject(position).getString("service_name");
			service_id = _array_services.getJSONObject(position).getString("service_id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 View vi=convertView;
	        if(convertView==null)
	        {
	            vi = inflater.inflate(R.layout.activity_rowfile_service, null);
	        }
	        final TextView tv_services_name = (TextView)vi.findViewById(R.id.rf_admin_tv_services_name);
	        final TextView tv_service_id = (TextView)vi.findViewById(R.id.rf_admin_service_id);
	        final CheckBox cb_services = (CheckBox)vi.findViewById(R.id.rf_admin_services_checkmark);
	        cb_services.setChecked(false);
	     
	        try {
	        	
				for(int i=0;i<array_local_services.length();i++)
				{
					if(array_local_services.getJSONObject(i).getString("service_id").toString().equalsIgnoreCase(service_id))
				    {
						System.out.println("hi_murtuza"+array_local_services);
						cb_services.setChecked(true);
				    }
					
				}
				
					System.out.println("!!!!local"+array_local_services);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        tv_services_name.setText(name_en);
	        tv_service_id.setText(service_id);
	        
	        
	        cb_services.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (((CheckBox) v).isChecked())
					{
						try {
							JSONObject j = new JSONObject();
							j.put("service_id", tv_service_id.getText().toString());
						    j.put("service_name", tv_services_name.getText().toString());
						    System.out.println("!!!!pankaj_json_object"+j);
						    array_local_services.put(j);
						    obj_services.put(tv_service_id.getText().toString(), tv_services_name.getText().toString());
							System.out.println("obj_services_main"+obj_services);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					else
					{
						try {
							
						    for(int i=0;i<array_local_services.length();i++)
							 {
						    	if(array_local_services.getJSONObject(i).getString("service_id").toString().equalsIgnoreCase(tv_service_id.getText().toString()))
								 {
						    		array_local_services =  RemoveJSONArray(array_local_services,i);
						    	     obj_services.remove(tv_service_id.getText().toString());
									 System.out.println("obj_services_main"+obj_services);
								 }
							 }

						    
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
	        
	        
	        return vi;
	
	}

	public static JSONArray RemoveJSONArray(JSONArray jarray, int pos) {

		JSONArray Njarray = new JSONArray();
		try {
			for (int i = 0; i < jarray.length(); i++) {
				if (i != pos)
					Njarray.put(jarray.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Njarray;
	}	

}

