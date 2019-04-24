package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import atmaauto.atmaauto.com.atmaauto.DetilList.DetailKatalog;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailKonsumen;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;

public class KonsumenAdapter extends RecyclerView.Adapter<KonsumenAdapter.MyViewHolder>{
    private Context context;
    private List<Konsumen> mList;

    public KonsumenAdapter(Context context,List<Konsumen> mList){
        this.context=context;
        this.mList=mList;
    }

    @Override
    public KonsumenAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.konsumen_adapter, viewGroup, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull KonsumenAdapter.MyViewHolder myViewHolder, int i) {
        final Konsumen konsumen = mList.get(i);
        myViewHolder.namakonsumen.setText(konsumen.getNamaKonsumen());
        myViewHolder.teleponkonsumen.setText(konsumen.getTeleponKonsumen());
        myViewHolder.kotak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(v.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, DetailKonsumen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idkn", konsumen.getIdKonsumen().toString());
                intent.putExtra("namakn", konsumen.getNamaKonsumen());
                intent.putExtra("alamatkn", konsumen.getAlamatKonsumen());
                intent.putExtra("teleponkn", konsumen.getTeleponKonsumen());
                context.startActivity(intent);
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView namakonsumen, teleponkonsumen;
        public ConstraintLayout kotak;
        public Button delete;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            kotak=itemView.findViewById(R.id.kotak);
            namakonsumen=itemView.findViewById(R.id.nama_konsumen_adapter);
            teleponkonsumen=itemView.findViewById(R.id.telepon_konsumen_adapter);
            delete=itemView.findViewById(R.id.deletebuttonadapter);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){}
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public int getItemCount(){
        return mList.size();
    }



}
