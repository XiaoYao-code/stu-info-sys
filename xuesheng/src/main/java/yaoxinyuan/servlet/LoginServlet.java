package yaoxinyuan.servlet;

import yaoxinyuan.dao.UserDAO;
import yaoxinyuan.model.User;
import yaoxinyuan.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/login")
public class LoginServlet  extends AbstractBaseServlet {
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User u= JSONUtil.read(req.getInputStream(),User.class);
        //数据库校验用户名密码
        User query= UserDAO.query(u);
        //通过查询出来的用户来
        if(query==null){
            throw new RuntimeException("用户名或者密码错误");
        }else {
            //通过用户名密码查询到用户时生产session信息并且保存到session里边去。
            HttpSession  session=req.getSession();//没有session时创建一个，设置进去
            session.setAttribute("user",query);
        }
        return null;
    }
}
