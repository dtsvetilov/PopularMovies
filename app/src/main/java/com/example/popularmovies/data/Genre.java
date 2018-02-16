package com.example.popularmovies.data;

import org.json.JSONObject;

public class Genre implements IJsonDeserialize, IUIAppendableObject {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void fillPropertiesByJsonObject(JSONObject jsonObject) {
        id = jsonObject.optInt("id");
        name = jsonObject.optString("name");
    }

    @Override
    public String getUIAppendableValue() {
        return name;
    }
}

