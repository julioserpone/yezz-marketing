package com.yezz.company.yezzclub;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.yezz.company.yezzclub.Adapters.DrawerListAdapter;
import com.yezz.company.yezzclub.helpers.ManageImage;
import com.yezz.company.yezzclub.lData.YezzSendData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ramon_000 on 18-08-2016.
 */
public class DrawerNavigation {
    private DrawerLayout drawer;
    private ListView leftDrawer;
    private ActionBarDrawerToggle actionBar;
    private YezzMeta activity;
    private String[] options=new String[]{"profile","dashboard","profile","store","route","sync","reset","logout"};
//    private String[] options=new String[]{"profile","dashboard","profile","store","storeMeta","route","sync","reset","logout"};


    public DrawerNavigation(YezzMeta activity,DrawerLayout drawer,ListView leftDrawer){
        this.activity=activity;
        this.drawer=drawer;
        this.leftDrawer=leftDrawer;

        this.iniComponent();
    }

    private void iniComponent(){
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        for (int i=0;i<options.length;i++) {
            if (i==0){
                continue;
            }
            items.add(new DrawerItem(this.getOptionText(options[i]),this.getOptionIconId(options[i])));
        }

        LayoutInflater inflanter=activity.getLayoutInflater();
        ViewGroup header=(ViewGroup) inflanter.inflate(R.layout.header_item,null,false);
        //new DownloadImageTask((ImageView) header.findViewById(R.id.ivHeaderItemPicture)).execute("http://masterherald.com/wp-content/uploads/2015/03/avatar-2.jpg");
        this.imageUser((ImageView) header.findViewById(R.id.ivHeaderItemPicture));
        this.leftDrawer.addHeaderView(header);
        // Relacionar el adaptador y la escucha de la lista del drawer
        this.leftDrawer.setAdapter(new DrawerListAdapter(activity, items));


        this.leftDrawer.setOnItemClickListener(new ItemClickListener());

        this.actionBar = new ActionBarDrawerToggle(this.activity,this.drawer,true,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        this.drawer.setDrawerListener(this.actionBar);
        this.activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.activity.getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void imageUser(ImageView imageView){
        try{
            ContextWrapper cw = new ContextWrapper(activity);
            ManageImage mi = new ManageImage();
            String route = cw.getDir("yezz_profile", Context.MODE_PRIVATE).toString();
            Bitmap bm = mi.loadImageFromStorage(route,"profile.jpg");
            if(bm != null){
                imageView.setImageBitmap(bm);
            }else{
                String url = activity.prepareUrlElement(activity.getPersonData().getString("pic_url"));
                new DownloadImageTask(null,activity).execute(url);
                //pruebo nuevamente
                Bitmap bm2 = mi.loadImageFromStorage(route,"profile.jpg");
                if(bm2 != null)
                    imageView.setImageBitmap(bm2);
                else
                    imageView.setImageResource(R.drawable.ic_icon);
            }
        }catch(Exception e){
            imageView.setImageResource(R.drawable.ic_icon);
        }
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private String getOptionText(String option){
        switch (option){
            case "dashboard":   return activity.getString(R.string.dashboard);
            case "profile":     return activity.getString(R.string.profile);
            case "store":       return activity.getString(R.string.store);
            case "storeMeta":   return activity.getString(R.string.channels);
            case "route":       return activity.getString(R.string.route);
            case "logout":      return activity.getString(R.string.logout);
            case "sync" :       return activity.getString(R.string.synchronize);
            case "reset" :       return activity.getString(R.string.reset);
        }
        return null;
    }

    private int getOptionIconId(String option){
        switch (option){
            case "dashboard":    return R.drawable.home;
            case "profile":     return R.drawable.profile;
            case "store":       return R.drawable.store;
            case "storeMeta":   return R.drawable.store;
            case "route":       return R.drawable.path;
            case "logout":      return R.drawable.logout;
            case "sync":        return R.drawable.sync;
            case "reset":        return R.drawable.reset;
        }
        return 0;
    }

    private class ItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            switch (options[position]){
                case "dashboard":
                    activity.startActivity(new Intent(activity,Dashboard.class));
                    break;
                case "profile":
                    activity.startActivity(new Intent(activity,Profile.class));
                    break;
                case "store":
                    if (activity.checkBranchSession()){
                        activity.startActivity(new Intent(activity,StoreMenu.class));
                    }else{
                        activity.startActivity(new Intent(activity,StoreIdentify.class));
                    }
                    break;
                case "storeMeta":
                    Intent intent =new Intent(activity,StoreMeta.class);
                    intent.putExtra("type","channel");
                    activity.startActivity(intent);
                    break;
                case "route":
                    activity.startActivity(new Intent(activity,Route.class));
                    break;
                case "logout":
                activity.logout(activity);
                break;
                case "sync":
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.synchronize_process);
                builder.setMessage(R.string.wait_minutes);
                builder.setCancelable(true);

                final AlertDialog closeDialog = builder.create();
                closeDialog.show();

                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            closeDialog.dismiss();
                            timer.cancel();
                        }
                    },6000);//50 segundos
                new YezzSendData(activity).synchronize();
                break;
                case "reset":
                    activity.startActivity(new Intent(activity,Reset.class));
                    break;
                default:
                    Toast.makeText(activity,"En Construcción, Disculpe las molestías",Toast.LENGTH_LONG).show();
            }
        }
    }

    public ActionBarDrawerToggle getActionBar(){
        return actionBar;
    }
}
