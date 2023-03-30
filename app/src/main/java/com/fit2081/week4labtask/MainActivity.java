package com.fit2081.week4labtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.text.DecimalFormat;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity  /*implements TokenizerInterface*/ {

    public static final String BOOK_NAME = "BookName";
    public static final String BOOK_ISBN = "BookISBN";
    public static final String SP_NAME = "SPName";
    public static final String SP_ID = "SP ID";
    public static final String SP_ISBN = "SP ISBN";
    public static final String SP_AUTHOR = "SP Author";
    public static final String SP_DESCRIPTION = "SP Description";
    public static final String SP_PRICE = "SP Price";
    EditText bookIDET, bookNameET, bookISBNET, bookMakerET, bookDesET, bookPriceET;
    SharedPreferences sharedPreferences;
    public static final String LIFE_CYCLE = "LifeCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookIDET    = findViewById(R.id.bookIDET);
        bookNameET  = findViewById(R.id.bookNameET);
        bookISBNET  = findViewById(R.id.bookISBNET);
        bookMakerET = findViewById(R.id.bookMakerET);
        bookDesET   = findViewById(R.id.bookDesET);
        bookPriceET = findViewById(R.id.BookPriceET);

        Log.d(LIFE_CYCLE,"onCreate");

        sharedPreferences = getPreferences(MODE_PRIVATE);

        String SPBookID = sharedPreferences.getString(SP_ID,"");
        String SPBookName = sharedPreferences.getString(SP_NAME,"");
        String SPBookISBN = sharedPreferences.getString(SP_ISBN,"");
        String SPBookMaker = sharedPreferences.getString(SP_AUTHOR,"");
        String SPBookDes = sharedPreferences.getString(SP_DESCRIPTION,"");
        float SPBookPrice = sharedPreferences.getFloat(SP_PRICE,0);
        DecimalFormat df = new DecimalFormat(".00");

        bookIDET.setText(SPBookID);
        bookNameET.setText(SPBookName);
        bookISBNET.setText(SPBookISBN);
        bookMakerET.setText(SPBookMaker);
        bookDesET.setText(SPBookDes);
        if (SPBookPrice == 0) {
            bookPriceET.setText("");
        } else {
            bookPriceET.setText(df.format(SPBookPrice));
        }

        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        /* Create and instantiate the local broadcast receiver
           This class listens to messages come from class SMSReceiver
         */
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();

        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
        /* Second Method for SMS
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        SMSReceiver receiver = new SMSReceiver(this);
        registerReceiver(receiver,filter);*/
    }
    class MyBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */
            StringTokenizer sT = new StringTokenizer(msg, "|");
            String tknBookID    = sT.nextToken();
            String tknBookName  = sT.nextToken();
            String tknBookISBN  = sT.nextToken();
            String tknBookMaker = sT.nextToken();
            String tknBookDes   = sT.nextToken();
            float tknBookPrice  = Float.parseFloat(sT.nextToken());
            DecimalFormat df = new DecimalFormat(".00");
            /*
             * Now, its time to update the UI
             * */
            bookIDET.setText(tknBookID);
            bookNameET.setText(tknBookName);
            bookISBNET.setText(tknBookISBN);
            bookMakerET.setText(tknBookMaker);
            bookDesET.setText(tknBookDes);
            bookPriceET.setText(df.format(tknBookPrice));
        }}
    /*Second Method for SMS
    @Override
    public void sendData(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        StringTokenizer sT = new StringTokenizer(data, "|");
        String tknBookID    = sT.nextToken();
        String tknBookName  = sT.nextToken();
        String tknBookISBN  = sT.nextToken();
        String tknBookMaker = sT.nextToken();
        String tknBookDes   = sT.nextToken();
        float tknBookPrice  = Float.parseFloat(sT.nextToken());
        DecimalFormat df = new DecimalFormat(".00");

        bookIDET.setText(tknBookID);
        bookNameET.setText(tknBookName);
        bookISBNET.setText(tknBookISBN);
        bookMakerET.setText(tknBookMaker);
        bookDesET.setText(tknBookDes);
        bookPriceET.setText(df.format(tknBookPrice));
       }*/

    public void handleReload(View v){

        String SPBookID = sharedPreferences.getString(SP_ID,"");
        String SPBookName = sharedPreferences.getString(SP_NAME,"");
        String SPBookISBN = sharedPreferences.getString(SP_ISBN,"");
        String SPBookMaker = sharedPreferences.getString(SP_AUTHOR,"");
        String SPBookDes = sharedPreferences.getString(SP_DESCRIPTION,"");
        float SPBookPrice = sharedPreferences.getFloat(SP_PRICE,0);
        DecimalFormat df = new DecimalFormat(".00");

        bookIDET.setText(SPBookID);
        bookNameET.setText(SPBookName);
        bookISBNET.setText(SPBookISBN);
        bookMakerET.setText(SPBookMaker);
        bookDesET.setText(SPBookDes);
        bookPriceET.setText(df.format(SPBookPrice));

    }
    public void handleClear(View random){

        bookIDET.setText("");
        bookNameET.setText("");
        bookISBNET.setText("");
        bookMakerET.setText("");
        bookDesET.setText("");
        bookPriceET.setText("");

    }
    public void handleSubmit(View v){

        String bookID = bookIDET.getText().toString();
        String bookName = bookNameET.getText().toString();
        String bookISBN = bookISBNET.getText().toString();
        String bookMaker = bookMakerET.getText().toString();
        String bookDes = bookDesET.getText().toString();
        float price = Float.parseFloat(bookPriceET.getText().toString());
        DecimalFormat df = new DecimalFormat(".00");

        Toast.makeText(this, "Book (" + bookName + ")" + " and the price (" + df.format(price) + ")", Toast.LENGTH_LONG).show();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SP_ID,bookID);
        editor.putString(SP_NAME,bookName);
        editor.putString(SP_ISBN,bookISBN);
        editor.putString(SP_AUTHOR,bookMaker);
        editor.putString(SP_DESCRIPTION,bookDes);
        editor.putFloat (SP_PRICE,price);

        editor.apply();
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        String BookNameETRestored = savedInstanceState.getString(BOOK_NAME);
        bookNameET.setText(BookNameETRestored);
        String bookISBNETRestored = savedInstanceState.getString(BOOK_ISBN);
        bookISBNET.setText(bookISBNETRestored);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
        String saveBookNameET = bookNameET.getText().toString();
        outState.putString(BOOK_NAME,saveBookNameET);
        String saveBookISBNET = bookISBNET.getText().toString();
        outState.putString(BOOK_ISBN,saveBookISBNET);}


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LIFE_CYCLE,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LIFE_CYCLE,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LIFE_CYCLE,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LIFE_CYCLE,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LIFE_CYCLE,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LIFE_CYCLE,"onDestroy");
    }

}