package atmaauto.atmaauto.com.atmaauto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.adapter.PengadaanAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.PenjualanAdapter;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPengadaan_data;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPenjualan_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuHistory extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner history;

    private List<TransaksiPengadaan> mListPengadaan = new ArrayList<>();
    private PengadaanAdapter pengadaanAdapter;
    private List<TransaksiPenjualan> mListPenjualan = new ArrayList<>();
    private PenjualanAdapter penjualanAdapter;


    private RecyclerView recyclerView,rview;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_history);

        history = (Spinner) findViewById(R.id.spinnerhistory);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.History,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        history.setAdapter(adapter1);
        history.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(MainActivity.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();

        if(text.equalsIgnoreCase("History Transaksi Barang Masuk"))
        {
            Toast.makeText(MenuHistory.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();
            showListmasuk();
        }else{
            Toast.makeText(MenuHistory.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();
            showlistkeluar();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showlistkeluar(){

        rview = (RecyclerView) findViewById(R.id.recycler_view_history);
        penjualanAdapter=new PenjualanAdapter(getApplication(),mListPenjualan);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(layoutManager);
        rview.setItemAnimator(new DefaultItemAnimator());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.
                Builder().baseUrl(ApiSupplierSales.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan = retrofit.create(ApiTransaksiPenjualan.class);

        //Calling APi
        Call<TransaksiPenjualan_data> transaksiPenjualan_dataCall = apiTransaksiPenjualan.transaksikeluar();

        transaksiPenjualan_dataCall.enqueue(new Callback<TransaksiPenjualan_data>() {
            @Override
            public void onResponse(Call<TransaksiPenjualan_data> call, Response<TransaksiPenjualan_data> response) {
                try {
                    penjualanAdapter.notifyDataSetChanged();
                    penjualanAdapter = new PenjualanAdapter(getApplicationContext(), response.body().getData());
                    recyclerView.setAdapter(penjualanAdapter);
                } catch (Exception e) {
                    Toast.makeText(MenuHistory.this, "Belum ada Pengadaan!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiPenjualan_data> call, Throwable t) {
                Toast.makeText(MenuHistory.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showListmasuk() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_history);
        pengadaanAdapter=new PengadaanAdapter(getApplication(),mListPengadaan);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Build Retroifit
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.
                Builder().baseUrl(ApiSupplierSales.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        ApiTransaksiPengadaan apiTransaksiPengadaan = retrofit.create(ApiTransaksiPengadaan.class);

        //Calling APi
        Call<TransaksiPengadaan_data> transaksiPengadaan_dataCall = apiTransaksiPengadaan.transaksimasuk();

        transaksiPengadaan_dataCall.enqueue(new Callback<TransaksiPengadaan_data>() {
            @Override
            public void onResponse(Call<TransaksiPengadaan_data> call, Response<TransaksiPengadaan_data> response) {
                try {
                    pengadaanAdapter.notifyDataSetChanged();
                    pengadaanAdapter = new PengadaanAdapter(getApplicationContext(), response.body().getData());
                    recyclerView.setAdapter(pengadaanAdapter);
                } catch (Exception e) {
                    Toast.makeText(MenuHistory.this, "Belum ada Pengadaan!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiPengadaan_data> call, Throwable t) {
                Toast.makeText(MenuHistory.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
