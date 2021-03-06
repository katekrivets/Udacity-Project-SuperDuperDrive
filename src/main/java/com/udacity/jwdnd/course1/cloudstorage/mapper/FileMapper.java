package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT filename, filesize, contenttype, fileid FROM FILES WHERE userid = #{userId}")
    List<File> getFiles(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userId, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void uploadFile(File file);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFile(Integer fileId);

    @Select("DELETE FROM FILES WHERE fileid = #{fileId}")
    void deleteFile(Integer fileId);

    @Select("SELECT filename, filesize, contenttype, fileid FROM FILES WHERE filename = #{fileName}")
    File getFileByName(String fileName);
}