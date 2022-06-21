package com.dnk.shairugems.Class;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import com.dnk.shairugems.BuildConfig;
import com.dnk.shairugems.R;
import com.dnk.shairugems.SearchResultActivity;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Volly.VolleyCallback;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DownloadImage {

    ArrayList<Uri> uri = new ArrayList<Uri>();
    String[] fourImage = {"/PR.jpg", "/AS.jpg", "/HT.jpg", "/HB.jpg"};
    String[] fourImagefile = {"_PR.jpg", "_AS.jpg", "_HT.jpg", "_HB.jpg"};

    public void download(final Context c, final ArrayList<BindData> list, final String si) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Const.showProgress(c);
            }

            @Override
            protected String doInBackground(Void... arg0) {
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getType().equalsIgnoreCase("false")) {
                        try {
                            URL url1 = new URL(list.get(j).getImageUrl());
                            URLConnection conn1 = url1.openConnection();
                            HttpURLConnection httpconn1 = (HttpURLConnection) conn1;
                            httpconn1.setAllowUserInteraction(false);
                            httpconn1.setConnectTimeout(10000);
                            httpconn1.setReadTimeout(35000);
                            httpconn1.setInstanceFollowRedirects(true);
                            httpconn1.setRequestMethod("GET");
                            httpconn1.connect();
                            int res1 = httpconn1.getResponseCode();
                            if (res1 == HttpURLConnection.HTTP_OK) {
                                InputStream is = httpconn1.getInputStream();
                                if (is != null) {
                                    Bitmap image = BitmapFactory.decodeStream(is);
                                    Bitmap img = image.copy(Bitmap.Config.ARGB_8888, true);
                                    Paint paint = new Paint();
                                    paint.setStyle(Paint.Style.FILL);
                                    paint.setColor(Color.WHITE);
                                    paint.setTypeface(ResourcesCompat.getFont(c, R.font.proxima_nova));
                                    paint.setTextAlign(Paint.Align.CENTER);
                                    paint.setTextSize(40);
                                    Rect rect = new Rect(150, 25, 0, 0);
                                    paint.getTextBounds(list.get(j).getId(), 0, list.get(j).getId().length(), rect);
                                    Canvas canvas = new Canvas(img);
                                    if (rect.width() >= (canvas.getWidth() - 4)) {
                                        paint.setTextSize(35);
                                    }
                                    canvas.drawText(list.get(j).getId(), 150, 75, paint);
                                    BitmapDrawable bmd = new BitmapDrawable(c.getResources(), img);
                                    Bitmap newimg = bmd.getBitmap();

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                                        } else {
                                            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Sunrise Diamonds");
                                            if (!directory.exists()) {
                                                if (directory.mkdirs()) {

                                                }
                                            }
                                            File file = new File(directory, list.get(j).getId() + "_PR.jpg");
                                            if (file.exists()) {
                                                file.delete();
                                            }
                                            FileOutputStream out = new FileOutputStream(file);
                                            newimg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                            out.flush();
                                            out.close();
                                            Uri uri1 = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".provider", file);
                                            uri.add(uri1);
                                        }
                                    } else {
                                        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Sunrise Diamonds");
                                        if (!directory.exists()) {
                                            if (directory.mkdirs()) {

                                            }
                                        }
                                        File file = new File(directory, list.get(j).getId() + "_PR.jpg");
                                        if (file.exists()) {
                                            file.delete();
                                        }
                                        FileOutputStream out = new FileOutputStream(file);
                                        newimg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                        out.flush();
                                        out.close();
                                        Uri uri1 = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".provider", file);
                                        uri.add(uri1);
                                    }
                                }
                            }
                        } catch (ConnectTimeoutException e) {
                            return "ConnectionTimeOut";
                        } catch (SocketTimeoutException e) {
                            return "RequestTimeOut";
                        } catch (Exception e) {
                            return "FAIL";
                        }
                    } else {
                        for (int i = 0; i < fourImage.length; i++) {
                            try {
                                URL url1 = new URL(Const.SiteUrl + list.get(j).getName() + fourImage[i]);
                                URLConnection conn1 = url1.openConnection();
                                HttpURLConnection httpconn1 = (HttpURLConnection) conn1;
                                httpconn1.setAllowUserInteraction(false);
                                httpconn1.setConnectTimeout(10000);
                                httpconn1.setReadTimeout(35000);
                                httpconn1.setInstanceFollowRedirects(true);
                                httpconn1.setRequestMethod("GET");
                                httpconn1.connect();
                                int res1 = httpconn1.getResponseCode();
                                if (res1 == HttpURLConnection.HTTP_OK) {
                                    InputStream is = httpconn1.getInputStream();
                                    if (is != null) {
                                        Bitmap image = BitmapFactory.decodeStream(is);
                                        Bitmap img = image.copy(Bitmap.Config.ARGB_8888, true);
                                        Paint paint = new Paint();
                                        paint.setStyle(Paint.Style.FILL);
                                        paint.setColor(Color.WHITE);
                                        paint.setTypeface(ResourcesCompat.getFont(c, R.font.proxima_nova));
                                        paint.setTextAlign(Paint.Align.CENTER);
                                        paint.setTextSize(40);
                                        Rect rect = new Rect(150, 25, 0, 0);
                                        paint.getTextBounds(list.get(j).getId(), 0, list.get(j).getId().length(), rect);
                                        Canvas canvas = new Canvas(img);
                                        if (rect.width() >= (canvas.getWidth() - 4)) {
                                            paint.setTextSize(35);
                                        }
                                        canvas.drawText(list.get(j).getId(), 150, 75, paint);
                                        BitmapDrawable bmd = new BitmapDrawable(c.getResources(), img);
                                        Bitmap newimg = bmd.getBitmap();

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                                            } else {
                                                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Sunrise Diamonds");
                                                if (!directory.exists()) {
                                                    if (directory.mkdirs()) {

                                                    }
                                                }
                                                File file = new File(directory, list.get(j).getId() + fourImagefile[i]);
                                                if (file.exists()) {
                                                    file.delete();
                                                }
                                                FileOutputStream out = new FileOutputStream(file);
                                                newimg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                                out.flush();
                                                out.close();
                                                uri.add(Uri.parse("file:///" + file.getAbsolutePath()));
                                            }
                                        } else {
                                            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Sunrise Diamonds");
                                            if (!directory.exists()) {
                                                if (directory.mkdirs()) {

                                                }
                                            }
                                            File file = new File(directory, list.get(j).getId() + "_PR.jpg");
                                            if (file.exists()) {
                                                file.delete();
                                            }
                                            FileOutputStream out = new FileOutputStream(file);
                                            newimg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                            out.flush();
                                            out.close();
                                            uri.add(Uri.parse("file:///" + file.getAbsolutePath()));
                                        }
                                    }
                                }
                            } catch (ConnectTimeoutException e) {
                                return "ConnectionTimeOut";
                            } catch (SocketTimeoutException e) {
                                return "RequestTimeOut";
                            } catch (Exception e) {
                                return "FAIL";
                            }
                        }
                    }
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                Const.dismissProgress();
                if (result.equals("RequestTimeOut")) {
                    Const.showErrorDialog(c, "Request Timeout. Please Try Again Later.");
                } else if (result.equalsIgnoreCase("ConnectionTimeOut")) {
                    Const.showErrorDialog(c, "Could Not Connect To Server. Please Try Again Later.");
                } else if (result.equalsIgnoreCase("FAIL")) {
                    Const.showErrorDialog(c, "Can't get image. Please Try Again Later.");
                } else {
                    String so = "";
                    so = so.concat(si);
                    if (uri.size() != 0) {
                        Intent i = new Intent(Intent.ACTION_SEND_MULTIPLE);
                        i.setType("image/*");
                        i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uri);
                        i.putExtra(Intent.EXTRA_TEXT, so);
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        builder.detectFileUriExposure();
                        try {
                            c.startActivity(Intent.createChooser(i, "Share via"));
                        } catch (Exception e) {
                            Log.e("errorImage", e.toString());
                        }
                    } else {
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).getImageUrl() != null && !list.get(j).getImageUrl().equalsIgnoreCase("")) {
//                                String si = "\nStone ID : " + list.get(j).getId() + "\n"
//                                        + "Image Link : " + list.get(j).getImageUrl() + "\n";
                                String si = "\nStone ID : " + list.get(j).getId() + "\n"
                                        + "Image Link : " + Const.ShareImageUrl + list.get(j).getId() + "\n";
                                so = so.concat(si);
                            }
                        }
                        if (!so.equals("")) {
                            final Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            i.putExtra(Intent.EXTRA_TEXT, so);
                            c.startActivity(Intent.createChooser(i, "Share via"));
                        } else {
                            Const.showErrorDialog(c, "Sorry no image(s) available for selected stone(s) to share");
                        }
                    }
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                try {
                    ActivityCompat.requestPermissions((Activity) c, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                task.execute();
            }
        } else {
            task.execute();
        }
    }

    public void download1(final Context c, final ArrayList<BindData> list) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Const.showProgress(c);
            }

            @Override
            protected String doInBackground(Void... arg0) {
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getType().equalsIgnoreCase("false")) {
                        try {
                            URL url1 = new URL(list.get(j).getImageUrl());
                            URLConnection conn1 = url1.openConnection();
                            HttpURLConnection httpconn1 = (HttpURLConnection) conn1;
                            httpconn1.setAllowUserInteraction(false);
                            httpconn1.setConnectTimeout(10000);
                            httpconn1.setReadTimeout(35000);
                            httpconn1.setInstanceFollowRedirects(true);
                            httpconn1.setRequestMethod("GET");
                            httpconn1.connect();
                            int res1 = httpconn1.getResponseCode();
                            if (res1 == HttpURLConnection.HTTP_OK) {
                                InputStream is = httpconn1.getInputStream();
                                if (is != null) {
                                    Bitmap image = BitmapFactory.decodeStream(is);
                                    Bitmap img = image.copy(Bitmap.Config.ARGB_8888, true);
                                    Paint paint = new Paint();
                                    paint.setStyle(Paint.Style.FILL);
                                    paint.setColor(Color.WHITE);
                                    paint.setTypeface(ResourcesCompat.getFont(c, R.font.proxima_nova));
                                    paint.setTextAlign(Paint.Align.CENTER);
                                    paint.setTextSize(40);
                                    Rect rect = new Rect(150, 25, 0, 0);
                                    paint.getTextBounds(list.get(j).getId(), 0, list.get(j).getId().length(), rect);
                                    Canvas canvas = new Canvas(img);
                                    if (rect.width() >= (canvas.getWidth() - 4)) {
                                        paint.setTextSize(35);
                                    }
                                    canvas.drawText(list.get(j).getId(), 150, 75, paint);
                                    BitmapDrawable bmd = new BitmapDrawable(c.getResources(), img);
                                    Bitmap newimg = bmd.getBitmap();

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                                        } else {
                                            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Sunrise Diamonds");
                                            if (!directory.exists()) {
                                                if (directory.mkdirs()) {

                                                }
                                            }
                                            File file = new File(directory, list.get(j).getId() + "_PR.jpg");
                                            if (file.exists()) {
                                                file.delete();
                                            }
                                            FileOutputStream out = new FileOutputStream(file);
                                            newimg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                            out.flush();
                                            out.close();
                                            Uri uri1 = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".provider", file);
                                            uri.add(uri1);
                                        }
                                    } else {
                                        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Sunrise Diamonds");
                                        if (!directory.exists()) {
                                            if (directory.mkdirs()) {

                                            }
                                        }
                                        File file = new File(directory, list.get(j).getId() + "_PR.jpg");
                                        if (file.exists()) {
                                            file.delete();
                                        }
                                        FileOutputStream out = new FileOutputStream(file);
                                        newimg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                        out.flush();
                                        out.close();
                                        Uri uri1 = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".provider", file);
                                        uri.add(uri1);
                                    }
                                }
                            }
                        } catch (ConnectTimeoutException e) {
                            return "ConnectionTimeOut";
                        } catch (SocketTimeoutException e) {
                            return "RequestTimeOut";
                        } catch (Exception e) {
                            return "FAIL";
                        }
                    } else {
                        for (int i = 0; i < fourImage.length; i++) {
                            try {
                                URL url1 = new URL(Const.SiteUrl + list.get(j).getName() + fourImage[i]);
                                URLConnection conn1 = url1.openConnection();
                                HttpURLConnection httpconn1 = (HttpURLConnection) conn1;
                                httpconn1.setAllowUserInteraction(false);
                                httpconn1.setConnectTimeout(10000);
                                httpconn1.setReadTimeout(35000);
                                httpconn1.setInstanceFollowRedirects(true);
                                httpconn1.setRequestMethod("GET");
                                httpconn1.connect();
                                int res1 = httpconn1.getResponseCode();
                                if (res1 == HttpURLConnection.HTTP_OK) {
                                    InputStream is = httpconn1.getInputStream();
                                    if (is != null) {
                                        Bitmap image = BitmapFactory.decodeStream(is);
                                        Bitmap img = image.copy(Bitmap.Config.ARGB_8888, true);
                                        Paint paint = new Paint();
                                        paint.setStyle(Paint.Style.FILL);
                                        paint.setColor(Color.WHITE);
                                        paint.setTypeface(ResourcesCompat.getFont(c, R.font.proxima_nova));
                                        paint.setTextAlign(Paint.Align.CENTER);
                                        paint.setTextSize(40);
                                        Rect rect = new Rect(150, 25, 0, 0);
                                        paint.getTextBounds(list.get(j).getId(), 0, list.get(j).getId().length(), rect);
                                        Canvas canvas = new Canvas(img);
                                        if (rect.width() >= (canvas.getWidth() - 4)) {
                                            paint.setTextSize(35);
                                        }
                                        canvas.drawText(list.get(j).getId(), 150, 75, paint);
                                        BitmapDrawable bmd = new BitmapDrawable(c.getResources(), img);
                                        Bitmap newimg = bmd.getBitmap();

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE)
                                                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                    != PackageManager.PERMISSION_GRANTED) {

                                            } else {
                                                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Sunrise Diamonds");
                                                if (!directory.exists()) {
                                                    if (directory.mkdirs()) {

                                                    }
                                                }
                                                File file = new File(directory, list.get(j).getId() + fourImagefile[i]);
                                                if (file.exists()) {
                                                    file.delete();
                                                }
                                                FileOutputStream out = new FileOutputStream(file);
                                                newimg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                                out.flush();
                                                out.close();
                                                try {
                                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(String.valueOf(url1)));
                                                    request.setDescription("download");
                                                    request.setTitle(list.get(j).getId() + fourImagefile[i]);

                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                                        request.allowScanningByMediaScanner();
                                                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                    }
                                                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, list.get(j).getId() + fourImagefile[i]);
                                                    DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
                                                    manager.enqueue(request);
                                                } catch (Exception ex) {
                                                }
                                                uri.add(Uri.parse("file:///" + file.getAbsolutePath()));
                                            }
                                        } else {
                                            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Sunrise Diamonds");
                                            if (!directory.exists()) {
                                                if (directory.mkdirs()) {

                                                }
                                            }
                                            File file = new File(directory, list.get(j).getId() + "_PR.jpg");
                                            if (file.exists()) {
                                                file.delete();
                                            }
                                            FileOutputStream out = new FileOutputStream(file);
                                            newimg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                            out.flush();
                                            out.close();
                                            try {
                                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(String.valueOf(url1)));
                                                request.setDescription("download");
                                                request.setTitle(list.get(j).getId() + fourImagefile[i]);

                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                                    request.allowScanningByMediaScanner();
                                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                }
                                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, list.get(j).getId() + fourImagefile[i]);
                                                DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
                                                manager.enqueue(request);
                                            } catch (Exception ex) {
                                            }
                                            uri.add(Uri.parse("file:///" + file.getAbsolutePath()));
                                        }
                                    }
                                }
                            } catch (ConnectTimeoutException e) {
                                return "ConnectionTimeOut";
                            } catch (SocketTimeoutException e) {
                                return "RequestTimeOut";
                            } catch (Exception e) {
                                return "FAIL";
                            }
                        }
                    }
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                Const.dismissProgress();
                if (result.equals("RequestTimeOut")) {
                    Const.showErrorDialog(c, "Request Timeout. Please Try Again Later.");
                } else if (result.equalsIgnoreCase("ConnectionTimeOut")) {
                    Const.showErrorDialog(c, "Could Not Connect To Server. Please Try Again Later.");
                } else if (result.equalsIgnoreCase("FAIL")) {
                    Const.showErrorDialog(c, "Can't get image. Please Try Again Later.");
                } else {
                    if (uri.size() != 0) {
                        Toast.makeText(c, "stones to download information successfully!", Toast.LENGTH_LONG).show();
                    } else {
//                        for (int j = 0; j < list.size(); j++) {
//                            downloadOversesImage(c,list.get(j).getId());
//                        }
                        Const.showErrorDialog(c, "Image is not available in this stone !");
                    }
//                    }
//                    if (uri.size() != 0) {
//                        Intent i = new Intent(Intent.ACTION_SEND_MULTIPLE);
//                        i.setType("image/*");
//                        i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uri);
//                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//                        StrictMode.setVmPolicy(builder.build());
//                        builder.detectFileUriExposure();
//                        try {
//                            c.startActivity(Intent.createChooser(i, "Share via"));
//                        } catch (Exception e) {
//                            Log.e("errorImage", e.toString());
//                        }
//                    }
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                try {
                    ActivityCompat.requestPermissions((Activity) c, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                task.execute();
            }
        } else {
            task.execute();
        }
    }

//    private void downloadOversesImage(final Context c, final String stoneid){
//        Const.showProgress(c);
//        final Map<String, String> map = new HashMap<>();
//        map.put("StoneID", Const.notNullString(stoneid, ""));
//        map.put("PageNo", "1");
//        map.put("DownloadMedia", "image");
//        Const.callPostApi(c, "Stock/DownloadStockMedia", map, new VolleyCallback() {
//            @Override
//            public void onSuccessResponse(String result1) {
//                Const.dismissProgress();
//                try {
//                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
//                    if (!result.startsWith("Image is not available")) {
//                        try {
//                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
//                            request.setDescription("download");
//                            request.setTitle(stoneid);
//
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                                request.allowScanningByMediaScanner();
//                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                            }
//                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, stoneid + ".zip");
//                            DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
//                            manager.enqueue(request);
//                            Toast.makeText(c, "stones to download information successfully!", Toast.LENGTH_LONG).show();
//                        } catch (Exception ex) {
//
//                        }
//                    } else {
//                        Const.showErrorDialog(c, "Image is not available in this stone !");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailerResponse(String error) {
//                Const.dismissProgress();
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        downloadOversesImage(c,stoneid);
//                    }
//                };
//                Const.showErrorApiDialog(c, runnable);
//            }
//        });
//    }
}