package sig.android.simpleoauth;

import java.util.ArrayList;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class TwitterStatusUpdateTask extends AsyncTask<Object, Object, Object> {

    private AccessToken accessToken;
    private String message;
    
    private final static String UPDATE_STATUS_URI = "https://api.twitter.com/1.1/statuses/update.json";

    public TwitterStatusUpdateTask(AccessToken accessToken, String message) {
        this.accessToken = accessToken;
        this.message = message;
    }

    @Override
    protected Object doInBackground(Object... params) {

        try {
            
            HttpPost request = new HttpPost(UPDATE_STATUS_URI);
            
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("status", message)); 
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_TOKEN, Constants.CONSUMER_SECRET);
            consumer.setTokenWithSecret(accessToken.getToken(), accessToken.getTokenSecret());
            consumer.sign(request);
            
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            
            int statusCode = response.getStatusLine().getStatusCode();
            
            if (statusCode != 200) {
                throw new RuntimeException("Bad response from Twitter (" + statusCode + "): " + response.getStatusLine().getReasonPhrase());
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
