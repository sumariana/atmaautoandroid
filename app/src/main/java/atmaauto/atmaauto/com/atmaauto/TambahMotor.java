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

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiMotor;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahMotor extends AppCompatActivity {

    String brandmotor,tipemotor;
    EditText vbrandmotor,vtipemotor;
    Button process;

    int edit,idmotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_motor);

        vbrandmotor=(EditText) findViewById(R.id.brandmotor);
        vtipemotor=(EditText) findViewById(R.id.tipemotor);
        process=(Button) findViewById(R.id.btntambahmotor);
        getintent();

        if(edit==1){
            settext();
        }

        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postmotor();
            }
        });
    }

    public void getintent(){
        Intent i=getIntent();
        edit=i.getIntExtra("editable",0);
        idmotor=i.getIntExtra("idmotor",0);
        brandmotor=i.getStringExtra("merkmotor");
        tipemotor=i.getStringExtra("tipemotor");
    }

    public void settext(){
        vbrandmotor.setText(brandmotor);
        vtipemotor.setText(tipemotor);
        process.setText("UPDATE");
    }

    public void postmotor(){

        if(edit==1)
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl(ApiSparepart.JSONURL).
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiMotor apiMotor=retrofit.create(ApiMotor.class);

            Call<ResponseBody> responseBodyCall = apiMotor.editmotor(idmotor,vbrandmotor.getText().toString(),vtipemotor.getText().toString());
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==200){
                        Toast.makeText(TambahMotor.this, "berhasil!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TambahMotor.this,MenuMotor.class);
                        startActivity(intent);
                        finish();

                    }else
                        Toast.makeText(TambahMotor.this, "Gagal!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TambahMotor.this, "network error!", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl(ApiSparepart.JSONURL).
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiMotor apiMotor=retrofit.create(ApiMotor.class);

            Call<ResponseBody> responseBodyCall = apiMotor.tambahmotor(vbrandmotor.getText().toString(),vtipemotor.getText().toString());
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==200){
                        Toast.makeText(TambahMotor.this, "berhasil!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TambahMotor.this,MenuMotor.class);
                        startActivity(intent);
                        finish();

                    }else
                        Toast.makeText(TambahMotor.this, "Gagal!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TambahMotor.this, "network error!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
