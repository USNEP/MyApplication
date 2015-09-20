package model;

/**
 * Created by ashok on 9/19/15.
 */
public class Status {
    long _id;
    String _cash;
    String _bank;
    String _loan;
    String _investment;

    public Status() {
    }

    public Status(long _id, String _cash, String _bank, String _loan, String _investment) {
        this._id = _id;
        this._cash = _cash;
        this._bank = _bank;
        this._loan = _loan;
        this._investment = _investment;
    }

    public Status(String _cash, String _bank, String _loan, String _investment) {
        this._cash = _cash;
        this._bank = _bank;
        this._loan = _loan;
        this._investment = _investment;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_cash() {
        return _cash;
    }

    public void set_cash(String _cash) {
        this._cash = _cash;
    }

    public String get_bank() {
        return _bank;
    }

    public void set_bank(String _bank) {
        this._bank = _bank;
    }

    public String get_loan() {
        return _loan;
    }

    public void set_loan(String _loan) {
        this._loan = _loan;
    }

    public String get_investment() {
        return _investment;
    }

    public void set_investment(String _investment) {
        this._investment = _investment;
    }
}
