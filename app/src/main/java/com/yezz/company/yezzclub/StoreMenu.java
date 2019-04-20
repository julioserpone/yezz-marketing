package com.yezz.company.yezzclub;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yezz.company.yezzclub.helpers.Constants;
import com.yezz.company.yezzclub.lData.YezzSendData;
import com.yezz.company.yezzclub.lData.entities.Branch;
import com.yezz.company.yezzclub.lData.entities.VisitStore;
import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;
import com.yezz.company.yezzclub.lData.entities.contracts.VisitStoreContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Ramon_000 on 14-09-2016.
 */
public class StoreMenu extends YezzMeta{
    private DrawerNavigation menu;
    private Button btnCloseVisit;
    private String branchId;
    private TextView tvTitle;
    private BranchContract branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_menu_store);
        this.iniComponent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        btnCloseVisit = (Button) findViewById(R.id.bStoreCloseVisit);

        ShowMessageGps();
    }

    private void iniComponent(){
        this.menu=new DrawerNavigation(this,
                (DrawerLayout)this.findViewById(R.id.drawer_menu_store),
                (ListView) this.findViewById(R.id.left_drawer_menu_store));

        try{
            JSONObject branchInfo = this.getBranchSession();
            if(branchInfo != null){
                //Save Key for ID
                this.branchId = branchInfo.getString("key");
                //Show Name Store in Menu
                this.branch =new Branch(getApplicationContext())
                        .getRegisterFull(BranchContract.BranchEntry.ID + " = ?",new String[]{ this.branchId });
                this.tvTitle=(TextView) this.findViewById(R.id.tvStoreName);
                this.tvTitle.setText(branch.getName());
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.d("Error: ",e.getMessage());
        }
    }

    public void clickStoreHasPop(View v) {
        showAlertHasPop(this.branch);
    }

    public void showAlertHasPop(BranchContract branch){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_msg_pop_installed)
                .setTitle(R.string.dialog_title_default);
        final BranchContract br = this.branch;
        // Add the buttons
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateBranchHasPop(br, 1);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateBranchHasPop(br, 0);
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void clickStoreQuantitative(View v)
    {
        Intent intent= new Intent(this,Quantitative.class);
        BranchContract branch =new Branch(getApplicationContext())
                .getRegister(BranchContract.BranchEntry.ID + " = ?",new String[]{ this.branchId });
        if (branch.getIs_customer().equals("0")) {
            intent.putExtra("id_phone","0");
            intent.putExtra("screen_size","");
            intent.putExtra("price","0");
        }
        intent.putExtra("store",branchId);
        this.startActivity(intent);
    }

    public void clickStorePhotoEvidence(View view) {
        Intent intent= new Intent(this,StorePhotoEvidence.class);
        intent.putExtra("branchId", this.branchId);
        this.startActivity(intent);
    }

    public void clickStoreClose(View v) {
        locationStart();
        btnCloseVisit.setEnabled(false);
        showLoad(4000);
        saveUbication(this.branchId);
        Log.d("SAVE EXIT STORE",this.branchId);
        JSONObject data = getBranchSession();

        try {
            if (this.user == null) {
                iniUserJson();
            }
            data.put("user",this.user.getString("id"));
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = mdformat.format(calendar.getTime());
            data.put("date", date);

            VisitStore visitiStore = new VisitStore(this);
            VisitStoreContract visitStoreContract = visitiStore.getRegister(VisitStoreContract.VisitStoreEntry.STATUS +" =?",
                    new String[]{Constants.STATUS_ACTIVE});

            visitStoreContract.setDate(date);
            visitStoreContract.setUserId(user.getString("id"));
            visitStoreContract.setStatus(Constants.STATUS_CLOSE);
            visitiStore.update(VisitStoreContract.VisitStoreEntry.TABLE_NAME, visitStoreContract.toContentValues(),
                    VisitStoreContract.VisitStoreEntry.ID, visitStoreContract.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.closeBranchSession();
        Log.d("SESION", data.toString());
        new YezzSendData(this).synchronize(data);
        this.startActivity(new Intent(this,Dashboard.class));
        this.finish();
    }

    public void clickStoreSynchronize(View v) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts_comment, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        final VisitStore visitiStore = new VisitStore(this);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                addCommentToBranch(userInput.getText().toString());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void clickStoreInfoShow(View view){

        /*BranchContract branch =new Branch(getApplicationContext())
                .getRegisterFull(BranchContract.BranchEntry.ID + " = ?",new String[]{ this.branchId });*/

        if (this.branch != null) {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(this.getString(R.string.store_information));
            builder.setMessage(this.getInfoStore(this.branch));
            builder.setPositiveButton(this.getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { }
            });
            builder.create().show();
        } else {
            this.showMsg(this.getString(R.string.msg_error_no_info_store));
            this.startActivity(new Intent(this,StoreMenu.class));
            this.finish();
        }
    }

    private String getInfoStore(BranchContract branch){
        String info="";

        info=this.getString(R.string.name) + ": "+branch.getName()+"\n";
        info=info.concat(
            this.getString(R.string.address) + ": " + branch.getAddress() + "\n" +
            this.getString(R.string.state) +  ": " + branch.getObjState().getName() + "\n"
        );

        if(branch.getObjCity() == null) {
            info=info.concat(this.getString(R.string.city) + ": " + branch.getObjCity().getName() + "\n");
        }

        if (branch.getObjCategory() != null) {
            info=info.concat(this.getString(R.string.category) + ": " + branch.getObjCategory().getName() + "\n");
        }

        if (branch.getObjChain() != null) {
            info=info.concat(this.getString(R.string.chain) + ": " + branch.getObjChain().getName() + "\n");
        }

        if (branch.getObjType()!=null){
            info=info.concat(this.getString(R.string.type_channel) + ": " + branch.getObjType().getName() + "\n");
        }

        info=info.concat(
                this.getString(R.string.dialog_msg_pop_installed) + ": " + (branch.getHas_pop()=="1"?"Si":"No") + "\n"
        );

        return info;
    }

    public void updateBranchHasPop(BranchContract branch, int has_pop) {
        branch.setHas_pop(""+has_pop);
        //branch.setStatus("update");
        (new Branch(this)).update(BranchContract.BranchEntry.TABLE_NAME,branch.toContentValues(), "id", branch.getId());
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
