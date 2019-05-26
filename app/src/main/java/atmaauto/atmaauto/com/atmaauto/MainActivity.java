package atmaauto.atmaauto.com.atmaauto;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
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
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailStatusPenjualanController;
import atmaauto.atmaauto.com.atmaauto.SessionManager.SessionManager;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements DialogStatus.DialogStatusListener{

    //baru pindah ke document

    private List<Sparepart> mListSparepart = new ArrayList<>();
    private SparepartAdapter sparepartAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    TextView login,cekstatus;

    SessionManager session;
    private long backPressedTime;

    private Spinner filter,sort;
    private String textfilter,textsort;
    Button okfilter;
    Integer x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(TextView) findViewById(R.id.loginMain);
        ClickLogin();

        cekstatus=(TextView) findViewById(R.id.cekstatuskendaraan);
        cekstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_sparepart);
        sparepartAdapter=new SparepartAdapter(getApplication(),mListSparepart);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        okfilter=(Button) findViewById(R.id.okfilter);

        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn())
        {
            if(session.getIdRole().equalsIgnoreCase("1"))
            {
                Intent intent = new Intent(getApplicationContext(), OwnerPanel.class);
                startActivity(intent);
            }else if(session.getIdRole().equalsIgnoreCase("2")){
                Intent intent = new Intent(getApplicationContext(), CSPanel.class);
                startActivity(intent);
            }else if(session.getIdRole().equalsIgnoreCase("3")){
                Intent intent = new Intent(getApplicationContext(), MenuPembayaran.class);
                startActivity(intent);
            }
        }

        //spinner pertama
        filter = (Spinner) findViewById(R.id.filter);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.filter,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter1);
        //spinner kedua
        sort=(Spinner) findViewById(R.id.sort);

        final ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this,R.array.sort,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> adapter3= ArrayAdapter.createFromResource(this,R.array.doublesort,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textfilter = parent.getItemAtPosition(position).toString();
                if(textfilter.equalsIgnoreCase("Reset"))
                {
                    sort.setAdapter(null);
                    x=0;
                    showList();
                }else if(textfilter.equalsIgnoreCase("Harga")){
                    sort.setAdapter(adapter2);
                    x=1;
                }else if(textfilter.equalsIgnoreCase("Jumlah")){
                    sort.setAdapter(adapter2);
                    x=2;
                } else{
                    sort.setAdapter(adapter3);
                    x=3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textsort = parent.getItemAtPosition(position).toString();
                 if(textsort.equalsIgnoreCase("Ascending")){
                    y=1;
                }else if(textsort.equalsIgnoreCase("Descending")){
                    y=2;
                }else if(textsort.equalsIgnoreCase("Ascending-Descending")){
                    y=3;
                }else if(textsort.equalsIgnoreCase("Descending-Ascending")){
                    y=4;
                }else if(textsort.equalsIgnoreCase("Ascending-Ascending")){
                    y=5;
                }
                else if(textsort.equalsIgnoreCase("Descending-Descending")){
                    y=6;
                }else
                    y=0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        okfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(x==1 && y==1)
                {
                    //hargatermurah
                    Toast.makeText(MainActivity.this, "harga termurah ", Toast.LENGTH_SHORT).show();
                    showListtermurah();
                }else if(x==1 && y==2)
                {
                    //hargatermahal
                    Toast.makeText(MainActivity.this, "harga termahal ", Toast.LENGTH_SHORT).show();
                    showListtermahal();
                }else if(x==2 && y==1)
                {
                    //jumlahtersedikit
                    Toast.makeText(MainActivity.this, "jumlah tersedikit ", Toast.LENGTH_SHORT).show();
                    showListtersedikit();
                }else if(x==2 && y==2)
                {
                    //jumlahterbanyak
                    Toast.makeText(MainActivity.this, "jumlah terbanyak ", Toast.LENGTH_SHORT).show();
                    showListterbanyak();
                }else if(x==3 && y==3)
                {
                    //hargamurahjumlahterbanyak
                    Toast.makeText(MainActivity.this, "harga termurah - Jumlah terbanyak ", Toast.LENGTH_SHORT).show();
                    showListtermurahterbanyak();
                }else if(x==3 && y==4)
                {
                    //hargatermahaljumlahtersedikit
                    Toast.makeText(MainActivity.this, "harga termahal - Jumlah tersedikit ", Toast.LENGTH_SHORT).show();
                    showListtermahaltersedikit();
                }else if(x==3 && y==5)
                {
                    //hargatermurahjumlahtersedikit
                    Toast.makeText(MainActivity.this, "harga termurah - Jumlah tersedikit ", Toast.LENGTH_SHORT).show();
                    showListtermurahtersedikit();

                }else if(x==3 && y==6){
                    //hargatermahaljumlahterbanyak
                    Toast.makeText(MainActivity.this, "harga termahal - Jumlah terbanyak ", Toast.LENGTH_SHORT).show();
                    showListtermahalterbanyak();
                }else if(x==0 && y==0)
                    showList();
            }
        });

    }

    @Override
    public void applyTexts(String plat, String nomer) {
        Intent intent = new Intent(getApplicationContext(), StatusKendaraanKonsumen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("plat",plat);
        intent.putExtra("nomer",nomer);
        startActivity(intent);
    }

    public void openDialog(){
        DialogStatus dialogStatus = new DialogStatus();
        dialogStatus.show(getSupportFragmentManager(),"DialogStatus");
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text = parent.getItemAtPosition(position).toString();
//        //Toast.makeText(MainActivity.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();
//
//        if(text.equalsIgnoreCase("Harga Termurah"))
//        {
//            showListtermurah();
//        }else if(text.equalsIgnoreCase("Harga Termahal")){
//
//            showListtermahal();
//        }else if(text.equalsIgnoreCase("Jumlah Terbanyak")){
//
//            showListterbanyak();
//        }else if(text.equalsIgnoreCase("Jumlah Paling Sedikit")){
//
//            showListtersedikit();
//        }else if(text.equalsIgnoreCase("Harga Termurah dan Jumlah Terbanyak")){
//
//            showListtermurahterbanyak();
//        }else if(text.equalsIgnoreCase("Harga Termurah dan Jumlah Paling Sedikit")){
//
//            showListtermurahtersedikit();
//        }else if(text.equalsIgnoreCase("Harga Termahal dan Jumlah Terbanyak")){
//
//            showListtermahalterbanyak();
//        }else if(text.equalsIgnoreCase("Harga Termahal dan Jumlah Paling Sedikit")){
//
//            showListtermahaltersedikit();
//        }else {
//
//            showList();
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
    private void ClickLogin(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotificationChannel();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ("Test");
            String description = ("Test");
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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

    public void showListtermurah(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.tampilkatalogtermurah();
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

    public void showListtermahal(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.tampilkatalogtermahal();
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
    public void showListtersedikit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.tampilkatalogtersedikit();
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

    public void showListterbanyak(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.tampilkatalogterbanyak();
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

    public void showListtermurahterbanyak(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.sorthargaascjumlahdesc();
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

    public void showListtermurahtersedikit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.sorthargaascjumlahasc();
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

    public void showListtermahalterbanyak(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.sorthargadescjumlahdesc();
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

    public void showListtermahaltersedikit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        //Calling APi
        Call<Sparepart_data> listsp = apiSparepart.sorthargadescjumlahasc();
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

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
