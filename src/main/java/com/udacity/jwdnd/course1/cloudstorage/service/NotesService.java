package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List<Note> getUserNotes(Integer userId) {
        return notesMapper.getUserNotes(userId);
    }

    public int saveNote(Note note, Integer userId) {
        if (note.getNoteId() == null) {
            note.setUserId(userId);
            return notesMapper.insert(note);
        } else {
            Note existedNote = notesMapper.getNote(note.getNoteId());
            if (existedNote != null && existedNote.getUserId().equals(userId)) {
                return notesMapper.update(note);
            } else {
                return -1;
            }
        }
    }

    public int deleteNote(Integer noteId, Integer userId) {
        Note existedNote = notesMapper.getNote(noteId);
        if (existedNote != null && existedNote.getUserId().equals(userId)) {
            return notesMapper.delete(noteId);
        } else {
            return -1;
        }
    }

}
