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
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahKonsumen extends AppCompatActivity {

    EditText nama,alamat,telepon;
    Button postkonsumen;

    //test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_konsumen);

        nama=(EditText) findViewById(R.id.tambahnamakonsumen);
        alamat=(EditText) findViewById(R.id.tambahalamatkonsumen);
        telepon=(EditText) findViewById(R.id.tambahteleponkonsumen);
        postkonsumen=(Button) findViewById(R.id.btntambahkonsumen);
        postkonsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postkonsumen();
            }
        });

    }
    public void postkonsumen(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiKonsumen apiKonsumen=retrofit.create(ApiKonsumen.class);

        Call<ResponseBody> responseBodyCall = apiKonsumen.tambahkonsumen(nama.getText().toString(),alamat.getText().toString(),telepon.getText().toString());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    Toast.makeText(TambahKonsumen.this, "berhasil!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TambahKonsumen.this,MenuKonsumen.class);
                    startActivity(intent);
                    finish();

                }else
                    Toast.makeText(TambahKonsumen.this, "Gagal!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TambahKonsumen.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
