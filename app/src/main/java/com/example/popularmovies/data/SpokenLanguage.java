package com.example.popularmovies.data;

import org.json.JSONObject;

public class SpokenLanguage implements IJsonDeserialize, IUIAppendableObject {
    private String iso6391;
    private String name;

    public String getIso6391() {
        return iso6391;
    }

    public String getName() {
        return name;
    }

    @Override
    public void fillPropertiesByJsonObject(JSONObject jsonObject) {
        name = jsonObject.optString("name");
        iso6391 = jsonObject.optString("iso_639_1");
    }

    @Override
    public String getUIAppendableValue() {
        return name;
    }
}