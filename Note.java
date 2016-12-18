package com.poonammishra.notebook;

/**
 * Created by Poonam Mishra on 07-06-2016.
 */

//this is the note class i created. Whenever you see this code, just kill yourself for thinking that java is an easier language
    // than c++. It's much more complex. Stop being a slut and go prep. Fucking illiterate
public class Note {
    private String title, message;
    private long noteId, dateCreatedMilli;
    private Category category;
    public enum Category{PERSONAL, TECHNICAL, QUOTE, FINANCE}

public Note(String title, String message, Category category)
{
    this.noteId=0;
    this.dateCreatedMilli=0;
    this.title=title;
    this.message=message;
    this.category=category;
}
public Note(String title, String message, Category category,long noteId, long dateCreatedMilli)
{
    this.noteId=noteId;
    this.title=title;
    this.message=message;
    this.category=category;
    this.dateCreatedMilli=dateCreatedMilli;

}
    public String getTitle()
    {return title;}
    public String getMessage()
    {return  message;}
    public Category getCategory()
    {return category;}
    public long getNoteId()
    {return  noteId;}
    public long getDateCreatedMilli()
    {return  dateCreatedMilli;}
    public String toString()
    { return "ID:" + noteId + "Title" + title + "Message" + message + "IconID:" + category.name() + "Date: " + dateCreatedMilli;}

    public int getAssociatedDrawable()
    { return categoryToDrawable(category);}

   public static int categoryToDrawable(Category noteCategory)
   {
       switch (noteCategory){
           case PERSONAL: return R.drawable.p;
           case TECHNICAL: return R.drawable.t;
           case QUOTE: return R.drawable.q;
           case FINANCE: return R.drawable.f;
       }
       return  R.drawable.p;
   }


}