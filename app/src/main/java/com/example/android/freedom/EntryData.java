package com.example.android.freedom;

import java.io.Serializable;
import java.util.ArrayList;

// Class containing an arraylist of data string arrays
// String arrays to be of template {id, title, bodytext, address} format

public class EntryData implements Serializable {

    private static final long serialVersionUID = -4258683134467413705L;
    private ArrayList<String[]> dataArrayList = new ArrayList<String[]>();

    public EntryData() {
    }

    public EntryData(ArrayList<String[]> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    public ArrayList<String[]> getData() {
        return dataArrayList;
    }

    public void add(String[] newEntry) {
        // Adds newEntry to the ArrayList stored in the EntryData Object
        dataArrayList.add(newEntry);
    }

}
