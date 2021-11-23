package com.rememberdev.tirtaagung.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.rememberdev.tirtaagung.MainActivity;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.model.User;
import com.rememberdev.tirtaagung.model.UserResponse;
import com.rememberdev.tirtaagung.retrofit.MethodHTTP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private int flagExit = 0;
    private ImageButton imgButtonViewHidePassword;
    private EditText edtEmailLogin, edtPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgButtonViewHidePassword = findViewById(R.id.img_btn_view_hide_password);
        edtEmailLogin = findViewById(R.id.edt_email_sign_in);
        edtPasswordLogin = findViewById(R.id.edt_password_sign_in);
    }

    public void actionSignIn(View view) {
        /*
        Method Login atau Sign in
         */
        boolean isInputValid = false;

        if (edtEmailLogin.getText().toString().isEmpty()) {
            edtEmailLogin.setError("Tidak boleh kosong!");
            edtEmailLogin.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (edtPasswordLogin.getText().toString().isEmpty()) {
            edtPasswordLogin.setError("Tidak boleh kosong!");
            edtPasswordLogin.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (isInputValid) {
            User user = new User();
            user.setEmail_user(edtEmailLogin.getText().toString());
            user.setPassword_user(edtPasswordLogin.getText().toString());

            loginUsingRetrofit(user.getEmail_user(), user.getPassword_user());
        }
    }

    public void actionRegisterNow(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
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

    public void actionViewHidePassword(View view) {
        if (edtPasswordLogin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            imgButtonViewHidePassword.setImageResource(R.drawable.ic_view_pass);
            edtPasswordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edtPasswordLogin.setSelection(edtPasswordLogin.length());
        } else {
            imgButtonViewHidePassword.setImageResource(R.drawable.ic_hide_pass);
            edtPasswordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edtPasswordLogin.setSelection(edtPasswordLogin.length());
        }
    }

    public void loginUsingRetrofit(String email, String password) {
        ProgressDialog proDialog = new ProgressDialog(this);
        proDialog.setTitle(getString(R.string.app_name));
        proDialog.setMessage("Silahkan tunggu");
        proDialog.show();

        GlobalVariabel globalVariabel = new GlobalVariabel();
        String globalUrlServer = globalVariabel.getGlobalUrlServer();

        Retrofit.Builder builder =  new Retrofit.Builder()
                .baseUrl(globalUrlServer)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        MethodHTTP client = retrofit.create(MethodHTTP.class);
        Call<UserResponse> call = client.login(email, password);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                proDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        User loggedUser = response.body().getUser_list().get(0);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", loggedUser.getNama_lengkap());
                        startActivity(intent);
                        finish();
                    } else if (response.body().getCode() == 401) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Peringatan!")
                                .setMessage(response.body().getStatus())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        edtPasswordLogin.setText("");
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    } else {
                        //code 400
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Peringatan!")
                                .setMessage(response.body().getStatus())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                        intent.putExtra("typeConnection", "retrofit");
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Status : Error!", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "Error : "+response.message());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                proDialog.dismiss();
                Log.d(TAG, t.getMessage());
                Toast.makeText(LoginActivity.this, "Error : "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}