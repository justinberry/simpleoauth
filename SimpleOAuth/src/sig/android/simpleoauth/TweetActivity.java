package sig.android.simpleoauth;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class TweetActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        
        String token = getIntent().getExtras().getString(Constants.ACCESS_TOKEN);
        String secret = getIntent().getExtras().getString(Constants.ACCESS_SECRET);
        
        new TwitterStatusUpdateTask(token, secret).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tweet, menu);
        return true;
    }
}
