package com.yezz.company.yezzclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yezz.company.yezzclub.lData.entities.Branch;
import com.yezz.company.yezzclub.lData.entities.StoreContact;
import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreContactContract;

/**
 * Created by SairioA on 20-04-2017.
 */
public class Customer extends YezzMeta {

    private EditText etName,etSurname,etStorePosition,etPhone,etEmail,etAddress;
    private String branchCode;
    private String branchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initComponent();
        this.branchId = getIntent().getExtras().getString("branchId");
    }

    public void initComponent() {
        this.etName = (EditText) this.findViewById(R.id.etCustomerName);
        this.etSurname = (EditText) this.findViewById(R.id.etCustomerSurname);
        this.etStorePosition = (EditText) this.findViewById(R.id.etCustomerPosition);
        this.etPhone = (EditText) this.findViewById(R.id.etCustomerPhone);
        this.etEmail = (EditText) this.findViewById(R.id.etCustomerEmail);
        this.etAddress = (EditText) this.findViewById(R.id.etCustomerAddress);
    }

    public void saveCustomer(View view) {
        BranchContract branch=new Branch(this)
                .getRegister(BranchContract.BranchEntry.ID + " = ?",new String[]{branchId});
        String name = this.etName.getText().toString().trim();
        String surname = this.etSurname.getText().toString().trim();
        String phone = this.etPhone.getText().toString().trim();
        String email = this.etEmail.getText().toString().trim();
        boolean valid = true;

        if(name.length() < 3) {
            this.showMsg("Debe indicar el nombre de contacto");
            valid = false;
        } else if(surname.length() < 3) {
            this.showMsg("Debe indicar el apellido de contacto");
            valid = false;
        } else if(email.length() < 6) {
            this.showMsg("Debe indicar un correo valido");
            valid = false;
        } else if(branch != null && valid) {
            StoreContactContract customer = new StoreContactContract();
            customer.setName(this.etName.getText().toString());
            customer.setSurname(this.etSurname.getText().toString());
            customer.setStore_position(this.etStorePosition.getText().toString());
            customer.setAddress(this.etAddress.getText().toString());
            customer.setPhone(this.etPhone.getText().toString());
            customer.setEmail(this.etEmail.getText().toString());

            StoreContact mgr = new StoreContact(this);
            mgr.create(customer);

            branch.setContact(customer.getId());
            (new Branch(this)).update(BranchContract.BranchEntry.TABLE_NAME,branch.toContentValues(),
                    "id", branch.getId());

            startActivity(new Intent(this,StoreMenu.class));
            this.finish();
        } else {
            this.showMsg("Error in contact");
        }
    }

    public void cancelRegister(View view) {
        startActivity(new Intent(this,StoreMenu.class));
        this.finish();
    }
}
