package com.nightvisionmedia.emergencyapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omar (GAZAMAN) Myers on 6/12/2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://nightvisionmedia.byethost18.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String fname, String lname, int age, String email, String password, String phone, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("fname",fname);
//        params.put("lname",lname);
//        params.put("age",age + "");
//        params.put("email",email);
//        params.put("password",password);
//        params.put("phone",phone);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
