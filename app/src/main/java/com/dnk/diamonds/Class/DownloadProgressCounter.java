package com.dnk.shairugems.Class;

import android.app.Activity;
import android.app.DownloadManager;
import android.database.Cursor;
import android.widget.ProgressBar;

public class DownloadProgressCounter extends Thread{

    private final long downloadId;
    private final DownloadManager.Query query;
    private Cursor cursor;
    private int lastBytesDownloadedSoFar;
    private int totalBytes;
    protected ProgressBar mProgressBar;
    private Activity mActivity;
    DownloadManager manager;

    public DownloadProgressCounter(long downloadId) {
        this.downloadId = downloadId;
        this.query = new DownloadManager.Query();
        query.setFilterById(this.downloadId);
    }

    @Override
    public void run() {

        while (downloadId > 0) {
            try {
                Thread.sleep(300);

                cursor = manager.query(query);
                if (cursor.moveToFirst()) {

                    //get total bytes of the file
                    if (totalBytes <= 0) {
                        totalBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    }

                    final int bytesDownloadedSoFar = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

                    if (bytesDownloadedSoFar == totalBytes && totalBytes > 0) {
                        this.interrupt();
                    } else {
                        //update progress bar
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(mProgressBar.getProgress() + (bytesDownloadedSoFar - lastBytesDownloadedSoFar));
                                lastBytesDownloadedSoFar = bytesDownloadedSoFar;
                            }
                        });
                    }

                }
                cursor.close();
            } catch (Exception e) {
                return;
            }
        }
    }
}
