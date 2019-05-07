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
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailSparepart;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPengadaan;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SparepartCartAdapter extends RecyclerView.Adapter<SparepartCartAdapter.MyViewHolder> {

    private Context context;
    private List<DetailPengadaan> mList;
    private List<DetailPengadaan> mListfilter;


    public SparepartCartAdapter(List<DetailPengadaan> mList){
        this.mList=mList;
        this.mListfilter=mList;
    }

    @Override
    public SparepartCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sparepartcart_adapter, viewGroup, false);
        return new SparepartCartAdapter.MyViewHolder(itemView);
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListfilter = mList;
                } else {
                    List<DetailPengadaan> filteredList = new ArrayList<>();
                    for (DetailPengadaan obj : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getKodeSparepart().toLowerCase().contains(charString.toLowerCase())) {
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
                mListfilter = (ArrayList<DetailPengadaan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull SparepartCartAdapter.MyViewHolder myViewHolder, final int i) {
        final DetailPengadaan detailPengadaan = mListfilter.get(i);
        myViewHolder.namasparepart.setText(detailPengadaan.getKodeSparepart());
        myViewHolder.jumlahsparepart.setText("Jumlah  : "+detailPengadaan.getJumlah());
//        myViewHolder.kotak.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                //Toast.makeText(v.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
//                Intent intent= new Intent(context, DetailSparepart.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("kodesp", sparepart.getKodeSparepart());
//                intent.putExtra("namasp", sparepart.getNamaSparepart());
//                intent.putExtra("tipesp", sparepart.getTipeBarang());
//                intent.putExtra("merksp", sparepart.getMerkSparepart());
//                intent.putExtra("raksp", sparepart.getRakSparepart());
//                intent.putExtra("jmlsp", sparepart.getJumlahSparepart());
//                intent.putExtra("jmlminsp", sparepart.getStokMinimumSparepart());
//                intent.putExtra("hargasp", sparepart.getHargaJual());
//                intent.putExtra("hargabelisp", sparepart.getHargaBeli());
//                intent.putExtra("gambarsp", sparepart.getGambar());
//                context.startActivity(intent);
//            }
//        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    @Override
    public int getItemCount(){
        return mListfilter.size();
    }



}
