package atmaauto.atmaauto.com.atmaauto;

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
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailPenjualanController;
import atmaauto.atmaauto.com.atmaauto.SessionManager.SessionManager;
import atmaauto.atmaauto.com.atmaauto.adapter.JasaCartAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.PenjualanSparepartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.DetailJasa;
import atmaauto.atmaauto.com.atmaauto.models.DetailJasa_data;
import atmaauto.atmaauto.com.atmaauto.models.DetailSparepart;
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

import static android.view.View.GONE;

public class TambahTransaksiSparepart extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView mDisplayDate,labelsparepart,labelservis;
    String jenistrk;

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

    private List<DetailSparepart> detailssparepart = new ArrayList<DetailSparepart>();
    private List<DetailJasa> detailJasas = new ArrayList<DetailJasa>();

    Button addtocart,postpenjualansparepart;
    EditText jumlah,total,diskon;

    private Integer isi=0;
    Integer nilai=0,totalharga=0;



    Spinner spinnerkonsumen,spinnersparepart,spinnermotorkonsumen,spinnermontir,spinnerjenistransaksi,spinnerjasaservis;
    String selectedidkonsumen,selectedIdSparepart,selectedHargaSparepart,selectedidmotorkonsumen,selectedidmontir,selectedhargaservis,selectedidservis,selectednamaservis;
    //Integer idpegawai;

    SessionManager session;

    RecyclerView rview,recycler_view_detailtransaksijasa;
    private PenjualanSparepartAdapter adapter;
    private JasaCartAdapter jasaCartAdapter;
    private RecyclerView.LayoutManager layout,layoutjasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi_sparepart);

        //dataintent();
        mDisplayDate = (TextView) findViewById(R.id.datepicker);

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


        addtocart=(Button) findViewById(R.id.addcart);
        jumlah=(EditText) findViewById(R.id.jumlah);
        diskon=(EditText) findViewById(R.id.diskon);
        total=(EditText) findViewById(R.id.total);
        postpenjualansparepart = (Button) findViewById(R.id.postpenjualan);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
            }
        });
        postpenjualansparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postpenjualansparepart();
            }
        });
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TambahTransaksiSparepart.this,
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

        spinnersparepart=(Spinner) findViewById(R.id.spinnersparepart);
        spinnerkonsumen=(Spinner) findViewById(R.id.spinnerkonsumen);
        spinnermontir=(Spinner) findViewById(R.id.spinnermontir);
        spinnermotorkonsumen=(Spinner) findViewById(R.id.spinnermotorkonsumen);
        spinnerjenistransaksi=(Spinner) findViewById(R.id.spinnerjenistransaksi);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.JenisTransaksi,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerjenistransaksi.setAdapter(adapter1);
        spinnerjenistransaksi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = parentView.getItemAtPosition(position).toString();
                //Toast.makeText(MainActivity.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();

                if(text.equalsIgnoreCase("Penjualan Sparepart"))
                {
                    Toast.makeText(TambahTransaksiSparepart.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();
                    spinnersparepart.setVisibility(View.VISIBLE);
                    labelsparepart.setVisibility(View.VISIBLE);
                    rview.setVisibility(View.VISIBLE);
                    jumlah.setVisibility(View.VISIBLE);
                    spinnerjasaservis.setVisibility(View.GONE);
                    labelservis.setVisibility(View.GONE);
                    recycler_view_detailtransaksijasa.setVisibility(View.GONE);
                    jenistrk="SP";
                    total.setText("");
                }else if(text.equalsIgnoreCase("Penjualan Jasa")){
                    Toast.makeText(TambahTransaksiSparepart.this, "Clicked! "+text, Toast.LENGTH_SHORT).show();
                    spinnersparepart.setVisibility(View.GONE);
                    labelsparepart.setVisibility(View.GONE);
                    rview.setVisibility(View.GONE);
                    jumlah.setVisibility(View.GONE);
                    spinnerjasaservis.setVisibility(View.VISIBLE);
                    labelservis.setVisibility(View.VISIBLE);
                    recycler_view_detailtransaksijasa.setVisibility(View.VISIBLE);
                    jenistrk="SV";
                    total.setText("");
                }else {
                    spinnersparepart.setVisibility(View.VISIBLE);
                    labelsparepart.setVisibility(View.VISIBLE);
                    rview.setVisibility(View.VISIBLE);
                    jumlah.setVisibility(View.VISIBLE);
                    spinnerjasaservis.setVisibility(View.VISIBLE);
                    labelservis.setVisibility(View.VISIBLE);
                    recycler_view_detailtransaksijasa.setVisibility(View.VISIBLE);
                    jenistrk="SS";
                    total.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        setDropdownmontir();
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahTransaksiSparepart.this,
                                    android.R.layout.simple_spinner_dropdown_item
                                    , listnamaservis);
                            spinnerjasaservis.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(TambahTransaksiSparepart.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Jasa_data> call, Throwable t) {

            }
        });
    }


    public void postpenjualansparepart(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);
//        Log.d("idkonsumen: ",selectedidkonsumen);
//        Log.d("date: ",mDisplayDate.getText().toString());
//        Log.d("total: ",total.getText().toString());
//        Log.d("diskon: ",diskon.getText().toString());
//        Log.d("idmotorkonsumen: ",selectedidmotorkonsumen);
//        Log.d("session: ",session.getKeyId());
        Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.addPenjualansparepart(Integer.parseInt(selectedidkonsumen),mDisplayDate.getText().toString(),jenistrk,Double.parseDouble(total.getText().toString()),Integer.parseInt(diskon.getText().toString()),Integer.parseInt(selectedidmontir),Integer.parseInt(selectedidmotorkonsumen),Integer.parseInt(session.getKeyId()));

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    JSONObject jsonRes = new JSONObject(response.body().string());
                    String idTransaksi =  jsonRes.getJSONObject("data").getString("Id_Transaksi");
                    Log.d("id_transaksi : ",idTransaksi);

                    if(jenistrk.equalsIgnoreCase("SP"))
                    {
                        for(int x=0;x<detailssparepart.size();x++)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiSparepart.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.adddetailpenjualansparepart(Integer.parseInt(idTransaksi),detailssparepart.get(x).getIdJasaMontir(),detailssparepart.get(x).getKodeSparepart(),detailssparepart.get(x).getHargaSatuan(),detailssparepart.get(x).getJumlah(),detailssparepart.get(x).getSubtotalDetailSparepart());
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
                    }else if(jenistrk.equalsIgnoreCase("SV"))
                    {
                        for(int x=0;x<detailJasas.size();x++)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiSparepart.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                            Log.d("onResponse: ",idTransaksi);
                            Log.d("onResponse: ",detailJasas.get(x).getIdJasa().toString());
                            Log.d("onResponse: ",detailJasas.get(x).getIdJasaMontir().toString());
                            Log.d("onResponse: ",detailJasas.get(x).getSubtotalDetailJasa().toString());


                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.adddetailpenjualanjasa(Integer.parseInt(idTransaksi),detailJasas.get(x).getIdJasa(),detailJasas.get(x).getSubtotalDetailJasa());
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
                    }else {
                        for (int x = 0; x < detailssparepart.size(); x++) {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder = new Retrofit.
                                    Builder().baseUrl(ApiSparepart.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit = builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan = retrofit.create(ApiTransaksiPenjualan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.adddetailpenjualansparepart(Integer.parseInt(idTransaksi), detailssparepart.get(x).getIdJasaMontir(), detailssparepart.get(x).getKodeSparepart(), detailssparepart.get(x).getHargaSatuan(), detailssparepart.get(x).getJumlah(), detailssparepart.get(x).getSubtotalDetailSparepart());
                            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        JSONObject jsonRes = new JSONObject(response.body().string());
                                        String iddetailpenjualansparepart = jsonRes.getJSONObject("data").getString("Id_Detail_Sparepart");
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

                        for(int x=0;x<detailJasas.size();x++)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiSparepart.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.adddetailpenjualanjasa(Integer.parseInt(idTransaksi),detailJasas.get(x).getIdJasa(),detailJasas.get(x).getSubtotalDetailJasa());
                            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        JSONObject jsonRes = new JSONObject(response.body().string());
                                        String iddetailpenjualanjasa =  jsonRes.getJSONObject("data").getString("Id_Detail_Jasas");
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
                }catch (JSONException e){
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                Intent i= new Intent(TambahTransaksiSparepart.this, MenuPenjualan.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



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
        total.setText(totalharga.toString());
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Integer value1;
            hitungtotal();
            Double harga = intent.getDoubleExtra("harga",0);
            value1=harga.intValue();
            Log.d( "subtotal  ",totalharga.toString());
        }
    };

    public BroadcastReceiver mMessageReceiverjasa = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Integer value1;
            hitungtotal();
            Double harga = intent.getDoubleExtra("harga",0);
            value1=harga.intValue();
            Log.d( "subtotal  ",totalharga.toString());
        }
    };

    public void addtoCart(){
        //Log.d( "add to cart",selectedIdSparepart);
        if(jenistrk.equalsIgnoreCase("SP"))
        {
            if(jumlah.getText().toString().isEmpty())
            {
                Toast.makeText(TambahTransaksiSparepart.this, "Tambah Jumlah Bos!", Toast.LENGTH_SHORT).show();
            }else{
                detailssparepart.add(new DetailSparepart(null,selectedIdSparepart,Double.parseDouble(selectedHargaSparepart),Integer.parseInt(jumlah.getText().toString()),Double.parseDouble(selectedHargaSparepart)*Double.parseDouble(jumlah.getText().toString())));
                adapter = new PenjualanSparepartAdapter(getApplicationContext(),detailssparepart,1);
                nilai=Integer.parseInt(selectedHargaSparepart)*Integer.parseInt(jumlah.getText().toString());
                totalharga=totalharga+nilai;
                total.setText(totalharga.toString());
                rview.setAdapter(adapter);
            }
        }else if(jenistrk.equalsIgnoreCase("SV")){
            detailJasas.add(new DetailJasa(Integer.parseInt(selectedidservis),selectednamaservis,Integer.parseInt(selectedidmontir),Double.parseDouble(selectedhargaservis)));
            jasaCartAdapter = new JasaCartAdapter(getApplicationContext(),detailJasas,1);
            recycler_view_detailtransaksijasa.setAdapter(jasaCartAdapter);
        }else{
            detailssparepart.add(new DetailSparepart(null,selectedIdSparepart,Double.parseDouble(selectedHargaSparepart),Integer.parseInt(jumlah.getText().toString()),Double.parseDouble(selectedHargaSparepart)*Double.parseDouble(jumlah.getText().toString())));
            detailJasas.add(new DetailJasa(Integer.parseInt(selectedidservis),selectednamaservis,Integer.parseInt(selectedidmontir),Double.parseDouble(selectedhargaservis)));
            adapter = new PenjualanSparepartAdapter(getApplicationContext(),detailssparepart,1);
            jasaCartAdapter = new JasaCartAdapter(getApplicationContext(),detailJasas,1);
            rview.setAdapter(adapter);
            recycler_view_detailtransaksijasa.setAdapter(jasaCartAdapter);

            Toast.makeText(TambahTransaksiSparepart.this, "Transaksi SS!", Toast.LENGTH_SHORT).show();
        }
        hitungtotal();
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
                Log.d("Nilai Response : ",String.valueOf(response.code()));
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahTransaksiSparepart.this,
                                    android.R.layout.simple_spinner_dropdown_item
                                    , listnamaMotorkonsumen);
                            spinnermotorkonsumen.setAdapter(adapter);
                            isi=1;
                        }
                    }
                }else{
                    Toast.makeText(TambahTransaksiSparepart.this, "Belum Ada Motor Konsumen!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MotorKonsumen_data> call, Throwable t) {
                Toast.makeText(TambahTransaksiSparepart.this, "network error!", Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahTransaksiSparepart.this,
                                android.R.layout.simple_spinner_dropdown_item
                                , listnamakonsumen);
                        spinnerkonsumen.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(TambahTransaksiSparepart.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Konsumen_data> call, Throwable t) {
                Toast.makeText(TambahTransaksiSparepart.this, "network error!", Toast.LENGTH_SHORT).show();
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

        Call<Pegawai_data> pegawai_dataCall = apiTransaksiPenjualan.tampilmontircabang(Integer.parseInt(session.getIdCabang()));
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahTransaksiSparepart.this,
                                    android.R.layout.simple_spinner_dropdown_item
                                    , listnamamontir);
                            spinnermontir.setAdapter(adapter);
                        }
                    }
                }else{
                    Toast.makeText(TambahTransaksiSparepart.this, "error!", Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahTransaksiSparepart.this,
                                android.R.layout.simple_spinner_dropdown_item
                                , listNameSparepart);
                        spinnersparepart.setAdapter(adapter);
                        isi=1;

                    }

                }else{
                    Toast.makeText(TambahTransaksiSparepart.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sparepart_data> call, Throwable t) {
                Toast.makeText(TambahTransaksiSparepart.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
