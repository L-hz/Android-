package notepad.itcast.cn.notepad.bean;

public class NotepadBean {
    private String id;
    private String notepadContent;
    private String notepadtime; 
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getNotepadContent(){
        return notepadContent;
    }
    public void setNotepadContent(String notepadContent){
        this.notepadContent=notepadContent;
    }
    public String getNotepadtime(){
        return notepadtime;
    }
    public void setNotepadtime(String notepadtime){
        this.notepadtime=notepadtime;
    }
}
