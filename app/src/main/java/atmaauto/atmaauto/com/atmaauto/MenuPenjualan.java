package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    Spinner spinnerpenjualan;
    SearchView search;
    private List<TransaksiPenjualan> mListPenjualan = new ArrayList<>();
    private List<TransaksiPenjualan> templist = new ArrayList<>();
    private PenjualanAdapter penjualanAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_penjualan);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_penjualan);
        penjualanAdapter=new PenjualanAdapter(getApplication(),mListPenjualan);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
                penjualanAdapter.getFilter().filter(text);
                return true;
            }
        });

        spinnerpenjualan=(Spinner) findViewById(R.id.spinnerpenjualan);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.filterpenjualan,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpenjualan.setAdapter(adapter1);
        spinnerpenjualan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                if(text.equalsIgnoreCase("All Transaction"))
                {
                    showList();
                }else if(text.equalsIgnoreCase("Unprocess Transaction")){
                    showListunprocessed();
                }else if(text.equalsIgnoreCase("Processed Transaction")){
                    showListprocessed();
                }else if(text.equalsIgnoreCase("Finished Transaction")){
                    showListfinished();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        tambah_penjualan=(Button) findViewById(R.id.tambahpenjualansparepart);
        tambah_penjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penjualan();
            }
        });
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
                    mListPenjualan=response.body().getData();
                    for (int x=0;x<mListPenjualan.size();x++)
                    {
                        if(mListPenjualan.get(x).getStatus()!=3)
                        {
                            templist.add(new TransaksiPenjualan(mListPenjualan.get(x).getIdTransaksi(),mListPenjualan.get(x).getIdKonsumen(),mListPenjualan.get(x).getTanggalTransaksi(),mListPenjualan.get(x).getNamaKonsumen(),mListPenjualan.get(x).getJenisTransaksi(),mListPenjualan.get(x).getSubtotal(),mListPenjualan.get(x).getDiskon(),mListPenjualan.get(x).getTotal(),mListPenjualan.get(x).getStatus(),mListPenjualan.get(x).getDetailJasa(),mListPenjualan.get(x).getDetailSparepart()));
                        }
                    }
                    penjualanAdapter.notifyDataSetChanged();
                    penjualanAdapter = new PenjualanAdapter(getApplicationContext(), templist);
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

    public void showListunprocessed() {
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
        Call<TransaksiPenjualan_data> transaksiPenjualan_dataCall = apiTransaksiPenjualan.transaksiunprocessed();

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

    public void showListprocessed() {
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
        Call<TransaksiPenjualan_data> transaksiPenjualan_dataCall = apiTransaksiPenjualan.transaksiprocessed();

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

    public void showListfinished() {
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
        Call<TransaksiPenjualan_data> transaksiPenjualan_dataCall = apiTransaksiPenjualan.transaksifinished();

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
