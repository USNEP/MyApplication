package model;

/**
 * Created by ashok on 9/13/15.
 */
public class History {

    //private variables
    long _id;
    String _type;
    String _sub_type;
    String _head;
    boolean _io;
    boolean _cb;
    double _amount;
    String _date;
    String _description;

    public History(){

    }

    public History(long _id, String _type, String _sub_type,String _head, boolean _io, boolean _cb, double _amount, String _date, String _description) {
        this._id = _id;
        this._type = _type;
        this._sub_type = _sub_type;
        this._head=_head;
        this._io = _io;
        this._cb = _cb;
        this._amount = _amount;
        this._date = _date;
        this._description = _description;
    }

    public History(String _type, String _sub_type,String _head, boolean _io, boolean _cb, double _amount, String _date, String _description) {
        this._type = _type;
        this._sub_type = _sub_type;
        this._head=_head;
        this._io = _io;
        this._cb = _cb;
        this._amount = _amount;
        this._date = _date;
        this._description = _description;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_sub_type() {
        return _sub_type;
    }

    public void set_sub_type(String _sub_type) {
        this._sub_type = _sub_type;
    }
    public String get_head() {
        return _head;
    }

    public void set_head(String _head) {
        this._head = _head;
    }


    public boolean get_io() {
        return _io;
    }

    public void set_io(boolean _io) {
        this._io = _io;
    }

    public boolean get_cb() {
        return _cb;
    }

    public void set_cb(boolean _cb) {
        this._cb = _cb;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public double get_amount() {
        return _amount;
    }

    public void set_amount(double _amount) {
        this._amount = _amount;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }



    // Empty constructor

    // constructor

    // getting ID

}

