package com.kaiquealves.ibank_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kaiquealves.ibank_android.api.REST;
import com.kaiquealves.ibank_android.model.Transfer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransferActivity extends AppCompatActivity {

    Retrofit retrofit;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        Bundle dados = getIntent().getExtras();
        token = dados.getString("token");

    }

    public void makeTransfer(View view){
        EditText edValue = findViewById(R.id.edValueTransfer);
        EditText edOutAcc = findViewById(R.id.edOutAccTransfer);
        EditText edIncAcc = findViewById(R.id.edIncAccTransfer);

        if(edValue.getText().toString().equals("") || edOutAcc.getText().toString().equals("") || edIncAcc.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Conta ou valor em branco. Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("https://simple-bank.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Double value = Double.valueOf(edValue.getText().toString());
        int outAcc = Integer.valueOf(edOutAcc.getText().toString());
        int incAcc = Integer.valueOf(edIncAcc.getText().toString());

        Transfer transfer = new Transfer(outAcc, incAcc, value);
        REST rest = retrofit.create(REST.class);
        Call<Transfer> call = rest.makeTransfer(transfer, token);
        call.enqueue(new Callback<Transfer>() {
            @Override
            public void onResponse(Call<Transfer> call, Response<Transfer> response) {
                if(response.code() == 200){
                    Toast.makeText(getApplicationContext(),"A transferência foi realizada com sucesso!",Toast.LENGTH_SHORT).show();

                    //this code is used to dont finish the activity in the same moment that account is created
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();;
                        }
                    }, 2500);
                }
                else if(response.code() == 400){
                    Toast.makeText(getApplicationContext(),"Você não possui esse valor para transferir",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Algo deu errado. Tente novamente!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Transfer> call, Throwable t) {

            }
        });

    }
}
