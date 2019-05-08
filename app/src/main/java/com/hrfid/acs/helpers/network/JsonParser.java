package com.hrfid.acs.helpers.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pramodsarafdar on 4/8/16.
 */
public class JsonParser {

  public static <T> List<T> getList(String json) {
    try {
      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
      Type typeOfList = new TypeToken<List<T>>() {
      }.getType();
      return gson.fromJson(json, typeOfList);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

//    public static <T> List<T> parseFeedList(String json) {
//        try {
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//            Type typeOfList = new TypeToken<List<Feed>>() {
//            }.getType();
//            return gson.fromJson(json, typeOfList);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


  public static <T> T parseClass(String jsonString, Class<T> clazz) throws JsonSyntaxException {
    GsonBuilder builder = new GsonBuilder();
    builder.setDateFormat("yyyy-MM-dd HH:mm:ss");

    Gson gson = builder.create();
    return gson.fromJson(jsonString, clazz);
  }

//    public static final <T> List<T> parseClass(String jsonString, Class<T> clazz) {
//        GsonBuilder builder = new GsonBuilder();
//        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Gson gson = builder.create();
//        return gson.fromJson(jsonString, clazz);
//    }


  public static <T> List<T> parseList(final String json, final Class<T[]> clazz) {
    try {
      final T[] jsonToObject = new Gson().fromJson(json, clazz);
      return Arrays.asList(jsonToObject);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }


}
