package com.example.minerd;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class FileUtils {

    public static void saveToFile(Context context, String fileName, JSONObject jsonObject) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(jsonObject.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readFromFile(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = context.openFileInput(fileName)) {
            int ch;
            while ((ch = fis.read()) != -1) {
                stringBuilder.append((char) ch);
            }
            return new JSONObject(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
