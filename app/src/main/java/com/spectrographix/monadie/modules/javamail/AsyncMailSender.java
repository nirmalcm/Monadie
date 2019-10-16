package com.spectrographix.monadie.modules.javamail;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncMailSender
{
    private String MESSAGE;
    private String SUBJECT;
    private String SERVICEMAIL = "testing.spectrographix@gmail.com";
    private String SERVICEPASSWORD = "testingspectro";
    private String SENDER = SERVICEMAIL;
    //private String RECEIPIENTS = "nirmalcm.sg@gmail.com,info.spectrographix@gmail.com,shijith.nair@gmail.com,info@imcmaintenance.com";
    //private String RECEIPIENTS = "nirmalcm.sg@gmail.com,info.spectrographix@gmail.com,shijith.nair@gmail.com";
    //private String RECEIPIENTS = "nirmalcm.sg@gmail.com";

    private String RECEIPIENTS_TO= "adarshspectrographix@gmail.com";
    private String RECEPIENTS_BCC = "nirmal.cm@gmail.com";

    //private String RECEIPIENTS_TO= "info@imcmaintenance.com";
    //private String RECEPIENTS_BCC = "info.spectrographix@gmail.com";

    public AsyncMailSender(String MESSAGE,String SUBJECT)
    {
        this.MESSAGE=MESSAGE;
        this.SUBJECT=SUBJECT;
    }

    public void SendEmail()
    {
        new SendEmailMethod().execute();
    }

    private void sendEmail(String MESSAGE,String SUBJECT)
    {
        //String SUBJECT = "Report a problem- topic";

        try
        {
            GMailSender sender = new GMailSender(SERVICEMAIL,SERVICEPASSWORD);
            sender.sendMail(SUBJECT,MESSAGE,SENDER,RECEIPIENTS_TO,RECEPIENTS_BCC);
        }
        catch (Exception e)
        {
            Log.e("SendMail", e.getMessage(), e);
        }
    }

    class SendEmailMethod extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            sendEmail(MESSAGE,SUBJECT);

            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            /*Intent intent = new Intent(context, ConfirmationActivity.class);
            context.startActivity(intent);
            ((Activity)context).finish();*/
        }

        @Override
        protected void onPreExecute()
        {
        }
    }
}