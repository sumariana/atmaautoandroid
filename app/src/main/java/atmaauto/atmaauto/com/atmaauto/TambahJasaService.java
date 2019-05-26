package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import atmaauto.atmaauto.com.atmaauto.Api.ApiJasaService;
import atmaauto.atmaauto.com.atmaauto.Api.ApiMotor;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahJasaService extends AppCompatActivity {

    EditText vnamaservice,vhargaservice;
    String namaservice,hargaservice;
    Button proceed;

    Integer edit,idservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jasa_service);

        vnamaservice=(EditText) findViewById(R.id.namaservis);
        vhargaservice=(EditText) findViewById(R.id.hargaservis);
        proceed=(Button) findViewById(R.id.btntambahservice);
        getintent();

        if(edit==1){
            settext();
        }

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
            }
        });
    }

    public void getintent(){
        Intent i=getIntent();
        edit=i.getIntExtra("editable",0);
        idservice=i.getIntExtra("idjasa",0);
        namaservice=i.getStringExtra("namajasa");
        hargaservice=i.getStringExtra("hargajasa");
    }

    public void settext(){
        vnamaservice.setText(namaservice);
        vhargaservice.setText(hargaservice);
        proceed.setText("UPDATE");
    }

    public void process(){

        if(edit==1)
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl(ApiSparepart.JSONURL).
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiJasaService apiJasaService=retrofit.create(ApiJasaService.class);

            Call<ResponseBody> responseBodyCall = apiJasaService.editservice(idservice,vnamaservice.getText().toString(),vhargaservice.getText().toString());
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==200){
                        Toast.makeText(TambahJasaService.this, "berhasil!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TambahJasaService.this,MenuServis.class);
                        startActivity(intent);
                        finish();

                    }else
                        Toast.makeText(TambahJasaService.this, "Gagal!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TambahJasaService.this, "network error!", Toast.LENGTH_SHORT).show();
                }
            });
        }else
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl(ApiSparepart.JSONURL).
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiJasaService apiJasaService=retrofit.create(ApiJasaService.class);

            Call<ResponseBody> responseBodyCall = apiJasaService.tambahservice(vnamaservice.getText().toString(),vhargaservice.getText().toString());
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==200){
                        Toast.makeText(TambahJasaService.this, "berhasil!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TambahJasaService.this,MenuServis.class);
                        startActivity(intent);
                        finish();

                    }else
                        Toast.makeText(TambahJasaService.this, "Gagal!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TambahJasaService.this, "network error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
