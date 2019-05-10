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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    String namasales,totalharga,date;
    Integer idpengadaan;

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
        Log.d( "onCreate: ",idpengadaan.toString());
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
    }

    public void init(){
        vnamasales=(TextView) findViewById(R.id.namasales);
        vtotalharga=(EditText) findViewById(R.id.totalharga);
        mDisplayDate = (TextView) findViewById(R.id.datepicker);
        spinnersparepart=(Spinner) findViewById(R.id.spinnersparepart);
        jumlahsparepart=(EditText) findViewById(R.id.jumlahsparepart);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_pengadaan);
        sparepartCartAdapter=new SparepartCartAdapter(getApplication(),mListDetailPengadaan);

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
                    sparepartCartAdapter = new SparepartCartAdapter(getApplicationContext(),response.body().getData());
                    recyclerView.setAdapter(sparepartCartAdapter);
                }catch(Exception e){
                    Toast.makeText(DetailPengadaanController.this, "Belum ada motor konsumen!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailPengadaan_data> call, Throwable t) {
                Toast.makeText(DetailPengadaanController.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
