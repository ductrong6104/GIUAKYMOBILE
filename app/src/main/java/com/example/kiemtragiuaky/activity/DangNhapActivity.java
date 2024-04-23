package com.example.kiemtragiuaky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.kiemtragiuaky.R;
import com.example.kiemtragiuaky.model.DangNhapModel;
import com.example.kiemtragiuaky.model.InputValidator;
import com.example.kiemtragiuaky.service.DangNhapService;
import com.example.kiemtragiuaky.singleton.MyApplication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DangNhapActivity extends AppCompatActivity {
    private DangNhapService apiService;
    private EditText etEmail;
    private EditText etMatKhau;
    private TextView tvQuenMK;
    private Button buttonDangNhap;
    private TextView tvDangKy;
    private ImageView ivHienMK;
    private boolean hienMK = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        setControl();
        setEvent();
    }

    private void setControl() {
        etEmail = findViewById(R.id.etEmail);
        etMatKhau = findViewById(R.id.etMatKhau);
        tvQuenMK = findViewById(R.id.tvQuenMK);
        buttonDangNhap = findViewById(R.id.btDangNhap);
        tvDangKy = findViewById(R.id.tvDangKy);
        ivHienMK = findViewById(R.id.ivHienMK);
    }

    private void setEvent() {
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDangKyActivity();

            }
        });

        ivHienMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hienMK) {
                    hienMK = false;
                    etMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivHienMK.setImageResource(R.drawable.show);
                } else {
                    hienMK = true;
                    etMatKhau.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivHienMK.setImageResource(R.drawable.hide);
                }

                // move cursor to end of password
                etMatKhau.setSelection(etMatKhau.length());
            }
        });

        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    // Khởi tạo Retrofit instance
                    String ip = getIpv4();

                    Retrofit retrofit = ((MyApplication) getApplicationContext()).getRetrofit();
                    apiService = retrofit.create(DangNhapService.class);

                    String taiKhoan = etEmail.getText().toString();
                    String matKhau = etMatKhau.getText().toString();
                    // Gọi API
                    Call<DangNhapModel> call = apiService.findByTaiKhoanAndMatKhau(taiKhoan, matKhau);
                    
                    call.enqueue(new Callback<DangNhapModel>() {
                        @Override
                        public void onResponse(Call<DangNhapModel> call, Response<DangNhapModel> response) {
                            if (response.isSuccessful()) {
                                // Xử lý dữ liệu trả về từ API ở đây
                                DangNhapModel data = new DangNhapModel();
                                data = response.body();
                                
                                Log.d("MainActivity", "API Response: " + data.toString());
                                Toast.makeText(DangNhapActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                            } else {
                                // Xử lý trường hợp lỗi
                                Log.e("MainActivity", "API Error: " + response.message());
                                Toast.makeText(DangNhapActivity.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DangNhapModel> call, Throwable t) {
                            // Xử lý trường hợp lỗi kết nối
                            Log.e("MainActivity", "Network Error: " + t.getMessage());
                            Toast.makeText(DangNhapActivity.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }

    public String getIpv4(){
        String ip = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if(Inet4Address.class == addr.getClass()){
                        ip = addr.getHostAddress();
                        System.out.println(iface.getDisplayName() + " " + ip);
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        Log.d("ipv4", ip);
        return ip;
    }
    public void setDangKyActivity() {
        Intent intent = new Intent(this, DangKyActivity.class);
        startActivity(intent);
    }

    public void setHomeActivity() {
        Intent intent = new Intent(this, DangKyActivity.class);
        startActivity(intent);
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
        if (InputValidator.isNullOrEmpty(matKhau)) {
            etMatKhau.setError("Mật khẩu không để trống");
            return false;
        }
        else if (!InputValidator.isValidPassword(matKhau)) {
            etMatKhau.setError("Mật khẩu chứa ít nhất 8 kí tự");
            return false;
        }
        etMatKhau.setError(null);
        return true;
    }


}