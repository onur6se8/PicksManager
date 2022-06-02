package com.jeupouradulte.monpoker.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jeupouradulte.monpoker.Home;
import com.jeupouradulte.monpoker.MainActivity;
import com.jeupouradulte.monpoker.PPF.CreatPart;
import com.jeupouradulte.monpoker.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.zelory.compressor.Compressor;

public class ParamPageFragment extends Fragment{

    private static  final int  RETOUR_PRENDRE_PHOTO = 1;

    private Button btnPrendrePhoto;
    private Button btnEnregistrerPhoto;
    private ImageView imgAffichePhoto;
    private String photoPath = null;

    private boolean photoPrise = false; // on sait si la photo à été prise ou pas

    public static ParamPageFragment newInstance() {
        return (new ParamPageFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {          //  Initialisation

        View v = inflater.inflate(R.layout.fragment_param_page, container, false);

        btnPrendrePhoto = (Button) v.findViewById(R.id.btnPrendrePhoto);
        btnEnregistrerPhoto = (Button) v.findViewById(R.id.btnEnregistrerPhoto);
        imgAffichePhoto = (ImageView) v.findViewById(R.id.imgAffichePhoto);


        btnPrendrePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                prendreUnePhoto();
            }
        });

        btnEnregistrerPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                try {
                    enregistrerPhoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    // Prendre une photo
    private void prendreUnePhoto(){ // acces appareil photo

        // cree un intent pour ouvrir une fenetre pour prendre une photo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // test pour controler que le telephone possede l'appareil photo
        if ( intent.resolveActivity(getContext().getPackageManager()) != null){
            // creer un nom de fichier unique
            String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File photoDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            try {
                File photoFile = File.createTempFile("photo"+time, ".jpg", photoDir);
                // enregistrer le chemin
                photoPath = photoFile.getAbsolutePath();
                // créer l'uri
                Uri photoUri = FileProvider.getUriForFile( getContext() ,
                        getContext().getApplicationContext().getPackageName()+".provider",
                        photoFile);
                // transfert uri vers l'intent pour enregistrer une photo dans un fichier temporaire
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                // ouvrir l'activity
                startActivityForResult(intent, RETOUR_PRENDRE_PHOTO);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    // retour de l'appel de l'appareil photo (startActivityForResult)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        // verifie le bon code de retour et l'état du retour ok
        if (requestCode == RETOUR_PRENDRE_PHOTO && resultCode == RESULT_OK){

            // récuperer l'image
            Bitmap image = BitmapFactory.decodeFile(photoPath);
            // afficher l'image
            imgAffichePhoto.setImageBitmap(image);

            photoPrise = true;
        }
    }





    // Enregistrer une photo

    public void enregistrerPhoto() throws IOException {

        if (photoPrise){ // si la photo est prise

            // récuperer l'image
            Bitmap image = BitmapFactory.decodeFile(photoPath);
            File imgFile = new  File(photoPath);


            Bitmap compressedImage = new Compressor(getContext())
                    .setMaxWidth(200)
                    .setMaxHeight(200)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(
                            Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES
                            ).getAbsolutePath()
                    )
                    .compressToBitmap(imgFile);




            // afficher l'image
            imgAffichePhoto.setImageBitmap(compressedImage);

            photoPrise = false;
        }
    }

}