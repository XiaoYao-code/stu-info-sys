package yaoxinyuan.servlet;

import com.sun.xml.internal.stream.events.NamedEvent;
import yaoxinyuan.dao.DictionaryTagDAO;
import yaoxinyuan.model.DictionaryTag;
import yaoxinyuan.model.Response;
import yaoxinyuan.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
//第一部分
@WebServlet("/dict/tag/query")
//Servlet一定要以/开头，后边跟的是请求路径，和前端的功能接口路径相匹配。否则tomcat会报错
public class DictionaryTagServlet extends AbstractBaseServlet {


    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String key=req.getParameter("dictionaryKey");//获取对应的键
        List<DictionaryTag> tags= DictionaryTagDAO.query(key);//获取对应的值
        return tags;
    }
}
