package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import atmaauto.atmaauto.com.atmaauto.models.LoginResponse;
import atmaauto.atmaauto.com.atmaauto.Api.ApiLogin;
import atmaauto.atmaauto.com.atmaauto.models.Pegawai;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username,password;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inisialisasi();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickLogin();
            }
        });
    }
    private void inisialisasi()
    {
        login=(Button) findViewById(R.id.login);
        username=(EditText) findViewById(R.id.txtusername);
        password=(EditText) findViewById(R.id.txtpassword);
//        info=(TextView) findViewById(R.id.infologin);
    }

    private void ClickLogin(){
        if(username.getText().toString().isEmpty()||
                password.getText().toString().isEmpty()){
            Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
        }else
        {
            //Build Retroifit
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl("https://atmauto.jasonfw.com/api/pegawais/").
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiLogin apiLogin=retrofit.create(ApiLogin.class);

            //calling API
            String tempUsername=username.getText().toString();
            String tempPassword=password.getText().toString();
            Call<LoginResponse> pegawaiDAOCall=apiLogin.loginPegawai(tempUsername,tempPassword);
            pegawaiDAOCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Log.d("TAG", response.toString());
//                    Log.d("TAG",response.body().getData().getNamaPegawai());
//                    info.setText(response.body().getData().getGajiPegawai().toString());
                    Toast.makeText(LoginActivity.this,"berhasil",Toast.LENGTH_SHORT).show();
                    sukseslogin();
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("TAG", t.toString());
                    Toast.makeText(LoginActivity.this,"Jaringan bermasalah",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void sukseslogin(){
        Intent intent=new Intent(LoginActivity.this,OwnerPanel.class);
        startActivity(intent);
    }
}
