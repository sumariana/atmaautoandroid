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

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.adapter.KonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.MotorAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.Motor;
import atmaauto.atmaauto.com.atmaauto.models.Motor_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuMotor extends AppCompatActivity {

    Button tambahmotor;
    private List<Motor> mListMotor = new ArrayList<>();
    private MotorAdapter motorAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_motor);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_motor);
        motorAdapter=new MotorAdapter(getApplication(),mListMotor);

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
                motorAdapter.getFilter().filter(text);
                return true;
            }
        });

        showList();

        tambahmotor=(Button) findViewById(R.id.tambahmotor);
        tambahmotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuMotor.this,TambahMotor.class);
                startActivity(intent);
                finish();
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
        Call<Motor_data> motor_dataCall = apiKonsumen.getlistmotor();
        motor_dataCall.enqueue(new Callback<Motor_data>() {
            @Override
            public void onResponse(Call<Motor_data> call, Response<Motor_data> response) {
                try{
                    motorAdapter.notifyDataSetChanged();
                    motorAdapter = new MotorAdapter(getApplicationContext(),response.body().getData());
                    recyclerView.setAdapter(motorAdapter);
                }catch(Exception e){
                    Toast.makeText(MenuMotor.this, "Belum ada motor!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Motor_data> call, Throwable t) {
                Toast.makeText(MenuMotor.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
