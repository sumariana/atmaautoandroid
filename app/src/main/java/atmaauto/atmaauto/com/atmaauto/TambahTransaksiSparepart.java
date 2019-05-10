package atmaauto.atmaauto.com.atmaauto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailKonsumen;
import atmaauto.atmaauto.com.atmaauto.SessionManager.SessionManager;
import atmaauto.atmaauto.com.atmaauto.adapter.KonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.MotorKonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.PenjualanSparepartAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartCartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan;
import atmaauto.atmaauto.com.atmaauto.models.DetailSparepart;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahTransaksiSparepart extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView mDisplayDate;

    private List<String> listNameSparepart = new ArrayList<String>();
    private List<String> listKodeSparepart = new ArrayList<String>();
    private List<String> listHargaSparepart = new ArrayList<String>();

    private List<String> listnamakonsumen = new ArrayList<String>();
    private List<String> listidkonsumen = new ArrayList<String>();

    private List<String> listnamaMotorkonsumen = new ArrayList<String>();
    private List<String> listidMotorkonsumen = new ArrayList<String>();

    private List<DetailSparepart> detailssparepart = new ArrayList<DetailSparepart>();

    Button addtocart,postpenjualansparepart;
    EditText jumlah,total,diskon;

    private Integer isi=0;
    Integer nilai=0,totalharga=0;


    Spinner spinnerkonsumen,spinnersparepart,spinnermotorkonsumen;
    String selectedidkonsumen,selectedIdSparepart,selectedHargaSparepart,selectedidmotorkonsumen;
    Integer idpegawai;

    SessionManager session;

    RecyclerView rview;
    private PenjualanSparepartAdapter adapter;
    private RecyclerView.LayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi_sparepart);

        //dataintent();
        mDisplayDate = (TextView) findViewById(R.id.datepicker);

        session = new SessionManager(TambahTransaksiSparepart.this);
        Log.d( "ID PEGAWAI : ",session.getKeyId());


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
        spinnermotorkonsumen=(Spinner) findViewById(R.id.spinnermotorkonsumen);
        setDropdownkonsumen();
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

        spinnermotorkonsumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedidmotorkonsumen = listidMotorkonsumen.get(position); //Mendapatkan id dari dropdown yang dipilih
                //selectedHargaSparepart = listHargaSparepart.get(position);
                //Log.d("ID Sparepart : ",selectedIdSparepart);
                //Log.d("Harga Sparepart : ",selectedHargaSparepart);
                //Double total = Double.parseDouble(selectedHargaSparepart)*nilai;
                //Log.d("total  : ",total.toString());

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

        spinnerkonsumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedidkonsumen = listidkonsumen.get(position); //Mendapatkan id dari dropdown yang dipilih
                Log.d("ID Konsumen : ",selectedidkonsumen);
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
                //Log.d("Harga Sparepart : ",selectedHargaSparepart);
                //Double total = Double.parseDouble(selectedHargaSparepart)*nilai;
                //Log.d("total  : ",total.toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
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
        Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.addPenjualansparepart(Integer.parseInt(selectedidkonsumen),mDisplayDate.getText().toString(),"SP",Double.parseDouble(total.getText().toString()),Integer.parseInt(diskon.getText().toString()),null,Integer.parseInt(selectedidmotorkonsumen),Integer.parseInt(session.getKeyId()));
        Log.d("TAG: ",selectedidkonsumen);
        Log.d("TAG: ",mDisplayDate.getText().toString());
        Log.d("TAG: ",total.getText().toString());
        Log.d("TAG: ",diskon.getText().toString());
        Log.d("TAG: ",selectedidmotorkonsumen);
        Log.d("TAG: ",session.getKeyId());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonRes = new JSONObject(response.body().string());
                    String idTransaksi =  jsonRes.getJSONObject("data").getString("Id_Transaksi");
                    Log.d("id_transaksi : ",idTransaksi);
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
                                    Log.d("Id_Detail_Pengadaan : ", iddetailpenjualansparepart);
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
                }catch (JSONException e){
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void addtoCart(){
        rview = findViewById(R.id.recycler_view_detailtransaksi);
        //rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(layout);
      detailssparepart.add(new DetailSparepart(null,selectedIdSparepart,Double.parseDouble(selectedHargaSparepart),Integer.parseInt(jumlah.getText().toString()),Double.parseDouble(selectedHargaSparepart)*Double.parseDouble(jumlah.getText().toString())));
      adapter = new PenjualanSparepartAdapter(detailssparepart);
      nilai=Integer.parseInt(selectedHargaSparepart)*Integer.parseInt(jumlah.getText().toString());
      totalharga=totalharga+nilai;
      total.setText(totalharga.toString());
      rview.setAdapter(adapter);


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

//    public void dataintent(){
//        Intent i=getIntent();
//        idpegawai=i.getIntExtra("idPegawai",0);
//        Log.d("dataintent: ",idpegawai.toString());
//    }

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
