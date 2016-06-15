package com.example.android.freedom;

/**
 * Created by Bevan on 15-Jun-16.
 */

import java.io.Serializable;

public class PlaceData implements Serializable {

    private static final long serialVersionUID = -2770603167363638920L;
    private String[] data = {"1", "Auckland really blows", "Honestly", "Address: anywhere in AKL"};

    public String[] getData() {
        return data;
    }
}
