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
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPengadaan;

//test

public class PengadaanAdapter extends RecyclerView.Adapter<PengadaanAdapter.MyViewHolder>{
    private Context context;
    private List<TransaksiPengadaan> mList;
    private List<TransaksiPengadaan> mListfilter;

    public PengadaanAdapter (Context context,List<TransaksiPengadaan>mList){
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }
    @Override
    public PengadaanAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pengadaan_adapter, viewGroup, false);
        return new PengadaanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PengadaanAdapter.MyViewHolder myViewHolder, int i) {
        final TransaksiPengadaan transaksiPengadaan = mListfilter.get(i);
        myViewHolder.tanggal.setText(transaksiPengadaan.getTanggalPengadaan());
        myViewHolder.namasales.setText(transaksiPengadaan.getNamaSales());
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
                    List<TransaksiPengadaan> filteredList = new ArrayList<>();
                    for (TransaksiPengadaan obj : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getNamaSales().toLowerCase().contains(charString.toLowerCase())) {
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
                mListfilter = (ArrayList<TransaksiPengadaan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tanggal,namasales,teleponsales,statuspengadaan;
        public ImageView editpengadaan,deletepengadaan;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            namasales=itemView.findViewById(R.id.salespengadaan);
            teleponsales=itemView.findViewById(R.id.telpsalespengadaan);
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
