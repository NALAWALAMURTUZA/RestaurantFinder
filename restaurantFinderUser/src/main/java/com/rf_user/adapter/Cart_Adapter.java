package com.rf_user.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rf.restaurant_user.R;
import com.rf_user.activity.Cart;
import com.rf_user.activity.Food_Categories_Details_List;
import com.rf_user.activity.Payment_Activity;
import com.rf_user.global.Global_variable;

public class Cart_Adapter extends BaseAdapter implements OnItemClickListener
{
	private Activity activity;
    private static LayoutInflater inflater=null;
    int layoutResID;
    public JSONArray cart;
   
	
	 public Cart_Adapter(Activity a,JSONArray cart) {
		 try{
		    activity = a;
		    this.cart = cart;
	      
			System.out.println("carty_value"+cart);
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        cart_total();   
	 } catch (NullPointerException n) {
			n.printStackTrace();
		}
	 }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cart.length();
	}

	
	@Override
	public JSONObject getItem(int position) 
	{
		
		// TODO Auto-generated method stub
		 JSONObject j = null;
			try 
			{
			   j = cart.getJSONObject(position);
			} catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				n.printStackTrace();
			}
		return j;
		
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public static class ViewHolder{
        
        public TextView txv_cart_name,txv_cart_value,
                        txv_cart_quantity,txv_cart_total_quantity,txv_cart_index,txv_cart_total_quantity_total;
        public Button btn_add,btn_minus;
     
  
    }

	 public void cart_total()
     {
		 if(Global_variable.cart_total!=0)
		 {
			 Global_variable.cart_total = 0;
		 }
    	 for(int y=0;y<Global_variable.cart.length();y++)
			{
				
				try {
					int quantity = Integer.parseInt(Global_variable.cart.getJSONObject(y).getString("quantity"));
					int price = Integer.parseInt(Global_variable.cart.getJSONObject(y).getString("price"));
					Global_variable.cart_total = Global_variable.cart_total + (quantity * price) ;
				
//					Global_variable.cart_total = Global_variable.cart_total + (quantity * price) ;
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch(NullPointerException n){
					n.printStackTrace();
				}
				
			}
    	 Cart.txv_sr_total.setText(Global_variable.Categories_sr+" "+String.valueOf(Global_variable.cart_total));
     }
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		
		 View vi=convertView;
		  
		 final ViewHolder holder;
		  vi = inflater.inflate(R.layout.my_cart_rawfile,parent,false );
          holder = new ViewHolder();
          holder.txv_cart_name = (TextView) vi.findViewById(R.id.txv_cart_name);
          holder.txv_cart_value=(TextView)vi.findViewById(R.id.txv_cart_value);
          holder.txv_cart_total_quantity=(TextView)vi.findViewById(R.id.txv_cart_total_quantity);
          holder.txv_cart_total_quantity_total=(TextView)vi.findViewById(R.id.txv_cart_total_quantity_total);
          holder.btn_add=(Button)vi.findViewById(R.id.btn_add);
          holder.txv_cart_quantity=(TextView)vi.findViewById(R.id.txv_cart_quantity);
          holder.btn_minus=(Button)vi.findViewById(R.id.btn_minus);
          holder.txv_cart_index=(TextView)vi.findViewById(R.id.txv_cart_index);
          
          vi.setTag( holder );
          
          //holder=(ViewHolder)vi.getTag();
          
          try {
        	  JSONObject cart_detail = getItem(position);
        	  System.out.println("cart.json_detail"+cart_detail);
        	  
	    	  String quantity = cart_detail.getString("quantity");
		      String index = cart_detail.getString("index");
	          final String price = cart_detail.getString("price");
	          final String spicy_level_position = cart_detail.getString("spicy_level_req_on");
	          final String comment = cart_detail.getString("comment");
	          final String full_name = cart_detail.getString("full_name");
	         
	          
	        
	          holder.txv_cart_index.setText(index);
	          holder.txv_cart_name.setText(full_name);
	          holder.txv_cart_quantity.setText(quantity);
	          holder.txv_cart_total_quantity.setText(quantity);
	          holder.txv_cart_total_quantity_total.setText((Integer.parseInt(quantity) * Integer.parseInt(price))+"");
	          holder.txv_cart_value.setText(Global_variable.Categories_sr+" "+price);
	          
	          
	          final String GetIndex = holder.txv_cart_index.getText().toString();
	          System.out.println("parent_index"+GetIndex);
	        
			  Cart.txv_sr_total.setText(Global_variable.Categories_sr+" "+String.valueOf(Global_variable.cart_total));
				
	          holder.btn_add.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						System.out.println("cart.indexofClicked item"+holder.txv_cart_index.getText().toString());
//						System.out.println("cart.indexofClicked position"+position);
//						System.out.println("cart.indexofClicked fullname"+holder.txv_cart_name.getText().toString());
						int i = Integer.parseInt(holder.txv_cart_quantity.getText().toString());
//						System.out.println("parent_index_quantity"+i);
						
						Payment_Activity.Bool_Apply=false;
						if(i>=0)
						{
							i = i+1;
							holder.txv_cart_quantity.setText(String.valueOf(i));
							holder.txv_cart_total_quantity.setText(String.valueOf(i));
							JSONObject cart_value = new JSONObject();
							try {
								if(Global_variable.cart.length()!=0)
								{
									for(int j=0;j<Global_variable.cart.length();j++)
									{
//										System.out.println("cart.index_loop"+Global_variable.cart.getJSONObject(j).getString("index")+"From Textview"+holder.txv_cart_index.getText().toString());
										if(Global_variable.cart.getJSONObject(j).getString("index")==holder.txv_cart_index.getText().toString())
										{
											
											  
											JSONArray jsonArray = Global_variable.cart;
											Global_variable.cart = new JSONArray();   
											int len = jsonArray.length();
											if (jsonArray != null) { 
											   for (int k=0;k<len;k++)
											   { 
											       //Excluding the item at position
											        if (k != j) 
											        {
											            Global_variable.cart.put(jsonArray.get(k));
											        }
											        
											   } 
											}
											
										}
									
									}
									if(i!=0)
									{
										cart_value.put("comment",comment);
										cart_value.put("index",holder.txv_cart_index.getText().toString());
										cart_value.put("quantity",i);
										cart_value.put("spicy_level_req_on",spicy_level_position);
										cart_value.put("full_name", full_name);
										cart_value.put("price", price);
										int price1 = Integer.parseInt(price);
										cart_value.put("total", price1*i);
										
										
										Global_variable.cart.put(cart_value);
									}
								}
								else
								{
									cart_value.put("comment",comment);
									cart_value.put("index",holder.txv_cart_index.getText().toString());
									cart_value.put("quantity",i);
									cart_value.put("spicy_level_req_on",spicy_level_position);
									cart_value.put("full_name", full_name);
									cart_value.put("price", price);
									int price1 = Integer.parseInt(price);
									cart_value.put("total", price1*i);
									Global_variable.cart.put(cart_value);
								}
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							catch(NullPointerException N)
							{
								N.printStackTrace();
							}
						}
						holder.txv_cart_total_quantity_total.setText((Integer.parseInt(holder.txv_cart_quantity.getText().toString()) * Integer.parseInt(price))+"");
						Food_Categories_Details_List.txv_cart.setText(Global_variable.cart.length()+"");
						System.out.println("Cart__plus"+Global_variable.cart);
						cart_total();
						
					}
				});
	          
	          holder.btn_minus.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) 
					{
						Payment_Activity.Bool_Apply=false;
						// TODO Auto-generated method stub
//						System.out.println("cart.indexofClicked item"+holder.txv_cart_index.getText().toString());
//						System.out.println("cart.indexofClicked position"+position);
//						System.out.println("cart.indexofClicked fullname"+holder.txv_cart_name.getText().toString());
						int i = Integer.parseInt(holder.txv_cart_quantity.getText().toString());
//						System.out.println("parent_index_quantity"+i);
						System.out.println("Holder index");
						
						
						if(i>0)
						{
							
							System.out.println("Murtuza_Nalawala");
							
							i = i-1;
							holder.txv_cart_quantity.setText(String.valueOf(i));
							holder.txv_cart_total_quantity.setText(String.valueOf(i));
							JSONObject cart_value = new JSONObject();
							try {
								if(Global_variable.cart.length()!=0)
								{
									for(int j=0;j<Global_variable.cart.length();j++)
									{
//										System.out.println("cart.index_loop"+Global_variable.cart.getJSONObject(j).getString("index")+"From Textview"+holder.txv_cart_index.getText().toString());
										if(Global_variable.cart.getJSONObject(j).getString("index")==holder.txv_cart_index.getText().toString())
										{
											
											  
											JSONArray jsonArray = Global_variable.cart;
											Global_variable.cart = new JSONArray();   
											int len = jsonArray.length();
											if (jsonArray != null) { 
											   for (int k=0;k<len;k++)
											   { 
											       //Excluding the item at position
											        if (k != j) 
											        {
											            Global_variable.cart.put(jsonArray.get(k));
											        }
											        
											   } 
											}
											
										}
									
									}
									if(i!=0)
									{
										cart_value.put("comment",comment);
										cart_value.put("index",holder.txv_cart_index.getText().toString());
										cart_value.put("quantity",i);
										cart_value.put("spicy_level_req_on",spicy_level_position);
										cart_value.put("full_name", full_name);
										cart_value.put("price", price);
										int price1 = Integer.parseInt(price);
										cart_value.put("total", price1*i);
										Global_variable.cart.put(cart_value);
									}
								}
								else
								{
									cart_value.put("comment",comment);
									cart_value.put("index",holder.txv_cart_index.getText().toString());
									cart_value.put("quantity",i);
									cart_value.put("spicy_level_req_on",spicy_level_position);
									cart_value.put("full_name", full_name);
									cart_value.put("price", price);
									int price1 = Integer.parseInt(price);
									cart_value.put("total", price1*i);
									Global_variable.cart.put(cart_value);
								}
								if(i==0)
								{

									System.out.println("JsonPosition"+position);
									//Global_variable.cart.remove(position);
									System.out.println("JsonPosition_after_Delete"+Global_variable.cart);
									//onItemClick(parent, convertView, position, layoutResID);
									try{
										
										activity.startActivity(new Intent(activity, Cart.class));
										
//									Cart cart = new Cart();
//									cart.abc();
									}
									catch(IllegalStateException ex)
									{
										ex.printStackTrace();
									}
								}

								
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}catch(NullPointerException n)
							{
								n.printStackTrace();
							}
						}
						holder.txv_cart_total_quantity_total.setText((Integer.parseInt(holder.txv_cart_quantity.getText().toString()) * Integer.parseInt(price))+"");
						Food_Categories_Details_List.txv_cart.setText(Global_variable.cart.length()+"");
						System.out.println("Cart__plus"+Global_variable.cart);
						cart_total();
					}
				});
	          
          } catch (JSONException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}catch(NullPointerException N)
  		{
  			
  		}
          
		// TODO Auto-generated method stub
		return vi;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		
	}

}
