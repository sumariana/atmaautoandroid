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
import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartCartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan;
import atmaauto.atmaauto.com.atmaauto.models.Motor;
import atmaauto.atmaauto.com.atmaauto.models.Motor_data;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import atmaauto.atmaauto.com.atmaauto.models.Supplier;
import atmaauto.atmaauto.com.atmaauto.models.Supplier_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahPengadaan extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView mDisplayDate;
    EditText jumlahsparepart;
    EditText vtotalharga;
    Integer totalharga=0;
    Integer nilai=0;

    Button addtocart,postpengadaan;
    Spinner spinnersales,spinnersparepart;
    String selectedIdSales,selectedIdSparepart,selectedHargaSparepart;
    private List<String> listNameSales = new ArrayList<String>();
    private List<String> listIdSales = new ArrayList<String>();
    private List<String> listNameSparepart = new ArrayList<String>();
    private List<String> listKodeSparepart = new ArrayList<String>();
    private List<String> listHargaSparepart = new ArrayList<String>();
    private List<DetailPengadaan> details = new ArrayList<DetailPengadaan>();

    RecyclerView rview;
    private SparepartCartAdapter adapter;
    private RecyclerView.LayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengadaan);
        spinnersales=(Spinner) findViewById(R.id.spinnersales);
        spinnersparepart=(Spinner) findViewById(R.id.spinnersparepart);
        postpengadaan=(Button) findViewById(R.id.postpengadaan);
        jumlahsparepart=(EditText) findViewById(R.id.jumlahsparepart);
        vtotalharga=(EditText) findViewById(R.id.totalharga);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        //kurangharga();

        addtocart=(Button) findViewById(R.id.addtocart);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
            }
        });
        postpengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postpengadaan();
            }
        });



        rview = findViewById(R.id.recycler_view_pengadaan);
        //rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(layout);

        setDropdownsales();
        setDropdownsparepart();
        mDisplayDate = (TextView) findViewById(R.id.datepicker);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TambahPengadaan.this,
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

        spinnersales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedIdSales = listIdSales.get(position); //Mendapatkan id dari dropdown yang dipilih
                Log.d("ID Sales : ",selectedIdSales);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        spinnersparepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedIdSparepart = listKodeSparepart.get(position); //Mendapatkan id dari dropdown yang dipilih
                selectedHargaSparepart = listHargaSparepart.get(position);
                Log.d("ID Sparepart : ",selectedIdSparepart);
                Log.d("Harga Sparepart : ",selectedHargaSparepart);
                //Double total = Double.parseDouble(selectedHargaSparepart)*nilai;
                //Log.d("total  : ",total.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    public void addtoCart(){
        details.add(new DetailPengadaan(0,selectedIdSparepart,
                Double.parseDouble(selectedHargaSparepart),
                Integer.parseInt(jumlahsparepart.getText().toString()),
                Double.parseDouble(selectedHargaSparepart)*Double.parseDouble(jumlahsparepart.getText().toString())));
        adapter = new SparepartCartAdapter(getApplication(),details);
        rview.setAdapter(adapter);

        hitungtotal();

        jumlahsparepart.setText("");
    }
    public void hitungtotal(){
       //totalhargamasih bug saat salah 1 detail dihapus
//        nilai=Integer.parseInt(selectedHargaSparepart)*Integer.parseInt(jumlahsparepart.getText().toString());
//        totalharga=totalharga+nilai;

        Integer totalharga=0;
        Integer value;
        for(int x=0;x<details.size();x++)
        {
            Log.d( "kode sparepart  ",details.get(x).getKodeSparepart());
            Log.d( "harga sparepart  ",details.get(x).getSubtotalPengadaan().toString());
            Double total=details.get(x).getSubtotalPengadaan();
            value=total.intValue();
            totalharga+=value;
            //Log.d( "total  ",totalharga.toString());
        }

        vtotalharga.setText(totalharga.toString());
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Integer value1;
            hitungtotal();
            Double harga = intent.getDoubleExtra("harga",0);
            value1=harga.intValue();
            Log.d( "subtotal  ",totalharga.toString());
        }
    };

    public void postpengadaan(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPengadaan apiTransaksiPengadaan=retrofit.create(ApiTransaksiPengadaan.class);

        Call<ResponseBody> responseBodyCall = apiTransaksiPengadaan.addpengadaan(Integer.parseInt(selectedIdSales),mDisplayDate.getText().toString(),Double.parseDouble(vtotalharga.getText().toString()),0);
        Log.d( "tanggal: ",mDisplayDate.getText().toString());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonRes = new JSONObject(response.body().string());
                    String idPengadaan =  jsonRes.getJSONObject("data").getString("Id_Pengadaan");

                    for(int x=0;x<details.size();x++)
                    {
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
                        Retrofit.Builder builder=new Retrofit.
                                Builder().baseUrl(ApiSparepart.JSONURL).
                                addConverterFactory(GsonConverterFactory.create(gson));
                        Retrofit retrofit=builder.build();
                        ApiTransaksiPengadaan apiTransaksiPengadaan=retrofit.create(ApiTransaksiPengadaan.class);

                        Call<ResponseBody> responseBodyCall = apiTransaksiPengadaan.adddetailpengadaan(Integer.parseInt(idPengadaan),details.get(x).getKodeSparepart(),details.get(x).getHargaSatuan(),details.get(x).getJumlah(),details.get(x).getSubtotalPengadaan());
                        responseBodyCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    JSONObject jsonRes = new JSONObject(response.body().string());
                                    String iddetailprocurement =  jsonRes.getJSONObject("data").getString("Id_Detail_Pengadaan");
                                    Log.d("Id_Detail_Pengadaan : ", iddetailprocurement);
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
                    final Intent intent= new Intent(TambahPengadaan.this,MenuPengadaan.class);
                    startActivity(intent);
                }catch (JSONException e) {
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

    public void setDropdownsales(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSupplierSales apiSupplierSales=retrofit.create(ApiSupplierSales.class);

        Call<Supplier_data> supplier_dataCall = apiSupplierSales.tampilSupplier();
        supplier_dataCall.enqueue(new Callback<Supplier_data>() {
            @Override
            public void onResponse(Call<Supplier_data> call, Response<Supplier_data> response) {
                if(response.code()==201)
                {
                    List<Supplier> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameSales = spinnerArrayList.get(i).getNamaSales();
                        String idSales = spinnerArrayList.get(i).getIdSupplier().toString();
                        listNameSales.add(nameSales);
                        listIdSales.add(idSales);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahPengadaan.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameSales);
                    spinnersales.setAdapter(adapter);
                }else{
                    Toast.makeText(TambahPengadaan.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Supplier_data> call, Throwable t) {
                Toast.makeText(TambahPengadaan.this, "network error!", Toast.LENGTH_SHORT).show();
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
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        Call<Sparepart_data> sparepart_dataCall = apiSparepart.tampilkatalog();
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahPengadaan.this,
                                android.R.layout.simple_spinner_dropdown_item
                                , listNameSparepart);
                        spinnersparepart.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(TambahPengadaan.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sparepart_data> call, Throwable t) {
                Toast.makeText(TambahPengadaan.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
