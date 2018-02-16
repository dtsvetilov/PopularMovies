package com.example.popularmovies.data;

import org.json.JSONObject;

public interface IJsonDeserialize {
    void fillPropertiesByJsonObject(JSONObject jsonObject);
}
