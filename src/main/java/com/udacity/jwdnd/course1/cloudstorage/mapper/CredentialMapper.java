package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{userName},#{key}, #{password} ,#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void saveCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{userName}, key=#{key}, password=#{password} WHERE credentialid=#{credentialId}")
    void updateCredential(Credential credential);

    @Select("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(Integer credentialId);
}
