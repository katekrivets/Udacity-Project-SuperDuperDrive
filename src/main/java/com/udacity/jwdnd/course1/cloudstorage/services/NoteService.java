package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotesByUser(Integer userId) {
        return noteMapper.getNotes(userId);
    }

    public void saveNote(Note note) {
        noteMapper.saveNote(note);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public Note getNoteById(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public void deleteNoteById(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
