package com.springboot.bean;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
//@Data注解：自动添加 toString hashCode equals setter getter
@Data
@NoArgsConstructor
//便于mapper找到具体要查询的数据库表格名称
@TableName("illness")
public class DataBean implements Serializable {
    private String area;
    private int nowConfirm;
    private int confirm;
    private int heal;
    private int dead;
}
