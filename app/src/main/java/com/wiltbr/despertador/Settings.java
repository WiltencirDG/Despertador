package com.wiltbr.despertador;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

/**
 * Created by wilte on 15/07/2017.
 */

public class Settings extends AppCompatActivity {

    private CheckBox vibrar;
    private Spinner musica;
    private Spinner snooze;
    private Button salvar;
    private SharedPreferences sharedPref;
    int musicaPos = 0;
    int snoozePos = 0;
    Boolean vibraPos = true;
    MediaPlayer mPlayer;
    Boolean initialDisplay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_app);

        musica = (Spinner) findViewById(R.id.spinmusicas);
        vibrar = (CheckBox) findViewById(R.id.vibrar);
        snooze = (Spinner) findViewById(R.id.snooze);

        salvar = (Button) findViewById(R.id.btnSalvar);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        musica.setAdapter(spinnerAdapter);

        ArrayAdapter<CharSequence> timesAdapter = ArrayAdapter.createFromResource(this,R.array.times,android.R.layout.simple_spinner_item);
        timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snooze.setAdapter(timesAdapter);


        musica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(initialDisplay){
                    initialDisplay = false;
                }else{
                    musicaPos = musica.getSelectedItemPosition();

                    switch (musicaPos) {
                        case 0:
                            pararMusica();
                            mPlayer = MediaPlayer.create(Settings.this,R.raw.aperture);
                            mPlayer.start();
                            break;
                        case 1:
                            pararMusica();
                            mPlayer = MediaPlayer.create(Settings.this,R.raw.morning);
                            mPlayer.start();
                            break;
                        case 2:
                            pararMusica();
                            mPlayer = MediaPlayer.create(Settings.this,R.raw.sandstorm);
                            mPlayer.start();
                            break;
                        case 3:
                            pararMusica();
                            mPlayer = MediaPlayer.create(Settings.this,R.raw.symphony40);
                            mPlayer.start();
                            break;
                        case 4:
                            pararMusica();
                            mPlayer = MediaPlayer.create(Settings.this,R.raw.whistle);
                            mPlayer.start();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pararMusica();
            }
        });

        snooze.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                snoozePos = snooze.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        musicaPos = sharedPref.getInt("musicaSpin",0);
        snoozePos = sharedPref.getInt("snoozeSpin",0);
        vibraPos = sharedPref.getBoolean("vibra",true);

        if(musicaPos > -1)
            musica.setSelection(musicaPos);
        if(snoozePos > -1)
            snooze.setSelection(snoozePos);

        musica.setSelection(musicaPos);
        snooze.setSelection(snoozePos);

        if (vibraPos){
            vibrar.setChecked(true);
        }else{
            vibrar.setChecked(false);
        }

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                vibraPos = vibrar.isChecked();
                editor.putInt("musicaSpin",musicaPos);
                editor.putInt("snoozeSpin",snoozePos);
                editor.putBoolean("vibra", vibraPos);
                editor.apply();
                finish();
            }
        });

    }

    private void pararMusica() {
        if(mPlayer!=null){
            if(mPlayer.isPlaying()){
                mPlayer.stop();
                mPlayer.release();
                mPlayer=null;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pararMusica();
    }
}
