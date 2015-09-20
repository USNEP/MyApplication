package model;

/**
 * Created by ashok on 9/19/15.
 */
public class Type {
    long _id;
    String _head;
    String _sub_type;
    String _type;

    public Type() {
    }

    public Type(long _id, String _head, String _sub_type, String _type) {
        this._id = _id;
        this._head = _head;
        this._sub_type = _sub_type;
        this._type = _type;
    }

    public Type(String _head, String _sub_type, String _type) {
        this._head = _head;
        this._sub_type = _sub_type;
        this._type = _type;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_head() {
        return _head;
    }

    public void set_head(String _head) {
        this._head = _head;
    }

    public String get_sub_type() {
        return _sub_type;
    }

    public void set_sub_type(String _sub_type) {
        this._sub_type = _sub_type;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }
}
