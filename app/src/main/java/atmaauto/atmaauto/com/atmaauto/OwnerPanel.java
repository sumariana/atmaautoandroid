package atmaauto.atmaauto.com.atmaauto;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OwnerPanel extends AppCompatActivity {

    Button logout,penjualan,pegawai,supplier,pengadaan,cabang,motor,konsumen,servis,sparepart,history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_panel);
        logout=(Button)findViewById(R.id.logoutOwner);
        pegawai=(Button)findViewById(R.id.pegawai);
        supplier=(Button)findViewById(R.id.supplier);

        pengadaan=(Button)findViewById(R.id.pengadaan);
        cabang=(Button)findViewById(R.id.cabang);
        motor=(Button)findViewById(R.id.motor);
        konsumen=(Button)findViewById(R.id.konsumen);
        servis=(Button)findViewById(R.id.servis);
        sparepart=(Button)findViewById(R.id.sparepart);
        history=(Button) findViewById(R.id.histori);
        init();
        history();
        ClickLogout();

        pengadaan();
        cabang();
        pegawai();
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
                Intent intent=new Intent(OwnerPanel.this,MainActivity.class);
                startActivity(intent);
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
    private void pegawai(){
        pegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerPanel.this,MenuPegawai.class);
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
    private void cabang(){
        cabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerPanel.this,MenuCabang.class);
                startActivity(intent);
            }
        });
    }
}
