package yaoxinyuan.dao;

import com.sun.xml.internal.stream.events.NamedEvent;
import yaoxinyuan.model.Classes;
import yaoxinyuan.model.DictionaryTag;
import yaoxinyuan.model.Page;
import yaoxinyuan.model.Student;
import yaoxinyuan.util.DBUtil;
import yaoxinyuan.util.ThreadLocalHolder;
//第二部分
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDAO {
    public static List<Student> query(Page p) {
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Student> list=new ArrayList<>();
        try {
            //1.获取数据库连接
            c= DBUtil.getConnection();//工具类以及该封装好的方法
            //sql语句预编译
            StringBuilder sql= new StringBuilder("select s.id," +
                    "       s.student_name," +
                    "       s.student_no," +
                    "       s.id_card," +
                    "       s.student_email," +
                    "       s.classes_id," +
                    "       s.create_time," +
                    "       c.id cid," +
                    "       c.classes_name," +
                    "       c.classes_graduate_year," +
                    "       c.classes_major," +
                    "       c.classes_desc" +
                    "  from student s" +
                    "         join classes c on s.classes_id = c.id");
            if (p.getSearchText()!=null&&p.getSearchText().trim().length()>0){
                sql.append(" where s.student_name like ?");//模糊查询
            }
            if (p.getSortOrder()!=null&&p.getSortOrder().trim().length()>0){
                String s=p.getSortOrder();
                sql.append("  order by s.create_time  "+s);
                //不能用占位符操作，字符串替换的时候会带上单引号  也就是order  by  xxx  ‘asc’
                //但是这种l注方式存在sq入的风险
            }
            //1.1  获取查询总数量：使用以上sql可以复用，可以使用子查询
            StringBuilder countsql=new StringBuilder("select count(0) count from (");
            countsql.append(sql);
            countsql.append(")tmp");
            ps=c.prepareStatement(countsql.toString());
            if (p.getSearchText()!=null&&p.getSearchText().trim().length()>0){
                ps.setString(1,"%"+p.getSearchText()+"%");
            }
            rs=ps.executeQuery();
            while (rs.next()){
                 int count=rs.getInt("count");
                 //TODO
                ThreadLocalHolder.getTOTAL().set(count);//设置total变量到当前线程中的ThreadLocalMap数据结构中保存
            }
            //1.2  获取分页数据
             sql.append(" limit ?,?");

            //在实际应用中，如果查询一张表的所有字段，sql语句也不要写成*，两张表如果有一样的字段，比如上面的id   一定要用别名区别
            //2.创建操作命令对象
            ps=c.prepareStatement(sql.toString());
            int index =1;
            if (p.getSearchText() != null&&p.getSearchText().trim().length()>0){
                ps.setString(index++,"%"+p.getSearchText()+"%");
            }
            ps.setInt(index++,(p.getPageNumber()-1)*p.getPageSize());//设置索引
            ps.setInt(index++,p.getPageSize());
            //3.执行查询操作
            rs=ps.executeQuery();

            //4.处理查询结果集
            while (rs.next()){
              Student student=new Student();
              //设置属性，通过结果集获取来设置
                student.setId(rs.getInt("id"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentNo(rs.getString("student_no"));
                student.setIdCard(rs.getString("id_card"));
                student.setStudentEmail(rs.getString("student_email"));
                student.setClassesId(rs.getInt("classes_id"));
                student.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                Classes  classes = new Classes();
                student.setClasses(classes);
                classes.setId(rs.getInt("cid"));
                classes.setClassesName(rs.getString("classes_name"));
                classes.setClassesGraduateYear(rs.getString("classes_graduate_year"));
                classes.setClassesMajor(rs.getString("classes_major"));
                classes.setClassesDesc(rs.getString("classes_desc"));


                // classes.setCreateTime(new Date(rs.getTimestamp("cct").getTime()));
                list.add(student);
            }
            return list;
        } catch (Exception e) {
            throw  new RuntimeException("查询学生列表出错",e);//如果不把这个具体的异常信息传入参数，那么异常信息就会丢失，出现问题时不方便定位
        } finally {
            //5.释放资源
            DBUtil.close(c,ps,rs);//封装得释放资源的方法
        }
    }

    public static Student queryById(int id) {
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Student student=new Student();
        try {
            //1.获取数据库连接
            c= DBUtil.getConnection();//工具类以及该封装好的方法
            //sql语句预编译
            String sql="select s.id," +
                    "       s.student_name," +
                    "       s.student_no," +
                    "       s.id_card," +
                    "       s.student_email," +
                    "       s.classes_id," +
                    "       s.create_time," +
                    "       c.id cid," +
                    "       c.classes_name," +
                    "       c.classes_graduate_year," +
                    "       c.classes_major," +
                    "       c.classes_desc" +
                    "  from student s" +
                    "         join classes c on s.classes_id = c.id" +
                    "    where s.id=?";
            //在实际应用中，如果查询一张表的所有字段，sql语句也不要写成*，两张表如果有一样的字段，比如上面的id   一定要用别名区别
            //2.创建操作命令对象
            ps=c.prepareStatement(sql);
            ps.setInt(1,id);
            //3.执行查询操作
            rs=ps.executeQuery();
            //4.处理查询结果集
            while (rs.next()){

                //设置属性，通过结果集获取来设置
                student.setId(rs.getInt("id"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentNo(rs.getString("student_no"));
                student.setIdCard(rs.getString("id_card"));
                student.setStudentEmail(rs.getString("student_email"));
                student.setClassesId(rs.getInt("classes_id"));
                student.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                Classes  classes = new Classes();
                student.setClasses(classes);
                classes.setId(rs.getInt("cid"));
                classes.setClassesName(rs.getString("classes_name"));
                classes.setClassesGraduateYear(rs.getString("classes_graduate_year"));
                classes.setClassesMajor(rs.getString("classes_major"));
                classes.setClassesDesc(rs.getString("classes_desc"));

            }
            return student;
        } catch (Exception e) {
            throw  new RuntimeException("查询学生详情信息出错",e);//如果不把这个具体的异常信息传入参数，那么异常信息就会丢失，出现问题时不方便定位
        } finally {
            //5.释放资源
            DBUtil.close(c,ps,rs);//封装得释放资源的方法
        }
    }

    public static void insert(Student s) {//插入接口
        Connection c=null;
        PreparedStatement ps=null;
   //没有结果集
        try {
            //1.获取数据库连接
            c= DBUtil.getConnection();//工具类以及该封装好的方法
            //sql语句预编译
            String sql=" insert into  student(student_name,student_no,id_card,student_email,classes_id) values (?,?,?,?,?)";

            //2.创建操作命令对象
            ps=c.prepareStatement(sql);
            ps.setString(1,s.getStudentName());
            ps.setString(2,s.getStudentNo());
            ps.setString(3,s.getIdCard());
            ps.setString(4,s.getStudentEmail());
            ps.setInt(5,s.getClassesId());
            //3.执行sql语句
            int num=ps.executeUpdate();//返回值是成功插入了几行数据,如果插入不成功也就说不等于1就算失败，这个方法没有实现
        } catch (Exception e) {
            throw  new RuntimeException("插入学生信息出错",e);//如果不把这个具体的异常信息传入参数，那么异常信息就会丢失，出现问题时不方便定位
        } finally {
            //5.释放资源
            DBUtil.close(c,ps);//封装得释放资源的方法
        }

    }

    public static void updata(Student s) {
        Connection c=null;
        PreparedStatement ps=null;
        //没有结果集
        try {
            //1.获取数据库连接
            c= DBUtil.getConnection();//工具类以及该封装好的方法
            //sql语句预编译
            String sql="update  student set  student_name=?,student_no=?,id_card=?,student_email=?,classes_id=?  where id=?";
            //根据学生id进行修改
            //2.创建操作命令对象
            ps=c.prepareStatement(sql);
            ps.setString(1,s.getStudentName());
            ps.setString(2,s.getStudentNo());
            ps.setString(3,s.getIdCard());
            ps.setString(4,s.getStudentEmail());
            ps.setInt(5,s.getClassesId());
            ps.setInt(6,s.getId());
            //3.执行sql语句
            int num=ps.executeUpdate();//返回值是成功插入了几行数据,如果插入不成功也就说不等于1就算失败，这个方法没有实现
        } catch (Exception e) {
            throw  new RuntimeException("修改学生信息出错",e);//如果不把这个具体的异常信息传入参数，那么异常信息就会丢失，出现问题时不方便定位
        } finally {
            //5.释放资源
            DBUtil.close(c,ps);//封装得释放资源的方法
        }

    }

    public static void delete(String[] ids) {
        Connection c=null;
        PreparedStatement ps=null;
        //没有结果集
        try {
            //1.获取数据库连接
            c= DBUtil.getConnection();//工具类以及该封装好的方法
            //sql语句预编译
            StringBuilder sql=new StringBuilder("delete from student where  id in (");
            for (int i = 0; i <ids.length ; i++) {
                if (i!=0) {
                    sql.append(",");
                }
                sql.append("?");
            }
            sql.append(")");
            //根据学生id进行修改
            //2.创建操作命令对象
            ps=c.prepareStatement(sql.toString());
            for (int i = 0; i <ids.length ; i++) {
                ps.setInt(i+1,Integer.parseInt(ids[i]));//jdbc设置占位符从1开始
            }

            //3.执行sql语句
            int num=ps.executeUpdate();//返回值是成功插入了几行数据,如果插入不成功也就说不等于1就算失败，这个方法没有实现
        } catch (Exception e) {
            throw  new RuntimeException("删除学生信息出错",e);//如果不把这个具体的异常信息传入参数，那么异常信息就会丢失，出现问题时不方便定位
        } finally {
            //5.释放资源
            DBUtil.close(c,ps);//封装得释放资源的方法
        }
    }
}
