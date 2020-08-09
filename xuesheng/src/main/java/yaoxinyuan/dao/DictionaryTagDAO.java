package yaoxinyuan.dao;

import yaoxinyuan.model.DictionaryTag;
import yaoxinyuan.util.DBUtil;
//第一部分
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DictionaryTagDAO {
    //操作数据JDBC的步骤：
    //1.创建数据库连接Connection。
    //2.创建操作命令对象Statement
    //3.执行sql
    //4。如果是查询操作处理结果集
    //5.释放资源
    public static List<DictionaryTag> query(String key) {
        Connection c=null;
        PreparedStatement  ps=null;
        ResultSet rs=null;
        List<DictionaryTag> list=new ArrayList<>();
        try {
            //1.获取数据库连接
            c= DBUtil.getConnection();//工具类以及该封装好的方法
            //sql语句预编译
            String sql="select concat(d.dictionary_key, dt.dictionary_tag_key) dictionary_tag_key," +//concat是sql中字符串拼接关键字，不能用简单的加号
                    "       dt.dictionary_tag_value" +
                    "   from dictionary d" +    //重命名可以使查询语句更加简单明了
                    "         join dictionary_tag dt on d.id = dt.dictionary_id" +//格式化代码时可能会产生错误，每条语句应该有空格
                    "   where d.dictionary_key = ?";//设置占位符
            //2.创建操作命令对象
            ps=c.prepareStatement(sql);
            ps.setString(1,key);
            //3.执行查询操作
            rs=ps.executeQuery();
            //4.处理查询结果集
            while (rs.next()){
                DictionaryTag tag=new DictionaryTag();
                //设置属性通过结果集获取，并添加到list中
                tag.setDictionaryTagKey(rs.getString("dictionary_tag_key"));
                tag.setDictionaryTagValue(rs.getString("dictionary_tag_value"));
                list.add(tag);
            }
            return list;
        } catch (Exception e) {
            throw  new RuntimeException("查询数据字典标签出错",e);//如果不把这个具体的异常信息传入参数，那么异常信息就会丢失，出现问题时不方便定位
        } finally {
            //5.释放资源
            DBUtil.close(c,ps,rs);//封装得释放资源的方法
        }
    }
}
