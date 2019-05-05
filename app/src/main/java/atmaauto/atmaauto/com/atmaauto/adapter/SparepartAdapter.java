package atmaauto.atmaauto.com.atmaauto.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import atmaauto.atmaauto.com.atmaauto.DetilList.DetailKatalog;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailSparepart;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;

public class SparepartAdapter extends RecyclerView.Adapter<SparepartAdapter.MyViewHolder> {

    private Context context;
    private List<Sparepart> mList;

    public SparepartAdapter(Context context,List<Sparepart> mList){
        this.context=context;
        this.mList=mList;
    }

    @Override
    public SparepartAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sparepart_adapter, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SparepartAdapter.MyViewHolder myViewHolder, int i) {
        final Sparepart sparepart = mList.get(i);
        myViewHolder.namasparepart.setText(sparepart.getNamaSparepart());
        myViewHolder.hargasparepart.setText("Rp "+sparepart.getHargaJual()+",00");
        myViewHolder.jumlahsparepart.setText("Stok : "+sparepart.getJumlahSparepart());
        Picasso.get().load("http://10.53.4.85:8000/images/"+sparepart.getGambar()).networkPolicy(NetworkPolicy.NO_CACHE).into(myViewHolder.picSparepart);
        myViewHolder.kotak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(v.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, DetailKatalog.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("kodesp", sparepart.getKodeSparepart());
                intent.putExtra("namasp", sparepart.getNamaSparepart());
                intent.putExtra("tipesp", sparepart.getTipeBarang());
                intent.putExtra("merksp", sparepart.getMerkSparepart());
                intent.putExtra("raksp", sparepart.getRakSparepart());
                intent.putExtra("jmlsp", sparepart.getJumlahSparepart());
                intent.putExtra("jmlminsp", sparepart.getStokMinimumSparepart());
                intent.putExtra("hargasp", sparepart.getHargaJual());
                intent.putExtra("hargabelisp", sparepart.getHargaBeli());
                intent.putExtra("gambarsp", sparepart.getGambar());
                context.startActivity(intent);
            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView namasparepart, hargasparepart,jumlahsparepart;
        public ConstraintLayout kotak;
        public ImageView picSparepart;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            kotak=itemView.findViewById(R.id.listSparepart);
            namasparepart=itemView.findViewById(R.id.nama_sparepart_adapter);
            hargasparepart=itemView.findViewById(R.id.harga_sparepart_adapter);
            jumlahsparepart=itemView.findViewById(R.id.jumlah_sparepart_adapter);
            picSparepart=itemView.findViewById(R.id.pic);


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
