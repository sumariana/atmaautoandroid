package atmaauto.atmaauto.com.atmaauto.DetilList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import atmaauto.atmaauto.com.atmaauto.R;

public class DetailKatalog extends AppCompatActivity {
    String namasp,hargasp,kodesp,merksp,tipesp,jmlsp,gambar;

    TextView nama,harga,kode,merk,tipe,jumlah;
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_katalog);
        getsparepart();
        inisialisasi();
        setText();
    }

    private void getsparepart(){
        Intent i=getIntent();

        namasp=i.getStringExtra("namasp");
        hargasp=i.getStringExtra("hargasp");
        kodesp=i.getStringExtra("kodesp");
        merksp=i.getStringExtra("merksp");
        tipesp=i.getStringExtra("tipesp");
        jmlsp=i.getStringExtra("jmlsp");
        gambar=i.getStringExtra("gambarsp");
    }

    private void inisialisasi(){
        nama=(TextView) findViewById(R.id.namasparepart);
        harga=(TextView) findViewById(R.id.hargasparepart);
        kode=(TextView) findViewById(R.id.kodesparepart);
        merk=(TextView) findViewById(R.id.merksparepart);
        tipe=(TextView) findViewById(R.id.tipesparepart);
        jumlah=(TextView) findViewById(R.id.jumlahsparepart);
        pic=(ImageView) findViewById(R.id.pic);
    }

    private void setText(){
        nama.setText(namasp);
        harga.setText("Rp "+hargasp);
        kode.setText("Kode Barang : "+kodesp);
        merk.setText("Brand Barang : "+merksp);
        tipe.setText("Tipe Barang : "+tipesp);
        jumlah.setText("Jumlah Stok : "+jmlsp+" *jumlah stok dapat berubah sewaktu-waktu");
        Picasso.get().load("http://10.53.4.85:8000/images/"+gambar).networkPolicy(NetworkPolicy.NO_CACHE).into(pic);
    }
}
