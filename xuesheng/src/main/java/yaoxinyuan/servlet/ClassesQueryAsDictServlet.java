package yaoxinyuan.servlet;

import yaoxinyuan.dao.classesDAO;
import yaoxinyuan.model.Classes;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//作为数据字典查询，而不是查询班级的列表。网页还有一个班级管理的接口
@WebServlet("/classes/queryAsDict")
public class ClassesQueryAsDictServlet extends  AbstractBaseServlet{

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Classes> list= classesDAO.queryAsDict();
        return list;
    }
}
