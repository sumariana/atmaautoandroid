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

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartAdminAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuSparepart extends AppCompatActivity {


    Button tambah;
    private List<Sparepart> mListSparepart = new ArrayList<>();
    private SparepartAdminAdapter sparepartAdminAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sparepart);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_sparepartadmin);
        sparepartAdminAdapter=new SparepartAdminAdapter(getApplication(),mListSparepart);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        tambah=(Button)findViewById(R.id.tambah_sparepart);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuSparepart.this,TambahSparepart.class);
                startActivity(intent);
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
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.tampilkatalog();
        listsp.enqueue(new Callback<Sparepart_data>() {
            @Override
            public void onResponse(Call<Sparepart_data> call, Response<Sparepart_data> response) {
                try{
                    sparepartAdminAdapter.notifyDataSetChanged();
                    sparepartAdminAdapter = new SparepartAdminAdapter(getApplicationContext(),response.body().getData());
                    recyclerView.setAdapter(sparepartAdminAdapter);
                }catch(Exception e){
                    Toast.makeText(MenuSparepart.this, "Belum ada supplier!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sparepart_data> call, Throwable t) {
                Toast.makeText(MenuSparepart.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
