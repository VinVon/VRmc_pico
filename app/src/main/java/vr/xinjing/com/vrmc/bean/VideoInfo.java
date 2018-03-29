package vr.xinjing.com.vrmc.bean;



/**
 * Created by raytine on 2017/2/20.
 */

public class VideoInfo {
    private String name;
    private boolean state;
    private String path;


    private int id;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
