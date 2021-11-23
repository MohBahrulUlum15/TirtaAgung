package com.rememberdev.tirtaagung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.rememberdev.tirtaagung.MainActivity;
import com.rememberdev.tirtaagung.R;

public class RegisterActivity extends AppCompatActivity {

    private int flagExit = 0;
    private ImageButton imgViewHidePassword, imgViewHideConfirmPassword;
    private EditText edtPasswordRegister, edtConfirmPasswordRegister, edtNamaLengkapRegister, edtNomorHPRegister, edtEmailRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgViewHidePassword = findViewById(R.id.img_btn_view_hide_password);
        imgViewHideConfirmPassword = findViewById(R.id.img_btn_view_hide_confirm_password);
        edtPasswordRegister = findViewById(R.id.edt_password_register);
        edtConfirmPasswordRegister = findViewById(R.id.edt_confirm_password_register);
        edtNamaLengkapRegister = findViewById(R.id.edt_nama_lengkap_register);
        edtNomorHPRegister = findViewById(R.id.edt_nomor_hp_register);
        edtEmailRegister = findViewById(R.id.edt_email_register);
    }

    @Override
    public void onBackPressed() {
        flagExit++;

        if (flagExit ==  2) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
    }

    public void actionRegister(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void actionToLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void actionViewHidePassword(View view) {
        if (edtPasswordRegister.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            imgViewHidePassword.setImageResource(R.drawable.ic_view_pass);
            edtPasswordRegister.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edtPasswordRegister.setSelection(edtPasswordRegister.length());
        } else {
            imgViewHidePassword.setImageResource(R.drawable.ic_hide_pass);
            edtPasswordRegister.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edtPasswordRegister.setSelection(edtPasswordRegister.length());
        }
    }

    public void actionViewHideConfirmPassword(View view) {
        if (edtConfirmPasswordRegister.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            imgViewHideConfirmPassword.setImageResource(R.drawable.ic_view_pass);
            edtConfirmPasswordRegister.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edtConfirmPasswordRegister.setSelection(edtConfirmPasswordRegister.length());
        } else {
            imgViewHideConfirmPassword.setImageResource(R.drawable.ic_hide_pass);
            edtConfirmPasswordRegister.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edtConfirmPasswordRegister.setSelection(edtConfirmPasswordRegister.length());
        }
    }
}