package z.dotloopviewpager.other;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by z on 2016/6/14.
 */
public abstract class MyListBeansCallBack<T> extends StringCallback {
    private T mT;

    @Override
    public void onError(Call call, Exception e) {

    }

    @Override
    public void onResponse(String response) {
        LogUtil.d(response);
        parser(response);

    }

    public abstract void onResultInfo(int result_code, String result_message);

    public abstract T getT();

    public abstract void onResultData(List<T> tLists, String response);

    private void parser(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String result_message = object.getString("result_message");
            int result_code = object.getInt("result_code");
            LogUtil.d("回调中" + result_code + result_message);
            onResultInfo(result_code, result_message);
            if (result_code == 1) {
                JSONArray jsonArray = object.getJSONArray("data");
                List<T> list = new ArrayList<>();
                mT = getT();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String string = jsonArray.getString(i);
                    mT = (T) new Gson().fromJson(string, mT.getClass());
                    list.add(mT);
                }
                onResultData(list, response);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
