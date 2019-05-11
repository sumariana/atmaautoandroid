package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.adapter.PengadaanAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.SupplierAdapter;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPengadaan_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuPengadaan extends AppCompatActivity {
    Button tambahPengadaan;
    SearchView search;
    private List<TransaksiPengadaan> mListPengadaan = new ArrayList<>();
    private PengadaanAdapter pengadaanAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pengadaan);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_pengadaan);
        pengadaanAdapter=new PengadaanAdapter(getApplication(),mListPengadaan);

        search=(SearchView) findViewById(R.id.searchbar);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("onQueryTextSubmit: ",query);
                //SAdapter.filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("onQueryTextChange: ","true");
                String text = newText.toLowerCase(Locale.getDefault());
                pengadaanAdapter.getFilter().filter(text);
                return true;
            }
        });

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        showList();

        tambahPengadaan = (Button) findViewById(R.id.tambah_pengadaan);
        tambahPengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pengadaan();
            }
        });
    }
    private void pengadaan(){
                Intent intent=new Intent(MenuPengadaan.this,TambahPengadaan.class);
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
        ApiTransaksiPengadaan apiTransaksiPengadaan = retrofit.create(ApiTransaksiPengadaan.class);

        //Calling APi
        Call<TransaksiPengadaan_data> transaksiPengadaan_dataCall = apiTransaksiPengadaan.tampiltransaksipengadaan();

        transaksiPengadaan_dataCall.enqueue(new Callback<TransaksiPengadaan_data>() {
            @Override
            public void onResponse(Call<TransaksiPengadaan_data> call, Response<TransaksiPengadaan_data> response) {
                try {
                    pengadaanAdapter.notifyDataSetChanged();
                    pengadaanAdapter = new PengadaanAdapter(getApplicationContext(), response.body().getData());
                    recyclerView.setAdapter(pengadaanAdapter);
                } catch (Exception e) {
                    Toast.makeText(MenuPengadaan.this, "Belum ada Pengadaan!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiPengadaan_data> call, Throwable t) {
                Toast.makeText(MenuPengadaan.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
