package com.kaiquealves.ibank_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kaiquealves.ibank_android.api.REST;
import com.kaiquealves.ibank_android.model.CreateAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateAccountActivity extends AppCompatActivity {

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createAccount(View view){
        EditText edName = findViewById(R.id.edNameCreate);
        EditText edEmail3 = findViewById(R.id.edEmail3);
        EditText edPassword = findViewById(R.id.edPasswordCreate);

        if(edName.getText().toString().equals("") || edEmail3.getText().toString().equals("") || edPassword.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Nenhum campo pode ficar em branco!",Toast.LENGTH_SHORT).show();
            return;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("https://simple-bank.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String name = edName.getText().toString();
        String email = edEmail3.getText().toString();
        String password = edPassword.getText().toString();

        CreateAccount createAccount = new CreateAccount(name, email, password);

        REST rest = retrofit.create(REST.class);
        Call<CreateAccount> call = rest.createAccount(createAccount);
        call.enqueue(new Callback<CreateAccount>() {
            @Override
            public void onResponse(Call<CreateAccount> call, Response<CreateAccount> response) {
                if(response.code() == 200){
                    Toast.makeText(getApplicationContext(),"A conta foi criada com sucesso! Você irá receber um email contendo o número da nova conta criada",Toast.LENGTH_SHORT).show();

                    //this code is used to dont finish the activity in the same moment that account is created
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();;
                        }
                    }, 2500);

                    //finish();
                }
                else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "Já existe uma conta com esse e-mail!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Algo deu errado! Tente novamente", Toast.LENGTH_SHORT).show();
                    Log.d("erro", "codigo: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CreateAccount> call, Throwable t) {

            }
        });

    }
}
