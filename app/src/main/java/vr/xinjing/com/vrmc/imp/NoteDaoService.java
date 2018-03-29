package vr.xinjing.com.vrmc.imp;

import java.util.List;

import me.itangqi.greendao.Note;

/**
 * Created by raytine on 2017/3/4.
 */

public interface NoteDaoService {
    public Note insertNote(Note note);
    public void deleteNote(long id);
    public Note getNoteById(long id);
    //    public Note getNoteByText(String text);
//    public boolean isExist(String name);
    public boolean isExistByContentId(String id);
    public  void updateData(String name ,Note n);
    public Note getNameById(String contentid);
    public List<Note> getNameByIdList(String contentid);
}
