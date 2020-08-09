package yaoxinyuan.dao;

import yaoxinyuan.model.Classes;
import yaoxinyuan.model.Student;
import yaoxinyuan.model.User;
import yaoxinyuan.util.DBUtil;

import java.security.spec.PSSParameterSpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class UserDAO {
    public static User query(User u) {
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        User query =null;
        try {
            //1.获取数据库连接
            c= DBUtil.getConnection();//工具类以及该封装好的方法
            //sql语句预编译
            String sql="select id, nickname, email, create_time from user where username=? and password=? ";

            //2.创建操作命令对象
            ps=c.prepareStatement(sql);
            ps.setString(1,u.getUsername());
            ps.setString(2,u.getPassword());
            //3.执行查询操作
            rs=ps.executeQuery();
            //4.处理查询结果集
            while (rs.next()){
                query=u;
                query.setId(rs.getInt("id"));
                query.setNickname(rs.getString("nickname"));
                query.setEmail(rs.getString("email"));
                query.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));

            }
            return query;
        } catch (Exception e) {
            throw  new RuntimeException("校验用户信息出错",e);//如果不把这个具体的异常信息传入参数，那么异常信息就会丢失，出现问题时不方便定位
        } finally {
            //5.释放资源
            DBUtil.close(c,ps,rs);//封装得释放资源的方法
        }
    }
}
