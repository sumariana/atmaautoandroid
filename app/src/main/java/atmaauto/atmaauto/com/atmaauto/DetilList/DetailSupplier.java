package atmaauto.atmaauto.com.atmaauto.DetilList;

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

import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.LoginActivity;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.Supplier;
import atmaauto.atmaauto.com.atmaauto.models.Supplier_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailSupplier extends AppCompatActivity {
    public String idsup,namasup,alamatsup,telpsup,namasal,telpsal;

    public EditText viewidsup,viewnamasup,viewalamatsup,viewtelpsup,viewnamasal,viewtelpsal;
    Button delSales,upSales,upSupplier,addSales;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_supplier);

        inisialisasi();
        getsupplier();
        SetText();
        delSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSales();
            }
        });
        upSales.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateSales();
            }
        });
        addSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSales();
            }
        });
        upSupplier.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                updateSupplier();
            }
        });
    }

    private void getsupplier(){
        Intent i=getIntent();

        idsup=i.getStringExtra("idsup");
        namasup=i.getStringExtra("namasup");
        alamatsup=i.getStringExtra("alamatsup");
        telpsup=i.getStringExtra("telpsup");
        namasal=i.getStringExtra("namasal");
        telpsal=i.getStringExtra("telpsal");

    }

    private void SetText(){
        viewidsup.setText(idsup);
        viewnamasup.setText(namasup);
        viewalamatsup.setText(alamatsup);
        viewtelpsup.setText(telpsup);
        viewnamasal.setText(namasal);
        viewtelpsal.setText(telpsal);
    }

    private void inisialisasi(){

    viewidsup=(EditText) findViewById(R.id.detilidsupplier);
    viewnamasup=(EditText) findViewById(R.id.detilnamasupplier);
    viewalamatsup=(EditText) findViewById(R.id.detilalamatsupplier);
    viewtelpsup=(EditText) findViewById(R.id.detilteleponsupplier);
    viewnamasal=(EditText) findViewById(R.id.detilnamasales);
    viewtelpsal=(EditText) findViewById(R.id.detilteleponsales);
    delSales=(Button) findViewById(R.id.btndeletesales);
    upSales=(Button) findViewById(R.id.btnupsales);
    addSales=(Button) findViewById(R.id.btnaddsales);
    upSupplier=(Button) findViewById(R.id.btnupsupplier);
    }

    private void deleteSales(){
        if(namasal==null || telpsal==null)
        {
            Toast.makeText(DetailSupplier.this,"Supplier belum memiliki Sales",Toast.LENGTH_SHORT).show();
        }else
        {
            //Building retrofit
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl("http://10.53.11.209:8000/").
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiSupplierSales apiSupplierSales = retrofit.create(ApiSupplierSales.class);

            //calling api
            Call<ResponseBody> supplier_dataCall=apiSupplierSales.hapusSales(Integer.parseInt(idsup));
            supplier_dataCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String kode = Integer.toString(response.code());
                    Log.d("TAG", kode);
                    if(response.code()==200)
                    {
                        Toast.makeText(DetailSupplier.this,"berhasil",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(DetailSupplier.this,"gagal",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("TAG", t.toString());
                    Toast.makeText(DetailSupplier.this,"Jaringan bermasalah",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateSales(){

        if(namasal==null || telpsal==null)
        {
            Toast.makeText(DetailSupplier.this,"Supplier belum memiliki Sales",Toast.LENGTH_SHORT).show();
        }else
        {
            //Building retrofit
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl("http://10.53.11.209:8000/").
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiSupplierSales apiSupplierSales = retrofit.create(ApiSupplierSales.class);

            //calling api
            Call<ResponseBody> supplier_dataCall=apiSupplierSales.updateSales(viewnamasal.getText().toString(),viewtelpsal.getText().toString(),Integer.parseInt(idsup));
            supplier_dataCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==200)
                    {
                        Toast.makeText(DetailSupplier.this,"berhasil",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(DetailSupplier.this,"gagal",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("TAG", t.toString());
                    Toast.makeText(DetailSupplier.this,"Jaringan bermasalah",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void postSales(){
        if(namasal==null || telpsal==null)
        {
            //Building retrofit
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl("http://10.53.11.209:8000/").
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiSupplierSales apiSupplierSales = retrofit.create(ApiSupplierSales.class);

            //calling api
            Call<ResponseBody> supplier_dataCall=apiSupplierSales.updateSales(viewnamasal.getText().toString(),viewtelpsal.getText().toString(),Integer.parseInt(idsup));
            supplier_dataCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==200)
                    {
                        Toast.makeText(DetailSupplier.this,"berhasil",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(DetailSupplier.this,"gagal",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("TAG", t.toString());
                    Toast.makeText(DetailSupplier.this,"Jaringan bermasalah",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(DetailSupplier.this,"Supplier sudah memiliki Sales",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSupplier(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl("http://10.53.11.209:8000/").
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSupplierSales apiSupplierSales = retrofit.create(ApiSupplierSales.class);

        //calling api
        Call<ResponseBody> supplier_dataCall=apiSupplierSales.updateSupplier(viewnamasup.getText().toString(),viewalamatsup.getText().toString(),viewtelpsup.getText().toString(),viewnamasal.getText().toString(),viewtelpsal.getText().toString(),Integer.parseInt(idsup));
        supplier_dataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200)
                {
                    Toast.makeText(DetailSupplier.this,"berhasil",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(DetailSupplier.this,"gagal",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", t.toString());
                Toast.makeText(DetailSupplier.this,"Jaringan bermasalah",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
