package atmaauto.atmaauto.com.atmaauto.DetilList;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.MenuPenjualan;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.SessionManager.SessionManager;
import atmaauto.atmaauto.com.atmaauto.TambahTransaksiSparepart;
import atmaauto.atmaauto.com.atmaauto.adapter.JasaCartAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.PenjualanSparepartAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartCartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.DetailJasa;
import atmaauto.atmaauto.com.atmaauto.models.DetailJasa_data;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan_data;
import atmaauto.atmaauto.com.atmaauto.models.DetailSparepart;
import atmaauto.atmaauto.com.atmaauto.models.DetailSparepart_data;
import atmaauto.atmaauto.com.atmaauto.models.Jasa;
import atmaauto.atmaauto.com.atmaauto.models.Jasa_data;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.Pegawai;
import atmaauto.atmaauto.com.atmaauto.models.Pegawai_data;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPenjualanController extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String datepicker,jenistrk;
    TextView mDisplayDate,labelsparepart,labelservis;
    EditText vtotal,vdiskon,vjumlah;

    Integer diskon,idtransaksi;
    private Integer isi=0;

    Button addtocart,patchpenjualansparepart;

    private List<String> listNameSparepart = new ArrayList<String>();
    private List<String> listKodeSparepart = new ArrayList<String>();
    private List<String> listHargaSparepart = new ArrayList<String>();

    private List<String> listnamakonsumen = new ArrayList<String>();
    private List<String> listidkonsumen = new ArrayList<String>();

    private List<String> listnamamontir = new ArrayList<String>();
    private List<String> listidmontir = new ArrayList<String>();

    private List<String> listnamaservis = new ArrayList<String>();
    private List<String> listidservis = new ArrayList<String>();
    private List<String> listhargaservis = new ArrayList<String>();

    private List<String> listnamaMotorkonsumen = new ArrayList<String>();
    private List<String> listidMotorkonsumen = new ArrayList<String>();

    private List<atmaauto.atmaauto.com.atmaauto.models.DetailSparepart> detailssparepart = new ArrayList<DetailSparepart>();
    private List<DetailJasa> detailJasas = new ArrayList<DetailJasa>();

    Spinner spinnerkonsumen,spinnersparepart,spinnermotorkonsumen,spinnermontir,spinnerjenistransaksi,spinnerjasaservis;
    String selectedidkonsumen,selectedIdSparepart,selectedHargaSparepart,selectedidmotorkonsumen,selectedidmontir,selectedhargaservis,selectedidservis,selectednamaservis;

    SessionManager session;

    RecyclerView rview,recycler_view_detailtransaksijasa;
    private PenjualanSparepartAdapter adapter;
    private JasaCartAdapter jasaCartAdapter;
    private RecyclerView.LayoutManager layout,layoutjasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjualan_controller);

        mDisplayDate = (TextView) findViewById(R.id.datepicker);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DetailPenjualanController.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = year + "-" + month + "-" +day ;
                mDisplayDate.setText(date);
            }
        };

        session = new SessionManager(getApplicationContext());
        Log.d( "ID PEGAWAI : ",session.getKeyId());

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiverjasa,
                new IntentFilter("custom-message-jasa"));



        labelsparepart=(TextView)findViewById(R.id.labelsparepart);
        labelservis=(TextView) findViewById(R.id.labelservis);
        spinnerjasaservis=(Spinner)findViewById(R.id.spinnerservis);
        recycler_view_detailtransaksijasa = findViewById(R.id.recycler_view_detailtransaksijasa);
        rview = findViewById(R.id.recycler_view_detailtransaksi);
        //rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getApplicationContext());
        layoutjasa = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(layout);
        recycler_view_detailtransaksijasa.setLayoutManager(layoutjasa);
        adapter= new PenjualanSparepartAdapter(getApplicationContext(),detailssparepart,1);
        jasaCartAdapter = new JasaCartAdapter(getApplicationContext(),detailJasas,1);
        addtocart=(Button) findViewById(R.id.addcart);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
            }
        });
        vjumlah=(EditText) findViewById(R.id.jumlah);
        vdiskon=(EditText) findViewById(R.id.diskon);
        vtotal=(EditText) findViewById(R.id.total);
        patchpenjualansparepart = (Button) findViewById(R.id.patchpenjualan);
        spinnersparepart=(Spinner) findViewById(R.id.spinnersparepart);
        spinnerkonsumen=(Spinner) findViewById(R.id.spinnerkonsumen);
        spinnermontir=(Spinner) findViewById(R.id.spinnermontir);
        spinnermotorkonsumen=(Spinner) findViewById(R.id.spinnermotorkonsumen);
        spinnerjenistransaksi=(Spinner) findViewById(R.id.spinnerjenistransaksi);

        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.JenisTransaksi,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerjenistransaksi.setAdapter(adapter1);
        spinnerjenistransaksi.setSelection(getIndex(spinnerjenistransaksi,getIntent().getStringExtra("jenis")));
        spinnerjenistransaksi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = parentView.getItemAtPosition(position).toString();
                //Toast.makeText(MainActivity.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();

                if(text.equalsIgnoreCase("Penjualan Sparepart"))
                {
                    Toast.makeText(DetailPenjualanController.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();
                    spinnersparepart.setVisibility(View.VISIBLE);
                    labelsparepart.setVisibility(View.VISIBLE);
                    rview.setVisibility(View.VISIBLE);
                    vjumlah.setVisibility(View.VISIBLE);
                    spinnerjasaservis.setVisibility(View.GONE);
                    labelservis.setVisibility(View.GONE);
                    recycler_view_detailtransaksijasa.setVisibility(View.GONE);
                    jenistrk="SP";
                    showListsparepart();
                }else if(text.equalsIgnoreCase("Penjualan Jasa")){
                    Toast.makeText(DetailPenjualanController.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();
                    spinnersparepart.setVisibility(View.GONE);
                    labelsparepart.setVisibility(View.GONE);
                    rview.setVisibility(View.GONE);
                    vjumlah.setVisibility(View.GONE);
                    spinnerjasaservis.setVisibility(View.VISIBLE);
                    labelservis.setVisibility(View.VISIBLE);
                    recycler_view_detailtransaksijasa.setVisibility(View.VISIBLE);
                    jenistrk="SV";
                    showListjasa();
                }else {
                    spinnersparepart.setVisibility(View.VISIBLE);
                    labelsparepart.setVisibility(View.VISIBLE);
                    rview.setVisibility(View.VISIBLE);
                    vjumlah.setVisibility(View.VISIBLE);
                    spinnerjasaservis.setVisibility(View.VISIBLE);
                    labelservis.setVisibility(View.VISIBLE);
                    recycler_view_detailtransaksijasa.setVisibility(View.VISIBLE);
                    jenistrk="SS";
                    showListsparepart();
                    showListjasa();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        getintent();
        setDropdownmontir();
        spinnerkonsumen.setEnabled(false);
        setDropdownkonsumen();
        setSpinnerjasaservis();


        spinnersparepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedIdSparepart = listKodeSparepart.get(position); //Mendapatkan id dari dropdown yang dipilih
                selectedHargaSparepart = listHargaSparepart.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinnermontir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedidmontir = listidmontir.get(position); //Mendapatkan id dari dropdown yang dipilih

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinnermotorkonsumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedidmotorkonsumen = listidMotorkonsumen.get(position); //Mendapatkan id dari dropdown yang dipilih

                if(isi==0)
                {
                    setDropdownsparepart();
                }else{
                    listKodeSparepart.clear();
                    listNameSparepart.clear();
                    listHargaSparepart.clear();
                    setDropdownsparepart();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinnerjasaservis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedidservis=listidservis.get(position);
                selectedhargaservis=listhargaservis.get(position);
                selectednamaservis=listnamaservis.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerkonsumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedidkonsumen = listidkonsumen.get(position); //Mendapatkan id dari dropdown yang dipilih
                if(isi==0)
                {
                    setDropdownmotor();
                }else
                {
                    for(int x=0;x<listnamaMotorkonsumen.size();x++)
                    {
                        listnamaMotorkonsumen.remove(x);
                        listidMotorkonsumen.remove(x);
                    }
                    setDropdownmotor();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        patchpenjualansparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patchtransaksi();
            }
        });

    }

    public void addtoCart(){
       // Log.d( "add to cart",selectedIdSparepart);
        if(jenistrk.equalsIgnoreCase("SP"))
        {
            if(vjumlah.getText().toString().isEmpty() || vdiskon.getText().toString().isEmpty())
            {
                Toast.makeText(DetailPenjualanController.this, "Tambah Jumlah dan diskon transaksi!", Toast.LENGTH_SHORT).show();
            }else{
                detailssparepart.add(new DetailSparepart(null,selectedIdSparepart,Double.parseDouble(selectedHargaSparepart),Integer.parseInt(vjumlah.getText().toString()),Double.parseDouble(selectedHargaSparepart)*Double.parseDouble(vjumlah.getText().toString())));
                adapter = new PenjualanSparepartAdapter(getApplicationContext(),detailssparepart,1);
                rview.setAdapter(adapter);
            }
        }else if(jenistrk.equalsIgnoreCase("SV")){
            detailJasas.add(new DetailJasa(Integer.parseInt(selectedidservis),selectednamaservis,Integer.parseInt(selectedidmontir),Double.parseDouble(selectedhargaservis)));
            jasaCartAdapter = new JasaCartAdapter(getApplicationContext(),detailJasas,1);
            recycler_view_detailtransaksijasa.setAdapter(jasaCartAdapter);
        }else{
            detailssparepart.add(new DetailSparepart(null,selectedIdSparepart,Double.parseDouble(selectedHargaSparepart),Integer.parseInt(vjumlah.getText().toString()),Double.parseDouble(selectedHargaSparepart)*Double.parseDouble(vjumlah.getText().toString())));
            detailJasas.add(new DetailJasa(Integer.parseInt(selectedidservis),selectednamaservis,Integer.parseInt(selectedidmontir),Double.parseDouble(selectedhargaservis)));
            adapter = new PenjualanSparepartAdapter(getApplicationContext(),detailssparepart,1);
            jasaCartAdapter = new JasaCartAdapter(getApplicationContext(),detailJasas,1);
            rview.setAdapter(adapter);
            recycler_view_detailtransaksijasa.setAdapter(jasaCartAdapter);
        }
        hitungtotal();
    }

    public void patchtransaksi(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

        Call<ResponseBody> responseBodyCall= apiTransaksiPenjualan.patchpenjualansparepart(idtransaksi,mDisplayDate.getText().toString(),jenistrk,Double.parseDouble(vtotal.getText().toString()),Integer.parseInt(vdiskon.getText().toString()));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            //jenistrk.equalsIgnoreCase(getIntent().getStringExtra("jenis"))
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!getIntent().getStringExtra("jenissingkat").equalsIgnoreCase(jenistrk))
                {
                    if(getIntent().getStringExtra("jenissingkat").equalsIgnoreCase("SV") && jenistrk.equalsIgnoreCase("SP"))
                    {
                        Log.d( "delete sv ",response.message());
                        //deletesv
                        for(int x=0;x<detailJasas.size();x++)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiTransaksiPenjualan.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.deletedetailjasa(detailJasas.get(x).getIdDetailJasa());
                            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.code()==200)
                                    {
                                        Log.d( "sucess: ",response.message());
                                    }else
                                        Log.d( "gagal: ",response.message());
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }else if(getIntent().getStringExtra("jenissingkat").equalsIgnoreCase("SP") && jenistrk.equalsIgnoreCase("SV"))
                    {
                        //deletesp
                        Log.d( "delete sp ",response.message());
                        for(int x=0;x<detailssparepart.size();x++)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiTransaksiPenjualan.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.deletedetailsparepart(detailssparepart.get(x).getIdDetailSparepart());
                            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.code()==200)
                                    {
                                        Log.d( "sucess: ",response.message());
                                    }else
                                        Log.d( "gagal: ",response.message());
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }else if(getIntent().getStringExtra("jenissingkat").equalsIgnoreCase("SS") || jenistrk.equalsIgnoreCase("SP") || jenistrk.equalsIgnoreCase("SS"))
                    {
                        if(jenistrk.equalsIgnoreCase("SP"))
                        {
                            //deletesv
                            Log.d( "deletesv ",response.message());
                            for(int x=0;x<detailJasas.size();x++)
                            {
                                Gson gson = new GsonBuilder()
                                        .setLenient()
                                        .create();
                                Retrofit.Builder builder=new Retrofit.
                                        Builder().baseUrl(ApiTransaksiPenjualan.JSONURL).
                                        addConverterFactory(GsonConverterFactory.create(gson));
                                Retrofit retrofit=builder.build();
                                ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                                Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.deletedetailjasa(detailJasas.get(x).getIdDetailJasa());
                                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if(response.code()==200)
                                        {
                                            Log.d( "sucess: ",response.message());
                                        }else
                                            Log.d( "gagal: ",response.message());
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });
                            }
                        }else if(jenistrk.equalsIgnoreCase("SV")){
                            //deletesp
                            Log.d( "deletesp ",response.message());
                            for(int x=0;x<detailssparepart.size();x++)
                            {
                                Gson gson = new GsonBuilder()
                                        .setLenient()
                                        .create();
                                Retrofit.Builder builder=new Retrofit.
                                        Builder().baseUrl(ApiTransaksiPenjualan.JSONURL).
                                        addConverterFactory(GsonConverterFactory.create(gson));
                                Retrofit retrofit=builder.build();
                                ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                                Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.deletedetailsparepart(detailssparepart.get(x).getIdDetailSparepart());
                                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if(response.code()==200)
                                        {
                                            Log.d( "sucess: ",response.message());
                                        }else
                                            Log.d( "gagal: ",response.message());
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    }
                }
                if(jenistrk.equalsIgnoreCase("SP"))
                {
                    for(int x=0;x<detailssparepart.size();x++)
                    {
                        if(detailssparepart.get(x).getIdDetailSparepart()==null)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiSparepart.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.adddetailpenjualansparepart(idtransaksi,detailssparepart.get(x).getIdJasaMontir(),detailssparepart.get(x).getKodeSparepart(),detailssparepart.get(x).getHargaSatuan(),detailssparepart.get(x).getJumlah(),detailssparepart.get(x).getSubtotalDetailSparepart());
                            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        JSONObject jsonRes = new JSONObject(response.body().string());
                                        String iddetailpenjualansparepart =  jsonRes.getJSONObject("data").getString("Id_Detail_Sparepart");
                                        Log.d("Id_Detail_Penjualan : ", iddetailpenjualansparepart);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }

                }else if(jenistrk.equalsIgnoreCase("SV"))
                {
                    for(int x=0;x<detailJasas.size();x++)
                    {
                        if(detailJasas.get(x).getIdDetailJasa()==null)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiSparepart.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.adddetailpenjualanjasa(idtransaksi,detailJasas.get(x).getIdJasa(),detailJasas.get(x).getSubtotalDetailJasa());
                            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        JSONObject jsonRes = new JSONObject(response.body().string());
                                        String iddetailpenjualanjasa =  jsonRes.getJSONObject("data").getString("Id_Detail_Jasa");
                                        Log.d("Id_Detail_Penjualan : ", iddetailpenjualanjasa);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }
                }else {
                    //add sparepart dan servis
                    for(int x=0;x<detailssparepart.size();x++)
                    {
                        if(detailssparepart.get(x).getIdDetailSparepart()==null)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiSparepart.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.adddetailpenjualansparepart(idtransaksi,detailssparepart.get(x).getIdJasaMontir(),detailssparepart.get(x).getKodeSparepart(),detailssparepart.get(x).getHargaSatuan(),detailssparepart.get(x).getJumlah(),detailssparepart.get(x).getSubtotalDetailSparepart());
                            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        JSONObject jsonRes = new JSONObject(response.body().string());
                                        String iddetailpenjualansparepart =  jsonRes.getJSONObject("data").getString("Id_Detail_Sparepart");
                                        Log.d("Id_Detail_Penjualan : ", iddetailpenjualansparepart);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }

                    for(int x=0;x<detailJasas.size();x++)
                    {
                        if(detailJasas.get(x).getIdDetailJasa()==null)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiSparepart.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.adddetailpenjualanjasa(idtransaksi,detailJasas.get(x).getIdJasa(),detailJasas.get(x).getSubtotalDetailJasa());
                            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        JSONObject jsonRes = new JSONObject(response.body().string());
                                        String iddetailpenjualanjasa =  jsonRes.getJSONObject("data").getString("Id_Detail_Jasa");
                                        Log.d("Id_Detail_Penjualan : ", iddetailpenjualanjasa);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }
                }
                Intent i= new Intent(DetailPenjualanController.this, MenuPenjualan.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void showListsparepart(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);
        //Calling APi
        Call<DetailSparepart_data> detailSparepart_dataCall = apiTransaksiPenjualan.tampildetailsparepartkonsumen(idtransaksi);
        detailSparepart_dataCall.enqueue(new Callback<DetailSparepart_data>() {
            @Override
            public void onResponse(Call<DetailSparepart_data> call, Response<DetailSparepart_data> response) {
                Log.d( "onResponse: ",response.body().getData().toString());
                try{
                    adapter.notifyDataSetChanged();
                    detailssparepart=response.body().getData();
                    //Log.d("id Detail Pengadaan: ",details.get(0).getIdDetailPengadaan().toString());
                    adapter = new PenjualanSparepartAdapter(getApplicationContext(),detailssparepart,1);
                    rview.setAdapter(adapter);
                }catch(Exception e){
                    Toast.makeText(DetailPenjualanController.this, "Belum ada detail!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailSparepart_data> call, Throwable t) {
                Toast.makeText(DetailPenjualanController.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showListjasa(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);
        //Calling APi
        Call<DetailJasa_data> detailSparepart_dataCall = apiTransaksiPenjualan.tampildetailjasakonsumen(idtransaksi);
        detailSparepart_dataCall.enqueue(new Callback<DetailJasa_data>() {
            @Override
            public void onResponse(Call<DetailJasa_data> call, Response<DetailJasa_data> response) {
                Log.d( "onResponse: ",response.body().getData().toString());
                try{
                    adapter.notifyDataSetChanged();
                    detailJasas=response.body().getData();
                    //Log.d("id Detail Pengadaan: ",details.get(0).getIdDetailPengadaan().toString());
                    jasaCartAdapter = new JasaCartAdapter(getApplicationContext(),detailJasas,1);
                    recycler_view_detailtransaksijasa.setAdapter(jasaCartAdapter);
                }catch(Exception e){
                    Toast.makeText(DetailPenjualanController.this, "Belum ada detail!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailJasa_data> call, Throwable t) {
                Toast.makeText(DetailPenjualanController.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getintent(){
        Integer subtotal=0;
        subtotal=getIntent().getIntExtra("subtotal",0);
        Log.d( "getintent: ",subtotal.toString());
        idtransaksi=getIntent().getIntExtra("idtransaksi",0);
        diskon=getIntent().getIntExtra("diskon",0);
        mDisplayDate.setText(getIntent().getStringExtra("tanggal"));
        vtotal.setText(subtotal.toString());
        vdiskon.setText(diskon.toString());
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Integer value1;
            hitungtotal();
            Double harga = intent.getDoubleExtra("harga",0);
            value1=harga.intValue();
            //Log.d( "subtotal  ",totalharga.toString());
        }
    };

    public void hitungtotal(){
        Integer totalharga=0;
        Integer value;

        if(jenistrk.equalsIgnoreCase("SP"))
        {
            for(int x=0;x<detailssparepart.size();x++)
            {
                Double total=detailssparepart.get(x).getSubtotalDetailSparepart();
                value=total.intValue();
                totalharga+=value;
            }
        }else if(jenistrk.equalsIgnoreCase("SV"))
        {
            for(int x=0;x<detailJasas.size();x++)
            {
                Double total=detailJasas.get(x).getSubtotalDetailJasa();
                value=total.intValue();
                totalharga+=value;
            }
        }else {
            for(int x=0;x<detailssparepart.size();x++)
            {
                Double total=detailssparepart.get(x).getSubtotalDetailSparepart();
                value=total.intValue();
                totalharga+=value;
            }
            for(int x=0;x<detailJasas.size();x++)
            {
                Double total=detailJasas.get(x).getSubtotalDetailJasa();
                value=total.intValue();
                totalharga+=value;
            }
        }
        vtotal.setText(totalharga.toString());
    }

    public BroadcastReceiver mMessageReceiverjasa = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Integer value1;
            hitungtotal();
            Double harga = intent.getDoubleExtra("harga",0);
            value1=harga.intValue();
            //Log.d( "subtotal  ",totalharga.toString());
        }
    };

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){

                return i;
            }
        }

        return 0;
    }

    public void setDropdownmotor(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiKonsumen apiKonsumen=retrofit.create(ApiKonsumen.class);

        //Calling APi
        Call<MotorKonsumen_data> listkonsumen = apiKonsumen.tampilmotorkonsumen(Integer.parseInt(selectedidkonsumen));
        listkonsumen.enqueue(new Callback<MotorKonsumen_data>() {
            @Override
            public void onResponse(Call<MotorKonsumen_data> call, Response<MotorKonsumen_data> response) {

                if(response.code()==200)
                {       List<MotorKonsumen> spinnerArrayList = response.body().getData();
                    if(response.body().getData().isEmpty())
                    {
                        spinnermotorkonsumen.setAdapter(null);
                    }else
                    {
                        for (int i = 0; i < spinnerArrayList.size(); i++) {
                            String namamotorkonsumen = spinnerArrayList.get(i).getTipe();
                            String idmotorkonsumen = spinnerArrayList.get(i).getIdMotor().toString();
                            listnamaMotorkonsumen.add(namamotorkonsumen);
                            listidMotorkonsumen.add(idmotorkonsumen);
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailPenjualanController.this,
                                    android.R.layout.simple_spinner_dropdown_item
                                    , listnamaMotorkonsumen);
                            spinnermotorkonsumen.setAdapter(adapter);
                            isi=1;
                        }
                    }
                }else{
                    Toast.makeText(DetailPenjualanController.this, "Belum Ada Motor Konsumen!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MotorKonsumen_data> call, Throwable t) {
                Toast.makeText(DetailPenjualanController.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDropdownkonsumen(){
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
                if(response.code()==201)
                {
                    List<Konsumen> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String namakonsumen = spinnerArrayList.get(i).getNamaKonsumen();
                        String idkonsumen = spinnerArrayList.get(i).getIdKonsumen().toString();
                        listnamakonsumen.add(namakonsumen);
                        listidkonsumen.add(idkonsumen);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailPenjualanController.this,
                                android.R.layout.simple_spinner_dropdown_item
                                , listnamakonsumen);
                        spinnerkonsumen.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(DetailPenjualanController.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Konsumen_data> call, Throwable t) {
                Toast.makeText(DetailPenjualanController.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDropdownmontir(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

        Call<Pegawai_data> pegawai_dataCall = apiTransaksiPenjualan.tampilmontir();
        pegawai_dataCall.enqueue(new Callback<Pegawai_data>() {
            @Override
            public void onResponse(Call<Pegawai_data> call, Response<Pegawai_data> response) {
                if(response.code()==201)
                {
                    List<Pegawai> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        if(spinnerArrayList.get(i).getIdRole()==4)
                        {
                            String namamontir = spinnerArrayList.get(i).getNamaPegawai();
                            String idmontir = spinnerArrayList.get(i).getId().toString();
                            listnamamontir.add(namamontir);
                            listidmontir.add(idmontir);
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailPenjualanController.this,
                                    android.R.layout.simple_spinner_dropdown_item
                                    , listnamamontir);
                            spinnermontir.setAdapter(adapter);
                        }
                    }
                }else{
                    Toast.makeText(DetailPenjualanController.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pegawai_data> call, Throwable t) {

            }
        });
    }

    public void setDropdownsparepart(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

        Call<Sparepart_data> sparepart_dataCall = apiTransaksiPenjualan.tampilsparepartmotor(Integer.parseInt(selectedidmotorkonsumen));
        sparepart_dataCall.enqueue(new Callback<Sparepart_data>() {
            @Override
            public void onResponse(Call<Sparepart_data> call, Response<Sparepart_data> response) {
                if(response.code()==201)
                {
                    List<Sparepart> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameSparepart = spinnerArrayList.get(i).getNamaSparepart();
                        String kodeSparepart = spinnerArrayList.get(i).getKodeSparepart();
                        String hargaSparepart = spinnerArrayList.get(i).getHargaBeli();
                        listNameSparepart.add(nameSparepart);
                        listKodeSparepart.add(kodeSparepart);
                        listHargaSparepart.add(hargaSparepart);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailPenjualanController.this,
                                android.R.layout.simple_spinner_dropdown_item
                                , listNameSparepart);
                        spinnersparepart.setAdapter(adapter);
                        isi=1;

                    }

                }else{
                    Toast.makeText(DetailPenjualanController.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sparepart_data> call, Throwable t) {
                Toast.makeText(DetailPenjualanController.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSpinnerjasaservis(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

        Call<Jasa_data> pegawai_dataCall = apiTransaksiPenjualan.tampilservis();
        pegawai_dataCall.enqueue(new Callback<Jasa_data>() {
            @Override
            public void onResponse(Call<Jasa_data> call, Response<Jasa_data> response) {
                if(response.code()==201)
                {
                    List<Jasa> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String namaservis = spinnerArrayList.get(i).getNamaJasa();
                        String idservis = spinnerArrayList.get(i).getIdJasa().toString();
                        String hargaservis = spinnerArrayList.get(i).getHargaJasa().toString();
                        listnamaservis.add(namaservis);
                        listidservis.add(idservis);
                        listhargaservis.add(hargaservis);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailPenjualanController.this,
                                android.R.layout.simple_spinner_dropdown_item
                                , listnamaservis);
                        spinnerjasaservis.setAdapter(adapter);

                    }
                }else{
                    Toast.makeText(DetailPenjualanController.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Jasa_data> call, Throwable t) {

            }
        });
    }
}