package com.spectrographix.monadie.database;

import java.util.HashMap;
import java.util.Vector;

public class DbTableValues {
    private HashMap<String, Vector<String>> mTableAttrValues = new HashMap<String, Vector<String>>();

    public void addAttributeValue(String attr, String value)
    {
        Vector<String> values = mTableAttrValues.get(attr);
        if (values == null)
        {
            values = new Vector<String>();
        }
        values.add(value);
        mTableAttrValues.put(attr, values );
    }
    public void addAttributeValues(String attr, Vector<String> values)
    {
        Vector<String> curValues = mTableAttrValues.get(attr);
        if (curValues == null)
        {
            curValues = values;
        }
        else
        {
            curValues.addAll(values);
        }
        mTableAttrValues.put(attr, values );
    }

    //if there is only one value.
    public String getAttributeValue(String attr)
    {
        Vector<String> curValues =  mTableAttrValues.get(attr);
        if (curValues == null || curValues.size() == 0)
            return null;

        return curValues.get(0);
    }

    public Vector<String> getAttributeValues(String attr)
    {
        return mTableAttrValues.get(attr);
    }
}