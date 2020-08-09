package yaoxinyuan.servlet;

import yaoxinyuan.dao.StudentDAO;
import yaoxinyuan.model.Student;
import yaoxinyuan.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//新增学生  第三部分
@WebServlet("/student/add")
public class StudentAddServlet extends AbstractBaseServlet {
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Student s= JSONUtil.read(req.getInputStream(),Student.class);
        //通过json工具类  把htp请求体中的json字符串转化为学生对象
        StudentDAO.insert(s);
        return null;
    }
}
