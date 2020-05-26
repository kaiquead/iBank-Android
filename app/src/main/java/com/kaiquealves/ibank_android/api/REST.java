package com.kaiquealves.ibank_android.api;

import com.kaiquealves.ibank_android.model.CreateAccount;
import com.kaiquealves.ibank_android.model.Deposit;
import com.kaiquealves.ibank_android.model.LoadAnAccount;
import com.kaiquealves.ibank_android.model.Login;
import com.kaiquealves.ibank_android.model.Transfer;
import com.kaiquealves.ibank_android.model.Withdraw;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface REST {

    @POST("/auth")
    Call<Login> makeLogin(@Body Login login);

    @GET("/bank/{account}")
    Call<LoadAnAccount>loadAccount(@Path("account") int account, @Header("Authorization") String authHeader);

    @POST("/bank/deposit")
    Call<Deposit>makeDeposit(@Body Deposit deposit);

    @POST("/bank/withdraw")
    Call<Withdraw>makeWithdraw(@Body Withdraw withdraw, @Header("Authorization") String authHeader);

    @PUT("bank/transfer")
    Call<Transfer>makeTransfer(@Body Transfer transfer, @Header("Authorization") String authHeader);

    @POST("/bank/create")
    Call<CreateAccount>createAccount(@Body CreateAccount createAccount);
}
