package com.example.yogesh.cameraspy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;


public class MessageBroadcast extends BroadcastReceiver {
    Camera cam;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast


        final SmsManager sms = SmsManager.getDefault();

        final Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                   String senderno =  currentMessage.getOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody().toString();
                    if(message.equals("flash")){
                        cam = Camera.open();
                        Camera.Parameters p = cam.getParameters();
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        cam.setParameters(p);
                        cam.startPreview();
                    }
                    if(message.equals("offflash")){
                        cam.stopPreview();
                        cam.release();
                    }
                    String[] splited = message.split("\\s+");
                    String input1 = "abc";
                    String input2 = splited[0];


                    try
                    {
                        // checking valid integer using parseInt() method
                        Integer.parseInt(input2);
                        System.out.println(input2 + " is a valid integer number");
                        Toast.makeText(context, ""+input2, Toast.LENGTH_SHORT).show();
                        Intent is =new Intent();
                        is.putExtra("otp",input2);
                        is.setClass(context, Main2Activity.class);
                        is.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(is);


                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println(input1 + " is not a valid integer number");
                    }









                    // String subscriberID=tm.getDeviceId();
         //           String SIMSerialNumber=tm.getSubscriberId();
                  //  Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();

           ///         Toast.makeText(context, ""+tm.getLine1Number().toString() , Toast.LENGTH_LONG).show();
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    Intent myIntent = new Intent("otp");
                    myIntent.putExtra("message", message);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
                    // Show Alert

                } // end for loop
            }
            throw new UnsupportedOperationException("Not yet implemented");
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }
    private void takeScreenshot(Context context) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture




        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    public static boolean isInteger(String s) {
        return isInteger(s,5);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
