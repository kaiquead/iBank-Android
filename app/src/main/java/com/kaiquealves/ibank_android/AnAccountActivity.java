package com.kaiquealves.ibank_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaiquealves.ibank_android.api.REST;
import com.kaiquealves.ibank_android.model.LoadAnAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnAccountActivity extends AppCompatActivity {

    Retrofit retrofit;
    private int actualAccount;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an_account);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView tvName, tvAccount, tvValue;
        tvName = findViewById(R.id.tvName);
        tvAccount = findViewById(R.id.tvAccountLoadAccount);
        tvValue = findViewById(R.id.tvValueLoadAccount);

        //get the token and account to LoginActivity
        Bundle dados = getIntent().getExtras();
        token = dados.getString("token");
        int account = dados.getInt("account");

        retrofit = new Retrofit.Builder()
                .baseUrl("https://simple-bank.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        REST rest = retrofit.create(REST.class);
        Call<LoadAnAccount> call = rest.loadAccount(account, token);
        call.enqueue(new Callback<LoadAnAccount>() {
            @Override
            public void onResponse(Call<LoadAnAccount> call, Response<LoadAnAccount> response) {
                if (response.isSuccessful()){

                    LoadAnAccount loadAnAccount = response.body();

                    tvName.setText(loadAnAccount.getOwner());
                    tvAccount.setText("Conta: " + String.valueOf(loadAnAccount.getAccount()));
                    tvValue.setText("Saldo: R$" + String.valueOf(loadAnAccount.getValue()));


                    actualAccount = loadAnAccount.getAccount();
                }
            }

            @Override
            public void onFailure(Call<LoadAnAccount> call, Throwable t) {

            }
        });

    }

    public void makeDeposit(View view){
        Intent intent = new Intent(getApplicationContext(), DeposityActivity.class);
        intent.putExtra("account", actualAccount);
        startActivity(intent);
    }

    public void makeWithdraw(View view){
        Intent intent = new Intent(getApplicationContext(), WithdrawActivity.class);
        intent.putExtra("account", actualAccount);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void makeTransfer(View view){
        Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
        intent.putExtra("account", actualAccount);
        intent.putExtra("token", token);
        startActivity(intent);
    }
}
