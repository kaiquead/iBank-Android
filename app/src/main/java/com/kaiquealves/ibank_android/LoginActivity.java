package com.kaiquealves.ibank_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kaiquealves.ibank_android.api.REST;
import com.kaiquealves.ibank_android.model.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void doLogin(View view){
        EditText edEmail = findViewById(R.id.edEmail);
        EditText edPassword = findViewById(R.id.edPassword);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://simple-bank.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //make a Object of type Login and pass the email and password to constructor
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        //if user dont input login or password, the function return
        if(email.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(),"E-mail ou senha em branco!",Toast.LENGTH_SHORT).show();
            return;
        }
        //making a object
        Login login = new Login (email, password);

        REST rest = retrofit.create(REST.class);
        Call<Login> call = rest.makeLogin(login);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    //I have a response of the POST, in this case token, parameters, etc...
                    Login loginResponse = response.body();

                    Intent intent = new Intent(getApplicationContext(), AnAccountActivity.class);
                    //pass the token to AnAccount Activity
                    intent.putExtra("token", loginResponse.getToken());
                    intent.putExtra("account", loginResponse.getAccount());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"E-mail ou senha inválido",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

    public void makeCredentials(View view){
        Button credentials = findViewById(R.id.btnCredentials);
        EditText edEmail = findViewById(R.id.edEmail);
        EditText edPassword = findViewById(R.id.edPassword);

        edEmail.setText("teste@teste.com");
        edPassword.setText("teste");
    }

    public void createAccount(View view){
        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(intent);
    }
}
