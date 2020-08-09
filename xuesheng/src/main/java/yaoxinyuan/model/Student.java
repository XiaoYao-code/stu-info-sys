package yaoxinyuan.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*** 学生表*/
@Getter
@Setter
@ToString
public class Student {

    private Integer id;

    /*** 姓名*/
    private String studentName;

    /*** 学号*/
    private String studentNo;

    /*** 身份证号*/
    private String idCard;

    /*** 邮箱*/
    private String studentEmail;

    /*** 班级id*/
    private Integer classesId;

    /*** 创建时间*/
    private Date createTime;

    //班级属性

    private  Classes classes;//和前端名字对应
}