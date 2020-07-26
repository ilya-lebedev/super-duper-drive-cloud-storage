package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT noteid, notetitle, notedescription FROM notes WHERE userid = #{userId}")
    List<Note> getUserNotes(Integer userId);

    @Select("SELECT noteid, notetitle, notedescription, userid FROM notes WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);

    @Insert("INSERT INTO notes (notetitle, notedescription, userid) " +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Note note);

    @Update("UPDATE notes SET notetitle = @{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    int update(Note note);

    @Delete("DELETE FROM notes WHERE noteid = @{noteId}")
    int delete(Integer noteId);

}
