package com.helluva.telephone_pictionary_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by cal on 2/25/17.
 */

public class GamelistActivity extends AppCompatActivity {

    ArrayList<String> displayContent = new ArrayList<>();
    ArrayList<String> gamesAndIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GamelistActivity.this, MainActivity.class);
                GamelistActivity.this.startActivity(i);
            }
        });

        ((ApplicationState)getApplicationContext()).makeRequest("requestListOfGames", new ApplicationState.NodeCallback() {
            @Override
            public void receivedString(final String message) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String[] games = message.split(";");

                        displayContent.removeAll(displayContent);
                        gamesAndIds.removeAll(gamesAndIds);
                        for (String gameInfo : games) {
                            if (gameInfo != "") {
                                gamesAndIds.add(gameInfo);

                                String diplayName = gameInfo.split(",")[1];
                                System.out.println(gameInfo);

                                displayContent.add(diplayName);
                            }
                        }

                        GamelistFragment fragment = (GamelistFragment) GamelistActivity.this.getFragmentManager().findFragmentById(R.id.gamelist_fragment);
                        if (fragment != null) {
                            fragment.adapter.notifyDataSetChanged();
                        }

                    }
                });


            }
        });
    }

    public void confirmJoinSession(int index) {
        final String gameInfo = gamesAndIds.get(index);
        final String gameId = gameInfo.split(",")[0];
        final String sessionName = gameInfo.split(",")[1];

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Join " + sessionName + "?");

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

                new AlertHelper(GamelistActivity.this, "Your Name", "Join").displayWithCompletion(new AlertHelper.AlertCompletion() {

                    @Override
                    public void receiveString(final String playerName) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                joinSession(gameId, playerName);
                            }
                        });
                    }

                });

            }

        });

        alertBuilder.show();

    }

    public void joinSession(String sessionId, final String playerName) {
        String joinGameMessage = "joinGame:" + playerName + "," + sessionId;
        ((ApplicationState)getApplicationContext()).sendMessage(joinGameMessage);

        Intent playerlistIntent = new Intent(this, PlayerlistActivity.class);
        playerlistIntent.putExtra("playerIsHost", false);
        this.startActivity(playerlistIntent);
    }

}
