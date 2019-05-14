package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import atmaauto.atmaauto.com.atmaauto.models.DetailSparepart;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan;

public class PenjualanSparepartAdapter extends RecyclerView.Adapter<PenjualanSparepartAdapter.MyViewHolder>{

    private Context context;
    private List<DetailSparepart> mList;
    private List<DetailSparepart> mListfilter;

    public PenjualanSparepartAdapter(List<DetailSparepart> mList){
        this.mList=mList;
        this.mListfilter=mList;
    }

    @Override
    public PenjualanSparepartAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sparepartcart_adapter, viewGroup, false);
        return new PenjualanSparepartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanSparepartAdapter.MyViewHolder myViewHolder, final int i) {
        final DetailSparepart detailSparepart = mListfilter.get(i);
        myViewHolder.namasparepart.setText(detailSparepart.getKodeSparepart());
        myViewHolder.jumlahsparepart.setText("Jumlah  : "+detailSparepart.getJumlah());
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double harga = detailSparepart.getSubtotalDetailSparepart();
                Intent intent = new Intent("custom-message");
                intent.putExtra("harga",harga);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                mListfilter.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i,getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListfilter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView namasparepart,jumlahsparepart;
        Button delete;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            //kotak=itemView.findViewById(R.id.listSparepartAdmin);
            namasparepart=itemView.findViewById(R.id.nama_sparepartcart_adapter);
            jumlahsparepart=itemView.findViewById(R.id.jumlah_sparepartcart_adapter);
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
}
