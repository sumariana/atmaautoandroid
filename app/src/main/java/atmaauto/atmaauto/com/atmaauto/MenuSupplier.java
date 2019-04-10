package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiLogin;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.adapter.SupplierAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Supplier_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import atmaauto.atmaauto.com.atmaauto.models.Supplier;

public class MenuSupplier extends AppCompatActivity {

    private List<Supplier> mListSupplier = new ArrayList<>();
    private SupplierAdapter supplierAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    Button btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_supplier);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_supplier);
        supplierAdapter=new SupplierAdapter(getApplication(),mListSupplier);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showList();
        btnTambah=(Button) findViewById(R.id.tambah_supplier);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahSupplier();
            }
        });


    }
    public void showList(){
        //Build Retroifit
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl("http://10.53.11.209:8000/").
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSupplierSales apiSupplierSales=retrofit.create(ApiSupplierSales.class);

        //Calling APi
        Call<Supplier_data> listsup = apiSupplierSales.tampilSupplier();

        listsup.enqueue(new Callback<Supplier_data>() {
            @Override
            public void onResponse(Call<Supplier_data> call, Response<Supplier_data> response) {
                try{
                    supplierAdapter.notifyDataSetChanged();
                    supplierAdapter = new SupplierAdapter(getApplicationContext(),response.body().getData());
                    recyclerView.setAdapter(supplierAdapter);
                }catch(Exception e){
                    Toast.makeText(MenuSupplier.this, "Belum ada supplier!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Supplier_data> call, Throwable t) {
                Toast.makeText(MenuSupplier.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void tambahSupplier(){
        Intent intent = new Intent(MenuSupplier.this,TambahSupplier.class);
        startActivity(intent);
    }
}
