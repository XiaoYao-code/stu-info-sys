package yaoxinyuan.servlet;

import yaoxinyuan.dao.StudentDAO;
import yaoxinyuan.model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student/delete")
public class StudentDeleteServlet extends AbstractBaseServlet {

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
      // req.getParameter();   只能返回一个String类型的字符串   实际上是多个删除 请求格式为ids=？&ids=？
        String[] ids=req.getParameterValues("ids");
        StudentDAO.delete(ids);
        return null;
    }
}
