package atmaauto.atmaauto.com.atmaauto;

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

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.adapter.KonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuKonsumen extends AppCompatActivity {

    private List<Konsumen> mListKonsumen = new ArrayList<>();
    private KonsumenAdapter konsumenAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_konsumen);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_konsumen);
        konsumenAdapter=new KonsumenAdapter(getApplication(),mListKonsumen);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
        ApiKonsumen apiKonsumen=retrofit.create(ApiKonsumen.class);

        //Calling APi
        Call<Konsumen_data> listkonsumen = apiKonsumen.tampilkonsumen();
        listkonsumen.enqueue(new Callback<Konsumen_data>() {
            @Override
            public void onResponse(Call<Konsumen_data> call, Response<Konsumen_data> response) {
                try{
                    konsumenAdapter.notifyDataSetChanged();
                    konsumenAdapter = new KonsumenAdapter(getApplicationContext(),response.body().getData());
                    recyclerView.setAdapter(konsumenAdapter);
                }catch(Exception e){
                    Toast.makeText(MenuKonsumen.this, "Belum ada supplier!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Konsumen_data> call, Throwable t) {
                Toast.makeText(MenuKonsumen.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
