package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailSupplier;
import atmaauto.atmaauto.com.atmaauto.models.Supplier_response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahSupplier extends AppCompatActivity {

    private EditText namasupplier,alamatsupplier,teleponsupplier,namasales,teleponsales;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_supplier);
        inisialisasi();
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSupplier();
            }
        });
    }

    public void inisialisasi(){
        namasupplier=(EditText) findViewById(R.id.tambahnamasupplier);
        alamatsupplier=(EditText) findViewById(R.id.tambahalamatsupplier);
        teleponsupplier=(EditText) findViewById(R.id.tambahteleponsupplier);
        namasales=(EditText) findViewById(R.id.tambahnamasales);
        teleponsales=(EditText)findViewById(R.id.tambahteleponsales);
        btnPost=(Button)findViewById(R.id.btntambahsupplier);

    }

    public void postSupplier(){

            if(namasupplier.getText().toString().isEmpty() || alamatsupplier.getText().toString().isEmpty() || teleponsupplier.getText().toString().isEmpty() || namasales.getText().toString().isEmpty() || teleponsales.getText().toString().isEmpty())
            {
                Toast.makeText(TambahSupplier.this,"Field can't be empty",Toast.LENGTH_SHORT).show();
            }else
            {
                //Build Retroifit
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder=new Retrofit.
                        Builder().baseUrl("http://10.53.12.16:8080").
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit=builder.build();
                ApiSupplierSales apiSupplierSales=retrofit.create(ApiSupplierSales.class);

                //
                Call<Supplier_response> supplier_responseCall=apiSupplierSales.tambahSupplier(namasupplier.getText().toString(),alamatsupplier.getText().toString(),teleponsupplier.getText().toString(),namasales.getText().toString(),teleponsales.getText().toString());
                supplier_responseCall.enqueue(new Callback<Supplier_response>() {
                    @Override
                    public void onResponse(Call<Supplier_response> call, Response<Supplier_response> response) {
                        Log.d("TAG", response.toString());
                        Toast.makeText(TambahSupplier.this,"berhasil",Toast.LENGTH_SHORT).show();
//                MenuSupplier tmpl= new MenuSupplier();
//                tmpl.showList();
                        Intent intent = new Intent(TambahSupplier.this,MenuSupplier.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Supplier_response> call, Throwable t) {
                        Log.d("TAG", t.toString());
                        Toast.makeText(TambahSupplier.this,"network error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }
}
