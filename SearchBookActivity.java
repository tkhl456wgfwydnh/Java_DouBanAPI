import java.util.ArrayList;

import javax.tools.JavaFileObject;

//import net.sf.json.*;

public class SearchBookActivity<JSONArray> {
		
		private ArrayList<BookInfo>bklist;
		private int json_length;
		private JavaFileObject jsonobj;
		
		public SearchBookActivity(){
			bklist=null;
			json_length=0;
			jsonobj=null;
		}
		
		public ArrayList<BookInfo> getBklist()
		{
			return bklist;
		}
		
		public void Json2java(String str)
		{
			try
			{
				
		     JavaFileObject jar=JavaFileObject.fromObject(str);
		     JSONArray array= jar.getJSONArray("books"); 
		     json_length=((ArrayList<BookInfo>) array).size();    
		     System.out.println(json_length);
		     
		     bklist=new  ArrayList<BookInfo>();
		    for(int i=0;i<json_length;i++)
		    {
		    	System.out.print(i+"¡¢");
		    	System.out.println(((ArrayList<BookInfo>) array).get(i));
		    	jsonobj =  ((Object) array).getJSONObject(i);
		    	
		    	BookInfo bk=new BookInfo();
		    	bk.setId(((Object) jsonobj).getString("id"));
		    	bk.setBookName(((Object) jsonobj).getString("bookName"));
		    	bk.setIsbn10(((Object) jsonobj).getString("isbn10"));
		    	//bk.setIsbn13(jsonobj.getString("isbn13"));
		    	bk.setImagePath(((Object) jsonobj).getString("image"));
		    	bk.setUrl(((Object) jsonobj).getString("url"));
		    	bk.setAuthor(((Object) jsonobj).getString("author"));
		    	bk.setBinding(((Object) jsonobj).getString("binding"));
		    	bk.setPages(((Object) jsonobj).getString("pages"));
		    	bk.setPrice(((Object) jsonobj).getString("price"));
		    	bk.setPubdate(((Object) jsonobj).getString("pubdate"));
		    	bk.setPublisher(((Object) jsonobj).getString("publisher"));
		    	bk.setSummary(((Object) jsonobj).getString("summary"));	
		    	bklist.add(bk); 	
		    }
		    
			}catch(Exception r)
			{
				r.printStackTrace();
			}
			System.out.println(bklist.size());
		}
		
