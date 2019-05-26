package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import atmaauto.atmaauto.com.atmaauto.Api.ApiJasaService;
import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.adapter.KonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.ServiceAdminAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Jasa;
import atmaauto.atmaauto.com.atmaauto.models.Jasa_data;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuServis extends AppCompatActivity {

    Button tambahservice;
    private List<Jasa> mListjasa = new ArrayList<>();
    private ServiceAdminAdapter serviceAdminAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_servis);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_service);
        serviceAdminAdapter=new ServiceAdminAdapter(getApplication(),mListjasa);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        tambahservice=(Button) findViewById(R.id.tambahservice);
        tambahservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuServis.this,TambahJasaService.class);
                startActivity(intent);
                finish();
            }
        });

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
                serviceAdminAdapter.getFilter().filter(text);
                return true;
            }
        });
        showList();
    }

    public void showList(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiJasaService apiJasaService=retrofit.create(ApiJasaService.class);

        //Calling APi
        Call<Jasa_data> jasa_dataCall = apiJasaService.tampilservis();
        jasa_dataCall.enqueue(new Callback<Jasa_data>() {
            @Override
            public void onResponse(Call<Jasa_data> call, Response<Jasa_data> response) {
                try{
                    serviceAdminAdapter.notifyDataSetChanged();
                    serviceAdminAdapter = new ServiceAdminAdapter(getApplicationContext(),response.body().getData());
                    recyclerView.setAdapter(serviceAdminAdapter);
                }catch(Exception e){
                    Toast.makeText(MenuServis.this, "Belum ada supplier!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Jasa_data> call, Throwable t) {
                Toast.makeText(MenuServis.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
