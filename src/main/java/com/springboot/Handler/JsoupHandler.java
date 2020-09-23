package com.springboot.Handler;

import com.google.gson.Gson;
import com.springboot.bean.DataBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;

public class JsoupHandler{

    public static String htmlStr = "<html><head></head><body>"+"<p>hello html</p></body></html>";
    public static String urlStr = "https://ncov.dxy.cn/ncovh5/view/pneumonia? +" +
            "scene=2&from=singlemessage&isappinstalled=0";

    public static void main(String[] args) {

    }
    public static ArrayList<DataBean> getData () {
       /* Document document = Jsoup.parse(htmlStr);
       // System.out.println(document);
        Elements elements = document.getElementsByTag("body");
        //System.out.println(elements);
*/
        ArrayList<DataBean> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(urlStr).get();
            //找到 script标签下的内容
            Elements scripts =  doc.select("script");
            //找到具体的srcipt标签下 id 为 " "的内容
            Element oneScript =doc.getElementById("getAreaStat");
            //拿到oneScript的 data数据。
            String data =  oneScript.data();
            // 字符串截取出json格式的数据
            //截取[ ] 里的 数据
            String subData = data.substring(data.indexOf("["),data.lastIndexOf("]")+1);
            Gson gson = new Gson();
            ArrayList list = gson.fromJson(subData,ArrayList.class);
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map)list.get(i);
                String name = (String)map.get("provinceName");
                double nowConfirm =(Double)map.get("currentConfirmedCount");
                double confirm = (Double)map.get("confirmedCount");
                double heal =(Double)map.get("curedCount");
                double dead = (Double)map.get("deadCount");
                DataBean dataBean =new DataBean(name,(int)nowConfirm,(int)confirm,(int)heal,(int)dead);
                result.add(dataBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
