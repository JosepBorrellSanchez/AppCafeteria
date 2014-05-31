package com.josepborrell.cafeteriadavinci;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DialogoAlerta extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
 
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
 
        builder.setMessage("Telèfon : 868542465 \n" 
        		+ "Adreça : Av/ports de caro nº6 \n"
        		+ "Població : Roquetes, 43520 \n"
        		+ "Email : davinciroquetes@gmail.com \n")
               .setTitle("Informació de la Cafeteria Da Vinci")
               .setPositiveButton("D'acord", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                   }
               });
 
        return builder.create();
    }
}