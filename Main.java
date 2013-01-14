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
	//�������
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
		jp_down=new JPanel(); //������ʾ����
		jta=new JTextArea("",20,7);
		jta.setEditable(false);//ֻ����ʾ�������Ա༭��
		jpe=new JScrollPane(jta);//���û��֣�������ʾ������Ϣ
		jtf=new JTextField("���������ߡ�ISBN");//����������Ĭ��ֵ
		
		
		jtf.setBounds(150, 20, 240, 35);
		jtf.addMouseListener(this);
		jb=new JButton("����");//����������ť
		jb.setBounds(395,20,80,35);
		jb.addActionListener(this);
	
		jp=new JPanel();//Ĭ����layout�����֡�
		
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
		this.setTitle("�������");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		 if(e.getSource()==jb){
			//���ö���api�����������Ϣ��
			//����java����json��ʽ�������ʾ��JTextArea��
			 if(jtf.getText().toString().equals("���������ߡ�ISBN")||jtf.getText().toString().equals(""))
				 return ;
			 jta.setText("");
			  temp=jtf.getText().trim().toString();//��ȥ�ո����ַ���  2023013
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
			 
			 //����jsonstr��json2java������
			 SearchBookActivity dj=new SearchBookActivity();
			 dj.Json2java(result);
			 dabalist=new ArrayList<BookInfo>();
			 dabalist=dj.getBklist();
			 System.out.println(dabalist.size());
			 if(dabalist.size()==0)
			 {
				 jta.append("�Բ���û���ҵ�������Ϣ���飡��");
			 }
			 else
			 {
			 for(int i=0;i<dabalist.size();i++)
			 {
			  BookInfo book=dabalist.get(i); 
			 jta.append(i+1+"��");
			 jta.append("������"+book.getBookName()+"\r\n");
			 jta.append("Isbn10��"+book.getIsbn10()+"\r\n");
			// jta.append("���Isbn13��"+book.getIsbn13()+"\r\n");
			 jta.append("����ҳ����"+book.getPages()+"\r\n");
			 jta.append("���ߣ�"+book.getAuthor()+"\r\n");
			 jta.append("װ����"+book.getBinding()+"\r\n");
			 jta.append("��ۣ�"+book.getPrice()+"\r\n");
			 jta.append("����ʱ�䣺"+book.getPubdate()+"\r\n");
			 jta.append("�����磺"+book.getPublisher()+"\r\n");
			 jta.append("��飺"+"\r\n");
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
		if(jtf.getText().toString().equals("���������ߡ�ISBN"))
		 jtf.setText("");
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(jtf.getText().toString().equals(""))
		 jtf.setText("���������ߡ�ISBN");
	}
}
