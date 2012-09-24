package sig.android.simpleoauth;

import oauth.signpost.OAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.os.AsyncTask;

public class TwitterStatusUpdateTask extends AsyncTask<Object, Object, Object> {

    private OAuthConsumer consumer;

    public TwitterStatusUpdateTask(OAuthConsumer consumer) {
        
        this.consumer = consumer;
        
    }

    @Override
    protected Object doInBackground(Object... params) {

        try {
            HttpPost request = new HttpPost("http://api.twitter.com/1/statuses/update.json");
            
            BasicHttpParams httpParams = new BasicHttpParams();
            httpParams.setParameter("status", "Hello World");
            request.setParams(httpParams);
            
//            request.addHeader("Content-Type", "application/x-www-form-urlencoded");
            
            consumer.sign(request);
            
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            
            int statusCode = response.getStatusLine().getStatusCode();
            
            if (statusCode != 200) {
                throw new RuntimeException("Bad response from Twitter - " + statusCode);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
