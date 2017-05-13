package com.example.raphifou.find.ShareAskCache;

import android.content.Context;

import com.example.raphifou.find.Cache.Cache;
import com.example.raphifou.find.Retrofit.FireBaseObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oliviermedec on 13/05/2017.
 */

public class ShareCache {
    public static String TAG = ShareCache.class.getSimpleName();
    private Context context;
    private List<FireBaseObject> fireBaseObjects;
    private Cache cache;

    public ShareCache(Context context) {
        this.context = context;
        cache = new Cache(this.context);
        fireBaseObjects = getFireBaseObjects();
        if (fireBaseObjects == null) {
            fireBaseObjects = new ArrayList<>();
        }
    }

    public void addFireBaseObject(FireBaseObject FireBaseObject) {
        fireBaseObjects.add(0, FireBaseObject);
        saveFireBaseObjects();
    }

    public void deleteFireBaseObject(int index) {
        fireBaseObjects.remove(index);
        saveFireBaseObjects();
    }

    public List<FireBaseObject> getFireBaseObjects() {
        List<FireBaseObject> list = cache.getListFireBaseObject(TAG);
        return list;
    }

    private void saveFireBaseObjects() {
        try {
            cache.serealize(fireBaseObjects, TAG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePanier(){
        cache.delFile(TAG);
    }
}
