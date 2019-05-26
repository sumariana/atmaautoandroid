package atmaauto.atmaauto.com.atmaauto;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DialogStatus extends AppCompatDialogFragment {
    private EditText platkendaraan;
    private EditText nomortelepon;
    private DialogStatusListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogstatus_layout, null);

        builder.setView(view)
                .setTitle("Cek Status Kendaraan")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Find", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String plat = platkendaraan.getText().toString();
                        String nomer = nomortelepon.getText().toString();

                        listener.applyTexts(plat,nomer);
                    }
                });
        platkendaraan = view.findViewById(R.id.platkendaraan);
        nomortelepon = view.findViewById(R.id.nomortelepon);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogStatusListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface DialogStatusListener {
        void applyTexts(String plat, String nomer);
    }
}
