package com.rf.restaurant_superadmin.adapter;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rf.restaurant_superadmin.R;
import com.superadmin.global.Global_function;
import com.superadmin.global.Global_variable;

public class generate_invoice_list extends BaseAdapter {
	private Activity _activity;
	private JSONArray _array_invoice_data_list;
	private static LayoutInflater inflater = null;

	public generate_invoice_list(Activity activity,
			JSONArray _array_invoice_data_list) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this._array_invoice_data_list = _array_invoice_data_list;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		System.out.println("!!!!generate_pdf"+Global_function.generate_pdf(this._array_invoice_data_list));
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return this._array_invoice_data_list.length();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;
		
		TextView txv_sr_no,txv_restaurant_name,txv_email,txv_po,txv_co,txv_rpo,txv_fo,
		 txv_nswt,txv_nsat,txv_cba,txv_cbu,txv_tb,txv_ulp,txv_total,txv_status, txv_booking_charge, txv_nop;

		
		
		
		TextView txv_sr_no_oo,txv_restaurant_name_oo,txv_email_oo,txv_po_oo,txv_co_oo,txv_cba_oo,txv_cbu_oo,txv_tb_oo,
		 txv_total_oo,txv_status_oo, txv_booking_charge_oo , txv_total_pa;

//		TG PDF
//            1. Pending Order = PO = tg_pending_order
//            2. Confirmed Order = CO = tg_confirmed_order
//            3. Review Pending Order = RPO = tg_review_order
//            4. Finish Order = FO = tg_finish_order
//            5. Not Show Within Time = NSWT = tg_not_show_with_in_time
//            6. Not Show After Time = NSAT = tg_not_show_after_time
//            7. Cancel By Admin = CBA = tg_cancel_by_admin_order
//            8. Cancel By User = CBU = tg_cancel_by_user_order
//            9. Total Booking = TB = tg_total_order
//			  10. Number of Peopele = NOP = number_of_people_valid_order	
//			  10. Booking Charge = BC = booking_charge	
//            11. Used Loyalty Points = ULP = used_loyalty_points
//
//            		
//
//		OO PDF
//		1. Pending Order =  PO = oo_waiting_order
//		2. Confirmed Order = CO = oo_confirmed_order
//		3. Cancel By Admin = CBA = oo_cancel_by_admin_order
//		4. Cancel By User = CBU = oo_cancel_by_user_order
//		5. Total Booking = TB = oo_global_total
//		6. Payable Amount = PA = oo_rate
//		7. Booking Charge = BC = percentage

//		TextView    ,   txv_used_loyalty_points_raw;
//		txv_sr_no = (TextView) vi.findViewById(R.id.txv_sr_no);
//		txv_restaurant_name = (TextView) vi
//				.findViewById(R.id.txv_restaurant_name);
//		txv_bookings = (TextView) vi.findViewById(R.id.txv_bookings);
//		txv_email = (TextView) vi.findViewById(R.id.txv_email);
//		txv_booking_charge = (TextView) vi.findViewById(R.id.txv_booking_charge);
//		txv_total = (TextView) vi.findViewById(R.id.txv_total);
//		txv_status = (TextView) vi.findViewById(R.id.txv_status);
//		txv_used_loyalty_points_raw = (TextView) vi.findViewById(R.id.txv_used_loyalty_points_raw);
		

		try {
			String restaurant_id, name_en, booking_charge, package_id, global_booking_charge, email, tg_count, oo_count, tg_rate, oo_rate, status, used_loyalty_points;

			String  tg_pending_order,
					tg_confirmed_order,
					tg_review_order,
					tg_finish_order,
					tg_not_show_with_in_time,
					tg_not_show_after_time,
					tg_cancel_by_admin_order,
					tg_cancel_by_user_order,
					tg_total_order,
					tg_number_of_people_valid_order;
			
			String  oo_waiting_order,
					oo_confirmed_order,
					oo_cancel_by_admin_order,
					oo_cancel_by_user_order,
					oo_total_order,
					oo_booking_charge;
			
			String temp_order_count = null,temp_booking_charge = null,temp_total = null;
			
			restaurant_id = _array_invoice_data_list.getJSONObject(position).getString("restaurant_id");
			name_en = _array_invoice_data_list.getJSONObject(position).getString("name_en");
			booking_charge = _array_invoice_data_list.getJSONObject(position).getString("booking_charge");
			package_id = _array_invoice_data_list.getJSONObject(position).getString("package_id");
			global_booking_charge = _array_invoice_data_list.getJSONObject(position).getString("global_booking_charge");
			email = _array_invoice_data_list.getJSONObject(position).getString("email");
			tg_count = _array_invoice_data_list.getJSONObject(position).getString("tg_count");
			oo_count = _array_invoice_data_list.getJSONObject(position).getString("oo_count");
			tg_rate = _array_invoice_data_list.getJSONObject(position).getString("tg_rate");
			oo_rate = _array_invoice_data_list.getJSONObject(position).getString("oo_rate");
			status = _array_invoice_data_list.getJSONObject(position).getString("status");
			used_loyalty_points = _array_invoice_data_list.getJSONObject(position).getString("used_loyalty_points");
			
			tg_pending_order = _array_invoice_data_list.getJSONObject(position).getString("tg_pending_order");
			tg_confirmed_order = _array_invoice_data_list.getJSONObject(position).getString("tg_confirmed_order");
			tg_review_order = _array_invoice_data_list.getJSONObject(position).getString("tg_review_order");
			tg_finish_order = _array_invoice_data_list.getJSONObject(position).getString("tg_finish_order");
			tg_not_show_with_in_time = _array_invoice_data_list.getJSONObject(position).getString("tg_not_show_with_in_time");
			tg_not_show_after_time = _array_invoice_data_list.getJSONObject(position).getString("tg_not_show_after_time");
			tg_cancel_by_admin_order = _array_invoice_data_list.getJSONObject(position).getString("tg_cancel_by_admin_order");
			tg_cancel_by_user_order = _array_invoice_data_list.getJSONObject(position).getString("tg_cancel_by_user_order");
			tg_total_order = _array_invoice_data_list.getJSONObject(position).getString("tg_total_order");
			tg_number_of_people_valid_order = _array_invoice_data_list.getJSONObject(position).getString("number_of_people_valid_order");
			
			oo_waiting_order = _array_invoice_data_list.getJSONObject(position).getString("oo_waiting_order");
			oo_confirmed_order = _array_invoice_data_list.getJSONObject(position).getString("oo_confirmed_order");
			oo_cancel_by_admin_order = _array_invoice_data_list.getJSONObject(position).getString("oo_cancel_by_admin_order");
			oo_cancel_by_user_order = _array_invoice_data_list.getJSONObject(position).getString("oo_cancel_by_user_order");
			oo_total_order = _array_invoice_data_list.getJSONObject(position).getString("oo_global_total");
			oo_booking_charge = _array_invoice_data_list.getJSONObject(position).getString("percentage");
			
			System.out.println("!!!!pankaj_invoice_value_restaurant_id"+restaurant_id);
			System.out.println("!!!!pankaj_invoice_value_name_en"+name_en);
			System.out.println("!!!!pankaj_invoice_value_booking_charge"+booking_charge);
			System.out.println("!!!!pankaj_invoice_value_package_id"+package_id);
			System.out.println("!!!!pankaj_invoice_value_global_booking_charge"+global_booking_charge);
			System.out.println("!!!!pankaj_invoice_value_email"+email);
			System.out.println("!!!!pankaj_invoice_value_tg_count"+tg_count);
			System.out.println("!!!!pankaj_invoice_value_oo_count"+oo_count);
			System.out.println("!!!!pankaj_invoice_value_tg_rate"+tg_rate);
			System.out.println("!!!!pankaj_invoice_value_oo_rate"+oo_rate);
			System.out.println("!!!!pankaj_invoice_value_status"+status);
			
			System.out.println("!!!!pankaj_invoice"+Global_variable.click_flag_tg_oo);
			switch (Global_variable.click_flag_tg_oo) {
			case "tg":
				
				vi = inflater.inflate(R.layout.activity_generate_invoice_tg_rawfile, null);
				
				txv_sr_no = (TextView)vi.findViewById(R.id.txv_sr_no);
				txv_restaurant_name = (TextView)vi.findViewById(R.id.txv_restaurant_name);
				txv_email = (TextView)vi.findViewById(R.id.txv_email);
				txv_po = (TextView)vi.findViewById(R.id.txv_po);
				txv_co = (TextView)vi.findViewById(R.id.txv_co);
				txv_rpo = (TextView)vi.findViewById(R.id.txv_rpo);
				txv_fo = (TextView)vi.findViewById(R.id.txv_fo);
				txv_nswt = (TextView)vi.findViewById(R.id.txv_nswt);
				txv_nsat = (TextView)vi.findViewById(R.id.txv_nsat);
				txv_cba = (TextView)vi.findViewById(R.id.txv_cba);
				txv_cbu = (TextView)vi.findViewById(R.id.txv_cbu);
				txv_tb = (TextView)vi.findViewById(R.id.txv_tb);
				txv_nop = (TextView)vi.findViewById(R.id.txv_nop);
				txv_ulp = (TextView)vi.findViewById(R.id.txv_ulp);
				txv_total = (TextView)vi.findViewById(R.id.txv_total);
				txv_status = (TextView)vi.findViewById(R.id.txv_status);
				txv_booking_charge = (TextView) vi.findViewById(R.id.txv_booking_charge);
				
				
//				txv_used_loyalty_points_raw.setVisibility(View.VISIBLE);
//				txv_used_loyalty_points_raw.setText(used_loyalty_points);
				temp_order_count = tg_count;
				temp_total = tg_rate;
				
				if(Double.parseDouble(booking_charge)!=0.00)
				{
					
					temp_booking_charge = booking_charge;
				}
				else
				{
					
					temp_booking_charge = global_booking_charge;
				}
				
				System.out.println("!!!!pankaj_invoice_tg_temp_order_count"+tg_count);
				System.out.println("!!!!pankaj_invoice_tg_temp_rate"+tg_rate);
				System.out.println("!!!!pankaj_invoice_oo_temp_order_count"+tg_count);
				System.out.println("!!!!pankaj_invoice_oo_temp_rate"+tg_rate);
				System.out.println("!!!!pankaj_invoice_temp_order_count"+temp_order_count);
				System.out.println("!!!!pankaj_invoice_temp_booking_charge"+temp_booking_charge);
				System.out.println("!!!!pankaj_invoice_temp_total"+temp_total);
				
				txv_sr_no.setText(position + 1 + "");
				txv_restaurant_name.setText(name_en);
				txv_tb.setText(temp_order_count);
				txv_nop.setText(tg_number_of_people_valid_order);
				txv_email.setText(email);
				txv_booking_charge.setText(temp_booking_charge);
				txv_total.setText(temp_total);
				txv_status.setText(status);
				
				
				txv_po.setText(tg_pending_order);
				txv_co.setText(tg_confirmed_order);
				txv_rpo.setText(tg_review_order);
				txv_fo.setText(tg_finish_order);
				txv_nswt.setText(tg_not_show_with_in_time);
				txv_nsat.setText(tg_not_show_after_time);
				txv_cba.setText(tg_cancel_by_admin_order);
				txv_cbu.setText(tg_cancel_by_user_order);
				txv_ulp.setText(used_loyalty_points);
				
				break;

			case "oo":
				vi = inflater.inflate(R.layout.activity_generate_invoice_oo_rawfile, null);
				
				txv_sr_no_oo = (TextView)vi.findViewById(R.id.txv_sr_no_oo);
				txv_restaurant_name_oo = (TextView)vi.findViewById(R.id.txv_restaurant_name_oo);
				txv_email_oo = (TextView)vi.findViewById(R.id.txv_email_oo);
				txv_po_oo = (TextView)vi.findViewById(R.id.txv_po_oo);
				txv_co_oo = (TextView)vi.findViewById(R.id.txv_co_oo);
				txv_cba_oo = (TextView)vi.findViewById(R.id.txv_cba_oo);
				txv_cbu_oo = (TextView)vi.findViewById(R.id.txv_cbu_oo);
				txv_tb_oo = (TextView)vi.findViewById(R.id.txv_tb_oo);
				txv_total_pa = (TextView)vi.findViewById(R.id.txv_total_pa);
				txv_total_oo = (TextView)vi.findViewById(R.id.txv_total_oo);
				txv_status_oo = (TextView)vi.findViewById(R.id.txv_status_oo);
				txv_booking_charge_oo = (TextView) vi.findViewById(R.id.txv_booking_charge_oo);				
				
//				txv_used_loyalty_points_raw.setVisibility(View.GONE);
				temp_order_count = oo_count;
				temp_total = oo_rate;
				
				if(Double.parseDouble(booking_charge)!=0.00)
				{
					
					temp_booking_charge = booking_charge;
				}
				else
				{
					
					temp_booking_charge = global_booking_charge;
				}
				
				System.out.println("!!!!pankaj_invoice_tg_temp_order_count"+tg_count);
				System.out.println("!!!!pankaj_invoice_tg_temp_rate"+tg_rate);
				System.out.println("!!!!pankaj_invoice_oo_temp_order_count"+tg_count);
				System.out.println("!!!!pankaj_invoice_oo_temp_rate"+tg_rate);
				System.out.println("!!!!pankaj_invoice_temp_order_count"+temp_order_count);
				System.out.println("!!!!pankaj_invoice_temp_booking_charge"+temp_booking_charge);
				System.out.println("!!!!pankaj_invoice_temp_total"+temp_total);
				
				txv_sr_no_oo.setText(position + 1 + "");
				txv_restaurant_name_oo.setText(name_en);
				txv_tb_oo.setText(temp_order_count);
				txv_email_oo.setText(email);
				txv_booking_charge_oo.setText(oo_booking_charge);
				txv_total_oo.setText(oo_total_order);
				txv_status_oo.setText(status);
				txv_total_pa.setText(oo_rate);
				
				txv_po_oo.setText(oo_waiting_order);
				txv_co_oo.setText(oo_confirmed_order);
				txv_cba_oo.setText(oo_cancel_by_admin_order);
				txv_cbu_oo.setText(oo_cancel_by_user_order);

				break;
			
			}
			

			
//			if(Double.parseDouble(booking_charge)>Double.parseDouble(global_booking_charge))
//			{
//				
//				temp_booking_charge = booking_charge;
//			}
//			else if(Double.parseDouble(booking_charge)<Double.parseDouble(global_booking_charge))
//			{
//				
//				temp_booking_charge = global_booking_charge;
//			}
			
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vi;

	}
	
	
	

}
