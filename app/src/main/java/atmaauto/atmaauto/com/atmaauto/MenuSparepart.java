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

public class MenuSparepart extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Button tambah;
    SearchView search;
    private List<Sparepart> mListSparepart = new ArrayList<>();
    private SparepartAdminAdapter sparepartAdminAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner sorting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sparepart);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_sparepartadmin);
        sparepartAdminAdapter=new SparepartAdminAdapter(getApplication(),mListSparepart);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sorting = (Spinner) findViewById(R.id.sorting);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.Sparepart,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sorting.setAdapter(adapter1);
        sorting.setOnItemSelectedListener(this);

        tambah=(Button)findViewById(R.id.tambah_sparepart);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuSparepart.this,TambahSparepart.class);
                startActivity(intent);
            }
        });

        showList();

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
                sparepartAdminAdapter.getFilter().filter(text);
                return true;
            }
        });
    }



    public void showlistmin(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.tampilstokmin();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(MainActivity.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();

        if(text.equalsIgnoreCase("Semua Sparepart"))
        {
            Toast.makeText(MenuSparepart.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();
            showList();
        }else{
            Toast.makeText(MenuSparepart.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();
            showlistmin();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
