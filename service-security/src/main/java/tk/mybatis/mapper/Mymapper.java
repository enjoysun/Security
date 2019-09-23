package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/*
 * @Time    : 2019/9/23 5:10 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Mymapper.java
 * @Software: IntelliJ IDEA
 */
public interface Mymapper<T> extends Mapper<T>, MySqlMapper<T> {
}
