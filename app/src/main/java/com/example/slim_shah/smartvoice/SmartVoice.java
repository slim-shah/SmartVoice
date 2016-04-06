package com.example.slim_shah.smartvoice;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SmartVoice extends AppCompatActivity {
    TextToSpeech ttsobject;
    int result;
    Bundle b=null;
    public MyDbHandler dbHandler;
    public EditText et,et1;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_voice);
        b=savedInstanceState;
        dbHandler = new MyDbHandler(this, null, null, 1); //database variable
        ttsobject = new TextToSpeech(SmartVoice.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status == TextToSpeech.SUCCESS){
                    result = ttsobject.setLanguage(Locale.US);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("ENGLISH(US)");
        categories.add("ENGLISH(UK)");
        categories.add("Chinese");
        categories.add("CANADA");
        categories.add("FRENCH");
        categories.add("GERMAN");
        categories.add("ITALIAN");
        categories.add("JAPANESE");
        categories.add("KOREAN");
        categories.add("TAIWAN");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {

                String item = parent.getItemAtPosition(position).toString();
                if(item=="ENGLISH(UK)")
                {
                    result = ttsobject.setLanguage(Locale.UK);
                }
                else if(item=="Chinese"){
                    result = ttsobject.setLanguage(Locale.CHINESE);
                }
                else if(item=="ENGLISH(US)"){
                    result = ttsobject.setLanguage(Locale.US);
                }
                else if(item=="CANADA"){
                    result = ttsobject.setLanguage(Locale.CANADA);
                }
                else if(item=="FRENCH"){
                    result = ttsobject.setLanguage(Locale.FRENCH);
                }
                else if(item=="GERMAN"){
                    result = ttsobject.setLanguage(Locale.GERMAN);
                }
                else if(item=="ITALIAN"){
                    result = ttsobject.setLanguage(Locale.ITALIAN);
                }
                else if(item=="JAPANESE"){
                    result = ttsobject.setLanguage(Locale.JAPANESE);
                }
                else if(item=="KOREAN"){
                    result = ttsobject.setLanguage(Locale.KOREAN);
                }
                else if(item=="TAIWAN"){
                    result = ttsobject.setLanguage(Locale.TAIWAN);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



        Bundle applesData = getIntent().getExtras();
        if(applesData==null)
            return;
        String applesMessage = applesData.getString("Value");
        et1=(EditText)findViewById(R.id.slimsInput);
        et1.setText(applesMessage);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_smart_voice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void dosomething(View v)
    {  et=(EditText)findViewById(R.id.slimsInput);
        switch(v.getId())
        {
            case R.id.bspeak:
                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                }
                else{
                    text=et.getText().toString();
                   //ttsobject.speak((CharSequence)text,TextToSpeech.QUEUE_FLUSH,b,null);
                   ttsobject.speak(text,TextToSpeech.QUEUE_FLUSH,b,null);
                    //ttsobject.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                }

                break;
            case R.id.bstopspeaking:

                if(ttsobject!=null)
                {
                    ttsobject.stop();
                }

                break;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(ttsobject!=null)
        {
            ttsobject.stop();
            ttsobject.shutdown();
        }
    }
    //Add a product to the database
    public void addButtonClicked(View view){
        et1=(EditText)findViewById(R.id.slimsInput);
        String s1 = et1.getText().toString();
        if(s1.length()>0)
           {Data data = new Data(et1.getText().toString());
        dbHandler.addProduct(data);
               int i=s1.length();
               Toast.makeText(getApplicationContext(),"Text Stored",Toast.LENGTH_SHORT).show();}
           else
           {
               Toast.makeText(getApplicationContext(), "Please Write something",Toast.LENGTH_SHORT).show();
           }

    }
    public void clearButtonClicked(View view){
        et1=(EditText)findViewById(R.id.slimsInput);
        et1.setText(null);

    }
    public void loadButtonClicked(View view){
            Intent i= new Intent(this, LoadData.class); // creating instance of intent class  which says this is the activity we want to launch

            startActivity(i); //to launch activity

    }

}
