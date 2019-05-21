package atmaauto.atmaauto.com.atmaauto.DetilList;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.MenuPembayaran;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.SessionManager.SessionManager;
import atmaauto.atmaauto.com.atmaauto.TambahTransaksiSparepart;
import atmaauto.atmaauto.com.atmaauto.adapter.JasaCartAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.PenjualanSparepartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.DetailJasa;
import atmaauto.atmaauto.com.atmaauto.models.DetailJasa_data;
import atmaauto.atmaauto.com.atmaauto.models.DetailSparepart;
import atmaauto.atmaauto.com.atmaauto.models.DetailSparepart_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailStatusPenjualanController extends AppCompatActivity {

    TextView vtanggal,vkode,vcustomer,vcustomerservice,vstatus,vtotalharga;
    Button buttonbayar;
    EditText nominal,kembalian;
    Integer total;
    RecyclerView rview,recycler_view_detailtransaksijasa;
    private PenjualanSparepartAdapter adapter;
    private JasaCartAdapter jasaCartAdapter;
    private RecyclerView.LayoutManager layout,layoutjasa;
    LinearLayout layoutbayar,layoutbayars;

    SessionManager session;

    private List<atmaauto.atmaauto.com.atmaauto.models.DetailSparepart> detailssparepart = new ArrayList<DetailSparepart>();
    private List<DetailJasa> detailJasas = new ArrayList<DetailJasa>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_status_penjualan_controller);

        init();
        session = new SessionManager(DetailStatusPenjualanController.this);

        vtanggal.setText(getIntent().getStringExtra("tanggal"));
        vkode.setText(getIntent().getStringExtra("kodetransaksi"));
        vcustomer.setText(getIntent().getStringExtra("namakonsumen"));
        vcustomerservice.setText(getIntent().getStringExtra("tanggal"));
        if(getIntent().getIntExtra("status",0) == 0)
        {
            vstatus.setText("Unprocessed");
            vstatus.setBackgroundColor(Color.parseColor("#f62d30"));
        }else if(getIntent().getIntExtra("status",0) == 1){
            vstatus.setText("Processed");
            vstatus.setBackgroundColor(Color.parseColor("#ffee58"));
        }else if(getIntent().getIntExtra("status",0) == 2){
            vstatus.setText("Finished");
            vstatus.setBackgroundColor(Color.parseColor("#66bb6a"));
        }else{
            vstatus.setText("Paid");
            vstatus.setBackgroundColor(Color.parseColor("#03a9f4"));
        }

        if(!session.getIdRole().equalsIgnoreCase("3"))
        {
            layoutbayars.setVisibility(View.GONE);
            layoutbayar.setVisibility(View.GONE);
        }

        total=getIntent().getIntExtra("total",0);
        vtotalharga.setText(total.toString());

        recycler_view_detailtransaksijasa = findViewById(R.id.recycler_view_detailtransaksijasa);
        rview = findViewById(R.id.recycler_view_detailtransaksi);
        //rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getApplicationContext());
        layoutjasa = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(layout);
        recycler_view_detailtransaksijasa.setLayoutManager(layoutjasa);
        adapter= new PenjualanSparepartAdapter(getApplicationContext(),detailssparepart,0);
        jasaCartAdapter = new JasaCartAdapter(getApplicationContext(),detailJasas,0);

        showListjasa();
        showListsparepart();

        buttonbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pembayaran();
            }
        });
    }

    public void init(){
        vtanggal=(TextView) findViewById(R.id.tanggaltransaksi);
        vkode=(TextView) findViewById(R.id.kodetransaksi);
        vcustomer=(TextView) findViewById(R.id.namacustomer);
        vcustomerservice=(TextView) findViewById(R.id.namaCS);
        vstatus=(TextView) findViewById(R.id.statustransaksi);
        vtotalharga=(TextView) findViewById(R.id.totalharga);
        layoutbayar=(LinearLayout) findViewById(R.id.layoutbayar);
        layoutbayars=(LinearLayout) findViewById(R.id.layoutbayars);
        buttonbayar=(Button) findViewById(R.id.buttonbayar);
        nominal=(EditText) findViewById(R.id.nominal);
        kembalian=(EditText) findViewById(R.id.kembalian);
    }

    public void pembayaran(){
        if(Integer.valueOf(nominal.getText().toString()) >= Integer.valueOf(vtotalharga.getText().toString()))
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl(ApiSparepart.JSONURL).
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.pembayarantransaksi(getIntent().getIntExtra("idtransaksi",0));
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==201)
                    {
                        Toast.makeText(DetailStatusPenjualanController.this, "Pembayaran Berhasil!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailStatusPenjualanController.this, MenuPembayaran.class);
                        startActivity(intent);
                        finish();
                    }else
                        Toast.makeText(DetailStatusPenjualanController.this, "Pembayaran Gagal!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }else
        {
            Toast.makeText(DetailStatusPenjualanController.this, "uang kurang!", Toast.LENGTH_SHORT).show();
        }
    }

    public void showListsparepart(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);
        //Calling APi
        Call<DetailSparepart_data> detailSparepart_dataCall = apiTransaksiPenjualan.tampildetailsparepartkonsumen(getIntent().getIntExtra("idtransaksi",0));
        detailSparepart_dataCall.enqueue(new Callback<DetailSparepart_data>() {
            @Override
            public void onResponse(Call<DetailSparepart_data> call, Response<DetailSparepart_data> response) {
                Log.d( "onResponse: ",response.body().getData().toString());
                try{
                    adapter.notifyDataSetChanged();
                    detailssparepart=response.body().getData();
                    //Log.d("id Detail Pengadaan: ",details.get(0).getIdDetailPengadaan().toString());
                    adapter = new PenjualanSparepartAdapter(getApplicationContext(),detailssparepart);
                    rview.setAdapter(adapter);
                }catch(Exception e){
                    Toast.makeText(DetailStatusPenjualanController.this, "Belum ada detail!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailSparepart_data> call, Throwable t) {
                Toast.makeText(DetailStatusPenjualanController.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showListjasa(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);
        //Calling APi
        Call<DetailJasa_data> detailSparepart_dataCall = apiTransaksiPenjualan.tampildetailjasakonsumen(getIntent().getIntExtra("idtransaksi",0));
        detailSparepart_dataCall.enqueue(new Callback<DetailJasa_data>() {
            @Override
            public void onResponse(Call<DetailJasa_data> call, Response<DetailJasa_data> response) {
                Log.d( "onResponse: ",response.body().getData().toString());
                try{
                    adapter.notifyDataSetChanged();
                    detailJasas=response.body().getData();
                    //Log.d("id Detail Pengadaan: ",details.get(0).getIdDetailPengadaan().toString());
                    jasaCartAdapter = new JasaCartAdapter(getApplicationContext(),detailJasas,0);
                    recycler_view_detailtransaksijasa.setAdapter(jasaCartAdapter);
                }catch(Exception e){
                    Toast.makeText(DetailStatusPenjualanController.this, "Belum ada detail!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailJasa_data> call, Throwable t) {
                Toast.makeText(DetailStatusPenjualanController.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
