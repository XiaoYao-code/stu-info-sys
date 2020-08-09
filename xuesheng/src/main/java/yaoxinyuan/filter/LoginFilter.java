package yaoxinyuan.filter;

import yaoxinyuan.model.Response;
import yaoxinyuan.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 配置过滤器，只要请求路径匹配到过滤器路径，都会先执行过滤器的doFilter方法，
 * 至于是否往后执行，则按照我们的意愿是否调用filterChain.doFilter方法
 */

@WebFilter("/*")//代表模糊匹配，所有路径都会匹配到
public class LoginFilter  implements Filter {   //继承接口  重写方法

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //初始化生命周期方法
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
     //过滤filter的方法实质是客户端请求通过doFilter过滤器，然后才匹配servlet资源，响应也是也通过doFilter过滤器再返回客户端

        //后端接口：只校验除登录接口外的其他接口，没有登录时不允许访问
        //前端资源：只校验/public/page/main.html首页，其他都放行，否则出现问题

        HttpServletRequest req =(HttpServletRequest) servletRequest;
        HttpServletResponse res=(HttpServletResponse) servletResponse;

        String url=req.getServletPath();

            HttpSession session = req.getSession(false);//没有session的时候返回一个null
            if (session == null) {
                //首页重定向到登录页面，如果后端接口，返回错误的json数据
               // req.setCharacterEncoding("UTF-8");
               // res.setCharacterEncoding("UTF-8");
                //首页重定向到登陆页面
                if ("/public/page/main.html".equals(url)) {
                    res.setContentType("test/html; charset=UTF-8");
                    String schema=req.getScheme();//http
                    String  host=req.getServerName();//服务器ip
                    int port=req.getServerPort();//端口号
                    String ctx=req.getContextPath();//项目部署路径，应用上下文sis
                //   res.sendRedirect("public/index.html");  //会出问题？？？
                    String basePath=schema+"://"+host+":"+port+ctx;
                    res.sendRedirect(basePath+"/public/index.html");
                    return;
                } else if ((!(url.startsWith("/public/")) && !(url.startsWith("/static/")) && !("/user/login".equals(url)))){
                    //请求后端非登录接口，未登录的请求返回401状态码
                    res.setContentType("application/json");
                    PrintWriter pw=res.getWriter();
                    Response r=new Response();//new 一个我们自己写的Response类错误信息
                    r.setCode("ERR401");
                    r.setMessage("不允许访问");
                    res.setStatus(401);
                    pw.println(JSONUtil.write(r));
                    pw.flush();
                    return;
                }


            }

        filterChain.doFilter(servletRequest,servletResponse);
        }



    @Override
    public void destroy() {
    //销毁生命周期方法
    }
}
