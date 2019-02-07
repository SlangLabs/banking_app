package in.slanglabs.bankingapp.network;

import android.content.res.Resources;
import android.util.Log;

import in.slanglabs.bankingapp.R;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecentTransactionsPojo {

    private static final String TAG = RecentTransactionsPojo.class.getSimpleName();

    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("info")
    @Expose
    public String info;
    @SerializedName("debit")
    @Expose
    public float debit;
    @SerializedName("credit")
    @Expose
    public float credit;
    @SerializedName("amount")
    @Expose
    public float amount;

    public static List<RecentTransactionsPojo> initRecentList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.csvjson);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonProductsString = writer.toString();
        Gson gson = new Gson();
        Type orderListType = new TypeToken<ArrayList<RecentTransactionsPojo>>() {
        }.getType();
        return gson.fromJson(jsonProductsString, orderListType);
    }

    public static List<RecentTransactionsPojo> initCompleteList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.csvjson_view_statement);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonProductsString = writer.toString();
        Gson gson = new Gson();
        Type orderListType = new TypeToken<ArrayList<RecentTransactionsPojo>>() {
        }.getType();
        return gson.fromJson(jsonProductsString, orderListType);
    }

}