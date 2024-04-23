package com.example.kiemtragiuaky.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import com.example.kiemtragiuaky.R;
import com.example.kiemtragiuaky.model.DangNhapModel;
import com.example.kiemtragiuaky.model.EmailSender;
import com.example.kiemtragiuaky.service.DangNhapService;
import com.example.kiemtragiuaky.singleton.MyApplication;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OtpActivity extends AppCompatActivity {
    private DangNhapService apiService;
    private EditText otp1, otp2, otp3, otp4, otp5, otp6;
    private TextView guiLaiOtp, tvEmail;
    private boolean resendEnable = false;
    private AppCompatButton btXacNhan;
    private int resendTime = 60;
    private int viTriOtp = 0;

    private String getMail;
    private String getMatKhau;
    private String OtpCurrent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getStringIntent();
        setControl();
        setEvent();

//        final String getEmail = getIntent().getStringExtra("etEmail");

        showKeyBoard(otp1);
        otp1.addTextChangedListener(textWatcher);
        otp2.addTextChangedListener(textWatcher);
        otp3.addTextChangedListener(textWatcher);
        otp4.addTextChangedListener(textWatcher);
        otp5.addTextChangedListener(textWatcher);
        otp6.addTextChangedListener(textWatcher);

        sendMail();
        demNguocTgGuiOtp();
        setEvent();

    }

    private void getStringIntent() {
        getMail = getIntent().getStringExtra("email");
        getMatKhau = getIntent().getStringExtra("matKhau");
    }


    private void sendMail() {

        tvEmail.setText(getMail);

        String recipientEmail = getMail;
        String subject = "Your OTP";
        String otpRandom = generateOTP();
        OtpCurrent = otpRandom;
        String body = "Your OTP code is: " + otpRandom; // Replace with your OTP code

        EmailSender emailSender = new EmailSender("n20dccn079@student.ptithcm.edu.vn", "n20dccn079#260902");
        emailSender.sendOTP(recipientEmail, subject, body);
    }


    private void setEvent() {
        guiLaiOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resendEnable){
                    sendMail();
                    demNguocTgGuiOtp();
                }
            }
        });

        btXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String otp = otp1.getText().toString()
                        + otp2.getText().toString()
                        + otp3.getText().toString()
                        + otp4.getText().toString()
                        + otp5.getText().toString()
                        + otp6.getText().toString();

                if (otp.length() == 6){
                    // xu ly xac thuc
                    if (otp.equals(OtpCurrent)){
//                        try {
//                            SQLConnection sql = new SQLConnection();
//                            Connection connection = sql.connect();
//                            Statement statement = connection.createStatement();
//                            ResultSet resultat = statement.executeQuery("abc");
//
//                            resultat.close();
//                            statement.close();
//                        } catch (SQLException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                            throw new RuntimeException(e);
//                        }
//                        MyDialog.showDialog(OtpActivity.this, "Thông báo", "Tạo tài khoản thành công!, Vui lòng đăng nhập");
                        Retrofit retrofit = ((MyApplication) getApplicationContext()).getRetrofit();
                        apiService = retrofit.create(DangNhapService.class);

                        DangNhapModel modelDN = new DangNhapModel(getMail, getMatKhau, "sinhvien");
                        // Gọi API
                        Call<Object> call = apiService.setAccount(modelDN);

                        call.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if (response.isSuccessful()) {
                                    // Xử lý dữ liệu trả về từ API ở đây
                                    Object data = new DangNhapModel();
                                    data = (Object) response.body();
                                    Toast.makeText(OtpActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(OtpActivity.this, DangNhapActivity.class));
                                    Log.d("MainActivity", "API Response: " + data.toString());

                                } else {
                                    // Xử lý trường hợp lỗi
                                    Log.e("MainActivity", "API Error: " + response.message());
                                    Toast.makeText(OtpActivity.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                // Xử lý trường hợp lỗi kết nối
                                Log.e("MainActivity", "Network Error: " + t.getMessage());
                                Toast.makeText(OtpActivity.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        tvEmail.setError("Mã sai, vui lòng nhập lại");
                    }

                }
                else {
                    tvEmail.setError("Nhập đủ 6 số");
                }

            }
        });
    }

    private void setControl() {
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);
        guiLaiOtp = findViewById(R.id.tvGuiLaiOTP);
        btXacNhan = findViewById(R.id.btXacNhan);
        tvEmail = findViewById(R.id.tvEmail);
    }

    public static String generateOTP() {
        // Độ dài của mã OTP
        int otpLength = 6;

        // Tạo một đối tượng StringBuilder để xây dựng chuỗi OTP
        StringBuilder otp = new StringBuilder();

        // Sử dụng đối tượng Random để tạo các chữ số ngẫu nhiên
        Random random = new Random();

        // Tạo mã OTP gồm 6 chữ số
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10)); // Sinh số ngẫu nhiên từ 0 đến 9 và thêm vào chuỗi OTP
        }

        return otp.toString(); // Trả về chuỗi OTP hoàn chỉnh
    }


    // dem nguoc thoi gian gui otp
    private void demNguocTgGuiOtp(){
        resendEnable = false;
        guiLaiOtp.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime*1000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                guiLaiOtp.setText("Gửi lại otp trong (" + millisUntilFinished/1000+")");
            }

            @Override
            public void onFinish() {
                resendEnable = true;
                guiLaiOtp.setText("Gửi lại OTP");
                guiLaiOtp.setTextColor(getResources().getColor(R.color.blue));
            }
        }.start();
    }
    // su kien lang nghe khi thay doi text trong edittext
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0){
                if (viTriOtp == 0){
                    viTriOtp = 1;
                    showKeyBoard(otp2);
                }
                else if (viTriOtp == 1){
                    viTriOtp = 2;
                    showKeyBoard(otp3);
                }
                else if (viTriOtp == 2){
                    viTriOtp = 3;
                    showKeyBoard(otp4);
                }
                else if (viTriOtp == 3){
                    viTriOtp = 4;
                    showKeyBoard(otp5);
                }
                else if (viTriOtp == 4){
                    viTriOtp = 5;
                    showKeyBoard(otp6);
                }


            }
        }
    };

    private void showKeyBoard(EditText etOtp){
        etOtp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(etOtp, InputMethodManager.SHOW_IMPLICIT);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL | keyCode == KeyEvent.KEYCODE_BACK){
            if (viTriOtp == 5){
                viTriOtp =4;
                showKeyBoard(otp5);
            }
            else if (viTriOtp == 4){
                viTriOtp =3;
                showKeyBoard(otp4);
            }
            else if (viTriOtp == 3){
                viTriOtp =2;
                showKeyBoard(otp3);
            }
            else if (viTriOtp == 2){
                viTriOtp =1;
                showKeyBoard(otp2);
            }
            else if (viTriOtp == 1){
                viTriOtp =0;
                showKeyBoard(otp1);
            }
            return true;
        }
        else {
            return super.onKeyUp(keyCode, event);
        }

    }
}