package com.thepriest.andrea.openfolder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    TextView folderTextView2;
    Button buttonOpenFolder;
    Button buttonExit;
    Button buttonClear;
    Spinner spinnerSaved,spinnerRecent;
    Button buttonSave,buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        folderTextView2= (TextView) findViewById(R.id.folderTextView2);
        buttonOpenFolder= (Button) findViewById(R.id.buttonOpenFolder);
        buttonOpenFolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //System.exit(0);
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.estrongs.android.pop");
                if (intent != null) {
    /* We found the activity now start the activity */
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse(folderTextView2.getText().toString()));
                    startActivity(intent);
                    System.exit(0);
                } else {
    /* Activity not found. */
                }


            }
        });
        buttonClear= (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                folderTextView2.setText("");

            }
        });
        buttonExit= (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    System.exit(0);


            }
        });
       // spinnerSaved,spinnerRecent;
       // Button buttonSave,buttonDelete;
        buttonSave= (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
           saveFolder();
            }
        });
        buttonDelete= (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteFolder();
            }
        });

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }}  else {
            // Handle other intents, such as being started from the home screen
        }}

    private void deleteFolder() {
    }

    private void saveFolder() {
    }

    /**
     *
      * @param intent
     */
    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            folderTextView2.setText(sharedText);
            intent = getPackageManager().getLaunchIntentForPackage("com.estrongs.android.pop");
            if (intent != null) {
    /* We found the activity now start the activity */
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse(folderTextView2.getText().toString()));
                startActivity(intent);
                System.exit(0);
            } else {
    /* Bring user to the market or let them choose an app? */

            }


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
