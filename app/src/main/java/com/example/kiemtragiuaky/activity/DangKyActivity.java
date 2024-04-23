package com.example.kiemtragiuaky.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.kiemtragiuaky.R;
import com.example.kiemtragiuaky.model.InputValidator;


public class DangKyActivity extends AppCompatActivity {

    private EditText etEmail, etSDT, etMatKhau, etXacNhanMatKhau;
    private AppCompatButton btTiepTuc;
    private TextView tvDangNhap;

    private ImageView ivHienMK, ivHienMKNL;
    private boolean hienMK = false;
    private boolean hienMKNL = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        setControl();
        setEvent();



    }

    private void setEvent() {
        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });

        btTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()){

                    Intent intent = new Intent(DangKyActivity.this, OtpActivity.class);
                    intent.putExtra("email", etEmail.getText().toString());
                    intent.putExtra("matKhau", etMatKhau.getText().toString());
                    startActivity(intent);
                }
            }
        });

        ivHienMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hienMK){
                    hienMK = false;
                    etMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivHienMK.setImageResource(R.drawable.show);
                }
                else{
                    hienMK = true;
                    etMatKhau.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivHienMK.setImageResource(R.drawable.hide);
                }
                etMatKhau.setSelection(etMatKhau.length());
            }
        });
        ivHienMKNL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hienMKNL){
                    hienMKNL = false;
                    etXacNhanMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivHienMKNL.setImageResource(R.drawable.show);
                }
                else{
                    hienMKNL = true;
                    etXacNhanMatKhau.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivHienMKNL.setImageResource(R.drawable.hide);
                }
                etXacNhanMatKhau.setSelection(etXacNhanMatKhau.length());
            }
        });
    }

    private void setControl() {
        etEmail = findViewById(R.id.etEmail);
        etMatKhau = findViewById(R.id.etMatKhau);
        etXacNhanMatKhau = findViewById(R.id.etNhapLaiMatKhau);
        btTiepTuc = findViewById(R.id.btTiepTuc);
        tvDangNhap = findViewById(R.id.tvDangKy);
        ivHienMK = findViewById(R.id.ivHienMK);
        ivHienMKNL = findViewById(R.id.ivHienMKNhapLai);
    }

    private boolean validateInput(){
        return validateEmail() && validateMatKhau();
    }
    private boolean validateEmail() {
        String email = etEmail.getText().toString();
        if (InputValidator.isNullOrEmpty(email)) {
            etEmail.setError("Email không để trống");
            return false;
        } else if (!InputValidator.isValidEmail(email)){
            etEmail.setError("Vui nhập đúng định email, eg: name@gmail.com");
            return false;
        }


        etEmail.setError(null);
        return true;

    }

    private boolean validateMatKhau(){
        String matKhau = etMatKhau.getText().toString();
        String matKhauNhapLai = etXacNhanMatKhau.getText().toString();
        if (InputValidator.isNullOrEmpty(matKhau)) {
            etMatKhau.setError("Mật khẩu không để trống");
            return false;
        }
        else if (!InputValidator.isValidPassword(matKhauNhapLai)) {
            etMatKhau.setError("Mật khẩu chứa ít nhất 8 kí tự");
            return false;
        }
        else if (InputValidator.isNullOrEmpty(matKhauNhapLai)) {
            etXacNhanMatKhau.setError("Mật khẩu xác nhận không để trống");
            return false;
        }
        else if (!matKhau.equals(matKhauNhapLai)) {
            etXacNhanMatKhau.setError("Mật khẩu xác nhận sai");
            return false;
        }

        etMatKhau.setError(null);
        etXacNhanMatKhau.setError(null);
        return true;
    }
}