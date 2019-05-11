package atmaauto.atmaauto.com.atmaauto.DetilList;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.TambahPengadaan;
import atmaauto.atmaauto.com.atmaauto.adapter.MotorKonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartCartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan_data;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPengadaanController extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView vnamasales,mDisplayDate;
    EditText vtotalharga;
    EditText jumlahsparepart;
    String namasales,totalharga,date,idsales;
    Integer idpengadaan;
    Button addtocart,patchpengadaan;

    private List<DetailPengadaan> mListDetailPengadaan = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SparepartCartAdapter sparepartCartAdapter;

    Spinner spinnersparepart;
    String selectedIdSparepart,selectedHargaSparepart;
    private List<String> listNameSparepart = new ArrayList<String>();
    private List<String> listKodeSparepart = new ArrayList<String>();
    private List<String> listHargaSparepart = new ArrayList<String>();
    private List<DetailPengadaan> details = new ArrayList<DetailPengadaan>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengadaan);
        getintent();
        init();
        Log.d( "id Pengadaan: ",idpengadaan.toString());
        setDropdownsparepart();
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DetailPengadaanController.this,
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

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
            }
        });

        patchpengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patchpengadaan();
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
        Log.d( "jumlah sparepart: ",jumlahsparepart.getText().toString());
        details.add(new DetailPengadaan(selectedIdSparepart,
                Double.parseDouble(selectedHargaSparepart),
                Integer.parseInt(jumlahsparepart.getText().toString()),
                Double.parseDouble(selectedHargaSparepart)*Double.parseDouble(jumlahsparepart.getText().toString())));
        sparepartCartAdapter.notifyDataSetChanged();
        //sparepartCartAdapter = new SparepartCartAdapter(getApplication(),details);
        recyclerView.setAdapter(sparepartCartAdapter);

        hitungtotal();

        jumlahsparepart.setText("");
    }

    public void patchpengadaan(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiTransaksiPengadaan apiTransaksiPengadaan=retrofit.create(ApiTransaksiPengadaan.class);

        Call<ResponseBody> responseBodyCall = apiTransaksiPengadaan.updatetransaksipengadaan(idpengadaan,mDisplayDate.getText().toString(),Double.parseDouble(vtotalharga.getText().toString()));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==201)
                {
                    for(int x=0;x<details.size();x++)
                    {
                        if(details.get(x).getIdPengadaan()==null)
                        {
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit.Builder builder=new Retrofit.
                                    Builder().baseUrl(ApiSparepart.JSONURL).
                                    addConverterFactory(GsonConverterFactory.create(gson));
                            Retrofit retrofit=builder.build();
                            ApiTransaksiPengadaan apiTransaksiPengadaan=retrofit.create(ApiTransaksiPengadaan.class);

                            Call<ResponseBody> responseBodyCall = apiTransaksiPengadaan.adddetailpengadaan(idpengadaan,details.get(x).getKodeSparepart(),details.get(x).getHargaSatuan(),details.get(x).getJumlah(),details.get(x).getSubtotalPengadaan());
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
                    }
                }else{
                    Toast.makeText(DetailPengadaanController.this, "error!", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
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
            Log.d( "total  ",totalharga.toString());
        }

        vtotalharga.setText(totalharga.toString());
    }

    public void init(){
        vnamasales=(TextView) findViewById(R.id.namasales);
        vtotalharga=(EditText) findViewById(R.id.totalharga);
        mDisplayDate = (TextView) findViewById(R.id.datepicker);
        spinnersparepart=(Spinner) findViewById(R.id.spinnersparepart);
        jumlahsparepart=(EditText) findViewById(R.id.jumlahsparepart);
        addtocart = (Button) findViewById(R.id.addtocart);
        patchpengadaan=(Button) findViewById(R.id.patchpengadaan);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_pengadaan);
        sparepartCartAdapter=new SparepartCartAdapter(getApplication(),details);

        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showList();

        mDisplayDate.setText(date);
        vnamasales.setText(namasales);
        vtotalharga.setText(totalharga);
    }

    public void getintent(){
        Intent i=getIntent();

        namasales=i.getStringExtra("namasales");
        idsales=i.getStringExtra("idsales");
        totalharga=i.getStringExtra("totalharga");
        date=i.getStringExtra("tanggal");
        idpengadaan=i.getIntExtra("idpengadaan",0);
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailPengadaanController.this,
                                android.R.layout.simple_spinner_dropdown_item
                                , listNameSparepart);
                        spinnersparepart.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(DetailPengadaanController.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sparepart_data> call, Throwable t) {
                Toast.makeText(DetailPengadaanController.this, "network error!", Toast.LENGTH_SHORT).show();
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
        ApiTransaksiPengadaan apiKonsumen=retrofit.create(ApiTransaksiPengadaan.class);

        //Calling APi
        Call<DetailPengadaan_data> detailPengadaan_dataCall = apiKonsumen.tampildetilpengadaan(idpengadaan);
        detailPengadaan_dataCall.enqueue(new Callback<DetailPengadaan_data>() {
            @Override
            public void onResponse(Call<DetailPengadaan_data> call, Response<DetailPengadaan_data> response) {
                try{
                    sparepartCartAdapter.notifyDataSetChanged();
                    details=response.body().getData();
                    sparepartCartAdapter = new SparepartCartAdapter(getApplicationContext(),details);
                    recyclerView.setAdapter(sparepartCartAdapter);
                }catch(Exception e){
                    Toast.makeText(DetailPengadaanController.this, "Belum ada detail!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailPengadaan_data> call, Throwable t) {
                Toast.makeText(DetailPengadaanController.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
