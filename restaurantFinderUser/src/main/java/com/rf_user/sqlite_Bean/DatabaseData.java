package com.rf_user.sqlite_Bean;

public  class DatabaseData {

	//private variables
  
    String _Api;
    String _Data;
    String _Timestamp;
 
    public String get_Timestamp() {
		return _Timestamp;
	}

	public void set_Timestamp(String _Timestamp) {
		this._Timestamp = _Timestamp;
	}

	public String get_Data() {
		return _Data;
	}

	public void set_Data(String _Data) {
		this._Data = _Data;
	}

	// Empty constructor
    public DatabaseData()
    {
 
    }

    // constructor
    public DatabaseData(String Api, String Data, String Timestamp){
        this._Api = Api;
        this._Data = Data;
        this._Timestamp = Timestamp;
    }
   
 
    
 
    // getting name
    public String get_Api(){
        return this._Api;
    }
 
    // setting name
    public void set_Api(String _Api){
        this._Api = _Api;
    }
 


	@Override
	public String toString() {
		return "DatabaseDataInfo [Api=" + _Api + ", Data=" + _Data + ", Timestemp=" + _Timestamp + "]";
	}
	
}
