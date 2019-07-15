package com.swapnotech.relaxation.meditation.tools;

import android.content.ContentValues;
import android.util.Log;

import com.google.gson.Gson;
import com.swapnotech.relaxation.meditation.model.MItems;
import com.swapnotech.relaxation.meditation.model.MSound;
import com.swapnotech.relaxation.meditation.model.MSoundDownload;
import com.swapnotech.relaxation.meditation.model.MWallpaper2;
import com.swapnotech.relaxation.meditation.model.Mdownload;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by JEWEL on 7/27/2016.
 */
public class DBManager {
    public static final String TABLE_RELAX = "tbl_relax_category";
    public static final String TABLE_WALLPAPER = "tbl_wallpaper";
    public static final String TABLE_DATE = "tbl_date";
    public static final String TABLE_ITEMS = "tbl_items";
    public static final String TABLE_VIDEO = "tbl_videos";
    public static final String TABLE_DOWNLOAD = "tbl_download";
    public static final String TABLE_ADS = "tbl_ads";
    public static final String TABLE_TIME_TRACKER = "tbl_time_tracker";
    public static final String TABLE_SOUND_DOWNLOAD = "tbl_sound_download";

    public static final String KEY_ID = "id";
    public static final String KEY_FAV = "fav";
    public static final String KEY_MOD_TIME = "lastMod";
    public static final String KEY_STATUS = "status";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_LOGOUT = "logout";


    private static final String DB_NAME = "candyParty.db";


    private static final String CREATE_TABLE_WALLPAPER_CATEGORY = DBQuery.init()
            .newTable(TABLE_RELAX)
            .addField("categoryId", DBQuery.INTEGER_PRI)
            .addField("categoryTotalItem", DBQuery.INTEGER)
            .addField("categoryType", DBQuery.INTEGER)
            .addField("ordering", DBQuery.INTEGER)
            .addField("categoryUpdateAvailable", DBQuery.INTEGER)

            .addField("categoryKeyword", DBQuery.TEXT)
            .addField("categoryTitle", DBQuery.TEXT)
            .addField("categoryDetails", DBQuery.TEXT)
            .addField("categoryPhoto", DBQuery.TEXT)
            .addField("categoryThumb", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_WALLPAPER = DBQuery.init()
            .newTable(TABLE_WALLPAPER)
            .addField("Id", DBQuery.INTEGER_PRI)
            .addField("category_id", DBQuery.INTEGER)

            .addField("category_title", DBQuery.TEXT)
            .addField("caption", DBQuery.TEXT)
            .addField("thumbnail", DBQuery.TEXT)
            .addField("original", DBQuery.TEXT)
            .addField("view", DBQuery.TEXT)
            .addField("share", DBQuery.TEXT)
            .addField("modified_date", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_ITEMS = DBQuery.init()
            .newTable(TABLE_ITEMS)
            .addField("Id", DBQuery.INTEGER)
            .addField("InApp", DBQuery.INTEGER)
            .addField("View", DBQuery.INTEGER)
            .addField("NumberOfPhotos", DBQuery.INTEGER)
            .addField("CategoryId", DBQuery.INTEGER)
            .addField("UpdateAvailable", DBQuery.INTEGER)
            .addField("Status", DBQuery.INTEGER)
            .addField("TotalLike", DBQuery.INTEGER)

            .addField("price", DBQuery.FLOAT)

            .addField("Title", DBQuery.TEXT)
            .addField("Photo", DBQuery.TEXT)
            .addField("Thumb", DBQuery.TEXT)
            .addField("MainSound", DBQuery.TEXT)
            .addField("CategoryTitle", DBQuery.TEXT)
            .addField("Keyword", DBQuery.TEXT)
            .addField("Description", DBQuery.TEXT)
            .addField("UpdateDateTime", DBQuery.TEXT)

            .addField("sounds", DBQuery.TEXT)
            .addField("wallpaper", DBQuery.TEXT)
            .getTable();

    private static final String CREATE_TABLE_VIDEO = DBQuery.init()
            .newTable(TABLE_VIDEO)
            .addField("Id", DBQuery.INTEGER_PRI)
            .addField("CategoryId", DBQuery.INTEGER)
            .addField("menuTitle", DBQuery.TEXT)
            .addField("menuFile_name", DBQuery.TEXT)
            .addField("CategoryTitle", DBQuery.TEXT)
            .addField("Thumb", DBQuery.TEXT)
            .addField("Ingredients", DBQuery.TEXT)
            .addField("Process", DBQuery.TEXT)
            .addField("PPhoto", DBQuery.TEXT)
            .addField("menuVideo", DBQuery.TEXT)
            .addField("SearchTag", DBQuery.TEXT)
            .addField("TypeOne", DBQuery.INTEGER)
            .addField("TypeTwo", DBQuery.INTEGER)
            .addField("TypeThree", DBQuery.INTEGER)
            .addField("TypeFour", DBQuery.INTEGER)
            .addField("TypeFive", DBQuery.INTEGER)

            .addField("fav", DBQuery.INTEGER)
            .addField("view", DBQuery.INTEGER)
            .getTable();

    private static final String CREATE_TABLE_ADS = DBQuery.init()
            .newTable(TABLE_ADS)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("productId", DBQuery.INTEGER)
            .getTable();
    private static final String CREATE_TABLE_DATE = DBQuery.init()
            .newTable(TABLE_DATE)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("date", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_DOWNLOAD = DBQuery.init()
            .newTable(TABLE_DOWNLOAD)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("downloadFile", DBQuery.TEXT)
            .getTable();

    private static final String CREATE_TABLE_SOUND_DOWNLOAD = DBQuery.init()
            .newTable(TABLE_SOUND_DOWNLOAD)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("sound", DBQuery.TEXT)
            .addField("isDownload", DBQuery.Boolean)
            .getTable();

    private static final String CREATE_TABLE_TIME_TRACKER = DBQuery.init()
            .newTable(TABLE_TIME_TRACKER)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("login", DBQuery.TEXT)
            .addField("logout", DBQuery.TEXT)
            .addField("status", DBQuery.INTEGER)
            .getTable();

    private static DBManager instance;
    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase db;

    private DBManager() {
        openDB();
        createTable();
    }

    public static DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    public static String getQueryAll(String table, String primaryKey, String value) {
        return "select * from " + table + " where " + primaryKey + "='" + value + "'";
    }

    public static String getQueryDate(String table, String primaryKey) {
        return "select * from " + table + " where " + primaryKey + "='";
    }

    public static String getQueryAll(String table) {
        return "select * from " + table;
    }


    public static String getRecipeQueryJointTable(int id) {
        return "select a.Id,a.CategoryId,a.TypeOne,a.TypeTwo,a.TypeThree,a.TypeFour,a.TypeFive,a.recipeDelete,a.fav,a.view,a.Title,a.Thumb,a.Photo,a.Ingredients,a.Process,a.PPhoto,a.CategoryTitle,a.SearchTag,a.Video,a.price,a.DiscountRate,a.cartStatus,a.addCart,a.quantity,a.status,a.sizeView,a.color,b.isNew from tbl_recipe a left join tbl_new b on a.CategoryId=b.CategoryId AND a.Id=b.productId where a.CategoryId='" + id + "'";
    }


    public static String getQueryFav(String table) {
        return "select * from " + table + " where " + KEY_FAV + "='1'";
    }

    public static String getQueryTimeTracker() {
        return "select * from " + TABLE_TIME_TRACKER + " where " + KEY_STATUS + "='0'";
    }


    private void openDB() {
        SQLiteDatabase.loadLibs(MyApp.getInstance().getContext());
        File databaseFile = MyApp.getInstance().getContext().getDatabasePath(DB_NAME);
        if (!databaseFile.exists()) {
            databaseFile.mkdirs();
            databaseFile.delete();
        }
        db = SQLiteDatabase.openOrCreateDatabase(databaseFile, Global.DB_PASS, null);
    }

    private void createTable() {
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_WALLPAPER_CATEGORY);
        db.execSQL(CREATE_TABLE_VIDEO);
        db.execSQL(CREATE_TABLE_ADS);
        db.execSQL(CREATE_TABLE_DATE);
        db.execSQL(CREATE_TABLE_TIME_TRACKER);
        db.execSQL(CREATE_TABLE_DOWNLOAD);
        db.execSQL(CREATE_TABLE_WALLPAPER);
        db.execSQL(CREATE_TABLE_SOUND_DOWNLOAD);
    }


    public long addData(String tableName, Object dataModelClass, String primaryKey) {
        long result = -1;
        String valueOfKey = "";
        try {
            Class myClass = dataModelClass.getClass();
            Field[] fields = myClass.getDeclaredFields();
            ContentValues contentValues = new ContentValues();

            for (Field field : fields) {
                //for getting access of private field


                String name = field.getName();
                field.setAccessible(true);
                Object value = field.get(dataModelClass);

                if (name.equalsIgnoreCase("serialVersionUID")
                        || name.equalsIgnoreCase("$change")
                        ) {

                } else {
                    contentValues.put(name, value + "");
                    if (name.equalsIgnoreCase(primaryKey)) {

                        valueOfKey = value + "";
                    }

                }


            }
            if (!valueOfKey.equals("") && Integer.parseInt(valueOfKey) < 1)
                contentValues.remove(primaryKey);
            if (!isExist(tableName, primaryKey, valueOfKey)) {
                result = db.insert(tableName, null, contentValues);
                MyLog.e("DB", "add:" + valueOfKey);
            } else {

                result = db.update(tableName, contentValues, primaryKey + "=?", new String[]{valueOfKey + ""});
                MyLog.e("DB", "update:" + valueOfKey + ":" + dataModelClass.getClass().getSimpleName());
            }


        } catch (Exception e) {
            MyLog.e("DB_ERR", e.toString());
        } finally {

        }
        return result;
    }


    public long addData(String tableName, Object dataModelClass, String primaryKey, boolean isAutoInc) {
        long result = -1;
        String valueOfKey = "";
        try {
            Class myClass = dataModelClass.getClass();
            Field[] fields = myClass.getDeclaredFields();
            ContentValues contentValues = new ContentValues();

            for (Field field : fields) {
                //for getting access of private field


                String name = field.getName();
                field.setAccessible(true);
                Object value = field.get(dataModelClass);

                if (name.equalsIgnoreCase("serialVersionUID")
                        || name.equalsIgnoreCase("$change")
                        ) {

                } else {
                    contentValues.put(name, value + "");
                    if (name.equalsIgnoreCase(primaryKey)) {

                        valueOfKey = value + "";
                    }

                }


            }
            if (isAutoInc)
                contentValues.remove(primaryKey);

            if (!isExist(tableName, primaryKey, valueOfKey)) {
                result = db.insert(tableName, null, contentValues);
            } else {

                result = db.update(tableName, contentValues, primaryKey + "=?", new String[]{valueOfKey + ""});
            }


        } catch (Exception e) {
        } finally {

        }
        return result;
    }

    private boolean isExist(String table, String searchField, String value) {
        if (value.equals("") || Integer.valueOf(value) <= 0)
            return false;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from " + table + " where " + searchField + "='" + value + "'", null);
            if (cursor != null && cursor.getCount() > 0)
                return true;
        } catch (Exception e) {

        } finally {
            if (cursor != null)
                cursor.close();

        }


        return false;
    }

    private String getStringValue(Cursor cursor, String key) {

        if (cursor.getColumnIndex(key) == -1)
            return "na";
        else
            return cursor.getString(cursor.getColumnIndex(key));
    }

    private int getIntValue(Cursor cursor, String key) {
        if (cursor.getColumnIndex(key) == -1)
            return 0;
        else
            return cursor.getInt(cursor.getColumnIndex(key));
    }

    public boolean isFav(String table, int id) {

        String sql = "select * from " + table + " where " + KEY_ID + "='" + id + "' and " + KEY_FAV + "='1'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0)
            return true;
        return false;
    }

    public <T> long addAllData(String tableName, ArrayList<T> dataModelClass, String primaryKey) {
        long result = -1;
        for (Object model : dataModelClass)
            result = addData(tableName, model, primaryKey);
        return result;
    }


    public void deleteData(String table, String primaryKey, String value) {
        if (!isExist(table, primaryKey, value))
            return;

        int r = db.delete(table, primaryKey + "=?", new String[]{value});
    }

    public <T> ArrayList<T> getData(String tableName, Object dataModelClass) {

        String sql = "select * from " + tableName;
        Cursor cursor = db.rawQuery(sql, null);
        JSONObject jsonObject = new JSONObject();
        final ArrayList<JSONObject> data = new ArrayList<JSONObject>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                jsonObject = new JSONObject();
                try {
                    Class myClass = dataModelClass.getClass();
                    Field[] fields = myClass.getDeclaredFields();

                    for (Field field : fields) {
                        //for getting access of private field
                        field.setAccessible(true);
                        String name = field.getName();

                        jsonObject.put(name, getStringValue(cursor, name));

                    }
                    data.add(jsonObject);

                } catch (SecurityException ex) {
                } catch (IllegalArgumentException ex) {
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        Gson gson = new Gson();
        ArrayList<T> output = new ArrayList<T>();
        for (int i = 0; i < data.size(); i++) {
            dataModelClass = gson.fromJson(data.get(i).toString(), dataModelClass.getClass());
            output.add((T) dataModelClass);
        }


        return output;
    }


    public <T> ArrayList<T> getList(String sql, Object myInstance) {

        ArrayList<T> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        Gson gson = new Gson();
        if (cursor != null && cursor.moveToFirst()) {
            JSONObject jsonObject = new JSONObject();
            do {

                try {
                    Class myClass = myInstance.getClass();
                    Field[] fields = myClass.getDeclaredFields();
                    for (Field field : fields) {
                        //for getting access of private field
                        field.setAccessible(true);
                        String name = field.getName();
                        if (!name.equalsIgnoreCase("type"))
                            jsonObject.put(name, getStringValue(cursor, name));

                    }
                } catch (SecurityException ex) {
                } catch (IllegalArgumentException ex) {
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                myInstance = gson.fromJson(jsonObject.toString(), myInstance.getClass());
                list.add((T) myInstance);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return list;
    }


    public void close() {
        try {
            if (db.isOpen())
                db.close();
        } catch (Exception e) {
        }

    }


    public void addItemsData(MItems mRecipe, String tableName) {
        Gson gson = new Gson();
        android.database.Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put("Id", mRecipe.getId());
            values.put("InApp", mRecipe.getInApp());
            values.put("View", mRecipe.getView());
            values.put("NumberOfPhotos", mRecipe.getNumberOfPhotos());
            values.put("CategoryId", mRecipe.getCategoryId());
            values.put("UpdateAvailable", mRecipe.getUpdateAvailable());
            values.put("Status", mRecipe.getStatus());
            values.put("TotalLike", mRecipe.getTotalLike());
            values.put("Title", mRecipe.getTitle());
            values.put("Photo", mRecipe.getPhoto());
            values.put("Thumb", mRecipe.getThumb());
            values.put("MainSound", mRecipe.getMainSound());
            values.put("CategoryTitle", mRecipe.getCategoryTitle());
            values.put("Keyword", mRecipe.getKeyword());
            values.put("Description", mRecipe.getDescription());
            values.put("UpdateDateTime", mRecipe.getUpdateDateTime());
            values.put("price", mRecipe.getPrice());

            String sounds = gson.toJson(mRecipe.getSounds());
            values.put("sounds", sounds);

            String wallpaper = gson.toJson(mRecipe.getWallpaper());
            values.put("wallpaper", wallpaper);

            String sql = "select * from " + tableName + " where Id='" + mRecipe.getId() + "'";
            cursor = db.rawQuery(sql, null);
            Log.e("cu", "has" + cursor);
            if (cursor != null && cursor.getCount() > 0) {
                int update = db.update(tableName, values, "Id=?", new String[]{mRecipe.getId() + ""});
                Log.e("sublevel", "sub level update : " + update);
            } else {
                long v = db.insert(tableName, null, values);
                Log.e("sublevel", "sub level insert : " + v);

            }


        } catch (Exception e) {

        }
        if (cursor != null)
            cursor.close();
    }


    public ArrayList<MItems> getItemsData(int id) {
        ArrayList<MItems> assetArrayList = new ArrayList<>();
        Log.e("DB", "S1");
        Gson gson = new Gson();
        MItems mRecipe;
        String sql = "select * from " + TABLE_ITEMS + " where CategoryId='" + id + "'";
        android.database.Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("DB", "S2 :" + cursor.getCount());
            do {
                mRecipe = new MItems();
                mRecipe.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                mRecipe.setInApp(cursor.getInt(cursor.getColumnIndex("InApp")));
                mRecipe.setView(cursor.getInt(cursor.getColumnIndex("View")));
                mRecipe.setNumberOfPhotos(cursor.getInt(cursor.getColumnIndex("NumberOfPhotos")));
                mRecipe.setCategoryId(cursor.getInt(cursor.getColumnIndex("CategoryId")));
                mRecipe.setUpdateAvailable(cursor.getInt(cursor.getColumnIndex("UpdateAvailable")));
                mRecipe.setStatus(cursor.getInt(cursor.getColumnIndex("Status")));
                mRecipe.setTotalLike(cursor.getInt(cursor.getColumnIndex("TotalLike")));
                mRecipe.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                mRecipe.setPhoto(cursor.getString(cursor.getColumnIndex("Photo")));
                mRecipe.setThumb(cursor.getString(cursor.getColumnIndex("Thumb")));
                mRecipe.setMainSound(cursor.getString(cursor.getColumnIndex("MainSound")));
                mRecipe.setCategoryTitle(cursor.getString(cursor.getColumnIndex("CategoryTitle")));
                mRecipe.setKeyword(cursor.getString(cursor.getColumnIndex("Keyword")));
                mRecipe.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                mRecipe.setUpdateDateTime(cursor.getString(cursor.getColumnIndex("UpdateDateTime")));
                mRecipe.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                mRecipe.setView(cursor.getInt(cursor.getColumnIndex("View")));

                String sounds = cursor.getString(cursor.getColumnIndex("sounds"));
                String wallpaper = cursor.getString(cursor.getColumnIndex("wallpaper"));

                MSound[] soundArray = gson.fromJson(sounds, MSound[].class);
                mRecipe.setSounds(new ArrayList<MSound>(Arrays.asList(soundArray)));

                MWallpaper2[] wallpaperArray = gson.fromJson(wallpaper, MWallpaper2[].class);
                mRecipe.setWallpaper(new ArrayList<MWallpaper2>(Arrays.asList(wallpaperArray)));


                assetArrayList.add(mRecipe);

            } while (cursor.moveToNext());

        }
        cursor.close();


        return assetArrayList;
    }


    public void addDownload(Mdownload mdownload, String tableName) {
        Gson gson = new Gson();
        android.database.Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put("downloadFile", mdownload.getDownloadFile());


            String sql = "select * from " + tableName + " where downloadFile='" + mdownload.getDownloadFile() + "'";
            cursor = db.rawQuery(sql, null);
            Log.e("cu", "has" + cursor);
            if (cursor != null && cursor.getCount() > 0) {
                int update = db.update(tableName, values, "downloadFile=?", new String[]{mdownload.getDownloadFile() + ""});
                Log.e("sublevel", "sub level update : " + update);
            } else {
                long v = db.insert(tableName, null, values);
                Log.e("sublevel", "sub level insert : " + v);

            }


        } catch (Exception e) {

        }
        if (cursor != null)
            cursor.close();
    }

    public void addSoundDownload(MSoundDownload mSoundDownload, String tableName) {
        Gson gson = new Gson();
        android.database.Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put("sound", mSoundDownload.getSound());
            values.put("isDownload", mSoundDownload.isDownload());


            String sql = "select * from " + tableName + " where sound='" + mSoundDownload.getSound() + "'";
            cursor = db.rawQuery(sql, null);
            Log.e("sounds", "has" + cursor);
            if (cursor != null && cursor.getCount() > 0) {
                int update = db.update(tableName, values, "sound=?", new String[]{mSoundDownload.getSound() + ""});
                Log.e("sounds", "sounds download update : " + update);
            } else {
                long v = db.insert(tableName, null, values);
                Log.e("sounds", "sounds download insert : " + v);

            }


        } catch (Exception e) {

        }
        if (cursor != null)
            cursor.close();
    }


}
