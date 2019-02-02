import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;

import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFrame;

public class Server   implements ActionListener  {
 
	static JFrame frame; 
	static Button btn=new Button("Sent");
	static TextArea area=new TextArea();
	static TextField field=new TextField(50);
	    ServerSocket serveurSocket  ;
	    Socket clientSocket ;
	  BufferedReader in;
	  PrintWriter out;
	    Scanner sc=new Scanner(System.in);
	
	Server(){
		frame=new JFrame("Server");
		frame.add(new Panel().add(area));
		frame.add(new Panel().add(field));
		 frame.add(new Panel().add(btn));
		 FlowLayout fl=new FlowLayout();
		 frame.setLayout(fl);
		 btn.addActionListener(this);
		frame.setVisible(true);
		frame.setSize(500,500);
	
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		  
	  
	  
	     try {
	       serveurSocket = new ServerSocket(5000);
	       clientSocket = serveurSocket.accept();
	       out = new PrintWriter(clientSocket.getOutputStream());
	       in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
	     
	   
	       Thread recevoir= new Thread(new Runnable() {
	          String msg ;
	          @Override
	          public void run() {
	             try {
	                msg = in.readLine();
	                //tant que le client est connecté
	                while(msg!=null){
	                   area.setText(area.getText()+"\n Client : "+msg);
	                   msg = in.readLine();
	                }
	                //sortir de la boucle si le client a déconecté
	                System.out.println("Client déconecté");
	                //fermer le flux et la session socket
	                out.close();
	                clientSocket.close();
	                serveurSocket.close();
	             } catch (IOException e) {
	                  e.printStackTrace();
	             }
	         }
	      });
	      recevoir.start();
	      }catch (IOException e) {
	         e.printStackTrace();
	      }
	   }



	@Override
	public void actionPerformed(ActionEvent e) {
		  Thread envoi= new Thread(new Runnable() {
	    	   String msg;
	          public void run() {
	             
	            	 if(e.getSource()==btn) {
	                msg = field.getText();
	    			area.setText(area.getText()+"\n You "+field.getText());
	    			
	                out.println(msg);
	                field.setText("");
	                out.flush(); }
	             }
	          
	       });
	       envoi.start();
		
		
		}
		
	
	
	public static void main(String[] args) {
		Server sc=new Server();
	}
	


}