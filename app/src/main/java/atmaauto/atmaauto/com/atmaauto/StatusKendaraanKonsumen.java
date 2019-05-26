package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.adapter.PenjualanAdapter;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPenjualan_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusKendaraanKonsumen extends AppCompatActivity{
    private List<TransaksiPenjualan> mListPenjualan = new ArrayList<>();
    private PenjualanAdapter penjualanAdapter;

    private RecyclerView rview;
    private RecyclerView.LayoutManager layoutManager;

    String plat,nomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_kendaraan_konsumen);

        Intent intent = getIntent();
        plat = intent.getStringExtra("plat");
        nomer = intent.getStringExtra("nomer");

        showlist();
    }

    public void showlist() {
        rview = (RecyclerView) findViewById(R.id.recycler_view_statuskendaraankonsumen);
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

        Call<TransaksiPenjualan_data> transaksiPenjualan_dataCall = apiTransaksiPenjualan.tampilstatuskonsumen(nomer,plat);
        transaksiPenjualan_dataCall.enqueue(new Callback<TransaksiPenjualan_data>() {
            @Override
            public void onResponse(Call<TransaksiPenjualan_data> call, Response<TransaksiPenjualan_data> response) {
                try {
                    penjualanAdapter.notifyDataSetChanged();
                    penjualanAdapter = new PenjualanAdapter(getApplicationContext(), response.body().getData(),1);
                    rview.setAdapter(penjualanAdapter);
                } catch (Exception e) {
                    Toast.makeText(StatusKendaraanKonsumen.this, "Belum ada Transaksi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiPenjualan_data> call, Throwable t) {
                Toast.makeText(StatusKendaraanKonsumen.this, "Network error or APi not on service!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
