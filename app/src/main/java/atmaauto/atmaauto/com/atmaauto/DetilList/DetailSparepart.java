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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.MenuSparepart;
import atmaauto.atmaauto.com.atmaauto.MenuSupplier;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.TambahSparepart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailSparepart extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner placement,rooming;

    Button selectimage,patchsparepart;


    String namasp,raksp,hargabelisp,hargasp,kodesp,merksp,tipesp,jmlsp,stokminsp,gambar;

    EditText kode,nama,merk,hargabeli,hargajual,jumlah,jmlmin,nomor,tipebarang;
    TextView rakv;
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sparepart);

        placement = (Spinner) findViewById(R.id.place);
        rooming = (Spinner) findViewById(R.id.room);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.Place,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this,R.array.Room,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placement.setAdapter(adapter1);
        placement.setOnItemSelectedListener(this);
        rooming.setAdapter(adapter2);
        rooming.setOnItemSelectedListener(this);

        inisialisasi();
        getsparepart();
        setText();

        patchsparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatesparepart();
            }
        });

    }



    private void getsparepart(){
        Intent i=getIntent();

        namasp=i.getStringExtra("namasp");
        hargabelisp=i.getStringExtra("hargabelisp");
        raksp=i.getStringExtra("raksp");
        hargasp=i.getStringExtra("hargasp");
        kodesp=i.getStringExtra("kodesp");
        merksp=i.getStringExtra("merksp");
        tipesp=i.getStringExtra("tipesp");
        jmlsp=i.getStringExtra("jmlsp");
        gambar=i.getStringExtra("gambarsp");
        stokminsp=i.getStringExtra("jmlminsp");
    }

    private void inisialisasi(){
        patchsparepart=(Button) findViewById(R.id.patchsparepart);
        kode=(EditText) findViewById(R.id.kodesparepart);
        nama=(EditText) findViewById(R.id.namasparepart);
        merk=(EditText) findViewById(R.id.merksparepart);
        hargabeli=(EditText) findViewById(R.id.hargabelisparepart);
        hargajual=(EditText) findViewById(R.id.hargajualsparepart);
        jumlah=(EditText) findViewById(R.id.jumlahsparepart);
        jmlmin=(EditText) findViewById(R.id.jumlahmin);
        nomor=(EditText) findViewById(R.id.nomor);
        pic=(ImageView) findViewById(R.id.pic);
        tipebarang=(EditText) findViewById(R.id.tipebarang);
        rakv=(TextView) findViewById(R.id.raksebelum);
    }
    public void updatesparepart(){
        if(nama.getText().toString()=="" ||
                merk.getText().toString()=="" ||
                hargabeli.getText().toString()=="" ||
                hargajual.getText().toString()=="" ||
                jumlah.getText().toString()=="" ||
                jmlmin.getText().toString()=="" ||
                nomor.getText().toString()=="" ||
                tipebarang.getText().toString()==""
                )
        {
            Toast.makeText(DetailSparepart.this,"Field can't be empty",Toast.LENGTH_SHORT).show();
        }else{
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder=new Retrofit.
                    Builder().baseUrl(ApiSupplierSales.JSONURL).
                    addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit=builder.build();
            ApiSparepart apiSparepart = retrofit.create(ApiSparepart.class);

            String raksparepart= placement.getSelectedItem().toString()+'-'+rooming.getSelectedItem().toString()+'-'+nomor.getText().toString();

            Call<ResponseBody> sparepartcall=apiSparepart.updatesparepart(kodesp,nama.getText().toString(),merk.getText().toString(),tipebarang.getText().toString(),Integer.parseInt(jumlah.getText().toString()),Integer.parseInt(jmlmin.getText().toString()),Integer.parseInt(hargabeli.getText().toString()),Integer.parseInt(hargajual.getText().toString()),raksparepart);
            sparepartcall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==201)
                    {
                        Toast.makeText(DetailSparepart.this,"berhasil",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailSparepart.this,MenuSparepart.class);
                        startActivity(intent);
                    }else
                        Toast.makeText(DetailSparepart.this,"gagal",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("TAG", t.toString());
                    Toast.makeText(DetailSparepart.this,"Jaringan bermasalah",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setText(){
        kode.setText(kodesp);
        nama.setText(namasp);
        merk.setText(merksp);
        hargabeli.setText(hargabelisp);
        hargajual.setText(hargasp);
        jumlah.setText(jmlsp);
        jmlmin.setText(stokminsp);
        tipebarang.setText(tipesp);
        rakv.setText(raksp);

        Picasso.get().load("https://atmauto.jasonfw.com/images/"+gambar).into(pic);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(DetailSparepart.this, "Clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
