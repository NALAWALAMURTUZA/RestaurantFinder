package com.rf.restaurant_superadmin.httpconnection;
//package com.example.super_admin.httpconnection;
//
//public class popupmenu {
//	
//	img_menu.setOnClickListener(new View.OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			// FilterDialog();
//			PopupMenu popup = new PopupMenu(TakeBooking_Tablayout.this,
//					TakeBooking_Tablayout.img_menu);
//			popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
//
//			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//				public boolean onMenuItemClick(MenuItem item) {
//					if (item.getTitle().toString().equals("Manage Photos")) {
//
//						Intent intent = new Intent(
//								TakeBooking_Tablayout.this,
//								ManageGalleryActivity.class);
//						startActivity(intent);
//						// finish();
//
//					} else if (item.getTitle().toString()
//							.equals("Food Order")) {
//
//						{
//							Intent intent = new Intent(
//									TakeBooking_Tablayout.this,
//
//									Food_Detail_Categories_Activity.class);
//							startActivity(intent);
//						}
//						// finish();
//
//					} else if (item.getTitle().toString()
//							.equals("Take Booking")) {
//
//						{
//							Intent intent = new Intent(
//									TakeBooking_Tablayout.this,
//
//									TakeBookingActivity.class);
//							startActivity(intent);
//						}
//						// finish();
//
//					}
//					return true;
//
//				}
//			});
//
//			popup.show();
//		}
//	});
//
//}

//
//final Calendar c = Calendar.getInstance();
//		mYear = c.get(Calendar.YEAR);
//		mMonth = c.get(Calendar.MONTH);
//		mDay = c.get(Calendar.DAY_OF_MONTH);



//	private void Date() {
//
//		DatePickerDialog dpd = new DatePickerDialog(this,
//				new DatePickerDialog.OnDateSetListener() {
//
//					@Override
//					public void onDateSet(DatePicker view, int selectedyear,
//							int selectedmonth, int selectedday) {
//						mYear = selectedyear;
//						mMonth = selectedmonth;
//						mDay = selectedday;
//						// Display Selected date in textbox
//						tb_ed_date.setText(selectedyear + "-" + (selectedmonth + 1)
//								+ "-" + selectedday);
//
//					}
//				}, mYear, mMonth, mDay);
//		dpd.show();
//		dpd.setCancelable(false);
//		dpd.setCanceledOnTouchOutside(false);
//	}




