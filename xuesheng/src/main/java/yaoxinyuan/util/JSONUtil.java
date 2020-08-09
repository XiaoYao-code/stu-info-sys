package yaoxinyuan.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class JSONUtil {
   //jackson框架提供处理json类
    private  static final ObjectMapper M=new ObjectMapper();
    static {
        //设置序列化和反序列化的日期格式
        M.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    //1.反序列化json字符串为java对象
    //1.1.因为不能通过http.HttpServletRequest对象.getParameter();获取我们url和请求体，
    // k1=v1&k2=v2格式的数据。可以通过key获取value。不能使用这种格式是因为json是{"k1":"v1"}的格式。
    //1.2.getInputStream();获取请求体，作为输入流，不知道流里是什么格式。自己编写代码
    public static <T> T read(InputStream is,Class<T> clazz){
        try {
            return M.readValue(is,clazz);
        } catch (IOException e) {
            throw new RuntimeException("JSON反序列化失败,传入的数据格式和class类型不匹配",e);
        }
    }
    //2.序列化java对象为json字符串
    public static String write(Object o){
        try {
            return M.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON序列化失败",e);
        }
    }
}
