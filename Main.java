import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.html.parser.Entity;
import org.apache.http.*;

public class Main<DefaultHttpClient, HttpResponse, HttpGet> extends JFrame implements ActionListener,MouseListener{
	//定义界面
	JLabel jlb=null;
	JPanel jp_down=null;
	JTextArea jta =null;
	
	JTextField jtf=null;
	
	JButton jb=null;
	JPanel jp=null;
	
	String temp="";

	JScrollPane jpe=null;
	String result=null;
	ArrayList<BookInfo>dabalist=null;
    
	public static void main(String[] args){
		// TODO Auto-generated method stub
             Main m=new Main();
	}
	
	
	 Main(){
		
		jlb=new  JLabel(new ImageIcon("Image/douban.png"));
		jp_down=new JPanel(); //定义显示界面
		jta=new JTextArea("",20,7);
		jta.setEditable(false);//只可显示，不可以编辑。
		jpe=new JScrollPane(jta);//设置滑轮，方便显示多条信息
		jtf=new JTextField("书名、作者、ISBN");//设置输入框和默认值
		
		
		jtf.setBounds(150, 20, 240, 35);
		jtf.addMouseListener(this);
		jb=new JButton("搜索");//设置搜索按钮
		jb.setBounds(395,20,80,35);
		jb.addActionListener(this);
	
		jp=new JPanel();//默认是layout流布局。
		
		jp.setLayout(null);

	
		jp.add(jtf);
		jp.add(jb);
		
		
		this.add(jlb,"North");
		
		this.add(jp,"Center");
		this.add(jpe,"South");
		
		this.setLocation(300, 200);
		this.setSize(600,500);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("豆瓣检索");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		 if(e.getSource()==jb){
			//调用豆瓣api，返回书的信息，
			//在用java解析json格式后输出显示在JTextArea中
			 if(jtf.getText().toString().equals("书名、作者、ISBN")||jtf.getText().toString().equals(""))
				 return ;
			 jta.setText("");
			  temp=jtf.getText().trim().toString();//出去空格后的字符串  2023013
			  System.out.println(temp);
			  String url="https://api.douban.com/v2/book/search?q="+temp+"&start=0&count=20";
			 try{
				 DefaultHttpClient client = new DefaultHttpClient();
                 HttpGet get = new HttpGet(url);   
                 HttpResponse response = ((Object) client).execute(get);
                 result=Entity.toString(((Object) response).getEntity()); 
                 System.out.println(result);
			 }catch(Exception re){	  
				 re.printStackTrace();
				 
			 } 
			 
			 //传入jsonstr给json2java函数。
			 SearchBookActivity dj=new SearchBookActivity();
			 dj.Json2java(result);
			 dabalist=new ArrayList<BookInfo>();
			 dabalist=dj.getBklist();
			 System.out.println(dabalist.size());
			 if(dabalist.size()==0)
			 {
				 jta.append("对不起，没有找到符合信息的书！！");
			 }
			 else
			 {
			 for(int i=0;i<dabalist.size();i++)
			 {
			  BookInfo book=dabalist.get(i); 
			 jta.append(i+1+"、");
			 jta.append("书名："+book.getBookName()+"\r\n");
			 jta.append("Isbn10："+book.getIsbn10()+"\r\n");
			// jta.append("书的Isbn13："+book.getIsbn13()+"\r\n");
			 jta.append("书总页数："+book.getPages()+"\r\n");
			 jta.append("作者："+book.getAuthor()+"\r\n");
			 jta.append("装订："+book.getBinding()+"\r\n");
			 jta.append("书价："+book.getPrice()+"\r\n");
			 jta.append("出版时间："+book.getPubdate()+"\r\n");
			 jta.append("出版社："+book.getPublisher()+"\r\n");
			 jta.append("简介："+"\r\n");
			 jta.append(book.getSummary()+"\r\n");
			 
			 }	
			 }
		}
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
               
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	
       	  
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(jtf.getText().toString().equals("书名、作者、ISBN"))
		 jtf.setText("");
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(jtf.getText().toString().equals(""))
		 jtf.setText("书名、作者、ISBN");
	}
}
