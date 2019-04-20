package com.yezz.company.yezzclub;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yezz.company.yezzclub.lData.entities.MediaFiles;
import com.yezz.company.yezzclub.lData.entities.contracts.MediaFilesContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StorePhotoEvidence extends YezzMeta {

    private DrawerNavigation menu;
    private String branchId;
    private static final int REQUEST_CODE = 1;
    private List<String> imagenes = new ArrayList<String>();
    private List<String> comments = new ArrayList<String>();
    EditText textVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_photo_evidence);
        this.iniComponent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    private void iniComponent(){
        this.menu=new DrawerNavigation(this,
                (DrawerLayout)this.findViewById(R.id.drawer_store_photo_evidence),
                (ListView) this.findViewById(R.id.left_drawer_store_photo_evidence));
        try{
            JSONObject branchInfo = this.getBranchSession();
            if(branchInfo != null){
                this.branchId = branchInfo.getString("key");
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.d("Error: ",e.getMessage());
        }
    }

    public void clickPhotoEvidencePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        InputStream stream = null;
        Bitmap bitmap = null;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0,5, 0, 5);
        params.height= 500;
        LinearLayout main = (LinearLayout) findViewById(R.id.photoEvidenteContent);
        ImageView imgV = new ImageView(this);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                // reciclo el bitmap
                if (bitmap != null) {
                    bitmap.recycle();
                }
                final Uri imageUri = data.getData();
                if(imageUri != null) {
                    stream = getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(stream);
                    imgV.setImageBitmap(bitmap);
                } else {
                    bitmap=(Bitmap) data.getExtras().get("data");
                    imgV.setImageBitmap(bitmap);
                }

                imgV.setLayoutParams(params);
                main.addView(imgV,this.imagenes.size()+1);
                textVar=new EditText(this);

                //codifico la imagen
                this.imagenes.add(this.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG,90));
                //comments.add(comment.getText().toString());
                //comment.setText("");

                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.prompts_comment, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);
                final ImageView imgView = (ImageView) promptsView
                        .findViewById(R.id.prompts_imageView);

                imgView.setVisibility(View.VISIBLE);
                imgView.setImageBitmap(bitmap);
                params.height=300;
                imgView.setLayoutParams(params);
                /*imgView.setMaxWidth(200);
                imgView.setMaxHeight(200);*/

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        comments.add(userInput.getText().toString());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void clickPhotoEvidenceSend(View view) {
        String userId = null;
        try {
            if (this.user == null) {
                iniUserJson();
            }
            userId = this.user.getString("id");
        } catch (JSONException e) {
            Log.d("MediaFile",e.getMessage());
        }
        MediaFiles mf = new MediaFiles(getApplicationContext());
        if (this.imagenes.size() > 0) {
            for (int i = 0; i < this.imagenes.size(); i ++) {
                MediaFilesContract entity = new MediaFilesContract();
                entity.setUserId(userId);
                entity.setSourceId(branchId);
                entity.setCode(this.imagenes.get(i));
                entity.setType("image");
                entity.setComment(this.comments.get(i));
                entity.setOrigin("storePhotoEvidence");
                mf.create(entity);
            }
            Intent intent= new Intent(this,StoreMenu.class);
            intent.putExtra("branchId", this.branchId);

            this.startActivity(intent);
            this.finish();
        } else {
            showMsg("Debe agregar al menos una foto");
        }
    }

    public void clickPhotoEvidenceCancel(View view) {
        Intent intent= new Intent(this,StoreMenu.class);
        intent.putExtra("branchId", this.branchId);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.menu.getActionBar().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.menu.getActionBar().onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.menu.getActionBar().onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
