package com.rf_user.activity;


public class ImageItem {

	public String restaurant_banner_id, restaurant_id,banner_name, description;

	//public Bitmap banner_name;

	public ImageItem(String restaurant_banner_id, String restaurant_id,
			String banner_name,String description) {
		super();
		this.restaurant_banner_id = restaurant_banner_id;
		this.restaurant_id = restaurant_id;
		this.banner_name = banner_name;
		this.description = description;
		
	}

	public String getRestaurant_banner_id() {
		return restaurant_banner_id;
	}

	public void setRestaurant_banner_id(String restaurant_banner_id) {
		this.restaurant_banner_id = restaurant_banner_id;
	}

	public String getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(String restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBanner_name() {
		return banner_name;
	}

	public void setBanner_name(String banner_name) {
		this.banner_name = banner_name;
	}

}
