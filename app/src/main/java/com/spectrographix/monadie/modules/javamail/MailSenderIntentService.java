package com.spectrographix.monadie.modules.javamail;

import android.app.IntentService;
import android.content.Intent;

import static android.content.ContentValues.TAG;

/**
 * Created by user name on 12/1/2017.
 */

public class MailSenderIntentService extends IntentService
{
    public MailSenderIntentService()
    {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent)
    {
    }
}
