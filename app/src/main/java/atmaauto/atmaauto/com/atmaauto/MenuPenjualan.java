package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

public class MenuPenjualan extends AppCompatActivity {

    Button tambah_penjualan;
    private List<TransaksiPenjualan> mListPenjualan = new ArrayList<>();
    private PenjualanAdapter penjualanAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_penjualan);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_penjualan);
        penjualanAdapter=new PenjualanAdapter(getApplication(),mListPenjualan);

        tambah_penjualan=(Button) findViewById(R.id.tambahpenjualansparepart);
        tambah_penjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penjualan();
            }
        });


        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        showList();
    }
    private void penjualan(){
        Intent intent=new Intent(MenuPenjualan.this,TambahTransaksiSparepart.class);
        startActivity(intent);

    }

    public void showList() {
        //Build Retroifit
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.
                Builder().baseUrl(ApiSupplierSales.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan = retrofit.create(ApiTransaksiPenjualan.class);

        //Calling APi
        Call<TransaksiPenjualan_data> transaksiPenjualan_dataCall = apiTransaksiPenjualan.tampilTransaksiPenjualan();

        transaksiPenjualan_dataCall.enqueue(new Callback<TransaksiPenjualan_data>() {
            @Override
            public void onResponse(Call<TransaksiPenjualan_data> call, Response<TransaksiPenjualan_data> response) {
                try {
                    penjualanAdapter.notifyDataSetChanged();
                    penjualanAdapter = new PenjualanAdapter(getApplicationContext(), response.body().getData());
                    recyclerView.setAdapter(penjualanAdapter);
                } catch (Exception e) {
                    Toast.makeText(MenuPenjualan.this, "Belum ada Pengadaan!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiPenjualan_data> call, Throwable t) {
                Toast.makeText(MenuPenjualan.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
