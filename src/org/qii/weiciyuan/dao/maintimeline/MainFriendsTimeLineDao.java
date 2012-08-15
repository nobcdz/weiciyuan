package org.qii.weiciyuan.dao.maintimeline;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.qii.weiciyuan.bean.MessageListBean;
import org.qii.weiciyuan.dao.URLHelper;
import org.qii.weiciyuan.support.error.WeiboException;
import org.qii.weiciyuan.support.http.HttpMethod;
import org.qii.weiciyuan.support.http.HttpUtility;
import org.qii.weiciyuan.support.utils.ActivityUtils;
import org.qii.weiciyuan.support.utils.AppConfig;
import org.qii.weiciyuan.support.utils.AppLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: qii
 * Date: 12-7-28
 */
public class MainFriendsTimeLineDao {

    private String getMsgListJson() {
        String url = URLHelper.getFriendsTimeLine();

        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        map.put("since_id", since_id);
        map.put("max_id", max_id);
        map.put("count", count);
        map.put("page", page);
        map.put("base_app", base_app);
        map.put("feature", feature);
        map.put("trim_user", trim_user);

        String jsonData = null;
        try {
            jsonData = HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, map);
        } catch (WeiboException e) {
            e.printStackTrace();
        }

        return jsonData;
    }

    public MessageListBean getGSONMsgList() {

        String json = getMsgListJson();
        Gson gson = new Gson();

        MessageListBean value = null;
        try {
            value = gson.fromJson(json, MessageListBean.class);
        } catch (JsonSyntaxException e) {
            ActivityUtils.showTips("发生错误，请重刷");
            AppLogger.e(e.getMessage());
        }

        return value;
    }


    private String access_token;
    private String since_id;
    private String max_id;
    private String count;
    private String page;
    private String base_app;
    private String feature;
    private String trim_user;

    public MainFriendsTimeLineDao(String access_token) {
        if (TextUtils.isEmpty(access_token))
            throw new IllegalArgumentException();
        this.access_token = access_token;
        this.count = String.valueOf(AppConfig.DEFAULT_MSG_NUMBERS);
    }

    public MainFriendsTimeLineDao setSince_id(String since_id) {
        this.since_id = since_id;
        return this;
    }

    public MainFriendsTimeLineDao setMax_id(String max_id) {
        this.max_id = max_id;
        return this;
    }

    public MainFriendsTimeLineDao setCount(String count) {
        this.count = count;
        return this;
    }

    public MainFriendsTimeLineDao setPage(String page) {
        this.page = page;
        return this;
    }

    public MainFriendsTimeLineDao setBase_app(String base_app) {
        this.base_app = base_app;
        return this;
    }

    public MainFriendsTimeLineDao setFeature(String feature) {
        this.feature = feature;
        return this;
    }

    public MainFriendsTimeLineDao setTrim_user(String trim_user) {
        this.trim_user = trim_user;
        return this;
    }


}