package yaoxinyuan.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/stu_info?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
   //编码采用Unicode，编码格式采用utf-8,用ssl安全协议连接，服务器的时区
    private static final String USERNAME = "root";
    private static final String PASSWORD = "yao199787";
    private static volatile DataSource DS;

    private DBUtil() {
    }//私有的构造函数

    private static DataSource getDatasource() {
        if (DS == null) {
            synchronized (DBUtil.class) {
                if (DS == null) {
                    DS = new MysqlDataSource();
                    ((MysqlDataSource) DS).setURL(URL);
                    ((MysqlDataSource) DS).setUser(USERNAME);
                    ((MysqlDataSource) DS).setPassword(PASSWORD);
                }
            }
        }
        return DS;
    }

    public static Connection getConnection() {
        try {
            return getDatasource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }
    public static void close(Connection c, Statement s){
        //更新操作调用的是两个参数的构造方法
        close(c,s,null);
    }
    public static void close(Connection c, Statement s, ResultSet r) {
        //查询操作调用的是三个参数的方法
        try {
            if (c != null) {
                c.close();
            }
            if (s != null) {
                s.close();
            }
            if (r != null) {
                r.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("释放数据库资源失败", e);
        }
    }
}


