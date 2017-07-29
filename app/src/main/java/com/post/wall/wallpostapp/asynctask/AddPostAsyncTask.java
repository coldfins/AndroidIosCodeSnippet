package com.post.wall.wallpostapp.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.post.wall.wallpostapp.GalleryPostScrollActivity;
import com.post.wall.wallpostapp.HomeActivity;
import com.post.wall.wallpostapp.model.Post;
import com.post.wall.wallpostapp.model.PostListModel;
import com.post.wall.wallpostapp.model.PostVideoImagesModel;
import com.post.wall.wallpostapp.utility.GetUrlClass;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by ved_pc on 2/20/2017.
 */

public class AddPostAsyncTask extends AsyncTask<String, Void, String> {
    String PostText;
    Activity activity;
    List<PostVideoImagesModel> postVideoImagesModels;


    public AddPostAsyncTask(Activity activity, String postText, List<PostVideoImagesModel> postVideoImagesModels) {
        PostText = postText;
        this.activity = activity;
        this.postVideoImagesModels = postVideoImagesModels;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(new GetUrlClass().getUrl()+"userPost");
        Log.v("TTT", "URL...." + new GetUrlClass().getUrl()+"userPost");

        String result = null;

        try
        {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("UserId", new StringBody(HomeActivity.user.getUser().getUserId() + ""));//...
            reqEntity.addPart("PostText", new StringBody(PostText));
            Log.v("TTT", HomeActivity.user.getUser().getUserId() + "...33." + PostText);

            //Add images Day wise

            Log.v("TTT", "postVideoImagesModels....11...." + postVideoImagesModels.size());
            String jsonKeyValue = "{";
            for(int i=0; i<postVideoImagesModels.size(); i++)
            {
                if(i != 0)
                    jsonKeyValue = jsonKeyValue + ",";
                            jsonKeyValue = jsonKeyValue + "\""+postVideoImagesModels.get(i).getFileBody().getFile().getName() + "\":\"" + postVideoImagesModels.get(i).getIsVideo() + "\"";
                reqEntity.addPart("ImageContent", new FileBody(new File(postVideoImagesModels.get(i).getFileBody().getFile().toString())));
                Log.v("TTT", postVideoImagesModels.get(i).getIsVideo() + "..22.." + postVideoImagesModels.get(i).getFileBody().getFile().toString());

                if(i == postVideoImagesModels.size()-1 ){
                    jsonKeyValue = jsonKeyValue + "}";
                }
            }
            Log.v("TTT", "ImagesVideos...." + jsonKeyValue);
            reqEntity.addPart("ImagesVideos",  new StringBody(jsonKeyValue));

            httpPost.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            result = convertStreamToString(instream);

            Log.v("TTT", "Async RESULT........" + result);

        } catch (ClientProtocolException e)
        {
            e.printStackTrace();
            Log.e("TTT", "ClientProtocolException try catch");
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.e("TTT", "IOException try catch");
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.v("TTT", "Async task111 ........" + s);
        ((GalleryPostScrollActivity)activity).showProgress(false);
        Post postListModel = new Gson().fromJson(s, Post.class);
        Log.v("TTT", "Async task 222........" + new Gson().toJson(postListModel));
        ((GalleryPostScrollActivity)activity).addSuccess(postListModel);

    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }




}
