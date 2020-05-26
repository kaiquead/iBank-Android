package com.kaiquealves.ibank_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kaiquealves.ibank_android.api.REST;
import com.kaiquealves.ibank_android.model.Deposit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeposityActivity extends AppCompatActivity {

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposity);
    }

    public void makeDeposit(View view) {
        EditText edAccount = findViewById(R.id.edAccountDeposit);
        EditText edValue = findViewById(R.id.edValueDeposit);

        if (edAccount.getText().toString().equals("") || edValue.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Conta ou valor em branco. Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("https://simple-bank.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        int account = Integer.valueOf(edAccount.getText().toString());
        Double value = Double.valueOf(edValue.getText().toString());

        Deposit deposit = new Deposit(account, value);
        REST rest = retrofit.create(REST.class);
        Call<Deposit> call = rest.makeDeposit(deposit);
        call.enqueue(new Callback<Deposit>() {
            @Override
            public void onResponse(Call<Deposit> call, Response<Deposit> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "O depósito foi realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Algo deu errado. Tente novamente!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Deposit> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "deu ruim!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}