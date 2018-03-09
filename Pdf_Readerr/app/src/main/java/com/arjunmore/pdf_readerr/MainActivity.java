package com.arjunmore.pdf_readerr;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdfView = findViewById(R.id.pdfview);
      //  pdfView.fromAsset("java.pdf").load();
        new RetrivePdf(MainActivity.this).execute("http://unipune.ac.in/university_files/pdf/old_papers/april2013/Commerce/BCA.pdf");

    }
    class RetrivePdf extends AsyncTask<String,Void,InputStream>{

        Context context;
        ProgressDialog progressDialog;

        public RetrivePdf(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("downloading");
            progressDialog.show();
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if(httpURLConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            pdfView.fromStream(inputStream).load();
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }
}
