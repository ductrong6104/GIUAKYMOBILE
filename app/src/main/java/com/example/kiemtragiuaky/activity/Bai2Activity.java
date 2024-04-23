package com.example.kiemtragiuaky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kiemtragiuaky.R;

import java.text.DecimalFormat;

public class Bai2Activity extends AppCompatActivity {

    EditText so1, so2, so3;
    Button btnCong, btnTru, btnNhan, btnChia;
    TextView tvKiemtra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double number1 = Double.parseDouble(so1.getText().toString());
                double number2 = Double.parseDouble(so2.getText().toString());
                double result = number1 + number2;

                so3.setText(removeDecimalIfZero(result));
                kiemTraPhepTinh(number1, number2, result);
            }
        });
        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double number1 = Double.parseDouble(so1.getText().toString());
                double number2 = Double.parseDouble(so2.getText().toString());
                double result = number1 - number2;
                so3.setText(removeDecimalIfZero(result));
                kiemTraPhepTinh(number1, number2, result);
            }
        });
        btnNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double number1 = Double.parseDouble(so1.getText().toString());
                double number2 = Double.parseDouble(so2.getText().toString());
                double result = number1 * number2;
                so3.setText(removeDecimalIfZero(result));
                kiemTraPhepTinh(number1, number2, result);
            }
        });
        btnChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double number1 = Double.parseDouble(so1.getText().toString());
                double number2 = Double.parseDouble(so2.getText().toString());
                double result = number1 / number2;
                so3.setText(removeDecimalIfZero(result));
                kiemTraPhepTinh(number1, number2, result);
            }
        });
    }

    public static String removeDecimalIfZero(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#." + "0".repeat(2));
        // Kiểm tra xem phần thập phân có bằng 0 không
        if (number == (int) number) {
            // Nếu là số nguyên, chuyển đổi thành chuỗi kiểu int và trả về
            return String.valueOf((int) number);
        } else {
//            // Tính toán số nhân để dịch chuyển số cần giới hạn về phía trước
//            double factor = Math.pow(10, 2);
//

//            // Làm tròn số cần giới hạn với số lượng chữ số thập phân cần giữ
//            double roundedNumber = Math.round(number * factor) / factor;
//            String.format("%.2f", roundedNumber);
            // Nếu không phải là số nguyên, chuyển đổi thành chuỗi kiểu double và trả về
            return decimalFormat.format(number);
        }
    }
    public void kiemTraPhepTinh(double nbe1, double nbe2, double resu){
        String text = "";
        String nb1 = removeDecimalIfZero(nbe1);
        String nb2 = removeDecimalIfZero(nbe2);
        String result = removeDecimalIfZero(resu);
        text = '\n' + (nb1) + " + " + (nb2) + '=' + (result) + " : " + String.valueOf((nbe1 + nbe2 == resu) ? "Đúng" : "Sai") + '\n' +
        (nb1) + " - " + (nb2) + '=' + (result) + " : " + String.valueOf((nbe1 - nbe2 == resu) ? "Đúng" : "Sai") + '\n' +
        (nb1) + " * " + (nb2) + '=' + (result) + " : " + String.valueOf((nbe1 * nbe2 == resu) ? "Đúng" : "Sai") + '\n' +
        (nb1) + " / " + (nb2) + '=' + (result) + " : " + String.valueOf((nbe1 / nbe2 == resu) ? "Đúng" : "Sai");
        tvKiemtra.setText(text);
    }


    private void setControl() {
        so1 = findViewById(R.id.edtSo1);
        so2 = findViewById(R.id.edtSo2);
        so3 = findViewById(R.id.edtSo3);
        btnCong = findViewById(R.id.btnCong);
        btnTru = findViewById(R.id.btnTru);
        btnNhan = findViewById(R.id.btnNhan);
        btnChia = findViewById(R.id.btnChia);
        tvKiemtra = findViewById(R.id.tvKiemTra);
    }
}