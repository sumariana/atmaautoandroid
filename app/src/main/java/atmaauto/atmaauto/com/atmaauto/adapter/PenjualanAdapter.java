package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPenjualan;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.MyViewHolder>{
    private Context context;
    private List<TransaksiPenjualan> mList;
    private List<TransaksiPenjualan> mListfilter;

    public PenjualanAdapter (Context context,List<TransaksiPenjualan>mList){
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }
    @Override
    public PenjualanAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transaksi_adapter, viewGroup, false);
        return new PenjualanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final TransaksiPenjualan transaksiPenjualan = mListfilter.get(i);
        myViewHolder.tanggal.setText(transaksiPenjualan.getTanggalTransaksi());
        myViewHolder.namakonsumen.setText("ID Konsumen : "+transaksiPenjualan.getIdKonsumen());
        myViewHolder.totalharga.setText("Total Harga : "+transaksiPenjualan.getTotal());
        //myViewHolder.teleponsales.setText(transaksiPengadaan.get());
    }
    @Override
    public int getItemCount(){
        return mListfilter.size();
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListfilter = mList;
                } else {
                    List<TransaksiPenjualan> filteredList = new ArrayList<>();
                    for (TransaksiPenjualan obj : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getJenisTransaksi().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj);
                        }
                    }

                    mListfilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListfilter = (ArrayList<TransaksiPenjualan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tanggal,namakonsumen,totalharga,statuspenjualan;
        public ImageView editpengadaan,deletepengadaan;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            namakonsumen=itemView.findViewById(R.id.namakonsumen);
            totalharga=itemView.findViewById(R.id.totalharga);
            tanggal=itemView.findViewById(R.id.tanggal);

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
