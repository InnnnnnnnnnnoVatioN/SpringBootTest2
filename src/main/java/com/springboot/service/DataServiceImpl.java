package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.Handler.DataHandler;
import com.springboot.Handler.JsoupHandler;
import com.springboot.bean.DataBean;
import com.springboot.mapper.DataMapper;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl extends ServiceImpl<DataMapper,DataBean>
        implements DataService {
   /* @Override
    public List<DataBean> list() {
        List<DataBean> result = null;
        try {
            result = DataHandler.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<DataBean> listById(int id) {
        if (id == 2) {
            List<DataBean> result = null;
            try {
                result = JsoupHandler.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }return list();
    }*/
}
