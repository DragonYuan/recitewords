package com.obito.recitewords.tools;
import com.obito.recitewords.bmobobject.Words;

/**
 * Created by obito on 17-8-27.
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseDataTool {
    public static Words parseJsonData(String data) throws JSONException {
        if (data == null)
            return null;
        Words words;
        //获取json对象
        JSONObject jsonObject = new JSONObject(data);
        //获取要翻译的字符串
        String query = jsonObject.getString("query");
        //获取翻译结果
        StringBuilder translation = new StringBuilder();
        JSONArray jsonArray = jsonObject.getJSONArray("translation");
        for (int i = 0; i < jsonArray.length(); i++) {
            translation.append(jsonArray.get(i) + "\n");
        }
        words = new Words();
        words.setParaphrase(translation.toString());
        words.setWords(query);
        return words;
    }
}
