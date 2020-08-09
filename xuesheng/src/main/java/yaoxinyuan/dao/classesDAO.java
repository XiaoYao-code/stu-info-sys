package yaoxinyuan.dao;

import yaoxinyuan.model.Classes;
import yaoxinyuan.model.DictionaryTag;
import yaoxinyuan.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
//第三部分
public class classesDAO {
    public static List<Classes> queryAsDict() {
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Classes> list=new ArrayList<>();
        try {
            //1.获取数据库连接
            c= DBUtil.getConnection();//工具类以及该封装好的方法
            //sql语句预编译
            String sql="select id, classes_name, classes_graduate_year, classes_major from classes";//设置占位符
            //2.创建操作命令对象
            ps=c.prepareStatement(sql);

            //3.执行查询操作
            rs=ps.executeQuery();
            //4.处理查询结果集
            while (rs.next()){
              Classes classes=new Classes();
                //设置属性通过结果集获取，并添加到list中
                classes.setDictionaryTagKey(String.valueOf(rs.getInt("id")));//int转化为string
                classes.setDictionaryTagValue(rs.getString("classes_name"));
                classes.setClassesGraduateYear(rs.getString("classes_graduate_year"));
                classes.setClassesMajor(rs.getString("classes_major"));
                list.add(classes);
            }
            return list;
        } catch (Exception e) {
            throw  new RuntimeException("查询班级数据字典出错",e);//如果不把这个具体的异常信息传入参数，那么异常信息就会丢失，出现问题时不方便定位
        } finally {
            //5.释放资源
            DBUtil.close(c,ps,rs);//封装得释放资源的方法
        }
    }
}
