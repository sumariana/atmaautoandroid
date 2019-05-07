package atmaauto.atmaauto.com.atmaauto;

import android.app.DatePickerDialog;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartCartAdapter;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan;
import atmaauto.atmaauto.com.atmaauto.models.Motor;
import atmaauto.atmaauto.com.atmaauto.models.Motor_data;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import atmaauto.atmaauto.com.atmaauto.models.Supplier;
import atmaauto.atmaauto.com.atmaauto.models.Supplier_data;
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
    Button addtocart;
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
        jumlahsparepart=(EditText) findViewById(R.id.jumlahsparepart);
        vtotalharga=(EditText) findViewById(R.id.totalharga);



        addtocart=(Button) findViewById(R.id.addtocart);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
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

                String date = month + "/" + day + "/" + year;
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
        details.add(new DetailPengadaan(selectedIdSparepart,
                Integer.parseInt(selectedHargaSparepart),
                Integer.parseInt(jumlahsparepart.getText().toString()),
                Integer.parseInt(selectedHargaSparepart)*Integer.parseInt(jumlahsparepart.getText().toString())));
        nilai=Integer.parseInt(selectedHargaSparepart)*Integer.parseInt(jumlahsparepart.getText().toString());
        adapter = new SparepartCartAdapter(details);
        rview.setAdapter(adapter);
        totalharga=totalharga+nilai;
        vtotalharga.setText(totalharga.toString());
        jumlahsparepart.setText("");
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
