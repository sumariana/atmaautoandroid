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

import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.DetailJasa;

public class JasaCartAdapter extends RecyclerView.Adapter<JasaCartAdapter.MyViewHolder> {
    private Context context;
    private List<DetailJasa> mList;
    private List<DetailJasa> mListfilter;

    public JasaCartAdapter(List<DetailJasa> mList){
        this.mList=mList;
        this.mListfilter=mList;
    }

    @Override
    public JasaCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.servis_adapter, viewGroup, false);
        return new JasaCartAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull JasaCartAdapter.MyViewHolder myViewHolder, final int i) {
        final DetailJasa detailJasa = mListfilter.get(i);
        myViewHolder.namaservis.setText(detailJasa.getNamaJasa());
        myViewHolder.hargaservis.setText("Harga  : "+detailJasa.getSubtotalDetailJasa());
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double harga = detailJasa.getSubtotalDetailJasa();
                Intent intent = new Intent("custom-message-jasa");
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
        public TextView namaservis,hargaservis;
        Button delete;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            //kotak=itemView.findViewById(R.id.listSparepartAdmin);
            namaservis=itemView.findViewById(R.id.namaservis);
            hargaservis=itemView.findViewById(R.id.hargaservis);
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
