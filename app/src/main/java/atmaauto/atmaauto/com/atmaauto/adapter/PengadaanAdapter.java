package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import atmaauto.atmaauto.com.atmaauto.DetilList.DetailPengadaanController;
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
        myViewHolder.teleponsales.setText(transaksiPengadaan.getNamaSupplier());
        if(transaksiPengadaan.getStatusPengadaan()==0)
        {
            myViewHolder.status.setText("Ordered");
            myViewHolder.status.setBackgroundColor(Color.parseColor("#f62d30"));
        }else if(transaksiPengadaan.getStatusPengadaan()==1){
            myViewHolder.status.setText("Printed");
            myViewHolder.status.setBackgroundColor(Color.parseColor("#fff176"));
        }else{
            myViewHolder.status.setText("Verified");
            myViewHolder.status.setBackgroundColor(Color.parseColor("#81c784"));
        }

        myViewHolder.editpengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, DetailPengadaanController.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idpengadaan", transaksiPengadaan.getIdPengadaan());
                intent.putExtra("tanggal", transaksiPengadaan.getTanggalPengadaan());
                intent.putExtra("namasales", transaksiPengadaan.getNamaSales());
                intent.putExtra("totalharga", transaksiPengadaan.getTotalHarga().toString());
                context.startActivity(intent);
            }
        });
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
        public TextView tanggal,namasales,teleponsales,statuspengadaan,status;
        public ImageView editpengadaan,deletepengadaan;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            namasales=itemView.findViewById(R.id.salespengadaan);
            teleponsales=itemView.findViewById(R.id.telpsalespengadaan);
            tanggal=itemView.findViewById(R.id.tanggal);
            status=itemView.findViewById(R.id.statuspengadaan);
            editpengadaan=itemView.findViewById(R.id.picedit);
            deletepengadaan=itemView.findViewById(R.id.picdel);

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
