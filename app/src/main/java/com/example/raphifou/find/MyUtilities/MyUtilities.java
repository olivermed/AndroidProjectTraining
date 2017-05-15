package com.example.raphifou.find.MyUtilities;

import java.util.List;

/**
 * Created by oliviermedec on 13/05/2017.
 */

public class MyUtilities {

    /*
     * Check if list of object null
     * If on object is null the the function
     * return false
     */
    public boolean checkIfObjectsNull(List<Object> list) {
        for (Object obj : list) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }
}
