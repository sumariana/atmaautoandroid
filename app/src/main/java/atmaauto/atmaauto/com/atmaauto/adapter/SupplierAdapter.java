package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import atmaauto.atmaauto.com.atmaauto.DetilList.DetailSupplier;
import atmaauto.atmaauto.com.atmaauto.MenuSupplier;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.Supplier;
import atmaauto.atmaauto.com.atmaauto.models.Supplier_data;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder>{

    private Context context;
    private List<Supplier> mList;

    public SupplierAdapter(Context context,List<Supplier> mList){
        this.context=context;
        this.mList=mList;
    }

    @Override
    public SupplierAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.supplier_adapter, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierAdapter.MyViewHolder myViewHolder, int i) {
        final Supplier supplier = mList.get(i);
        myViewHolder.mNamaSupplier.setText(supplier.getNamaSupplier());
        myViewHolder.mTeleponSupplier.setText(supplier.getTeleponSupplier());
        myViewHolder.kotak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(v.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, DetailSupplier.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idsup", supplier.getIdSupplier().toString());
                intent.putExtra("tempid", supplier.getIdSupplier());
                intent.putExtra("namasup", supplier.getNamaSupplier());
                intent.putExtra("alamatsup", supplier.getAlamatSupplier());
                intent.putExtra("telpsup", supplier.getTeleponSupplier());
                intent.putExtra("namasal", supplier.getNamaSales());
                intent.putExtra("telpsal", supplier.getTeleponSales());
                context.startActivity(intent);
            }
        });
    }

   public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mNamaSupplier, mTeleponSupplier;
        public Button mDeleteSupplier;
        public ConstraintLayout kotak;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            kotak=itemView.findViewById(R.id.listSupplier);
            mNamaSupplier=itemView.findViewById(R.id.nama_supplier_adapter);
            mTeleponSupplier=itemView.findViewById(R.id.telepon_supplier_adapter);
            mDeleteSupplier=itemView.findViewById(R.id.delete_button_adapter);

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
