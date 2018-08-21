package com.instigatemobile.grapes.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.instigatemobile.grapes.R;
import com.instigatemobile.grapes.models.FileModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteJson {

    private static final String TAG_FILE_READ = "Fail reading";
    private static final String JSON_TAG = "Json error";
    private final String M_FILE_NAME = Util.mNickname + "files.json";
    private Context mContext;

    public ReadWriteJson(Context context) {
        mContext = context;
    }

    public String readJson() {
        final StringBuilder json = new StringBuilder();
        try {
            final FileInputStream fileInputStream = mContext.openFileInput(M_FILE_NAME);
            final BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG_FILE_READ, "Cannot read file : ReadWriteJson.class");
        }
        return String.valueOf(json);
    }

    public void deleteObject(int position) {
        final String jsonRootElem = "mFiles";
        JSONArray jsonArray;
        final JSONArray newJsonArray = new JSONArray();
        final JSONObject fileObj = new JSONObject();
        try {
            final String tmp = readJson();
            if (tmp.length() > 4) {
                JSONObject tmpObj = new JSONObject(tmp);
                jsonArray = tmpObj.getJSONArray(jsonRootElem);
                int size = jsonArray.length();
                for (int i = 0; i < size; i++) {
                    if (i != position) {
                        newJsonArray.put(jsonArray.getJSONObject(i));
                    }
                }
            }
            fileObj.put(jsonRootElem, newJsonArray);
        } catch (JSONException e) {
            Log.e(JSON_TAG, "Json is invalid");
        }
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(String.valueOf(fileObj));
        writeJsonInFile(String.valueOf(jsonElement));
    }

    public void writeJson(final String filePath) {
        final String fileName = getFileName(filePath);
        final String fileSize = getFileSize(filePath);
        final String extension = getExtension(filePath);
        final String icon = String.valueOf(getIconId(extension));
        final String date = getLastModifiedDate(filePath);
        JSONObject fileObj = new JSONObject();
        FileModel file = new FileModel(fileSize, icon, fileName, filePath, date, extension);
        Util.mFileList.add(file);
        fileObj = getJsonObject(filePath, fileName, fileSize, extension, icon, date, fileObj);
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(String.valueOf(fileObj));
        writeJsonInFile(String.valueOf(jsonElement));
    }

    private JSONObject getJsonObject(String filePath, String fileName,
                                     String fileSize, String extension,
                                     String icon, String date, JSONObject fileObj) {
        final String jsonRootElem = "mFiles";
        JSONArray jsonArray;
        try {
            final String tmp = readJson();
            final List<JSONObject> jsonList = new ArrayList<>();
            if (tmp.length() > 4) {
                JSONObject tmpObj = new JSONObject(tmp);
                jsonArray = tmpObj.getJSONArray(jsonRootElem);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonList.add(jsonArray.getJSONObject(i));
                }
            }
            fileObj.put(Util.NAME, fileName);
            fileObj.put(Util.PATH, filePath);
            fileObj.put(Util.SIZE, fileSize);
            fileObj.put(Util.EXTENSION, extension);
            fileObj.put(Util.ICON, icon);
            fileObj.put(Util.DATE, date);
            jsonArray = new JSONArray();
            jsonList.add(fileObj);
            int size = jsonList.size();
            for (int i = 0; i < size; i++) {
                jsonArray.put(jsonList.get(i));
            }
            fileObj = new JSONObject();
            fileObj.put(jsonRootElem, jsonArray);
        } catch (JSONException e) {
            Log.e(JSON_TAG, "Invalid json");
        }
        return fileObj;
    }


    private void writeJsonInFile(String content) {
        File root = mContext.getFilesDir();
        File file = new File(root, M_FILE_NAME);
        Util.mFilePath = file.getAbsolutePath();
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(content);
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String getLastModifiedDate(final String filePath) {
        File file = new File(filePath);
        return String.valueOf(file.lastModified());
    }

    private static int getIconId(final String extension) {
        switch (extension) {
            case "jpg":
            case "png":
                return R.drawable.icons_jpg;
            case "mp3":
                return R.drawable.icons_mp3;
            case "pdf":
                return R.drawable.icons_pdf;
            case "mp4":
                return R.drawable.icons_video;
            default:
                return R.drawable.icons_other;
        }
    }

    private static String getExtension(final String filePath) {
        if (filePath.lastIndexOf(".") != -1 && filePath.lastIndexOf(".") != 0) {
            return filePath.substring(filePath.lastIndexOf(".") + 1);
        }
        return "";
    }

    private String getFileSize(final String filePath) {
        File file = new File(filePath);
        return String.valueOf(file.length());
    }

    private String getFileName(final String path) {
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        if (fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }
}