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

import atmaauto.atmaauto.com.atmaauto.DetilList.DetailKatalog;
import atmaauto.atmaauto.com.atmaauto.SessionManager.SessionManager;
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

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
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
                    Builder().baseUrl(ApiLogin.JSONURL).
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
                    if(response.code()==201){
                        Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        String idcabang=response.body().getData().getIdCabang().toString();
                        String idrole=response.body().getData().getIdRole().toString();
                        String username=response.body().getData().getUsername();
                        String idpegawai=response.body().getData().getId().toString();
                        Log.d("id rol: ",idrole);
                        Log.d("id rol: ",username);
                        Log.d("id rol: ",idpegawai);
                        session.createLoginSessions(idrole,username,idpegawai,idcabang);
                        if(response.body().getData().getIdRole()==1)
                        {
                            suksesloginadmin();
                        }else if(response.body().getData().getIdRole()==2)
                        {
                            sukseslogincs();
                        }else if(response.body().getData().getIdRole()==3)
                        {
                            suksesloginkasir();
                        }

                    }else if(response.code()==404)
                    {
                        Toast.makeText(LoginActivity.this,"Account not Found",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("TAG", t.getMessage().toString());
                    Toast.makeText(LoginActivity.this,"Username or Password incorrect",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void suksesloginadmin(){
        Intent intent=new Intent(LoginActivity.this,OwnerPanel.class);
        startActivity(intent);

    }
    private void sukseslogincs(){
        Intent intent=new Intent(LoginActivity.this,CSPanel.class);
        startActivity(intent);

    }
    private void suksesloginkasir(){
        Intent intent=new Intent(LoginActivity.this,MenuPembayaran.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(a);
    }
}
