package com.example.backslash.backslash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;
    EditText text;
    float tsize;
    AlertDialog.Builder dialogBuilder;
    String strName;
    boolean isbold = false;
    boolean isitalic = false;
    Spinner spinner;
    int fonti = 1;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        text = (EditText) findViewById(R.id.text);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                setContentView(R.layout.activity_main);
                final EditText txt = (EditText) findViewById(R.id.text);
                spinner = (Spinner) findViewById(R.id.spinner);
                adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.spinner_item, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                txt.setTextColor(Color.BLACK);
                                break;
                            case 1:
                                txt.setTextColor(Color.GRAY);
                                break;
                            case 2:
                                txt.setTextColor(Color.GREEN);
                                break;
                            case 3:
                                txt.setTextColor(Color.MAGENTA);
                                break;
                            case 4:
                                txt.setTextColor(Color.CYAN);
                                break;
                            case 5:
                                txt.setTextColor(Color.BLUE);
                                break;
                            case 6:
                                txt.setTextColor(Color.DKGRAY);
                                break;
                            case 7:
                                txt.setTextColor(Color.RED);
                                break;
                            case 8:
                                txt.setTextColor(Color.YELLOW);
                                break;
                            case 9:
                                txt.setTextColor(Color.WHITE);
                                break;
                            case 10:
                                txt.setTextColor(Color.LTGRAY);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        }, SPLASH_TIME_OUT);
        tsize = 20;
    }

    public void SaveDialog(View v) {
        dialogBuilder = new AlertDialog.Builder(this);
        final EditText txtinput = new EditText(this);
        txtinput.setTextColor(Color.GREEN);
        txtinput.setBackgroundColor(Color.RED);
        txtinput.setGravity(0x01);
        dialogBuilder.setTitle("SAVE");
        dialogBuilder.setMessage("Enter the name of the file :-");
        dialogBuilder.setView(txtinput);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                strName = txtinput.getText().toString();
                save(strName);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog dialogName = dialogBuilder.create();
        dialogName.show();
    }

    public void OpenDialog(View v) {
        dialogBuilder = new AlertDialog.Builder(this);
        final EditText txtinput = new EditText(this);
        txtinput.setTextColor(Color.GREEN);
        txtinput.setBackgroundColor(Color.RED);
        txtinput.setGravity(0x01);
        dialogBuilder.setTitle("OPEN");
        dialogBuilder.setMessage("Enter the name of the file :-");
        dialogBuilder.setView(txtinput);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                strName = txtinput.getText().toString();
                open(strName);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialogName = dialogBuilder.create();
        dialogName.show();
    }

    public void inc(View v) {
        text = (EditText) findViewById(R.id.text);
        tsize++;
        text.setTextSize(tsize);
    }

    public void dec(View v) {
        text = (EditText) findViewById(R.id.text);
        tsize--;
        text.setTextSize(tsize);
    }

    public void open(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName + ".txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                EditText et = (EditText) findViewById(R.id.text);
                et.setText(stringBuilder.toString());
                Toast.makeText(this, "File Opened !", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            Toast.makeText(this, "File doesn't exists !", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public void save(String fileName) {
        try {
            EditText text = (EditText) findViewById(R.id.text);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(fileName + ".txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(text.getText().toString());
            outputStreamWriter.close();
            Toast.makeText(this, "File Saved !", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void share(View v) {
        EditText text = (EditText) findViewById(R.id.text);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Text shared using Back\\Slash text Editor !");
        intent.putExtra(Intent.EXTRA_TEXT, text.getText().toString());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void bold(View v) {
        EditText text = (EditText) findViewById(R.id.text);
        if (isbold == false) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
            isbold = true;
        } else {
            text.setTypeface(Typeface.DEFAULT);
            isbold = false;
        }
    }

    public void italic(View v) {
        EditText text = (EditText) findViewById(R.id.text);
        if (isitalic == false) {
            text.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            isitalic = true;
        } else {
            text.setTypeface(Typeface.DEFAULT);
            isitalic = false;
        }
    }

    public void left(View v) {
        EditText text = (EditText) findViewById(R.id.text);
        text.setGravity(0x03);
    }

    public void right(View v) {
        EditText text = (EditText) findViewById(R.id.text);
        text.setGravity(0x05);
    }

    public void ctr(View v) {
        EditText text = (EditText) findViewById(R.id.text);
        text.setGravity(0x01);
    }

    public void font(View v) {
        EditText text = (EditText) findViewById(R.id.text);
        int x = fonti % 4;
        switch (x) {
            case 0:
                text.setTypeface(Typeface.DEFAULT);
                fonti++;
                break;
            case 1:
                text.setTypeface(Typeface.SERIF);
                fonti++;
                break;
            case 2:
                text.setTypeface(Typeface.SANS_SERIF);
                fonti++;
                break;
            case 3:
                text.setTypeface(Typeface.MONOSPACE);
                fonti++;
                break;
        }
    }
}
