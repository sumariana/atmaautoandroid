package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //baru pindah ke document

    private List<Sparepart> mListSparepart = new ArrayList<>();
    private SparepartAdapter sparepartAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(TextView) findViewById(R.id.loginMain);
        ClickLogin();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_sparepart);
        sparepartAdapter=new SparepartAdapter(getApplication(),mListSparepart);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showList();
    }
    private void ClickLogin(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
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
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.tampilkatalog();
        listsp.enqueue(new Callback<Sparepart_data>() {
            @Override
            public void onResponse(Call<Sparepart_data> call, Response<Sparepart_data> response) {
                try{
                    sparepartAdapter.notifyDataSetChanged();
                    sparepartAdapter = new SparepartAdapter(getApplicationContext(),response.body().getData());
                    recyclerView.setAdapter(sparepartAdapter);
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Belum ada supplier!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sparepart_data> call, Throwable t) {
                Toast.makeText(MainActivity.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
