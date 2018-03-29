package vr.xinjing.com.vrmc.utils;

/**
 * Created by raytine on 2017/3/4.
 */

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import me.itangqi.greendao.DaoMaster;
import me.itangqi.greendao.DaoSession;
import me.itangqi.greendao.Note;
import me.itangqi.greendao.NoteDao;
import vr.xinjing.com.vrmc.imp.NoteDaoService;

/**
 * Created by raytine on 2017/2/27.
 */

public class NoteService implements NoteDaoService {
    private DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static NoteService noteService;
    private NoteDao noteDao;
    public  NoteService(NoteDao noteDao){
        this.noteDao =noteDao;
    }
    public static NoteDao getNoteDao11(){
        return daoSession.getNoteDao();
    }
    public  static NoteService getNoteService(Context context){
        if (noteService == null){
            daoSession = NoteGreenDao.getDaoSession(context);
            noteService = new NoteService(daoSession.getNoteDao());
        }
        return  noteService;
    }
    @Override
    public Note insertNote(Note note) {

        return noteDao.loadByRowId(noteDao.insertOrReplace(note));
    }

    @Override
    public void deleteNote(long id) {
        noteDao.deleteByKey(id);
    }

    @Override
    public Note getNoteById(long id) {
        return noteDao.queryBuilder().where(NoteDao.Properties.Id.eq(id)).unique();
    }

//    @Override
//    public Note getNoteByText(String text) {
//        return noteDao.queryBuilder().where(NoteDao.Properties.Name.eq(text)).unique();
//    }

    //    @Override
//    public boolean isExist(String name) {
//        Note unique = noteDao.queryBuilder().where(NoteDao.Properties.Name.eq(name)).unique();
//
//        if (unique ==null){
//            return  false;
//        }else{
//            return true;
//        }
//    }
    @Override
    public boolean isExistByContentId(String id) {
        List<Note> unique = noteDao.queryBuilder().where(NoteDao.Properties.Contentid.eq(id)).list();

        if (unique ==null || unique.size() ==0){
            return  false;
        }else{
            return true;
        }
    }
    @Override
    public void updateData(String contentId ,Note n) {
        Note noteByText = getNameById(contentId);
        long id = noteByText.getId();
        n.setId(id);
        noteDao.update(n);
    }

    @Override
    public List<Note> getNameByIdList(String contentid) {
        List<Note> unique = noteDao.queryBuilder().where(NoteDao.Properties.Contentid.eq(contentid)).list();
        return unique;
    }

    @Override
    public Note getNameById(String contentid) {
        Note result = null;
        List<Note> unique = noteDao.queryBuilder().where(NoteDao.Properties.Contentid.eq(contentid)).list();
        int index = 0;
        if (unique.size()>1){
            for (int i = 0; i < unique.size()-1; i++) {
                result = unique.get(i);
                for (int j = i+1; j < unique.size(); j++) {
                    Note note = unique.get(j);
                    if (datexmilions(result.getDate())<datexmilions(note.getDate())){
                        index = j;
                        result = note;
                    }
                }
            }
            for (int i = 0; i < unique.size(); i++) {
                if (!(index == i)){
                    deleteNote(unique.get(i).getId());
                }
            }

        }else if (unique.size() == 1){
            result = unique.get(0);
        }
        if (result !=null){
            return  result;
        }
        return null;
    }

    public NoteDao getCarsDao() {
        return noteDao;
    }

    public List<Note> getAll() {
        return noteDao.loadAll();
    }

    public long datexmilions(String date){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTimeInMillis();
    }
}

