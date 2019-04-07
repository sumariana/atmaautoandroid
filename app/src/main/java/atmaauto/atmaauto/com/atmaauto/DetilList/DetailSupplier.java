package atmaauto.atmaauto.com.atmaauto.DetilList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import atmaauto.atmaauto.com.atmaauto.R;

public class DetailSupplier extends AppCompatActivity {
    public String idsup,namasup,alamatsup,telpsup,namasal,telpsal;
    public EditText viewidsup,viewnamasup,viewalamatsup,viewtelpsup,viewnamasal,viewtelpsal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_supplier);

        inisialisasi();
        Intent i=getIntent();

        idsup=i.getStringExtra("idsup");
        namasup=i.getStringExtra("namasup");
        alamatsup=i.getStringExtra("alamatsup");
        telpsup=i.getStringExtra("telpsup");
        namasal=i.getStringExtra("namasal");
        telpsal=i.getStringExtra("telpsal");

        viewidsup.setText(idsup);
        viewnamasup.setText(namasup);
        viewalamatsup.setText(alamatsup);
        viewtelpsup.setText(telpsup);
        viewnamasal.setText(namasal);
        viewtelpsal.setText(telpsal);
    }

    private void inisialisasi(){

    viewidsup=(EditText) findViewById(R.id.detilidsupplier);
    viewnamasup=(EditText) findViewById(R.id.detilnamasupplier);
    viewalamatsup=(EditText) findViewById(R.id.detilalamatsupplier);
    viewtelpsup=(EditText) findViewById(R.id.detilteleponsupplier);
    viewnamasal=(EditText) findViewById(R.id.detilnamasales);
    viewtelpsal=(EditText) findViewById(R.id.detilteleponsales);
    }
}
