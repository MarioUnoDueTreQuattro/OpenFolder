package com.thepriest.andrea.openfolder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView folderTextView2;
    Button buttonOpenFolder;
    Button buttonExit;
    Button buttonClear;//
    Spinner spinnerSaved, spinnerRecent;
    Button buttonSave, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        folderTextView2 = (TextView) findViewById(R.id.folderTextView2);
        buttonOpenFolder = (Button) findViewById(R.id.buttonOpenFolder);
        buttonOpenFolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //System.exit(0);
                openFolder();
/*
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.estrongs.android.pop");
                if (intent != null) {
    */
/* We found the activity now start the activity *//*

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse(folderTextView2.getText().toString()));
                    startActivity(intent);
*/
                    System.exit(0);
                /*} else {
    *//* Activity not found. *//*
                }
*/

            }
        });
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                folderTextView2.setText("");

            }
        });
        buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.exit(0);


            }
        });
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveFolder();
            }
        });
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteFolder();
            }
        });
        spinnerSaved = (Spinner) findViewById(R.id.spinnerSaved);
        //spinnerSaved.setOnTouchListener(Spinner_OnTouch);
/*
        spinnerSaved.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick()");
            }
        });
*/
/*
  spinnerSaved.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          folderTextView2 = (TextView) findViewById(R.id.folderTextView2);
          folderTextView2.setText(parent.getItemAtPosition(position).toString());

      }
  });
*/
        spinnerSaved.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                folderTextView2 = (TextView) findViewById(R.id.folderTextView2);
                folderTextView2.setText(arg0.getItemAtPosition(arg2).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.d(TAG, "onNothingSelected()");

            }
        });
        spinnerRecent = (Spinner) findViewById(R.id.spinnerRecent);
        spinnerRecent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                folderTextView2 = (TextView) findViewById(R.id.folderTextView2);
                folderTextView2.setText(arg0.getItemAtPosition(arg2).toString());
                Log.d(TAG, "onItemSelected()");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.d(TAG, "onNothingSelected()");

            }
        });
        loadFolders();
        loadRecentFolders();
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    private View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //TODO
                Log.d(TAG, "OnTouchListener");
                this.onTouch(v, event);
            }
            return true;
        }
    };

    private boolean savedFoldersExists() {
        return new File("/data/data/com.thepriest.andrea.openfolder/files/savedFolders.txt").exists();
    }

    private boolean recentFoldersExists() {
        return new File("/data/data/com.thepriest.andrea.openfolder/files/savedFolders.txt").exists();
    }

    private void loadFolders() {
        String sCurPath = folderTextView2.getText().toString();
        Vector<String> str = null;
        try {
            str = new Vector<String>();
            FileInputStream fstream = new FileInputStream("/data/data/com.thepriest.andrea.openfolder/files/savedFolders.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(fstream));
            String line = in.readLine();
            int index = 0;
            while (line != null) {

                str.add(line);
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, str);

        spinnerSaved.setAdapter(adapter);
        folderTextView2.setText(sCurPath);
/*
        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("savedFolders.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                temp1 = line;
                temp2 = line;

            }

            in.close();
        }catch (Exception e){//Catch exception if any

        }
*/
    }

    private void loadRecentFolders() {
        // String sCurPath = folderTextView2.getText().toString();
        Vector<String> str = null;
        try {
            str = new Vector<String>();
            FileInputStream fstream = new FileInputStream("/data/data/com.thepriest.andrea.openfolder/files/recentFolders.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(fstream));
            String line = in.readLine();
            int index = 0;
            while (line != null) {

                str.add(line);
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (str.isEmpty() == false) {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, str);
            spinnerRecent.setAdapter(adapter);
        }
        // folderTextView2.setText(sCurPath);
    }

    private void deleteFolder() {
        String string = folderTextView2.getText().toString();
        ArrayAdapter<String> dataAdapter;
        SpinnerAdapter spinAdapter = spinnerSaved.getAdapter();
        dataAdapter = ((ArrayAdapter<String>) spinAdapter);
        dataAdapter.remove(string);
        String filename = "/data/data/com.thepriest.andrea.openfolder/files/savedFolders.txt";
        string = folderTextView2.getText().toString();
        //if (string.length()==0) return;
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            for (int i = 0; i < dataAdapter.getCount(); i++) {
                out.println(dataAdapter.getItem(i));
            }
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    private void saveFolder() {
        boolean bExists = savedFoldersExists();
        String filename = "/data/data/com.thepriest.andrea.openfolder/files/savedFolders.txt";
        String string = folderTextView2.getText().toString();
        if (string.length() == 0) return;
        try {
            if (bExists) {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
                out.println(string);
                out.close();
            } else {
                //string+="\n";
                FileOutputStream outputStream;
                outputStream = openFileOutput("savedFolders.txt", Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
                //PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
                //out.println(string);
                //out.close();
            }
        } catch (IOException e) {
        }
/*
        FileOutputStream outputStream;
        try {
            File file = new File(filename);
//if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(string);
            bufferWritter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/


/*
    try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            final PrintStream printStream = new PrintStream(outputStream);
            //outputStream.write(string.getBytes());
            //outputStream.close();
            printStream.print(string);
            //printStream.append(string);
            printStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        loadFolders();
    }

    private void saveRecentFolder(String sharedText) {
        boolean bExists = recentFoldersExists();
        String filename = "/data/data/com.thepriest.andrea.openfolder/files/recentFolders.txt";
        String string = sharedText;
        if (string.length() == 0) return;
        try {
            if (bExists) {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
                out.println(string);
                out.close();
            } else {
                //string+="\n";
                FileOutputStream outputStream;
                outputStream = openFileOutput("recentFolders.txt", Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
                //PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
                //out.println(string);
                //out.close();
            }
        } catch (IOException e) {
        }

        //      loadFolders();
    }

    /**
     * @param intent
     */
    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            folderTextView2.setText(sharedText);
            //saveRecentFolder(sharedText);
/*
            intent = getPackageManager().getLaunchIntentForPackage("com.estrongs.android.pop");
            if (intent != null) {
    */
/* We found the activity now start the activity *//*

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse(folderTextView2.getText().toString()));
                startActivity(intent);
*/
            openFolder();
            //System.exit(0);

        } else {
    /* Bring user to the market or let them choose an app? */

        }


    }


    private void openFolder() {
        String sPath=folderTextView2.getText().toString();
        Uri selectedUri = Uri.parse( sPath.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Log.d(TAG,selectedUri.toString());
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
            startActivity(intent);
        } else {
            // if you reach this place, it means there is no any file
            // explorer app installed on your device
        }
        saveRecentFolder(sPath);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
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
/*
    private String writeXml(List<Message> messages){
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "messages");
            serializer.attribute("", "number", String.valueOf(messages.size()));
            for (Message msg: messages){
                serializer.startTag("", "message");
                serializer.attribute("", "date", msg.getDate());
                serializer.startTag("", "title");
                serializer.text(msg.getTitle());
                serializer.endTag("", "title");
                serializer.startTag("", "url");
                serializer.text(msg.getLink().toExternalForm());
                serializer.endTag("", "url");
                serializer.startTag("", "body");
                serializer.text(msg.getDescription());
                serializer.endTag("", "body");
                serializer.endTag("", "message");
            }
            serializer.endTag("", "messages");
            serializer.endDocument();
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
*/
}
