package com.rf_user.notification;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rf.restaurant_user.R;
import com.rf_user.global.Global_variable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class notification_cart_adapter extends BaseAdapter {
    private Activity activity;
    private static LayoutInflater inflater = null;
    int layoutResID;
    public JSONArray cart;

    public notification_cart_adapter(Activity a, JSONArray cart) {
        try{
            activity = a;
            this.cart = cart;

            System.out.println("carty_value_notification" + cart);
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }catch(NullPointerException n)
        {
            n.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cart.length();
    }

    @Override
    public JSONObject getItem(int position) {
        // TODO Auto-generated method stub
        JSONObject j = null;
        try {
            j = cart.getJSONObject(position);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(NullPointerException n)
        {
            n.printStackTrace();
        }
        return j;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public static class ViewHolder {

        public TextView txv_cart_name, txv_cart_value, txv_cart_quantity,
                txv_cart_total_quantity, txv_cart_index,txv_cart_spicy_level,txv_cart_price;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        final ViewHolder holder;
        vi = inflater
                .inflate(R.layout.delivery_payment_raw_file, parent, false);
        holder = new ViewHolder();
        holder.txv_cart_index = (TextView) vi.findViewById(R.id.txv_cart_index);
        holder.txv_cart_name = (TextView) vi.findViewById(R.id.txv_cart_name);
        holder.txv_cart_spicy_level = (TextView) vi.findViewById(R.id.txv_cart_spicy_level);
        holder.txv_cart_value = (TextView) vi.findViewById(R.id.txv_cart_value);
        holder.txv_cart_quantity = (TextView) vi
                .findViewById(R.id.txv_cart_quantity);
        holder.txv_cart_price = (TextView) vi
                .findViewById(R.id.txv_cart_price);
        // TODO Auto-generated method stub

        vi.setTag(holder);

        try {

                System.out.println("carty_value_Payment_if");
                JSONObject cart_detail = getItem(position);
                System.out.println("cart.json_detail" + cart_detail);

                String quantity = cart_detail.getString("quantity");

                final String price = cart_detail.getString("price");

                final String full_name = cart_detail.getString("name");


                holder.txv_cart_index.setText((position + 1) + "");
                holder.txv_cart_name.setText(full_name);
                holder.txv_cart_price.setText(Global_variable.Categories_sr + " " +price);
                //holder.txv_cart_quantity.setText("<" + " " + quantity + " " + ">");
                holder.txv_cart_quantity.setText(quantity);

            final String total = cart_detail.getString("total");
            System.out.println("carty_value_Payment_else_price" + total);
                holder.txv_cart_value
                        .setText((Global_variable.Categories_sr + " " + total)
                                + "");



        } catch (NullPointerException n) {

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return vi;
    }
}
