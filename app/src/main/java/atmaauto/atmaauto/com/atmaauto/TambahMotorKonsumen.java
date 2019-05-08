package atmaauto.atmaauto.com.atmaauto;

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
import atmaauto.atmaauto.com.atmaauto.adapter.MotorKonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Motor;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen_response;
import atmaauto.atmaauto.com.atmaauto.models.Motor_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahMotorKonsumen extends AppCompatActivity {

    String id;
    TextView idkonsumen;
    Button postmotor;
    EditText platmotor;
    MotorKonsumenAdapter motorKonsumenAdapter;

    Spinner spinnermotor;
    String selectedId;
    private List<String> listNameType = new ArrayList<String>();
    private List<String> listIdType = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_motor_konsumen);
        getkonsumen();
        inisialisasi();
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
        postmotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder=new Retrofit.
                        Builder().baseUrl(ApiSparepart.JSONURL).
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit=builder.build();
                ApiKonsumen apiKonsumen=retrofit.create(ApiKonsumen.class);

                //Calling APi
                Call<MotorKonsumen_response> motorKonsumen_responseCall = apiKonsumen.tambahMotorKonsumen(Integer.parseInt(id),Integer.parseInt(selectedId),platmotor.getText().toString());
                motorKonsumen_responseCall.enqueue(new Callback<MotorKonsumen_response>() {
                    @Override
                    public void onResponse(Call<MotorKonsumen_response> call, Response<MotorKonsumen_response> response) {
                        Toast.makeText(TambahMotorKonsumen.this, "berhasil!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<MotorKonsumen_response> call, Throwable t) {
                        Toast.makeText(TambahMotorKonsumen.this, "network error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void inisialisasi(){
        idkonsumen=(TextView) findViewById(R.id.idkonsumen);
        spinnermotor=(Spinner) findViewById(R.id.spinnertipe);
        postmotor=(Button) findViewById(R.id.btntambahmotor);
        platmotor=(EditText) findViewById(R.id.platmotor);
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahMotorKonsumen.this,
                                android.R.layout.simple_spinner_dropdown_item
                                , listNameType);
                        spinnermotor.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(TambahMotorKonsumen.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Motor_data> call, Throwable t) {
                Toast.makeText(TambahMotorKonsumen.this, "network error!", Toast.LENGTH_SHORT).show();
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

    private void getkonsumen() {
        Intent i = getIntent();

        id = i.getStringExtra("id");
    }
    private void setText(){
        idkonsumen.setText(id);
    }
}
