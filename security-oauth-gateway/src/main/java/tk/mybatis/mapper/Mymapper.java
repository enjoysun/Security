package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/*
 * @Time    : 2019/10/14 11:22 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Mymapper.java
 * @Software: IntelliJ IDEA
 */
public interface Mymapper<T> extends Mapper<T>, MySqlMapper<T> {
}
