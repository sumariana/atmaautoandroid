package atmaauto.atmaauto.com.atmaauto;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import atmaauto.atmaauto.com.atmaauto.SessionManager.SessionManager;

public class OwnerPanel extends AppCompatActivity {

    CardView logout,penjualan,pegawai,supplier,pengadaan,cabang,motor,konsumen,servis,sparepart,history;
    SessionManager session;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_panel);
        logout=(CardView)findViewById(R.id.logoutOwner);
        supplier=(CardView)findViewById(R.id.supplier);
        pengadaan=(CardView)findViewById(R.id.pengadaan);
        motor=(CardView)findViewById(R.id.motor);
        konsumen=(CardView)findViewById(R.id.konsumen);
        servis=(CardView)findViewById(R.id.servis);
        sparepart=(CardView)findViewById(R.id.sparepart);
        history=(CardView) findViewById(R.id.histori);

        session = new SessionManager(getApplicationContext());
        init();
        history();
        ClickLogout();

        pengadaan();
        supplier();
        motor();
        konsumen();
        servis();
        sparepart();
    }

    private void init(){

        Intent intent = new Intent(this, MenuSparepart.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.gear)
                .setContentTitle("Sparepart Menipis !")
                .setContentText("Hai Admin sparepart berikut mencapai stok minimum !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    private void ClickLogout(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });
    }
    private void history(){
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerPanel.this,MenuHistory.class);
                startActivity(intent);
            }
        });
    }
    private void motor(){
        motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerPanel.this,MenuMotor.class);
                startActivity(intent);
            }
        });
    }
    private void servis(){
        servis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerPanel.this,MenuServis.class);
                startActivity(intent);
            }
        });
    }
    private void supplier(){
        supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerPanel.this,MenuSupplier.class);
                startActivity(intent);
            }
        });
    }
    private void konsumen(){
        konsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerPanel.this,MenuKonsumen.class);
                startActivity(intent);
            }
        });
    }
    private void sparepart(){
        sparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerPanel.this,MenuSparepart.class);
                startActivity(intent);
            }
        });
    }
    private void pengadaan(){
        pengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerPanel.this,MenuPengadaan.class);
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
