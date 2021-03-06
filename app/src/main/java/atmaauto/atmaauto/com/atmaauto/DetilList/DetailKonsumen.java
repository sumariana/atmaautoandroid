package atmaauto.atmaauto.com.atmaauto.DetilList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.MenuKonsumen;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.TambahMotorKonsumen;
import atmaauto.atmaauto.com.atmaauto.adapter.KonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.MotorKonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailKonsumen extends AppCompatActivity {

    private List<MotorKonsumen> mListMotorKonsumen = new ArrayList<>();
    private MotorKonsumenAdapter motorKonsumenAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    String idkn,namakn,alamatkn,teleponkn;

    Button tambahmotor,editkonsumen;
    SearchView search;

    EditText vid,vnama,valamat,vtelepon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_konsumen);
        getsparepart();
        inisialisasi();
        setText();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_motorkonsumen);
        motorKonsumenAdapter=new MotorKonsumenAdapter(getApplication(),mListMotorKonsumen);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showList();

        tambahmotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DetailKonsumen.this, TambahMotorKonsumen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",idkn);
                startActivity(intent);
                finish();
            }
        });

        editkonsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder=new Retrofit.
                        Builder().baseUrl(ApiSparepart.JSONURL).
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit=builder.build();
                ApiKonsumen apiKonsumen=retrofit.create(ApiKonsumen.class);

                Call<ResponseBody> responseBodyCall = apiKonsumen.editkonsumen(Integer.parseInt(idkn),vnama.getText().toString(),valamat.getText().toString(),vtelepon.getText().toString());
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200)
                        {
                            Toast.makeText(DetailKonsumen.this, "Berhasil Edit Konsumen!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailKonsumen.this,MenuKonsumen.class);
                                    startActivity(intent);
                                    finish();
                        }else
                            Toast.makeText(DetailKonsumen.this, "Gagal Edit Konsumen!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(DetailKonsumen.this, "network error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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
                motorKonsumenAdapter.getFilter().filter(text);
                return true;
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
        ApiKonsumen apiKonsumen=retrofit.create(ApiKonsumen.class);

        //Calling APi
        Call<MotorKonsumen_data> listkonsumen = apiKonsumen.tampilmotorkonsumen(Integer.parseInt(idkn));
        listkonsumen.enqueue(new Callback<MotorKonsumen_data>() {
            @Override
            public void onResponse(Call<MotorKonsumen_data> call, Response<MotorKonsumen_data> response) {
                try{
                    motorKonsumenAdapter.notifyDataSetChanged();
                    motorKonsumenAdapter = new MotorKonsumenAdapter(getApplicationContext(),response.body().getData());
                    recyclerView.setAdapter(motorKonsumenAdapter);
                }catch(Exception e){
                    Toast.makeText(DetailKonsumen.this, "Belum ada motor konsumen!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MotorKonsumen_data> call, Throwable t) {
                Toast.makeText(DetailKonsumen.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getsparepart(){
        Intent i=getIntent();

        idkn=i.getStringExtra("idkn");
        namakn=i.getStringExtra("namakn");
        alamatkn=i.getStringExtra("alamatkn");
        teleponkn=i.getStringExtra("teleponkn");
    }

    private void inisialisasi(){

        vid=(EditText) findViewById(R.id.detilidkonsumen);
        vnama=(EditText) findViewById(R.id.detilnamakonsumen);
        valamat=(EditText) findViewById(R.id.detilalamatkonsumen);
        vtelepon=(EditText) findViewById(R.id.detilteleponkonsumen);
        tambahmotor=(Button) findViewById(R.id.btntambahmotor);
        editkonsumen=(Button) findViewById(R.id.btneditkonsumen);
        search=(SearchView) findViewById(R.id.searchbar);
    }

    private void setText(){
        vid.setText(idkn);
        vnama.setText(namakn);
        valamat.setText(alamatkn);
        vtelepon.setText(teleponkn);
    }
}
