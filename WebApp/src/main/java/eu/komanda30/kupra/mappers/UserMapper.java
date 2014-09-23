package eu.komanda30.kupra.mappers;

import eu.komanda30.kupra.model.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Insert("INSERT INTO users(login, name, hashed_password)"
            + "VALUES (#{login}, #{name}, #{hashed_password})")
    @Options(keyProperty="login", keyColumn="login", flushCache=true)
    public void insert(User user);

    @Select("SELECT login, name, hashed_password as hashedPassword"
            + "FROM users WHERE login = #{login}")
    public User getByLogin(@Param("login") String login);
}
