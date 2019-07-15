package com.swapnotech.relaxation.meditation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.activity.MainActivity;
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.MyApp;
import com.swapnotech.relaxation.meditation.tools.Utils;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;

/**
 * Created by JEWEL on 9/2/2016.
 */
public class FragContact extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private View viewMain;
    private TextView tvFromTxt, tvToTxt, tvTo, tvSubTxt, tvDetailsTxt;
    private EditText edtFrom, edtSubject, edtDetails;
    private Button btnSend;


    public static FragContact getInstance() {
//        MainActivity.selectTabPos=2;
        return new FragContact();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.lay_send_mail, container, false);
        MyApp.getInstance().setupAnalytics("FragContact Screen");
        init();
        prepareDisplay();
        return viewMain;
    }

    private void init() {

        tvFromTxt = (TextView) viewMain.findViewById(R.id.tvFromTxt);
        tvToTxt = (TextView) viewMain.findViewById(R.id.tvToTxt);
        tvSubTxt = (TextView) viewMain.findViewById(R.id.tvSubTxt);
        tvDetailsTxt = (TextView) viewMain.findViewById(R.id.tvDetailsTxt);
        tvTo = (TextView) viewMain.findViewById(R.id.tvTo);

        edtFrom = (EditText) viewMain.findViewById(R.id.edtFrom);
        edtSubject = (EditText) viewMain.findViewById(R.id.edtSubject);
        edtDetails = (EditText) viewMain.findViewById(R.id.edtDetails);

        edtFrom.setOnFocusChangeListener(this);
        edtSubject.setOnFocusChangeListener(this);
        edtDetails.setOnFocusChangeListener(this);

        btnSend = (Button) viewMain.findViewById(R.id.btnSend);
    }

    private void prepareDisplay() {
        Utils.setFont(tvFromTxt, tvSubTxt, tvTo, tvToTxt, tvDetailsTxt, edtFrom, edtDetails, edtSubject, btnSend);

        tvFromTxt.setText(Global.EMAIL_FROM_TXT);
        tvToTxt.setText(Global.EMAIL_TO_TXT);
        tvSubTxt.setText(Global.EMAIL_SUBJECT_TXT);
        tvDetailsTxt.setText(Global.EMAIL_DETAILS_TXT);
        tvTo.setText(Global.EMAIL_TO);

        edtSubject.setText(Global.EMAIL_SUBJECT);
        edtFrom.setText(Utils.getPhoneGmailAcc());

        Utils.setRounded(edtDetails, Color.WHITE, Color.BLACK);
        Utils.setRounded(edtSubject, Color.WHITE, Color.BLACK);
        Utils.setRounded(edtFrom, Color.WHITE, Color.BLACK);

        btnSend.setText(Global.EMAIL_SEND_TXT);
        btnSend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btnSend:
////                send("mzijewel@gmail.com", "nayan5565@gmail.com", "test sub", "test body", Utils.getDateTime());
//                if (Utils.isInternetOn())
////                    send(Global.EMAIL_TO, edtFrom.getText().toString(), edtSubject.getText().toString(), edtDetails.getText().toString(), Utils.getDateTime());
//                else
//                    Utils.showDialog(getContext(), Global.MSG_SORRY, Global.MSG_NO_INTERNET);

        }

    }


//    private void send(String to, String from, String sub, String body, String time) {
////        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
//        Map<String, String> params = new HashMap<>();
//        params.put("form_no", "12");
//        params.put("email_to", to);
//        params.put("email_from", from);
//        params.put("email_subject", sub);
//        params.put("email_body", body);
//        params.put("email_date_time", time);
//        CustomRequest customRequest = new CustomRequest(Request.Method.POST, Global.BASE + Global.API_SEND_EMAIL, params, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    if (response.getString("message").equalsIgnoreCase("success"))
//                        Utils.showDialog(getContext(), "মতামত ", Global.MSG_SEND_MAIL);
//                    else
//                        Utils.showToast("Failed!!!");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                MyLog.e("TEST", "ERR:" + error.toString());
//                Utils.showDialog(getContext(), Global.MSG_SORRY, "");
//            }
//        });
//        requestQueue.add(customRequest);
//    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        MainActivity.getInstance().footerTab.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
    }
}
