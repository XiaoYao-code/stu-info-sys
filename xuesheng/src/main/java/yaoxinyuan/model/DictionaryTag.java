package yaoxinyuan.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class DictionaryTag {
    //这种类对应得就是每个数据库的表的一一映射关系，类中的字段是表的列
    private  Integer id;//为什么要使用默认值？
    //因为new一个DictionaryTag对象得时候，使用基本数据类型可能会出现错误
    private String dictionaryTagKey;
    private String dictionaryTagValue;
    private  Integer dictionaryId;
    private String dictionaryTagDecs;//备注信息
    //一般日期类型，为了我们的方便，我们都是用java.util.Data来和表中的创建时间产生映射关系，
    // mysql中默认的映射关系是java.sql.xxx
    private Date createTime;


}
