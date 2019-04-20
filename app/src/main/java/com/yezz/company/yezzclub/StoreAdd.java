package com.yezz.company.yezzclub;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yezz.company.yezzclub.Adapters.GenericArrayAdapter;
import com.yezz.company.yezzclub.lData.entities.Branch;
import com.yezz.company.yezzclub.lData.entities.Category;
import com.yezz.company.yezzclub.lData.entities.Chain;
import com.yezz.company.yezzclub.lData.entities.City;
import com.yezz.company.yezzclub.lData.entities.State;
import com.yezz.company.yezzclub.lData.entities.StoreType;
import com.yezz.company.yezzclub.lData.entities.Township;
import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CategoryContract;
import com.yezz.company.yezzclub.lData.entities.contracts.ChainContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CityContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StateContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreTypeContract;
import com.yezz.company.yezzclub.lData.entities.contracts.TownshipsContract;

import org.json.JSONException;
import java.util.List;

/**
 * Created by ramon_000 on 01-09-2016.
 */
public class StoreAdd extends YezzMeta implements AdapterView.OnItemSelectedListener {
    private DrawerNavigation menu;
    private Spinner sCity,sType, sState, sChain, sTownship, sCategory;
    private EditText etName,etCity,etCode,etAddress,etPhone,etTownship,etChain,etComments;
    private CheckBox cbIsYezz, cbBelongChain, cbHasPop;
    private TextView tvTownship,tvCity;
    private boolean alwaysHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_menu_store_add);

        if(this.checkBranchSession()){
            this.goToMenuStore(null);
        }

        this.iniComponent();
        this.iniListeners();
        this.etName.setText(this.getIntent().getExtras().getString("code"));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    protected void onStart() {
        super.onStart();
        this.iniUserJson();
        alwaysHide = false;
        try {
            if(this.user != null && user.getString("country_id").equals("47")) {
                this.sTownship.setVisibility(View.GONE);
                this.tvTownship.setVisibility(View.GONE);
                this.etTownship.setVisibility(View.GONE);
                this.tvCity.setText("Municipio");
                alwaysHide = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void iniComponent(){
        this.sCity = (Spinner)this.findViewById(R.id.sStoreAddCity);
        this.sState = (Spinner) this.findViewById(R.id.sStoreAddState);
        this.sType = (Spinner) this.findViewById(R.id.sStoreAddType);
        this.sTownship = (Spinner) this.findViewById(R.id.sStoreAddTownship);
        this.sCategory = (Spinner) this.findViewById(R.id.sStoreAddcategory);
        this.sChain = (Spinner) this.findViewById(R.id.sStoreAddChain);
        this.etName = (EditText) this.findViewById(R.id.etStoreAddName);
        this.etCode = (EditText) this.findViewById(R.id.etStoreAddCode);
        this.etAddress  = (EditText) this.findViewById(R.id.etStoreAddAddress);
        this.etCity = (EditText) this.findViewById(R.id.etStoreAddCity);
        this.etTownship = (EditText) this.findViewById(R.id.etStoreAddTownship);
        this.etPhone = (EditText) this.findViewById(R.id.etStoreAddphone);
        this.etChain = (EditText) this.findViewById(R.id.etStoreAddChain);
        this.etChain.setEnabled(false);
        this.etComments  = (EditText) this.findViewById(R.id.etStoreAddComments);
        this.cbIsYezz = (CheckBox) this.findViewById(R.id.cbIsYezz);
        this.cbHasPop = (CheckBox) this.findViewById(R.id.cbHasPop);
        this.cbBelongChain = (CheckBox) this.findViewById(R.id.cbStoreAddBelongChain);
        this.tvTownship = (TextView) this.findViewById(R.id.tvTownship);
        this.tvCity = (TextView) this.findViewById(R.id.tvCity);

        this.menu=new DrawerNavigation(this,
                (DrawerLayout)this.findViewById(R.id.drawer_store_add),
                (ListView) this.findViewById(R.id.left_drawer_store_add));
        loadSpinners();

        this.cbBelongChain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sChain.setVisibility(cbBelongChain.isChecked() ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void iniListeners() {
        this.sCity.setOnItemSelectedListener(this);
        this.sChain.setOnItemSelectedListener(this);
        this.sState.setOnItemSelectedListener(this);
        this.sType.setOnItemSelectedListener(this);
        this.sTownship.setOnItemSelectedListener(this);
        this.etPhone.didTouchFocusSelect();
        this.etPhone.requestFocus();
    }

    public void clickAddStore(View v) {
        CityContract  cityC   = (CityContract)this.sCity.getSelectedItem();
        ChainContract chainC  = (ChainContract)this.sChain.getSelectedItem();
        StateContract  stateC = (StateContract) this.sState.getSelectedItem();
        StoreTypeContract typeC = (StoreTypeContract) this.sType.getSelectedItem();
        TownshipsContract townshipC = (TownshipsContract)this.sTownship.getSelectedItem();
        CategoryContract categoryc = (CategoryContract)this.sCategory.getSelectedItem();

        if (this.etAddress.getText().toString().length() < 10) {
            this.showMsg("Debe indicar una dirección valida");
        } else if(stateC == null) {
            this.showMsg("Debe indicar el Estado");
        }else if(chainC == null  && this.sChain.isEnabled())
            this.showMsg("Debe indicar la cadena");
        else if(cityC == null && (this.etCity.getText().toString().equals("") ||
                this.etCity.getText().toString().length() < 3)){
            this.showMsg("Debe indicar una ciudad valida");
        }
        else if(typeC == null)
            this.showMsg("Debe indicar el tipo de canal");
        else {
            String countryId = "", userId = "", cityId ="", townshipId = "", chainId;
            try {
                if (this.user == null) {
                    iniUserJson();
                }
                countryId = this.user.getString("country_id");
                userId = this.user.getString("id");
            }catch (JSONException ex){
                Log.d("clickAddStore lastUser",ex.getMessage());
            }
            if(cbBelongChain.isChecked()) {
                if (chainC.getName().equalsIgnoreCase("otro")){
                    Chain chain = new Chain(this);
                    ChainContract chainContract = new ChainContract();
                    chainContract.setName(this.etChain.getText().toString());
                    chainContract.setUser_id(userId);
                    chainContract.setCountry_id(countryId);
                    chain.create(chainContract);
                    chainId = chainContract.getId();
                    this.showMsg("Cadena registrada");
                } else {
                    chainId = chainC.getId();
                }
            } else {
                chainId = "-1";
            }

            if(cityC == null) {
                City city = new City(this);
                CityContract cityContract = new CityContract();
                cityContract.setName(this.etCity.getText().toString());
                cityContract.setState_id(stateC.getId());
                city.create(cityContract);
                cityId=cityContract.getId();
                this.showMsg("Ciudad registrada");
            } else {
                cityId = cityC.getId();
            }

            if(townshipC == null && !this.etTownship.getText().toString().equals(""))
            {
                Township township = new Township(this);
                TownshipsContract townshipContract = new TownshipsContract();
                townshipContract.setName(this.etTownship.getText().toString());
                townshipContract.setCity_id(cityId);
                township.create(townshipContract);
                townshipId = townshipContract.getId();
                this.showMsg("Municipio registrado");
            } else if(townshipC != null){
                townshipId = townshipC.getId();
            }

            Branch branch =new Branch(this);
            BranchContract branchC = new BranchContract();
            branchC.setName(this.etName.getText().toString());
            branchC.setCode(this.etCode.getText().toString());
            branchC.setAddress(this.etAddress.getText().toString());
            branchC.setPhone(this.etPhone.getText().toString());
            branchC.setType_id(typeC.getId());
            branchC.setChain_id(chainId);
            branchC.setState_id(stateC.getId());
            branchC.setCountry_id(countryId);
            branchC.setCity_id(cityId);
            branchC.setTownship_id(townshipId);
            branchC.setIs_customer(cbIsYezz.isChecked() ? "1" : "0");
            branchC.setHas_pop(cbHasPop.isChecked() ? "1" : "0");
            branchC.setCategory(categoryc.getId());
            branchC.setComments(this.etComments.getText().toString());
            branch.create(branchC);
            this.showMsg("PDV registrada");

            this.goToMenuStore(branchC);
        }
    }

    public void loadSpinners(){
        Context context = getApplicationContext();

        //Declarando los arreglos genericos
        //type spinner
        GenericArrayAdapter<StoreTypeContract> typeAdapter;
        List<StoreTypeContract> dataType = (new StoreType(context)).getAllRegister();
        typeAdapter = new GenericArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataType);
        this.sType.setAdapter(typeAdapter);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //chain
        GenericArrayAdapter<ChainContract> chainAdapter;
        List<ChainContract> dataChain = (new Chain(context)).getAllRegister();

        if(dataChain.size() == 0){
            this.showChainEt(true);
        }else{
            this.showChainEt(false);
        }
        //se prepara la opcion otros
        dataChain.add(new ChainContract(null,"-1","-1","-1","Otro","-1","-1","-1","-1"));
        chainAdapter = new GenericArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataChain);
        this.sChain.setAdapter(chainAdapter);
        chainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //category
        List<CategoryContract> dataCategories = (new Category(getApplicationContext()))
                .getAllRegister();
        GenericArrayAdapter<CategoryContract> categoryAdapter = new GenericArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,dataCategories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.sCategory.setAdapter(categoryAdapter);

        //Estados
        List<StateContract> dataState = (new State(context)).getAllRegister();
        GenericArrayAdapter<StateContract> stateAdapter = new GenericArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,dataState);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.sState.setAdapter(stateAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

        Context context = getApplicationContext();
        if(parentView == findViewById(R.id.sStoreAddState))
        {
            StateContract selected = (StateContract)parentView.getItemAtPosition(position);

            GenericArrayAdapter<CityContract> cityAdapter;
            List<CityContract> dataCity = (new City(context)).getRegisters(CityContract.CityEntry.STATE_ID + " = ?", new String[]{selected.getId()});
            if (dataCity.size() > 0) {
                this.showCityEt(false);
                cityAdapter = new GenericArrayAdapter<>(this,android.R.layout.simple_spinner_item,dataCity);
                this.sCity.setAdapter(cityAdapter);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            } else {
                this.showCityEt(true);
            }
        }else if (parentView == findViewById(R.id.sStoreAddChain)){
            ChainContract selected = (ChainContract) parentView.getItemAtPosition(position);
            if(selected.getName().equals("Otro") && this.sChain.isEnabled())
                this.showChainEt(true);
            else{
                this.showChainEt(false);
            }
        } else if (parentView == findViewById(R.id.sStoreAddCity)){
            CityContract selected = (CityContract)parentView.getItemAtPosition(position);

            GenericArrayAdapter<TownshipsContract> townshipAdapter;
            List<TownshipsContract> dataTownship = (new Township(context)).getRegisters(TownshipsContract.TownshipsEntry.CITY_ID + " = ?", new String[]{selected.getId()});
            if (dataTownship.size() > 0) {
                this.showTownshipEt(false);
                dataTownship.add(new TownshipsContract(null,"-1","Otro","-1"));
                townshipAdapter = new GenericArrayAdapter<>(this,android.R.layout.simple_spinner_item,dataTownship);
                this.sTownship.setAdapter(townshipAdapter);
                townshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            } else {
                this.showTownshipEt(true);
            }
        } else if (parentView == findViewById(R.id.sStoreAddTownship)) {
            TownshipsContract selected = (TownshipsContract)parentView.getItemAtPosition(position);

            this.showTownshipEt(selected.getName().equalsIgnoreCase("Otro"));
        }
    }

    private void showChainEt(boolean visibility){
        this.etChain.setEnabled(visibility);
        this.etChain.setText("");
        this.etChain.setVisibility(visibility?View.VISIBLE:View.GONE);
        if (visibility){
            this.etChain.didTouchFocusSelect();
            this.etChain.requestFocus();
        }
    }

    private void showCityEt(boolean visibility){
        this.etCity.setEnabled(visibility);
        this.etCity.setText("");
        this.etCity.setVisibility(visibility?View.VISIBLE:View.GONE);
        this.showTownshipEt(visibility);
        if (visibility){
            if (this.etPhone.getText().toString().equals("")) {
                this.etPhone.didTouchFocusSelect();
                this.etPhone.requestFocus();
            } else {
                this.etCity.didTouchFocusSelect();
                this.etCity.requestFocus();
            }
        }
    }

    private void showTownshipEt(boolean visibility){
        if(alwaysHide == true) {
            visibility = false;
        }
        this.etTownship.setEnabled(visibility);
        this.etTownship.setText("");
        this.etTownship.setVisibility(visibility?View.VISIBLE:View.GONE);
        if (visibility){
            if (this.etPhone.getText().toString().equals("")) {
                this.etPhone.didTouchFocusSelect();
                this.etPhone.requestFocus();
            } else {
                this.etTownship.didTouchFocusSelect();
                this.etTownship.requestFocus();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void goToMenuStore(@Nullable BranchContract branch){
        if(branch!=null){
                this.locationStart();
                showLoad(3000);
                this.saveUbication(branch.getId());
            Log.d("SAVE UBICATION STORE",branch.getId());
                this.startBranchSession(branch.getId());
                if (branch.getContact() != null) {
                    Intent intent = new Intent(this,StoreMenu.class);
                    intent.putExtra("branchId", branch.getId());
                    intent.putExtra("isYezz",branch.getIs_customer());
                    intent.putExtra("hasPop",branch.getHas_pop());
                    this.startActivity(intent);
                } else {
                    Intent intent = new Intent(this, Customer.class);
                    intent.putExtra("branchCode", branch.getCode());
                    startActivity(intent);
                }
                this.finish();
        }else{
            this.showMsg("Intente Nuevamente");
        }
    }
}
