package yaoxinyuan.util;

public class ThreadLocalHolder {
    //线程本地变量保存的作用
    private  static   ThreadLocal<Integer> TOTAL=new ThreadLocal<>();

    public static ThreadLocal<Integer> getTOTAL() {
        return TOTAL;
    }


}
