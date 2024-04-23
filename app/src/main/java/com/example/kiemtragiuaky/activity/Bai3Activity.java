package com.example.kiemtragiuaky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.kiemtragiuaky.R;

public class Bai3Activity extends AppCompatActivity {
    TextView tvHienThiDK;
    EditText edtHoTen, edtCCCD;
    CheckBox chkChoiGame, chkDocSach, chkDocBao;
    RadioButton rdbDaiHoc, rdbCaoDang, rdbTrungCap;
    RadioGroup rdbGroup;
    Button btnDangKy, btnNhapLai, btnThoat;
    String selectedRadio = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);
        setControl();
        setEvent();
    }

    private void setEvent() {
        rdbGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rdb = findViewById(checkedId);
            selectedRadio = rdb.getText().toString();
        });
        btnNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtHoTen.setText("");
                edtCCCD.setText("");
                chkChoiGame.setChecked(false);
                chkDocSach.setChecked(false);
                chkDocBao.setChecked(false);
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "Thông tin\n";
                text += "Họ tên: " + edtHoTen.getText().toString() + "\n";
                text += "CCCD: " + edtCCCD.getText().toString() + "\n";
                text += "Bằng cấp: " + selectedRadio + "\n";
                text += "Sở thích: ";
                if (chkChoiGame.isChecked())
                    text += chkChoiGame.getText().toString() + ' ';
                if (chkDocSach.isChecked())
                    text += chkDocSach.getText().toString() + ' ';
                if (chkDocBao.isChecked())
                    text += chkDocBao.getText().toString() + ' ';
                tvHienThiDK.setText(text);

            }
        });
    }

    private void setControl() {
        edtHoTen = findViewById(R.id.edtHoTen);
        edtCCCD = findViewById(R.id.edtCCCD);
        rdbGroup = findViewById(R.id.rdbGroup);
        chkChoiGame = findViewById(R.id.chkChoiGame);
        chkDocSach = findViewById(R.id.chkDocSach);
        chkDocBao = findViewById(R.id.chkDocBao);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnNhapLai = findViewById(R.id.btnNhapLai);
        btnThoat = findViewById(R.id.btnThoat);
        tvHienThiDK = findViewById(R.id.tvShowDK);
    }
}