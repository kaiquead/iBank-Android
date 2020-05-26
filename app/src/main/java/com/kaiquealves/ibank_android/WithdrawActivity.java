package com.kaiquealves.ibank_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kaiquealves.ibank_android.api.REST;
import com.kaiquealves.ibank_android.model.Withdraw;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WithdrawActivity extends AppCompatActivity {

    Retrofit retrofit;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        Bundle dados = getIntent().getExtras();
        token = dados.getString("token");
    }


    public void makeWithdraw(View view){
        TextView edAccount = findViewById(R.id.edAccountWithdraw);
        TextView edValue = findViewById(R.id.edValueWithdraw);

        if(edAccount.getText().toString().equals("") || edValue.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Conta ou valor em branco. Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("https://simple-bank.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        int account = Integer.valueOf(edAccount.getText().toString());
        Double value = Double.valueOf(edValue.getText().toString());

        Withdraw withdraw = new Withdraw(account, value);
        REST rest = retrofit.create(REST.class);
        Call<Withdraw> call = rest.makeWithdraw(withdraw, token);
        call.enqueue(new Callback<Withdraw>() {
            @Override
            public void onResponse(Call<Withdraw> call, Response<Withdraw> response) {
                if (response.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"O saque foi realizado com sucesso!",Toast.LENGTH_SHORT).show();

                    //this code is used to dont finish the activity in the same moment that account is created
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();;
                        }
                    }, 2500);
                }
                else {
                    Toast.makeText(getApplicationContext(),"O saque NÃO foi realizado. Revise as informações",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Withdraw> call, Throwable t) {

            }
        });

    }
}
