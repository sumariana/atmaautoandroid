package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //baru pindah ke document

    private List<Sparepart> mListSparepart = new ArrayList<>();
    private Sparepart sparepart;
    private SparepartAdapter sparepartAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    TextView login;

    private Spinner sorting;

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

        sorting = (Spinner) findViewById(R.id.sorting);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.Sorting,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sorting.setAdapter(adapter1);
        sorting.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(MainActivity.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();

//        if(text == "Harga Termurah"){
//            Collections.sort(mListSparepart, new Comparator<Sparepart>() {
//                @Override
//                public int compare(Sparepart o1, Sparepart o2) {
//                    return o1.getJumlahSparepart().compareTo(o2.getJumlahSparepart());
//                }
//            });
//            sparepartAdapter.notifyDataSetChanged();
//        }else {
//            //
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    Toast.makeText(MainActivity.this, "Belum ada sparepart!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sparepart_data> call, Throwable t) {
                Toast.makeText(MainActivity.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
