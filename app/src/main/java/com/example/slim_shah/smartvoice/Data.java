package com.example.slim_shah.smartvoice;
/**
 * Created by Slim_Shah on 28-Mar-16.
 */
public class Data {
    private int _id;
    private String _dataname;

    public Data(){
    }

    public Data(String dataname){
        this._dataname = dataname;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_dataname(String _dataname) {
        this._dataname = _dataname;
    }

    public int get_id() {
        return _id;
    }

    public String get_dataname() {
        return _dataname;
    }

}



