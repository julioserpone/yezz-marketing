package com.yezz.company.yezzclub;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.yezz.company.yezzclub.Adapters.GenericArrayAdapter;
import com.yezz.company.yezzclub.lData.YezzSendData;
import com.yezz.company.yezzclub.lData.entities.Phone;
import com.yezz.company.yezzclub.lData.entities.PhoneBrand;
import com.yezz.company.yezzclub.lData.entities.PhoneModel;
import com.yezz.company.yezzclub.lData.entities.contracts.BrandPhoneContract;
import com.yezz.company.yezzclub.lData.entities.contracts.PhoneContract;
import com.yezz.company.yezzclub.lData.entities.contracts.PhoneModelContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramon_000 on 14-09-2016.
 */
public class Quantitative extends YezzMeta implements AdapterView.OnItemSelectedListener{
    private DrawerNavigation menu;

    private EditText etStock,etSalePrice,etExhibition, etSale,etPurchasePrice,etBrand,etModel,etComment;

    private ImageButton ibPicture;

    private Spinner sBrand,sModel;

    private String picture = null;

    private @Nullable String phone_id,store,price;

    private ImageView imageView;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.phone_id = getIntent().getStringExtra("id_phone");
        this.price = getIntent().getStringExtra("price");
        this.store = getIntent().getStringExtra("store");

        if(this.phone_id == null) {
            this.setContentView(R.layout.activity_menu_quantitative);
            this.menu=new DrawerNavigation(this,
                    (DrawerLayout)this.findViewById(R.id.drawer_quantitative),
                    (ListView) this.findViewById(R.id.left_drawer_quantitative));
        } else {
            //this.showMsg(this.phone_id);
            this.showMsg("Recuerde que el precio debe estar entre un rango marcado de 20% por arriba y por debajo del precio del producto yezz");
            this.setContentView(R.layout.activity_menu_quantitative_competition);

            this.menu=new DrawerNavigation(this,
                    (DrawerLayout)this.findViewById(R.id.drawer_quantitative_competition),
                    (ListView) this.findViewById(R.id.left_drawer_quantitative_competition));
            this.etBrand = (EditText) this.findViewById(R.id.etQuantitativeBrand);
            this.etModel = (EditText) this.findViewById(R.id.etQuantitativeModel);
        }
        this.iniComponent();
        this.iniListeners();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    private void iniComponent(){
        this.etStock = (EditText) this.findViewById(R.id.etQuantitativeStock);
        this.etSalePrice = (EditText) this.findViewById(R.id.etQuantitativeSalePrice);
        this.etExhibition = (EditText) this.findViewById(R.id.etQuantitativeExhibition);
        this.etSale = (EditText) this.findViewById(R.id.etQuantitativeSales);
        this.etPurchasePrice = (EditText) this.findViewById(R.id.etQuantitativePurchasePrice);
        this.etComment = (EditText) this.findViewById(R.id.etQuantitativePictureComment);
        this.etModel = (EditText) this.findViewById(R.id.etQuantitativeModel);
        this.etBrand = (EditText) this.findViewById(R.id.etQuantitativeBrand);

        this.ibPicture = (ImageButton) this.findViewById(R.id.ibQuantitativeExhibition);
        this.sBrand = (Spinner) this.findViewById(R.id.sQuantitativeBrand);
        this.sModel = (Spinner) this.findViewById(R.id.sQuantitativeModel);

        this.etExhibition.setOnFocusChangeListener(this.focusLost);

        if(this.phone_id != null){
            //this.showMsg(phone_id);
            float precio = Float.parseFloat(this.price);
            float porcentaje;
            porcentaje = (precio * 20) /100;
            this.etSalePrice.setHint("Precio (" + (precio - porcentaje) + " - " + (precio + porcentaje)+")");
        }

        Context context = getApplicationContext();
        GenericArrayAdapter<BrandPhoneContract> adapterB;
        GenericArrayAdapter<PhoneModelContract> adapterM;

        List<BrandPhoneContract> dataStoreB = ( new PhoneBrand(context) ).getPhoneBrandYezz((this.phone_id == null) ? true : false);

        if(phone_id!= null){
            dataStoreB.add(new BrandPhoneContract(null,"otro","",""));
        }
        List<PhoneModelContract> dataStoreM = new ArrayList<>();
        dataStoreM.add(new PhoneModelContract(null,"otro","","",""));

        adapterB = new GenericArrayAdapter<>(this,android.R.layout.simple_spinner_item,dataStoreB);
        adapterM = new GenericArrayAdapter<>(this,android.R.layout.simple_spinner_item,dataStoreM);

        this.sBrand.setAdapter(adapterB);
        this.sModel.setAdapter(adapterM);

        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        imageView = (ImageView)findViewById(R.id.imageView);

    }

    private void iniListeners()
    {
        this.sBrand.setOnItemSelectedListener(this);
        this.sModel.setOnItemSelectedListener(this);
    }

    private void showEditTextFromOthers(EditText et,boolean visibility){
        et.setVisibility(visibility ? View.VISIBLE : View.GONE);
        et.setEnabled(visibility);
        et.setText(visibility?et.getText():"");
        if (visibility){
            et.requestFocus();
        }
    }

    private String getEndValue(String value,EditText control){
        return value!=null?value:control.getText().toString();
    }

    private View.OnFocusChangeListener focusLost = new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            if(!etExhibition.getText().toString().equals("")) {
                if(Integer.parseInt( etExhibition.getText().toString() ) > 0){
                    ibPicture.setVisibility(View.VISIBLE);
                    ibPicture.requestFocus();
                } else {
                    ibPicture.setVisibility(View.GONE);
                }
            } else {
                    ibPicture.setVisibility(View.GONE);
            }
        }
        }
    };

    public void clickQuantitativeTakePicture(View view)
    {
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
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            try {
                // reciclo el bitmap
                if (bitmap != null) {
                    bitmap.recycle();
                }
                final Uri imageUri = data.getData();
                if(imageUri != null)
                {
                    stream = getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(stream);
                    imageView.setImageBitmap(bitmap);
                }
                else
                {
                    bitmap=(Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                }
                imageView.setVisibility(View.VISIBLE);
                imageView.requestFocus();

                //codifico la imagen
                this.picture = this.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG,100);
                etComment.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                etComment.setVisibility(View.GONE);
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        this.etComment.requestFocus();
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

    public void clickQuantitativeSend(View view)
    {
        String brand, model, branchId = "";

        BrandPhoneContract brandC = (BrandPhoneContract)this.sBrand.getSelectedItem();
        PhoneModelContract modelC = (PhoneModelContract)this.sModel.getSelectedItem();

        if(brandC.getName().equals("otro")){
            if(this.etBrand.getText().toString().equalsIgnoreCase("otro") ||
                    this.etBrand.getText().toString().equalsIgnoreCase("otros")){
                brand = "";
            }else{
                brand = this.etBrand.getText().toString();
            }
        } else{
            brand = brandC.getName();
        }

        if(modelC.getName().equals("otro"))
        {
            if(this.etModel.getText().toString().equalsIgnoreCase("otro") ||
                    this.etModel.getText().toString().equalsIgnoreCase("otros")){
                model = "";
            }else{
                model = this.etModel.getText().toString();
            }
        }else{
            model = modelC.getName();
        }

        if(!this.etExhibition.getText().toString().equals("") && this.picture == null){
            this.showMsg("tome la foto evidencia");
        }else if(brand.equals("")) {
            this.showMsg("Debe indicar la marca del producto");
        }else if(model.equals("")) {
            this.showMsg("Debe indicar el modelo del producto");
        }else if(this.etStock.getText().toString().equals("")) {
            this.showMsg("Debe indicar el stock del producto");
        }else if(this.etSalePrice.getText().toString().equals("")) {
            this.showMsg("Debe indicar el precio del producto");
        }/*else if(this.etPurchasePrice.getText().toString().equals("")) {
            this.showMsg("Debe indicar el precio de compra del producto");
        }*/else if(this.etExhibition.getText().toString().equals("")) {
            this.showMsg("Debe indicar la cantidad en exhibicion");
        }else if(this.etSale.getText().toString().equals("")) {
            this.showMsg("Debe indicar la venta del dispositivo");
        } else {
            String userId="",productId;

            if (brandC.getName().equals("otro")) {
                PhoneBrand bp = new PhoneBrand(getBaseContext());
                BrandPhoneContract brandContract =new BrandPhoneContract(null, brand,(this.phone_id == null) ? "1" : "0","new");
                bp.create(brandContract);
            }

            if(modelC.getName().equals("otro")){
                PhoneModel pm = new PhoneModel(getBaseContext());
                PhoneModelContract modelContract = new PhoneModelContract(null,model, brand,"0","new");
                pm.create(modelContract);
                productId = null;
            } else {
                productId = modelC.getProduct_id();
            }

            try{
                this.iniUserJson();
                userId=this.user.getString("id");

                JSONObject branchInfo = this.getBranchSession();
                if(branchInfo != null){
                    branchId = branchInfo.getString("key");
                }
            }catch (JSONException e){
                Log.d("get user ID",e.getMessage());
            }

            Phone phone=new Phone(this.getApplicationContext());
            PhoneContract phoneContract = new PhoneContract();
            phoneContract.setBrand(brand);
            phoneContract.setExhibition(this.etExhibition.getText().toString());
            phoneContract.setExhibition_media(picture);
            phoneContract.setExhibition_media_comment(this.etComment.getText().toString());
            phoneContract.setModel(model);
            phoneContract.setParent_id(this.phone_id);
            phoneContract.setSale_price(this.etSalePrice.getText().toString());
            phoneContract.setPurchase_price(this.etPurchasePrice.getText().toString());
            phoneContract.setSales(this.etSale.getText().toString());
            phoneContract.setStock(this.etStock.getText().toString());
            phoneContract.setStore(this.store);
            phoneContract.setUser(userId);
            phoneContract.setBranch_id(branchId);
            phone.createAndReturn(phoneContract);

            if(productId == null) {
                productId = phoneContract.getId();
            }

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(this.getString(R.string.end_process));
            if (this.phone_id == null) {
                this.phone_id = productId;//String.valueOf(phoneContract.getId());
                this.price = this.etSalePrice.getText().toString();

                builder.setMessage(this.getString(R.string.msg_form_quantitative));
                builder.setNegativeButton(this.getString(R.string._continue), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    goToCompetition();
                    }
                });
            } else {
                builder.setMessage(this.getString(R.string.msg_dialog_end_process));
                builder.setNegativeButton(this.getString(R.string.add_another), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToCompetition();
                    }
                });
            }
            builder.setPositiveButton(this.getString(R.string.finalize), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                }
            });
            builder.create().show();
        }
    }

    private void goToCompetition(){
        Intent competition = new Intent(this,Quantitative.class);
        competition.putExtra("id_phone",this.phone_id);
        competition.putExtra("store",this.store);
        competition.putExtra("price",this.price);
        this.startActivity(competition);
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        Context context = getApplicationContext();
        if(parentView == findViewById(R.id.sQuantitativeBrand)) {
            BrandPhoneContract selected = (BrandPhoneContract)parentView.getItemAtPosition(position);
            if(selected.getName().equals("otro")){
                this.changeStateEditText(this.etBrand,true);
                this.etBrand.requestFocus();
            } else {
                GenericArrayAdapter<PhoneModelContract> adapterM;
                List<PhoneModelContract> dataStoreM = ( new PhoneModel(context) ).getModelByBrand(selected.getName());
                dataStoreM.add(new PhoneModelContract(null,"otro","","",""));
                adapterM = new GenericArrayAdapter<>(this,android.R.layout.simple_spinner_item,dataStoreM);
                this.sModel.setAdapter(adapterM);
                adapterM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.sModel.setAdapter(adapterM);
                this.etBrand.setText(selected.getName());
                this.changeStateEditText(this.etBrand,false);
            }
        } else if (parentView == findViewById(R.id.sQuantitativeModel)){
            PhoneModelContract selected = (PhoneModelContract) parentView.getItemAtPosition(position);
            if(selected.getName().equals("otro")) {
                this.changeStateEditText(this.etModel, true);
                this.etModel.requestFocus();
            } else {
                this.etModel.setText(selected.getName());
                this.changeStateEditText(this.etModel,false);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void changeStateEditText(EditText et,boolean state){
        et.setVisibility(state?View.VISIBLE:View.GONE);
        et.setText("");
    }
}