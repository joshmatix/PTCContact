package com.sharperture.remotecsv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


//https://inducesmile.com/android-tips/android-how-to-read-csv-file-from-remote-server-or-assets-folder-in-android/

public class MainActivity extends AppCompatActivity  implements MainActivityCallback {


    private static TextView fileContent;
//    private static MainActivity mainActivity;
    private ArrayList<String> items = new ArrayList<String>();

    public static final String CUSTOM_INTENT = "android.sharperture.br.CUSTOM_INTENT";

    private static final String PATH_TO_SERVER = "https://www.pacquenactennisclub.com/ptccontacts.tsv";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        fileContent = (TextView) findViewById(R.id.load_file_from_server);
        Button loadTextButton = (Button)findViewById(R.id.load_file_from_server);

        loadTextButton.setOnClickListener( new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  DownloadFilesTask downloadFilesTask = new DownloadFilesTask();

                  downloadFilesTask.setContext(getApplicationContext());
                  downloadFilesTask.setitems(items);

                  downloadFilesTask.execute();
              }
        }
        );
//        https://stackoverflow.com/questions/13139531/read-data-from-database-and-show-it-in-gridview-in-android
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
         //       android.R.layout.simple_list_item_1, ArrayofName);

      //  final ArrayList<String> items = new ArrayList<String>();

       // items.add("1 , Hello11 , Hello12");
       // items.add("2 , Hello21 , Hello22");

        // gridView.setAdapter(new GridAdapter(items));


        Intent menuIntent = new Intent(this, Main2Activity.class);
        startActivity(menuIntent);
    }

    @Override
    protected void onResume() {
        this.registerReceiver(receiver, intent_filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        this.unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void processData(List<String[]> data) {
        
    }


    private static class DownloadFilesTask extends AsyncTask<URL, Void, List<String[]>>{
        private Context mContext;
        private ArrayList<String> items = new ArrayList<String>();
        public void setitems(ArrayList<String> i){
            items = i;
        }
        public void setContext(Context context){
            mContext = context;
        }
        protected List<String[]> doInBackground(URL... urls) {
            return downloadRemoteTextFileContent();
        }
        protected void onPostExecute(List<String[]> result){
            if(result!=null){
                //final ArrayList<String> items = new ArrayList<String>();
                printCVSContent(result, items);
                Intent intent = new Intent();
                intent.setAction(CUSTOM_INTENT);
//                intent.putExtra("strings", (android.os.Parcelable)result);
                mContext.sendBroadcast(intent);
            }
        }
    }
    private static void printCVSContent(List<String[]> result, ArrayList<String> items){
        String cvsColumn = "";
        //final ArrayList<String> items = new ArrayList<String>();
        for (int i=0; i<result.size(); i++){
            String [] rows = result.get(i);
            if(rows.length>3) {
                cvsColumn += rows[0] + " " + rows[1] + " " + rows[2] + " " + rows[3] + "\n";
                items.add( rows[0] + "/t" + rows[1] + "/t" + rows[2] + "/t" + rows[3] );
            }
         }
       // gridView = (GridView) mainActivity.findViewById(R.id.gridView1);
        //gridView.setAdapter(new GridAdapter(items));
     //    fileContent.setText(cvsColumn);
    }
    private static List<String[]>downloadRemoteTextFileContent() {
        URL mUrl = null;
        List<String[]> csvLine = new ArrayList<>();
        String[] content = null;
        try {
            mUrl = new URL(PATH_TO_SERVER);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        try {
            assert mUrl != null;
            URLConnection connection = mUrl.openConnection();
//            BufferedReader br = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader( isr);

            //////////////////////////
            String line = "";
            while((line=br.readLine())!=null) {
//                content = line.split(",");
                content = line.split("\t");
                csvLine.add(content);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvLine;
    }


 //   ArrayAdapter<PTCContact> ptcContactArrayAdapter =
   //         new ArrayAdapter<PTCContact>(this, 0, PTCContacts) {

     //       };
 private IntentFilter intent_filter = new IntentFilter(CUSTOM_INTENT);
 private BroadcastReceiver receiver = new BroadcastReceiver(){
     @Override
     public void onReceive(Context context, Intent intent) {
         long addr;
         List<String[]> result = null;
        // intent.getLongExtra("strings", result);
        // List<String[]> result = (List<String[]>)addr;


         Toast.makeText(context, "Intent for Broadcast fired", Toast.LENGTH_SHORT).show();
     }
 };

}
