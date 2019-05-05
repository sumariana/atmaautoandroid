package atmaauto.atmaauto.com.atmaauto.DetilList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.TambahMotorKonsumen;
import atmaauto.atmaauto.com.atmaauto.adapter.MotorKonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Motor;
import atmaauto.atmaauto.com.atmaauto.models.Motor_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailMotorKonsumen extends AppCompatActivity {

    TextView vidmkn,vidkn,vidm,vplat;
    String idmkn,idkn,idm,plat;
    Spinner spinnermotor;
    String selectedId;
    Button patchmotorkonsumen;
    MotorKonsumenAdapter motorKonsumenAdapter;
    private List<String> listNameType = new ArrayList<String>();
    private List<String> listIdType = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_motor_konsumen);
        inisialisasi();
        getData();
        setText();
        setDropdown();


        spinnermotor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedId = listIdType.get(position); //Mendapatkan id dari dropdown yang dipilih
                Log.d("ID Motor : ",selectedId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        patchmotorkonsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patchmkn();
            }
        });
    }

    private void patchmkn(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiKonsumen apiKonsumen=retrofit.create(ApiKonsumen.class);

        Call<ResponseBody> updatemotor = apiKonsumen.updatemotorkonsumen(Integer.parseInt(idmkn),Integer.parseInt(selectedId),vplat.getText().toString());
        updatemotor.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()!=500)
                {
                    Toast.makeText(DetailMotorKonsumen.this, "berhasil update!", Toast.LENGTH_SHORT).show();
                    motorKonsumenAdapter.notifyDataSetChanged();
                    finish();
                }else {
                    Toast.makeText(DetailMotorKonsumen.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void inisialisasi(){
        spinnermotor=(Spinner) findViewById(R.id.spinnertipe);
        vidmkn=(TextView) findViewById(R.id.idmotorkonsumen);
        vidkn=(TextView) findViewById(R.id.idkonsumen);
        vidm=(TextView) findViewById(R.id.idmotor);
        vplat=(TextView) findViewById(R.id.platmotor);
        patchmotorkonsumen=(Button) findViewById(R.id.btnupdatemotor);
    }

    private void getData() {
        Intent i = getIntent();

        idmkn = i.getStringExtra("idmkn");
        idkn = i.getStringExtra("idkn");
        idm = i.getStringExtra("idm");
        plat = i.getStringExtra("plat");
    }
    private void setText(){
        vidmkn.setText(idmkn);
        vidkn.setText(idkn);
        vidm.setText(idm);
        vplat.setText(plat);
    }

    public void setDropdown(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiKonsumen apiKonsumen=retrofit.create(ApiKonsumen.class);

        Call<Motor_data> listmotor = apiKonsumen.getlistmotor();
        listmotor.enqueue(new Callback<Motor_data>() {
            @Override
            public void onResponse(Call<Motor_data> call, Response<Motor_data> response) {
                if(response.code()==201)
                {
                    List<Motor> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameType = spinnerArrayList.get(i).getTipe();
                        String idType = spinnerArrayList.get(i).getIdMotor().toString();
                        listNameType.add(nameType);
                        listIdType.add(idType);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailMotorKonsumen.this,
                                android.R.layout.simple_spinner_dropdown_item
                                , listNameType);
                        spinnermotor.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(DetailMotorKonsumen.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Motor_data> call, Throwable t) {
                Toast.makeText(DetailMotorKonsumen.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){

                return i;
            }
        }

        return 0;
    }
}
