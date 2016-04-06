package com.example.slim_shah.smartvoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;

public class LoadData extends AppCompatActivity {
    MyDbHandler dbHandler;
    String datatosend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        dbHandler = new MyDbHandler(this, null, null, 1);
        datatosend=null;
        printDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_data, menu);
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
    //Print the database
    public void printDatabase(){
        ArrayList<String> dbString = dbHandler.databaseToString();
        ListAdapter slimsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dbString);
        //ListAdapter slimsAdapter = new CustomAdapter(LoadData.this,dbString);
        ListView slimsListView = (ListView) findViewById(R.id.slimsList);
        slimsListView.setAdapter(slimsAdapter);
        slimsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setSelected(true);
                        datatosend = String.valueOf(parent.getItemAtPosition(position));


                    }
                }
        );
    }
    public void loadButtonClicked(View view){

          if(datatosend!=null)
          {Intent i;
              i = new Intent(LoadData.this,SmartVoice.class);
              i.putExtra("Value", datatosend);
              startActivity(i);}
        else
          {   Context context = getApplicationContext();
              Toast toast = Toast.makeText(context,"please tap on data you want to Load",Toast.LENGTH_SHORT);
              toast.show();

          }
    }
    public void deleteButtonClicked(View view){
            if(datatosend!=null)
            {dbHandler.deleteProduct(datatosend);
             printDatabase();
            datatosend=null;}

            else
            {   Context context = getApplicationContext();
                Toast toast = Toast.makeText(context,"please tap on data you want to delete",Toast.LENGTH_SHORT);
                toast.show();
            }
    }
}
