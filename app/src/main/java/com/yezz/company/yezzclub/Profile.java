package com.yezz.company.yezzclub;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yezz.company.yezzclub.helpers.ManageImage;

import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by ramon_000 on 18-08-2016.
 */
public class Profile extends YezzMeta{
    private TextView tvName,tvProfile,tvEmail,tvBirthdate,tvGenre,tvAddress;
    private DrawerNavigation menu;
    private ImageButton ibProfile;
    private JSONObject personalInfo,profileInfo;
    private ContextWrapper cw = new ContextWrapper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        this.setContentView(R.layout.activity_menu_profile);
        this.iniComponent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    private void iniComponent(){
        this.tvEmail=(TextView) this.findViewById(R.id.tvProfileEmail);
        this.tvName=(TextView) this.findViewById(R.id.tvProfileName);
        this.tvProfile= (TextView) this.findViewById(R.id.tvProfileProfile);
        this.tvBirthdate = (TextView) this.findViewById(R.id.tvProfileBirthdate);
        this.tvGenre = (TextView) this.findViewById(R.id.tvProfileGender);
        this.tvAddress = (TextView) this.findViewById(R.id.tvProfileAddress);
        this.ibProfile = (ImageButton)this.findViewById(R.id.ib_user_profile_photo);


        this.menu=new DrawerNavigation(this,
                (DrawerLayout) this.findViewById(R.id.drawer_profile),
                (ListView) this.findViewById(R.id.left_drawer_profile));

        this.iniUserJson();
        this.personalInfo=this.getPersonData();
        this.profileInfo=this.getProfileData();

        if(this.personalInfo == null || this.profileInfo == null){
            this.showMsg("Error con datos de usuario");
            this.startActivity(new Intent(this,Dashboard.class));
            finish();
        }

        this.customImage();
        //Bitmap bm = this.getImageBitmap("http://masterherald.com/wp-content/uploads/2015/03/avatar-2.jpg");
        this.showNameAndProfile();
        this.showUserData();
    }

    private void customImage(){
        try{
            ManageImage mi = new ManageImage();
            Bitmap bm = mi.loadImageFromStorage(cw.getDir("yezz_profile", Context.MODE_PRIVATE).toString(),"profile.jpg");
            if(bm != null){
                this.ibProfile.setImageBitmap(bm);
            }else{
                this.ibProfile.setImageResource(R.drawable.ic_icon);
            }
        }catch (Exception e){
            this.ibProfile.setImageResource(R.drawable.ic_icon);
            Log.d("download profile image",e.getMessage());
        }
        this.ibProfile.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private void showNameAndProfile(){
        try {
            this.tvName.setText((this.personalInfo.getString("first_name")+" "+this.personalInfo.getString("last_name")));
            this.tvProfile.setText(this.profileInfo.getString("name").toUpperCase());
        }catch (Exception e){
            Log.d("showNameAndProfile",e.getMessage());
        }
    }

    private void showUserData(){
        ArrayList<ProfileUserDataItem> items=new ArrayList<ProfileUserDataItem>();

        try {
            this.tvEmail.setText(getResources().getString(R.string.email) + " : " + this.user.getString("email"));
            this.tvBirthdate.setText(getResources().getString(R.string.birthdate) + " : " + this.personalInfo.getString("birthdate"));
            this.tvGenre.setText(getResources().getString(R.string.gender) + " : " + this.personalInfo.getString("gender"));
            this.tvAddress.setText(getResources().getString(R.string.address) + " : " + this.personalInfo.getString("address"));
        }catch (Exception e){
            Log.d("getting profile data",e.getMessage());
        }
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
