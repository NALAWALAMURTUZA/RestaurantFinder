package com.bean;

public class DateDetail 
{
	String _Str_Date_Detail;
	
	public String get_Date_Detail()
	{
		return _Str_Date_Detail;
		
	}
	public void Set_Date_Deatil(String Str_Date_Detail)
	{
		this._Str_Date_Detail=Str_Date_Detail;
	}
	public DateDetail()
	{
		
	}
	public DateDetail (String Str_Date_Detail)
	{
		this._Str_Date_Detail = Str_Date_Detail;
	}
	
	public String toString() {
		return "DateDetail  [Time =" + _Str_Date_Detail + "]";
	}
}
