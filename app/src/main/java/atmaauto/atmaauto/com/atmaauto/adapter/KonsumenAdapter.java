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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailKatalog;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailKonsumen;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import atmaauto.atmaauto.com.atmaauto.models.Supplier;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonsumenAdapter extends RecyclerView.Adapter<KonsumenAdapter.MyViewHolder>{
    private Context context;
    private List<Konsumen> mList;
    private List<Konsumen> mListfilter;

    public KonsumenAdapter(Context context,List<Konsumen> mList){
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }

    @Override
    public KonsumenAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.konsumen_adapter, viewGroup, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull KonsumenAdapter.MyViewHolder myViewHolder, final int i) {
        final Konsumen konsumen = mListfilter.get(i);
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
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder=new Retrofit.
                        Builder().baseUrl(ApiTransaksiPengadaan.JSONURL).
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit=builder.build();
                ApiKonsumen apiKonsumen=retrofit.create(ApiKonsumen.class);

                Call<ResponseBody> responseBodyCall = apiKonsumen.deletekonsumen(konsumen.getIdKonsumen());
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        mList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i,getItemCount());
                        Toast.makeText(context, "berhasil!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "network error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListfilter = mList;
                } else {
                    List<Konsumen> filteredList = new ArrayList<>();
                    for (Konsumen obj : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getNamaKonsumen().toLowerCase().contains(charString.toLowerCase()) || obj.getTeleponKonsumen().contains(charSequence)) {
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
                mListfilter = (ArrayList<Konsumen>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
        return mListfilter.size();
    }



}
