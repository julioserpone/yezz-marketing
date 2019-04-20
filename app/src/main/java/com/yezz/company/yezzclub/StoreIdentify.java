package com.yezz.company.yezzclub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.yezz.company.yezzclub.Adapters.GenericArrayAdapter;
import com.yezz.company.yezzclub.lData.entities.Branch;
import com.yezz.company.yezzclub.lData.entities.Category;
import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CategoryContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ramon_000 on 23-08-2016.
 */

public class StoreIdentify extends YezzMeta implements AdapterView.OnItemSelectedListener {
    private AutoCompleteTextView actvStore;
    private Spinner sRouteStore;
    private Button bRegister,bSearch;
    private RadioButton rbRoute,rbOthers;
    private DrawerNavigation menu;
    private LinearLayout llInfo;
    private TextView tvChannel,tvType,tvSegment,tvChain,tvLocation,tvCity,tvZone,tvAddress,tvLabel;
    private String[] branchesText, branchesCode;
    private String codeStore="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        this.setContentView(R.layout.activity_menu_store_identify);

        if(this.checkBranchSession()){
            this.goToMenuStore(null);
        }
        this.iniComponent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

    }

    private void iniComponent(){
        this.actvStore=(AutoCompleteTextView) this.findViewById(R.id.actvStoreIdentifyStore);
        this.sRouteStore=(Spinner) this.findViewById(R.id.sStoreIdentifyRouteStore);
        this.bRegister=(Button) this.findViewById(R.id.bStoreIdentifyRegister);
        this.bSearch=(Button) this.findViewById(R.id.bStoreIdentifySearch);
        this.rbOthers=(RadioButton) this.findViewById(R.id.rbStoreIdentifyOthers);
        this.rbRoute=(RadioButton) this.findViewById(R.id.rbStoreIndentifyRoute);
        this.tvChannel=(TextView) this.findViewById(R.id.tvStoreIdentifyChannel);
        this.tvType=(TextView) this.findViewById(R.id.tvStoreIdentifyType);
        this.tvSegment=(TextView) this.findViewById(R.id.tvStoreIdentifySegment);
        this.tvChain=(TextView) this.findViewById(R.id.tvStoreIdentifyChain);
        this.tvLocation=(TextView) this.findViewById(R.id.tvStoreIdentifyLocation);
        this.tvCity=(TextView) this.findViewById(R.id.tvStoreIdentifyCity);
        this.tvZone=(TextView) this.findViewById(R.id.tvStoreIdentifyZone);
        this.tvAddress=(TextView) this.findViewById(R.id.tvStoreIdentifyAddress);
        this.tvLabel=(TextView) this.findViewById(R.id.tvStoreIdentifyLabelSpinner);
        this.llInfo=(LinearLayout) this.findViewById(R.id.llStoreIdentifyInfo);

        this.menu=new DrawerNavigation(this,
                (DrawerLayout) this.findViewById(R.id.drawer_store_identify),
                (ListView) this.findViewById(R.id.left_drawer_store_identify));

        this.loadSpinner();
        this.loadAutoComplete();
        this.iniListeners();
    }

    private void iniListeners(){
        this.actvStore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                showRegisterOption(false);
            }
        });

        this.sRouteStore.setOnItemSelectedListener(this);
    }

    private void loadSpinner(){
        Context context = this.getApplicationContext();
        GenericArrayAdapter<BranchContract> branchAdapter;
        JSONArray routes=this.getRouteData();
        if (routes.length()>0){
            try {
                String where="";
                String values[]=new String[routes.length()];
                for (int i=0;i<routes.length();i++){
                    where+=where.equals("")?"":" OR ";
                    where+= BranchContract.BranchEntry.ID+" = ?";
                    JSONObject route=routes.getJSONObject(i);
                    values[i]=route.getString("branch_id");
                }
                List<BranchContract> dataBranch = ( new Branch(context) )
                        .getRegisters(where,values);

                branchAdapter = new GenericArrayAdapter<>(this,android.R.layout.simple_spinner_item,dataBranch);
                this.sRouteStore.setAdapter(branchAdapter);
                branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }catch (JSONException e){
                Log.d("showList",e.getMessage());
            }
        }else{
            this.tvLabel.setText("No hay ruta asignada");
            this.sRouteStore.setVisibility(View.GONE);
            this.rbRoute.setEnabled(false);
            this.bSearch.setText(this.getString(R.string.search));
            this.rbOthers.setChecked(true);
            this.actvStore.setEnabled(true);
        }
    }

    private void loadAutoComplete(){
        List<BranchContract> list=new Branch(this.getApplicationContext()).getAllRegisterWithDataAddress();
        this.branchesText=new String[list.size()];
        this.branchesCode=new String[list.size()];
        for (int i=0;i<list.size();i++){
            BranchContract branch=list.get(i);

            this.branchesText[i]=branch.getName()+", "+branch.getAddress();
            this.branchesText[i]+=branch.getObjCity()!=null ? ", "+branch.getObjCity().getName() :"";
            this.branchesText[i]+=branch.getObjTownShip()!=null ? ", "+branch.getObjTownShip().getName() :"";
            this.branchesCode[i]=branch.getId();    //getCode puede estar en blanco, y esto podria traer problemas al momento de hacer la busqueda del item seleccionado en la lista de tiendas disponibles
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.list_item, R.id.item,this.branchesText);
        this.actvStore.setAdapter(adapter);
        this.actvStore.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
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

    public void clickStoreIdentifyRadio(View view){
        switch (view.getId()){
            case R.id.rbStoreIndentifyRoute:
                this.sRouteStore.setEnabled(true);
                this.actvStore.setEnabled(false);
//                etStore.setEnabled(false);
                this.bSearch.setText(this.getString(R.string.enter));
                break;
            case R.id.rbStoreIdentifyOthers:
                this.sRouteStore.setEnabled(false);
                this.actvStore.setEnabled(true);
//                etStore.setEnabled(true);
                this.bSearch.setText(this.getString(R.string.search));
                break;
        }
        this.showRegisterOption(false);
    }

    public void clickStoreIdentifySearch(View view){
        this.bRegister.setEnabled(false);
        if (rbRoute.isChecked()) {
            this.goToMenuStore((BranchContract) this.sRouteStore.getSelectedItem());
        }else{
            if (this.actvStore.getText().toString()==""){
                this.showMsg("Por favor indique el nombre de la tienda a buscar");
                return;
            }
            this.setStoreValue();
            BranchContract branchContract=new Branch(getApplicationContext())
                    .getRegister(BranchContract.BranchEntry.ID + " = ?",new String[]{this.codeStore});
            if (branchContract!=null){
                this.goToMenuStore(branchContract);
            }else{
                this.showRegisterOption(true);
                this.showMsg(this.getString(R.string.credential_invalid));
            }
        }
    }

    private void setStoreValue(){
        this.codeStore=null;
        for (int i=0;i<this.branchesText.length;i++){
            if (this.branchesText[i].equalsIgnoreCase(this.actvStore.getText().toString())){
                this.codeStore=this.branchesCode[i];    //Aqui ahorita se esta guardando son los ID, anteriormente estaba el Code, pero estos estan algunas veces en blanco
                break;
            }
        }
        this.codeStore=this.codeStore!=null?this.codeStore:this.actvStore.getText().toString();
    }

    public void clickStoreIdentifyRegister(View view){
        Intent intent=new Intent(this,StoreAdd.class);
        intent.putExtra("code",actvStore.getText().toString());
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        BranchContract branchContract=(BranchContract)adapterView.getItemAtPosition(i);
        this.tvChannel.setText(branchContract.getId());
        /*this.tvType.setText(branchContract.getCity());
        this.tvSegment.setText(branchContract.getCity());
        this.tvChain.setText(branchContract.getCity());
        this.tvLocation.setText(branchContract.getLocation());*/
        this.tvAddress.setText(branchContract.getAddress());
        /*this.tvCity.setText(branchContract.getCity());
        this.tvZone.setText(branchContract.getZone());*/
        this.showBranchInfo(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showBranchInfo(boolean visibility){
        this.llInfo.setVisibility(visibility?View.VISIBLE:View.GONE);
    }

    private void showRegisterOption(boolean visibility){
        this.bRegister.setVisibility(visibility?View.VISIBLE:View.GONE);
        this.bRegister.setEnabled(visibility);
    }

    private void goToMenuStore(@Nullable BranchContract branch){
        if(branch!=null){
            if(branch.getIs_customer() == null || branch.getIs_customer().equals("null")) {
                showAlert(branch);
            } else if(branch.getCategory() == null || branch.getCategory().equals("null")) {
                showAlertCategories(branch);
            } else if (branch.getHas_pop() == null || branch.getHas_pop().equals("null")) {
                showAlertHasPop(branch);
            } else {
                this.locationStart();
                showLoad(4000);
                this.saveUbication(branch.getId());
                Log.d("SAVE UBICATION STORE",branch.getId());
                this.startBranchSession(branch.getId());
                if (branch.getContact() != null && !branch.getContact().equals("null")) {
                    Intent intent = new Intent(this,StoreMenu.class);
                    intent.putExtra("branchId", branch.getId());
                    intent.putExtra("isYezz",branch.getIs_customer());
                    intent.putExtra("hasPop",branch.getHas_pop());
                    this.startActivity(intent);
                } else {
                    Intent intent = new Intent(this, Customer.class);
                    intent.putExtra("branchCode", branch.getCode());
                    intent.putExtra("branchId", branch.getId());
                    intent.putExtra("isYezz",branch.getIs_customer());
                    intent.putExtra("hasPop",branch.getHas_pop());
                    startActivity(intent);
                }
                this.finish();
            }
        }else {
            this.showMsg("Intente nuevamente");
        }
    }

    public void showAlert(BranchContract branch){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_msg_customer)
        .setTitle(R.string.dialog_title_default);
        final BranchContract br = branch;
        // Add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateBranchType(br, 1);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateBranchType(br, 0);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showAlertHasPop(BranchContract branch){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_msg_pop_installed)
                .setTitle(R.string.dialog_title_default);
        final BranchContract br = branch;
        // Add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateBranchHasPop(br, 1);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateBranchHasPop(br, 0);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showAlertCategories(BranchContract branch) {
        List<CategoryContract> dataCategories = ( new Category(getApplicationContext()) )
                .getAllRegister();
        final GenericArrayAdapter<CategoryContract> categoryAdapter = new GenericArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,dataCategories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner sp = new Spinner(this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(categoryAdapter);
        final BranchContract br = branch;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_category_title);
        builder.setView(sp);
        builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                CategoryContract category = (CategoryContract)sp.getSelectedItem();
                updateBranchCategory(br, category.getId());
            }
        });
        builder.create().show();
    }

    public void updateBranchType(BranchContract branch, int is_yezz) {
        branch.setIs_customer(""+is_yezz);
        branch.setStatus("update");
        (new Branch(this)).update(BranchContract.BranchEntry.TABLE_NAME,branch.toContentValues(), "id", branch.getId());
        goToMenuStore(branch);
    }

    public void updateBranchCategory(BranchContract branch, String category_id) {
        branch.setCategory(""+category_id);
        branch.setStatus("update");
        (new Branch(this)).update(BranchContract.BranchEntry.TABLE_NAME,branch.toContentValues(), "id", branch.getId());
        goToMenuStore(branch);
    }

    public void updateBranchHasPop(BranchContract branch, int has_pop) {
        branch.setHas_pop(""+has_pop);
        branch.setStatus("update");
        (new Branch(this)).update(BranchContract.BranchEntry.TABLE_NAME,branch.toContentValues(), "id", branch.getId());
        goToMenuStore(branch);
    }
}
