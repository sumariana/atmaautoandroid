package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CSPanel extends AppCompatActivity {

    Button logout,penjualan,pegawai,supplier,pengadaan,cabang,motor,konsumen,servis,sparepart,history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cspanel);

        logout=(Button)findViewById(R.id.logoutOwner);

        penjualan=(Button) findViewById(R.id.penjualan);
        motor=(Button)findViewById(R.id.motor);
        konsumen=(Button)findViewById(R.id.konsumen);

        ClickLogout();
        penjualan();
        motor();
        konsumen();
    }
    private void ClickLogout(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CSPanel.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void motor(){
        motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CSPanel.this,MenuMotor.class);
                startActivity(intent);
            }
        });
    }
    private void konsumen(){
        konsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CSPanel.this,MenuKonsumen.class);
                startActivity(intent);
            }
        });
    }

    private void penjualan(){
        penjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CSPanel.this,MenuPenjualan.class);
                startActivity(intent);
            }
        });
    }
}
