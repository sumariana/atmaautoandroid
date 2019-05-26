package atmaauto.atmaauto.com.atmaauto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import atmaauto.atmaauto.com.atmaauto.SessionManager.SessionManager;

public class CSPanel extends AppCompatActivity {

    SessionManager session;

    CardView logout,penjualan,motor,konsumen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cspanel);

        logout=(CardView) findViewById(R.id.logout);
        penjualan=(CardView) findViewById(R.id.penjualan);
        motor=(CardView) findViewById(R.id.motor);
        konsumen=(CardView) findViewById(R.id.konsumen);

        session = new SessionManager(getApplicationContext());
        ClickLogout();
        penjualan();
        motor();
        konsumen();
    }
    private void ClickLogout(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
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
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
