package com.example.banckaccount;

import org.json.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.security.ProviderInstaller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;

public class MainActivity extends AppCompatActivity {
    public static boolean connecte = false;
    private static final String PERSO = "example.txt";
    private static final String ACC = "example2.txt";
    private static final String URL = "example3.txt";
    private static final String CLEF = "example4.txt";
    static { System.loadLibrary("native-lib"); }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("----------------------------------------------------------------------------------");
    }
    public void print(String s){
        System.out.println(s);
    }
    public void printtoast(String s){ TextView tw = findViewById(R.id.info);
    tw.setText("[Info] " + s);}
    public String geturl(){ return DeCode(Read(URL)); }
    public String getclef(){ return DeCode(Read(CLEF)).replace("\u000F",""); }
    public String getaccount(){return DeCode(Read(ACC));}
    public String getnomprenom(){ return DeCode(Read(PERSO).split("-end-")[0]) +" "+DeCode(Read(PERSO).split("-end-")[1]); }



    public boolean Connexion(){
        boolean internet;
        String res = GetApi.Companion.getRes(geturl());
        return !res.equals("error");
    }
    @Override
    public void onStart(){
        super.onStart();
        Flush(ACC);
        Flush(PERSO);
        Flush(URL);
        Flush(CLEF);
        Write(EnCode(CPPgetCLEF()),CLEF);
        Write(EnCode(CPPgetURL()),URL);
        Write(EnCode(CPPgetPRENOM())+ "-end-" +EnCode(CPPgetNOM()),PERSO);
        print("TEEEEEEEEEEEEESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        print(CPPgetURL());
    }
    public void SetButtonText(boolean b){
        Button bt = findViewById(R.id.bt);
        if(b){
            bt.setText("Raffraichir");
        }
        else{
            bt.setText("SE CONNECTER A VOTRE COMPTE");
        }
    }
    public void Login(View v)  {
        EditText tclef = findViewById(R.id.Clef);
        print(getclef() + " and " + tclef.getText().toString());
        print(String.valueOf(getclef().equals(tclef.getText().toString())));
        if(getclef().equals(tclef.getText().toString())){
            Afficher();
            connecte = true;
            SetButtonText(connecte);
            print(getclef());
        }
        else
        {
            connecte = false;
            SetButtonText(connecte);
            printtoast("Echec de l'authentification.");
        }
    }
    public void Afficher(){
        boolean co = Connexion();
        print(String.valueOf("internet : " + co));
        if(co){
            try{
                Flush(ACC);
                Write(EnCode(GetApi.Companion.getRes(geturl())),ACC);
                JSONArray comptes = new JSONArray(getaccount());
                String total = "\nBonjour " + getnomprenom() + ",\n" + "Voici les informations de vos comptes : " + "\n" + "\n";
                for (int i = 0; i < comptes.length(); i++)
                {
                    String currency = comptes.getJSONObject(i).getString("currency");
                    String iban = comptes.getJSONObject(i).getString("iban");
                    String accountName = comptes.getJSONObject(i).getString("accountName");
                    String amount = comptes.getJSONObject(i).getString("amount");
                    total = total + accountName + ":" + amount + " " + currency + "\n" + "iban : " + iban + "\n\n";
                }
                printtoast("Connexion réussie !");
                TextView t = findViewById(R.id.sample_text);
                t.setText(total);
            }catch (Exception e){
                printtoast("Fatal error dans la lecture du json. ");
            }
        }
        else {
            if(getaccount()==null || getaccount().equals("")){
                printtoast("L'authentification a réussi, mais vous êtes hors-ligne et vous ne " +
                        "vous êtes jamais connectés auparavant ; nous ne pouvons rien afficher !");
            }
            else
            {
                printtoast("Vous êtes hors-ligne.");
                try{
                    JSONArray comptes = new JSONArray(getaccount());
                    String total = "Bonjour " + getnomprenom() + ",\n" + "Voici les informations de vos comptes : " + "\n" + "\n";
                    for (int i = 0; i < comptes.length(); i++)
                    {
                        String currency = comptes.getJSONObject(i).getString("currency");
                        String iban = comptes.getJSONObject(i).getString("iban");
                        String accountName = comptes.getJSONObject(i).getString("accountName");
                        String amount = comptes.getJSONObject(i).getString("amount");
                        total = total + accountName + ":" + amount + " " + currency + "\n" + "iban : " + iban + "\n\n";
                    }
                    TextView t = findViewById(R.id.sample_text);
                    t.setText(total);
                }
                catch (Exception e){
                    printtoast("Fatal error dans la lecture du json. ");
                }

            }
        }
    }













    public void Write(String s,String FILENAME)
    {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME,MODE_PRIVATE);
            fos.write(s.getBytes());
            System.out.println("Saved to " + getFilesDir() + "/" + FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fos!=null)
            {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void Flush(String FILENAME)
    {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME,MODE_PRIVATE);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String Read(String FILENAME){
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String text;
            int i=0;
            while((text = br.readLine())!=null)
            {
                sb.append(text).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    public native String stringFromJNI();
    public native String EnCode(String tohash);
    public native String DeCode(String toDehash);
    public native String CPPgetURL();
    public native String CPPgetCLEF();
    public native String CPPgetNOM();
    public native String CPPgetPRENOM();
}