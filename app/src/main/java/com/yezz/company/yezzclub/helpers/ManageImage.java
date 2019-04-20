package com.yezz.company.yezzclub.helpers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by ramon_000 on 26-12-2016.
 */

public class ManageImage {

    public String saveToInternalStorage(Bitmap bitmapImage, Context context, String name){

        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir

        String name_="yezz_profile"; //Folder name in device android/data/
        File directory = cw.getDir(name_, Context.MODE_PRIVATE);

        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("absolutepath ", directory.getAbsolutePath());
        return directory.getAbsolutePath();
    }

    /** Method to retrieve image from your device **/

    public Bitmap loadImageFromStorage(String path, String name)
    {
        Bitmap b;
        String name_="yezz_profile";
        try {
            File f=new File(path, name);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
