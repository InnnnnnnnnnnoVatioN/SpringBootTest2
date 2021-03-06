package com.springboot.Handler;
import com.google.gson.Gson;
import com.springboot.bean.DataBean;
import com.springboot.service.DataService;
import com.springboot.util.HttpURLConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DataHandler {

    @Autowired
    private DataService dataService;

    @PostConstruct
    public void saveData(){
        try {
            List<DataBean> dataBeans = getData();
            //先将数据清空
            dataService.remove(null);
            //再将数据储存
            dataService.saveBatch(dataBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    public static void main(String[] args) throws Exception {
        getData();
    }


    //    @PostConstruct
    /*public void saveData() {
        List<DataBean> dataBeans = getData();
        // 先将数据清空  然后存储数据
        dataService.remove(null);
        dataService.saveBatch(dataBeans);

    }*/
    // 配置定时执行的注解  支持cron表达式
   @Scheduled(cron = "0 0/1 * * * ?")
    public void updataData(){
       //更新数据
       saveData();
       // git test  11111111111111111
   }
    /*public void updateData() {
        System.out.println("更新数据");

        // TODO 增加监听  提供用户订阅功能的   比如关注黑龙江省份 新增人数的变化

        //  邮件   登录  spring-security（AOP）  复杂的springboot项目分析
        saveData();
    }*/


    public static List<DataBean> getData()  throws  Exception {
//        Gson gson = new Gson();
////        Gson gson1 = new GsonBuilder().create();
//        Map map = gson.fromJson(testStr,Map.class);
//        System.out.println(map);


        // 读取文件中的文本内容   然后再转化为java对象
//        File file = new File("tmp.txt");

//        FileReader fr = new FileReader("tmp.txt");
//        char[] cBuf = new char[1024];
//        int cRead = 0;
//        StringBuilder builder = new StringBuilder();
//        while ((cRead = fr.read(cBuf)) > 0) {
//            builder.append(new String(cBuf, 0, cRead));
//        }
//
//        fr.close();

//        System.out.println(builder.toString());


        // 实时获取数据
        //实时接收到腾讯网站上json形式的String类型的数据
        String respJson = HttpURLConnectionUtil.doGet(urlStr);
        Gson gson = new Gson();
        //将String类型的json数据读取为一个Map
        Map map = gson.fromJson(respJson, Map.class);
        // 此时增加了一层处理  而且data对应的数据格式是string 可以看到key为data的值才是我们想要的数据
        String subStr = (String) map.get("data");
        //得到想要的数据 再将数据转化为 map
        Map subMap = gson.fromJson(subStr, Map.class);
//        System.out.println(map);
        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map) areaList.get(0);
        ArrayList childrenList = (ArrayList) dataMap.get("children");

        // 遍历然后转化
        List<DataBean> result = new ArrayList<>();

        for (int i = 0; i < childrenList.size(); i++) {
            Map tmp = (Map) childrenList.get(i);
            String name = (String) tmp.get("name");

            Map totalMap = (Map) tmp.get("total");
            double nowConfirm = (Double) totalMap.get("nowConfirm");
            double confirm = (Double) totalMap.get("confirm");
            double heal = (Double) totalMap.get("heal");
            double dead = (Double) totalMap.get("dead");

            DataBean dataBean = new DataBean(name, (int) nowConfirm, (int) confirm,
                    (int) heal, (int) dead);

            result.add(dataBean);
        }

//        System.out.println(result);

        return result;
    }
}
