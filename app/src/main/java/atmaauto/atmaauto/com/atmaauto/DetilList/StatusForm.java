package atmaauto.atmaauto.com.atmaauto.DetilList;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.MenuPengadaan;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.adapter.StatusFormAdapter;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusForm extends AppCompatActivity {

    String date,sales;
    Integer status,totalharga,idPengadaan;
    TextView vdate,vsales,vstatus;
    EditText vtotalharga;
    Button verifikasi;

    private List<DetailPengadaan> details = new ArrayList<DetailPengadaan>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StatusFormAdapter statusFormAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_form);
        init();
        getintent();
        Log.d("getintent: ",idPengadaan.toString());
        showList();

        verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    public void init(){
        Intent i = getIntent();
        vdate=(TextView) findViewById(R.id.datepicker);
        vsales=(TextView) findViewById(R.id.namasales);
        vstatus=(TextView) findViewById(R.id.status);
        vtotalharga=(EditText) findViewById(R.id.totalharga);
        verifikasi=(Button) findViewById(R.id.verif);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_status);

        if(i.getIntExtra("status",0)==1)
        {
            statusFormAdapter=new StatusFormAdapter(getApplicationContext(),details,1);
        }else
        {
            statusFormAdapter=new StatusFormAdapter(getApplicationContext(),details,0);
        }

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public void verify(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPengadaan apiTransaksiPengadaan=retrofit.create(ApiTransaksiPengadaan.class);
        Log.d( "verify: ",idPengadaan.toString());
        Call<ResponseBody> responseBodyCall = apiTransaksiPengadaan.verifypengadaan(idPengadaan);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==201)
                {
                    Toast.makeText(StatusForm.this, "berhasil verify!", Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(StatusForm.this, MenuPengadaan.class);
                    startActivity(i);
                }else
                    Toast.makeText(StatusForm.this, "gagal verify!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(StatusForm.this, "Network Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showList(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPengadaan apiKonsumen=retrofit.create(ApiTransaksiPengadaan.class);
        //Calling APi
        Call<DetailPengadaan_data> detailPengadaan_dataCall = apiKonsumen.tampildetilpengadaan(idPengadaan);
        detailPengadaan_dataCall.enqueue(new Callback<DetailPengadaan_data>() {
            @Override
            public void onResponse(Call<DetailPengadaan_data> call, Response<DetailPengadaan_data> response) {
                try{
                    statusFormAdapter.notifyDataSetChanged();
                    details=response.body().getData();
                    //Log.d("id Detail Pengadaan: ",details.get(0).getIdDetailPengadaan().toString());

                    if(status==2)
                    {
                        statusFormAdapter=new StatusFormAdapter(getApplicationContext(),details,1);
                    }else
                    {
                        statusFormAdapter=new StatusFormAdapter(getApplicationContext(),details,0);
                    }

                    recyclerView.setAdapter(statusFormAdapter);
                }catch(Exception e){
                    Toast.makeText(StatusForm.this, "Belum ada detail!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailPengadaan_data> call, Throwable t) {
                Toast.makeText(StatusForm.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getintent(){
        Intent i=getIntent();
        date=i.getStringExtra("tanggal");
        sales=i.getStringExtra("namasales");
        totalharga=i.getIntExtra("totalharga",0);
        idPengadaan=i.getIntExtra("idpengadaan",0);
        Log.d("getintent: ",idPengadaan.toString());
        status=i.getIntExtra("status",0);

        vdate.setText(date);
        vsales.setText(sales);
        if (status==1)
        {
            vstatus.setText("Printed");
            vstatus.setBackgroundColor(Color.parseColor("#fff176"));
        }

        if(status==2)
        {
            vstatus.setText("Verified");
            vstatus.setBackgroundColor(Color.parseColor("#81c784"));
            verifikasi.setVisibility(View.GONE);
        }else{
            verifikasi.setVisibility(View.VISIBLE);
        }
        vtotalharga.setText(totalharga.toString());
    }
}
