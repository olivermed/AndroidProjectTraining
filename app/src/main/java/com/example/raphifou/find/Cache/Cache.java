package com.example.raphifou.find.Cache;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.raphifou.find.Retrofit.FireBaseObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by oliviermedec on 13/05/2017.
 */

public class Cache {
    private Context context;
    private Gson gson;
    ObjectMapper mapper = new ObjectMapper();

    public Cache(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void serealize(Object object, String id) throws IOException {
        File file = new File(context.getFilesDir(), id);
        mapper.writeValue(file, object);
    }

    public Object deserialize(String id, Class type) throws IOException {
        File file = new File(context.getFilesDir(), id);
        Object object = mapper.readValue(file, type);
        return object;
    }

    public List<FireBaseObject> getListFireBaseObject(String id){
        CacheFileReader cacheFileReader = new CacheFileReader();
        String jsonCat = cacheFileReader.read(context.getFilesDir() + "/" + id);

        if (jsonCat == null) {
            return null;
        }
        System.out.println("getListFireBaseObject json return :: " + jsonCat);
        return gson.fromJson(jsonCat, new TypeToken<List<FireBaseObject>>(){}.getType());
    }

    public void delFile(String id) {
        File file = new File(context.getFilesDir(), id);
        file.delete();
    }
}
