package com.yezz.company.yezzclub;

import android.os.AsyncTask;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.Exception;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ramon_000 on 28-07-2016.
 */
public class HttpRequest extends AsyncTask<String,Void,String>
{
    private String link;
    private int method=0;
    private EditText response;



    public HttpRequest(String link, EditText response){
        this.link=link;
        this.response=response;
    }

    public HttpRequest(String link,EditText response, int method){
        this.link=link;
        this.response=response;
        this.method=method;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data=this.setData(this.method,strings);

        /*Post Method*/
        if (this.method==1){
            try {
                URL url=new URL(this.link);
                URLConnection con= url.openConnection();
                con.setDoInput(true);

                OutputStreamWriter osw=new OutputStreamWriter(con.getOutputStream());
                osw.write(data);
                osw.flush();

                BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));

                StringBuilder sb=new StringBuilder();
                String line;

                //Read Server Response
                while ((line= reader.readLine())!=null){
                    sb.append(line);
                    break;
                }

                return sb.toString();
            }catch (Exception e){
                return new String("Exception: "+e.getMessage());
            }
        }else{ /*Get Method*/
            try {
                this.link=this.link.concat("?"+data);
                HttpClient client=new DefaultHttpClient();
                HttpGet request=new HttpGet();
                request.setURI(new URI(this.link));

                HttpResponse response=client.execute(request);
                BufferedReader in=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb=new StringBuffer();
                String line;

                while ((line=in.readLine())!=null){
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            }catch (Exception e){
                return new String("Excepcion: "+e.getMessage());
            }
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.response.setText(s);
    }

    private String setData(int method, String... data){
        if (data.length%2!=0){
            return null;
        }
        if (method==1){
            return this.setPostData(data);
        }
        return this.setGetData(data);
    }

    private String setGetData(String... data){
        String get="";
        for (int i=0; i<data.length; i+=2){
            get=get.concat(this.setAnd(get)+data[i]+"="+data[i+1]);
        }
        return get;
    }

    private String setPostData(String... data){
        try {
            String post="";
            for (int i=0; i<data.length; i+=2){
                post.concat(this.setAnd(post)+ URLEncoder.encode(data[i],"UTF-8")+"="+URLEncoder.encode(data[i+1],"UTF-8"));
            }
            return post;
        }catch (Exception e){

        }
        return null;
    }

    private String setAnd(String get){
        return get.equals("")?"":"&";
    }
}
