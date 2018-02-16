package com.example.popularmovies.utilities;

import com.example.popularmovies.data.IJsonDeserialize;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<Integer> optIntList(JSONObject jsonObject, String name) {
        JSONArray jsonArray = jsonObject.optJSONArray(name);
        if (jsonArray == null)
            return new ArrayList<>();

        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            int item = jsonArray.optInt(i);
            resultList.add(item);
        }

        return resultList;
    }

    public static <T extends IJsonDeserialize> ArrayList<T> optArrayList(JSONObject jsonObject, String name, Class<T> cls) {
        JSONArray propertyJsonArray = jsonObject.optJSONArray(name);
        if (propertyJsonArray == null)
            return null;

        ArrayList<T> arrayList = new ArrayList<>();
        for (int i = 0; i < propertyJsonArray.length(); i++) {
            JSONObject itemJsonObject = propertyJsonArray.optJSONObject(i);
            if (itemJsonObject == null) {
                arrayList.add(null);
                continue;
            }

            T obj = null;
            try {
                obj = cls.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (obj == null) {
                arrayList.add(null);
                continue;
            }

            obj.fillPropertiesByJsonObject(itemJsonObject);
            arrayList.add(obj);
        }
        return arrayList;
    }
}
