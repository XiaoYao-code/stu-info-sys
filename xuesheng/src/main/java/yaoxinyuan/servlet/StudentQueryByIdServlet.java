package yaoxinyuan.servlet;

import yaoxinyuan.dao.StudentDAO;
import yaoxinyuan.model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student/queryById")
public class StudentQueryByIdServlet  extends AbstractBaseServlet {
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
       String id=req.getParameter("id");
        Student  s= StudentDAO.queryById(Integer.parseInt(id));//string 类型的id强转为int
        return  s;
    }
}
