package com.yezz.company.yezzclub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.yezz.company.yezzclub.helpers.ManageImage;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by ramon_000 on 19-08-2016.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Context context;

    public DownloadImageTask(ImageView bmImage, Context context) {
        this.bmImage = bmImage;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);


        } catch(FileNotFoundException e) {
            Log.e("DownloadImge", e.getMessage());
            //e.printStackTrace();
        }
        catch (Exception e) {
            Log.e("DownloadImge", e.getMessage());
            //e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {


        if(result != null){
            ManageImage mi = new ManageImage();
            mi.saveToInternalStorage(result,this.context,"profile.jpg");
        }

        if(bmImage != null){
            if(result != null)
                bmImage.setImageBitmap(result);
            else
                bmImage.setImageResource(R.drawable.ic_icon);

            bmImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        //bmImage.setAdjustViewBounds(true);
    }
}
