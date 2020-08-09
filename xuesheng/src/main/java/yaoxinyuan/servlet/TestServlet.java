package yaoxinyuan.servlet;

import yaoxinyuan.model.Response;
import yaoxinyuan.util.JSONUtil;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/test")
//模拟servlet返回给前端，通过统一返回得格式包装一下，看数据格式是否相同
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter pw=resp.getWriter();
        Response response=new Response();
        response.setCode("COK200");
        response.setMessage("操作成功");
        List<String> list=new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        response.setData(list);
        pw.println(JSONUtil.write(response));
        pw.flush();
    }
}
