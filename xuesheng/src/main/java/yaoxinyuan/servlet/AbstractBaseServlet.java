package yaoxinyuan.servlet;

import yaoxinyuan.dao.DictionaryTagDAO;
import yaoxinyuan.model.DictionaryTag;
import yaoxinyuan.model.Response;
import yaoxinyuan.util.JSONUtil;
import yaoxinyuan.util.ThreadLocalHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
//写成一个模板方法：参照HttpServlet的service方法和doXXX（）；service就是一个模板方法
//模板方法是提供一种统一的处理逻辑，在不同条件调用不同的方法（子类重写的方法)
public  abstract   class AbstractBaseServlet   extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter pw=resp.getWriter();
        Response r=new Response();

        //对数据库JDBC的操作一般不要和Servlet混在一起。重新写了一个包dao，专门写JDBC
        //HttpServletRequest对象.getParameter()表示接收参数，这里的参数为页面提交的参数。
        // 一般包括：
        // 1.表单提交的参数，这里的表单是指默认的表单提交方式，表示的是Content-Type字段x-www-form-urlencoded
        //get在url中，post在body请求体中，格式是k1=v1&k2=v2；
        //如果不采用默认方法，比如手写前端的ajax请求，请求格式为application/json；请求体，转为字符串
        // 2.URL重写（就是***?id = 1)传的参数等，
        // 因此这个没有设置参数的方法(没有SetParameter),而接收参数的返回是String.
        //HttpServlet对象。getInputStream();通过输入流获取？请求体都可以获取到，但是需要解析（依赖代码实现）

        try {///这段代码内部可能会出现异常，虽然在DictionaryTagDAO中已经处理，但是现在仍然要处理
            Object o=process(req,resp);
            r.setSuccess(true);//页面在设计的时候，是根据如果这和success字段是true的时候才进行业务数据的处理。
            r.setCode("COK200");
            r.setMessage("操作成功");
            r.setTotal(ThreadLocalHolder.getTOTAL().get());//不管是否分页操作，都获取当前线程中的Total字段
            r.setData(o);
        } catch (Exception e) {
            r.setCode("ERR500");
            r.setMessage(e.getMessage());
            StringWriter sw = new StringWriter();//通过输出流获取出现异常的堆栈信息
            PrintWriter writer = new PrintWriter(sw);
            e.printStackTrace(writer);//异常打印堆栈信息
            String stackTrace =sw.toString();
            System.err.println(stackTrace);//打印到后台的控制台
            r.setStackTrace(stackTrace);//把stackTrace作为异常信息再返回到前端
        }finally {
            ThreadLocalHolder.getTOTAL().remove();//在线程结束前，一定要记得删除变量，否则存在内存泄露问题
        }

        pw.println(JSONUtil.write(r));
        pw.flush();
    }

    protected abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;

}
