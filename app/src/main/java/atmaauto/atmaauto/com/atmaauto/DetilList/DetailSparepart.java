package atmaauto.atmaauto.com.atmaauto.DetilList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.MenuSparepart;
import atmaauto.atmaauto.com.atmaauto.MenuSupplier;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.TambahSparepart;
import atmaauto.atmaauto.com.atmaauto.adapter.SparepartAdapter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailSparepart extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner placement,rooming;

    Button selectimage,patchsparepart;
    public Bitmap ImageBitmap;
    SparepartAdapter sparepartAdapter;

    int Image_Request_Code = 1;
    Uri FilePathUri,FilePathUri2;

    String namasp,raksp,hargabelisp,hargasp,kodesp,merksp,tipesp,jmlsp,stokminsp,gambar;

    EditText kode,nama,merk,hargabeli,hargajual,jumlah,jmlmin,nomor,tipebarang;
    TextView rakv;
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sparepart);

        inisialisasi();

        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.Place,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this,R.array.Room,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placement.setAdapter(adapter1);
        placement.setOnItemSelectedListener(this);
        rooming.setAdapter(adapter2);
        rooming.setOnItemSelectedListener(this);

        getsparepart();
        setText();

        patchsparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatesparepart();
            }
        });


        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
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
        placement = (Spinner) findViewById(R.id.place);
        rooming = (Spinner) findViewById(R.id.room);
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
        selectimage=(Button) findViewById(R.id.selectimage);
        pic=(ImageView) findViewById(R.id.pic);
    }

    public void updategambar(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data =baos.toByteArray();

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), data);
        RequestBody Kode_Sparepart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), kode.getText().toString());
        MultipartBody.Part body = MultipartBody.Part.createFormData("Gambar", "image.jpg", requestFile);
        Call<ResponseBody> updategmb = apiSparepart.patchpicSparepart(body,Kode_Sparepart);
        updategmb.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                //Log.d("on respon : ",String.valueOf(response.code()));

//                    try{
                        Toast.makeText(DetailSparepart.this, "Berhasil!", Toast.LENGTH_SHORT).show();
                        sparepartAdapter.notifyDataSetChanged();
                        finish();
//                    }catch(Exception e){
//                        Toast.makeText(TambahSparepart.this, "gagal!", Toast.LENGTH_SHORT).show();
//                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailSparepart.this, "network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // Mendapatkan data gambar yang dipilih

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
//            Log.d("Test1",FilePathUri.toString());

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), FilePathUri);
                ImageBitmap=bitmap;
                pic.setImageBitmap(bitmap);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatesparepart(){
        if(nama.getText().toString().isEmpty() ||
                merk.getText().toString().isEmpty() ||
                hargabeli.getText().toString().isEmpty()||
                hargajual.getText().toString().isEmpty()||
                jumlah.getText().toString().isEmpty()||
                jmlmin.getText().toString().isEmpty()||
                nomor.getText().toString().isEmpty()||
                tipebarang.getText().toString().isEmpty()
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
                        if(ImageBitmap!=null){
                            updategambar();
                        }
                        Toast.makeText(DetailSparepart.this,"berhasil",Toast.LENGTH_SHORT).show();
                        sparepartAdapter.notifyDataSetChanged();
                        finish();

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

        Picasso.get().load("http://10.53.11.59:8000/images/"+gambar).into(pic);
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
