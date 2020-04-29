package com.torres.nicolas.scorekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private var team_1_score = 0
    private var team_2_score = 0

    private var item_ID_Clicked : Int = 0

    companion object {
        private const val STATE_SCORE_1 = "STATE_SCORE_1"
        private const val STATE_SCORE_2 = "STATE_SCORE_2"
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {

        //Save the scores
        if (outState != null) {

            outState.putInt(STATE_SCORE_1, team_1_score)
            outState.putInt(STATE_SCORE_2, team_2_score)
        }

        if (outState != null) {
            super.onSaveInstanceState(outState)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            team_1_score = savedInstanceState.getInt(STATE_SCORE_1)
            team_2_score = savedInstanceState.getInt(STATE_SCORE_2)
            //Set the score text views
            refreshDisplay()
        }


        decreaseTeam1.setOnClickListener {view ->
            //Get the ID of the button that was clicked
            item_ID_Clicked = R.id.decreaseTeam1
            refreshScore(item_ID_Clicked)
        }

        decreaseTeam2.setOnClickListener {view ->
            //Get the ID of the button that was clicked
            item_ID_Clicked = R.id.decreaseTeam2
            refreshScore(item_ID_Clicked)
        }

        increaseTeam1.setOnClickListener {view ->
            //Get the ID of the button that was clicked
            item_ID_Clicked = R.id.increaseTeam1
            score_1.text.take(team_1_score.inc())
            refreshScore(item_ID_Clicked)
        }

        increaseTeam2.setOnClickListener {view ->
            //Get the ID of the button that was clicked
            item_ID_Clicked = R.id.increaseTeam2
            refreshScore(item_ID_Clicked)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflate the menu from XML
        menuInflater.inflate(R.menu.main_menu, menu);

        //Change the label of the menu based on the state of the app
        if (menu != null) {

            val nightMode = AppCompatDelegate.getDefaultNightMode()
            when (nightMode) {
                AppCompatDelegate.MODE_NIGHT_YES -> menu.findItem(R.id.night_mode).setTitle(R.string.day_mode)
                else -> menu.findItem(R.id.night_mode).setTitle(R.string.night_mode)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //Check if the correct item was clicked
        if (item != null && item.itemId == R.id.night_mode) {
            //Get the night mode state of the app
            val nightMode: Int = AppCompatDelegate.getDefaultNightMode()

            //Set the theme mode for the restarted activity
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

        }
        this.recreate()
        return true
    }

    private fun refreshScore(itemID : Int) {
        when(itemID) {
            //Decrement the score of Team 1
            R.id.decreaseTeam1 -> team_1_score = team_1_score.dec()
            //Decrement the score of Team 2
            R.id.decreaseTeam2 -> team_2_score = team_2_score.dec()
            //Increment the score of Team 1
            R.id.increaseTeam1 -> team_1_score = team_1_score.inc()
            //Increment the score of Team 2
            R.id.increaseTeam2 -> team_2_score = team_2_score.inc()
        }
        refreshDisplay()
    }

    private fun refreshDisplay() {

        score_1.text = team_1_score.toString()
        score_2.text = team_2_score.toString()

    }
}