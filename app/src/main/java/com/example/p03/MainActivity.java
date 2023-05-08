package com.example.p03;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    EditText etAmount;
    EditText etPax;
    ToggleButton toggleSvs;
    ToggleButton toggleGst;
    EditText etDiscount;
    RadioGroup rgPay;
    Button btnSplit;
    Button btnReset;
    TextView totalbill;
    TextView eachpay;
    EditText etPhonePay;
    RadioButton radioCash;
    RadioButton radioPaynow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount =  findViewById(R.id.etAmount);
        etPax = findViewById(R.id.etPax);
        toggleSvs =  findViewById(R.id.toggleSvs);
        toggleGst = findViewById(R.id.toggleGst);
        etDiscount = findViewById(R.id.etDiscount);
        rgPay =  findViewById(R.id.rgPay);
        radioCash =  findViewById(R.id.radioCash);
        radioPaynow =  findViewById(R.id.radioPaynow);
        btnSplit = findViewById(R.id.btnSplit);
        btnReset = findViewById(R.id.btnReset);
        totalbill = findViewById(R.id.totalbill);
        eachpay= findViewById(R.id.eachpay);
        etPhonePay = findViewById(R.id.etPhonePay);

//        rgPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int checkedRadioID = rgPay.getCheckedRadioButtonId();
//                etPhonePay.setEnabled(checkedRadioID == R.id.radioPaynow);
//            }
//        });
        rgPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if ((radioCash.isChecked())){
                    etPhonePay.setEnabled(false);
                    etPhonePay.getText().clear();
                    etPhonePay.setInputType(InputType.TYPE_NULL);
                    etPhonePay.setFocusableInTouchMode(false);
                } else if (radioPaynow.isChecked()) {
                    etPhonePay.setEnabled(true);
                    etPhonePay.setFocusableInTouchMode(true);
                } else {
                    etPhonePay.setEnabled(false);
                    etPhonePay.getText().clear();
                    etPhonePay.setInputType(InputType.TYPE_NULL);
                    etPhonePay.setFocusableInTouchMode(false);
                }
            }
        });




        btnSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//        double discountCheck = 0;
        String amount_str = etAmount.getText().toString();
        String discount_str =  etDiscount.getText().toString();
        final double[] amount_d = {Double.parseDouble(amount_str)};
        double discount_d = 0;
        if (discount_str.equals("")) {
            discount_d =  0;
        } else {
            discount_d = Double.parseDouble(discount_str);
        }

//        if (discount_str == "") {
//            discountCheck = 0;
//        } else {
//            discountCheck  = Double.parseDouble(discount_str);
//        }
//        double discount_d =  discountCheck;

        String pax_str = etPax.getText().toString();
        int pax_int = Integer.parseInt(pax_str);


                if (toggleSvs.isChecked()){
                    amount_d[0] *= 1.1;
                } else {
                    amount_d[0] *= 1;
                }

                if (toggleGst.isChecked()){
                    amount_d[0] *= 1.07;
                } else {
                    amount_d[0] *= 1;
                }


        double amount_new = amount_d[0];
        double bill_d = amount_new * (1-(discount_d/100));
        double split_d = (bill_d/pax_int);
        String payment = "";
        String bill = String.format("%.2f", bill_d );
        String split = String.format("%.2f", split_d);

        String phone = etPhonePay.getText().toString();
        int checkedRadioID = rgPay.getCheckedRadioButtonId();
        if (checkedRadioID == R.id.radioCash){
           payment= " in cash";
        } else if (checkedRadioID == R.id.radioPaynow){
            payment= " via Paynow to " + phone;
        } else {
            payment = "ERROR PAYMENT TYPE NOT SELECTED";
        }
        if ((amount_new < 1) || (discount_d >= 100) || (pax_int < 1) || (discount_d < 0 )){
            totalbill.setTextColor(Color.RED);
            eachpay.setTextColor(Color.RED);
            totalbill.setText("IMPROPER INPUTS");
            eachpay.setText("KINDLY INSERT VALID VALUES");

        } else {
            totalbill.setTextColor(Color.BLACK);
            eachpay.setTextColor(Color.BLACK);
            totalbill.setText("Total Bill: $"+bill+payment);
            eachpay.setText("Each Pays: $"+split+payment);
        }






            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAmount.getText().clear();
                etPax.getText().clear();
                etDiscount.getText().clear();
                radioCash.setChecked(true);
                radioPaynow.setChecked(false);
                toggleGst.setChecked(false);
                toggleSvs.setChecked(false);
                etPhonePay.getText().clear();
                totalbill.setText(R.string.totalBill);
                eachpay.setText(R.string.eachPays);
            }
        });







    }
}