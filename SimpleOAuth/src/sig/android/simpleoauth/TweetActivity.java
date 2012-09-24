package sig.android.simpleoauth;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class TweetActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        
        String token = getIntent().getExtras().getString(Preferences.ACCESS_TOKEN);
        String secret = getIntent().getExtras().getString(Preferences.ACCESS_SECRET);
        
        CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(Preferences.CONSUMER_TOKEN,
                Preferences.CONSUMER_SECRET);
        consumer.setTokenWithSecret(token, secret);
        
        new TwitterStatusUpdateTask(consumer).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tweet, menu);
        return true;
    }
}
