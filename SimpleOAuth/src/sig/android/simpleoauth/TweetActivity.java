package sig.android.simpleoauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class TweetActivity extends Activity {

    AccessToken accessToken;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        
        String token = getIntent().getExtras().getString(Constants.ACCESS_TOKEN);
        String secret = getIntent().getExtras().getString(Constants.ACCESS_SECRET);
        
        accessToken = new AccessToken(token, secret);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tweet, menu);
        return true;
    }
    
    public void onTweetClick(View button) {
        EditText tweetTextEdit = (EditText) findViewById(R.id.tweetText);
        String message = tweetTextEdit.getText().toString();
        new TwitterStatusUpdateTask(accessToken, message).execute();
    }
    
    public void onClearAuthClick(View button) { 
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        Editor editor = sharedPreferences.edit();
        
        editor.putString(Constants.ACCESS_TOKEN, null);
        editor.putString(Constants.ACCESS_SECRET, null);
        
        editor.commit();
        
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
