package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.adapter.KonsumenAdapter;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahSparepart extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    private Spinner placement,rooming;

    ImageView pic;
    public Bitmap ImageBitmap;
    Uri FilePathUri,FilePathUri2;
    int Image_Request_Code = 1;

    Button selectimage,postsparepart;

    EditText kode,nama,merk,hargabeli,hargajual,jumlah,jmlmin,nomor,tipebarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_sparepart);

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

        selectimage=(Button) findViewById(R.id.selectimage);
        pic=(ImageView) findViewById(R.id.pic);
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

        inisialisasi();
        postsparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSparepart();
            }
        });
    }

    private void addSparepart(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder=new Retrofit.
                Builder().baseUrl(ApiSparepart.JSONURL).
                addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiSparepart apiSparepart=retrofit.create(ApiSparepart.class);

        if(ImageBitmap!=null){

            if(kode.getText().toString().isEmpty() ||nama.getText().toString().isEmpty() ||merk.getText().toString().isEmpty() ||hargabeli.getText().toString().isEmpty() ||hargajual.getText().toString().isEmpty() ||jumlah.getText().toString().isEmpty() ||jmlmin.getText().toString().isEmpty() ||nomor.getText().toString().isEmpty()){
                Toast.makeText(TambahSparepart.this, "field can't be empty!", Toast.LENGTH_SHORT).show();
            }else{
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] data =baos.toByteArray();

                String rak= placement.getSelectedItem().toString()+'-'+rooming.getSelectedItem().toString()+'-'+nomor.getText().toString();

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), data);

                Log.d("LogCat : ",kode.getText().toString());
                Log.d("LogCat : ",nama.getText().toString());
                Log.d("LogCat : ",merk.getText().toString());
                Log.d("LogCat : ",hargabeli.getText().toString());
                Log.d("LogCat : ",hargajual.getText().toString());
                Log.d("LogCat : ",jumlah.getText().toString());
                Log.d("LogCat : ",jmlmin.getText().toString());
                Log.d("LogCat : ",rak);
                RequestBody Kode_Sparepart =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), kode.getText().toString());
                RequestBody Nama_Sparepart =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), nama.getText().toString());
                RequestBody Merk_Sparepart =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), merk.getText().toString());
                RequestBody Tipe_barang =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), tipebarang.getText().toString());
                RequestBody Harga_Beli =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), hargabeli.getText().toString());
                RequestBody Harga_Jual =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), hargajual.getText().toString());
                RequestBody Jumlah_Sparepart =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), jumlah.getText().toString());
                RequestBody Stok_Minimum_Sparepart =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), jmlmin.getText().toString());
                RequestBody Rak_Sparepart =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), rak);
                MultipartBody.Part body = MultipartBody.Part.createFormData("Gambar", "image.jpg", requestFile);

//            CallingAPI

                Call<ResponseBody> tambahsp = apiSparepart.addSparepart(body,Kode_Sparepart,Nama_Sparepart,Merk_Sparepart,Tipe_barang,Jumlah_Sparepart,Stok_Minimum_Sparepart,Harga_Beli,Harga_Jual,Rak_Sparepart);
                tambahsp.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d("on respon : ",String.valueOf(response.code()));

//                    try{
//                        Toast.makeText(TambahSparepart.this, "Berhasil!", Toast.LENGTH_SHORT).show();
//                    }catch(Exception e){
//                        Toast.makeText(TambahSparepart.this, "gagal!", Toast.LENGTH_SHORT).show();
//                    }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(TambahSparepart.this, "network error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else
        {
            Toast.makeText(TambahSparepart.this, "Pilih gambar dulu!", Toast.LENGTH_SHORT).show();
        }
    }

    private void inisialisasi(){
        postsparepart=(Button) findViewById(R.id.postsparepart);
        kode=(EditText) findViewById(R.id.kodesparepart);
        nama=(EditText) findViewById(R.id.namasparepart);
        merk=(EditText) findViewById(R.id.merksparepart);
        hargabeli=(EditText) findViewById(R.id.hargabelisparepart);
        hargajual=(EditText) findViewById(R.id.hargajualsparepart);
        jumlah=(EditText) findViewById(R.id.jumlahsparepart);
        jmlmin=(EditText) findViewById(R.id.jumlahmin);
        nomor=(EditText) findViewById(R.id.nomor);
        tipebarang=(EditText) findViewById(R.id.tipebarang);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(TambahSparepart.this, "Clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
