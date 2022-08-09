package Embeded_Project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.ws4d.coap.core.CoapClient;
import org.ws4d.coap.core.CoapConstants;
import org.ws4d.coap.core.connection.BasicCoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapClientChannel;
import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.enumerations.CoapRequestCode;
import org.ws4d.coap.core.messages.api.CoapRequest;
import org.ws4d.coap.core.messages.api.CoapResponse;
import org.ws4d.coap.core.rest.CoapData;
import org.ws4d.coap.core.tools.Encoder;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException;


public class Outback_Client extends JFrame implements CoapClient{

	private static final boolean exitAfterResponse = false;
	
	JButton btn_start = new JButton("START GET");
	JButton btn_output = new JButton("OUT_PUT");
	JButton btn_openput = new JButton("OPEN_PUT");
	JButton btn_eat = new JButton("EAT_PUT");
	JButton btn_observetable = new JButton("OBSERVE TABLE");
	
	JLabel path_label = new JLabel("Resource");
	JTextArea path_text = new JTextArea("/.well-known/core", 1,1);//스크롤바 없음
	JLabel payload_label = new JLabel("Table Number");
	JTextArea payload_text = new JTextArea("", 1,1);//스크롤바 없음
	JTextArea display_text = new JTextArea();
	JScrollPane display_text_jp  = new JScrollPane(display_text);
	JLabel display_label = new JLabel("Display");

	JLabel open_table_label = new JLabel("Open_Table");
	JLabel open_table_label_2top = new JLabel("2top");
	JLabel open_2top= new JLabel("3");
	JLabel open_table_label_3top = new JLabel("3top");
	JLabel open_3top= new JLabel("4");
	JLabel open_table_label_4top = new JLabel("4top");
	JLabel open_4top= new JLabel("25");
	JLabel open_table_label_6top = new JLabel("6top");
	JLabel open_6top= new JLabel("5");
	
	JLabel out_table_label = new JLabel("Out_Table");
	JLabel out_table_label_2top = new JLabel("2top");
	JLabel out_2top= new JLabel("0");
	JLabel out_table_label_3top = new JLabel("3top");
	JLabel out_3top= new JLabel("0");
	JLabel out_table_label_4top = new JLabel("4top");
	JLabel out_4top= new JLabel("0");
	JLabel out_table_label_6top = new JLabel("6top");
	JLabel out_6top= new JLabel("0");
	
	JLabel eating_table_label = new JLabel("Eating_Table");
	JLabel eat_table_label_2top = new JLabel("2top");
	JLabel eat_2top= new JLabel("0");
	JLabel eat_table_label_3top = new JLabel("3top");
	JLabel eat_3top= new JLabel("0");
	JLabel eat_table_label_4top = new JLabel("4top");
	JLabel eat_4top= new JLabel("0");
	JLabel eat_table_label_6top = new JLabel("6top");
	JLabel eat_6top= new JLabel("0");

	CoapClientChannel clientChannel = null;

	JButton[] btn_table = new JButton[65];
	boolean customer_detected = false;
	
	MongoClient mongo = null;
	
	public Outback_Client (String serverAddress, int serverPort) {
		//제목 설정
		super("OutBack_SANGBONG_TableClient");
		//레이아웃 설정
		this.setLayout(null);
	
		String sAddress = serverAddress;
		int sPort = serverPort;

		CoapChannelManager channelManager = BasicCoapChannelManager.getInstance();

		try {
			clientChannel = channelManager.connect(this, InetAddress.getByName(sAddress), sPort);
	
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		}


		if (null == clientChannel) {
			return;
		}

		//버튼 위치 설정
		btn_start.setBounds(20, 670, 130, 50);
		btn_output.setBounds(170, 670, 130, 50);
		btn_openput.setBounds(320, 670, 130, 50);
		btn_eat.setBounds(470, 670, 130, 50);
		btn_observetable.setBounds(620, 670, 150, 50); //770
		
		String[] table_num = new String[65];
		for(int i = 0; i < 65; i++) {
			table_num[i] = Integer.toString(i);
		}
		

		for(int i = 0; i < table_num.length; i++) {
			btn_table[i] = new JButton(Integer.toString(i));
			btn_table[i].setBackground(Color.LIGHT_GRAY);
		}

		btn_table[1].setBounds(1020, 40, 70, 50);
		btn_table[3].setBounds(1100, 40, 50, 50);
		btn_table[5].setBounds(1160, 40, 50, 50);
		btn_table[7].setBounds(1220, 40, 70, 50);
		btn_table[8].setBounds(1300, 40, 50, 50);
		btn_table[9].setBounds(1350, 40, 50, 50);
		
		btn_table[11].setBounds(950, 40, 50, 50);
		btn_table[12].setBounds(950, 90, 50, 50);
		btn_table[13].setBounds(900, 90, 50, 50);
		btn_table[14].setBounds(850, 90, 50, 50);
		btn_table[15].setBounds(800, 90, 50, 50);
		
		btn_table[21].setBounds(900, 160, 50, 50);
		btn_table[22].setBounds(850, 160, 50, 50);
		btn_table[23].setBounds(800, 160, 50, 50);
		btn_table[24].setBounds(800, 230, 50, 50);
		btn_table[25].setBounds(850, 230, 50, 50);
		btn_table[26].setBounds(900, 230, 50, 50);
		btn_table[27].setBounds(950, 230, 50, 50);
		btn_table[28].setBounds(1000, 230, 50, 50);
		btn_table[29].setBounds(1050, 230, 50, 50);
		btn_table[30].setBounds(1100, 230, 50, 50);
		
		btn_table[31].setBounds(1070, 110, 50, 50);
		btn_table[32].setBounds(1070, 160, 50, 50);
		btn_table[33].setBounds(1020, 160, 50, 50);
		btn_table[34].setBounds(1020, 110, 50, 50);
		
		btn_table[41].setBounds(1210, 110, 50, 50);
		btn_table[42].setBounds(1210, 160, 50, 50);
		btn_table[43].setBounds(1160, 160, 50, 50);
		btn_table[44].setBounds(1160, 110, 50, 50);
		
		btn_table[51].setBounds(1350, 90, 50, 70);
		btn_table[52].setBounds(1350, 160, 50, 70);
		btn_table[53].setBounds(1300, 160, 50, 50);
		btn_table[54].setBounds(1300, 90, 50, 70);
		
		btn_table[61].setBounds(1200, 230, 50, 50);
		btn_table[62].setBounds(1250, 230, 50, 50);
		btn_table[63].setBounds(1300, 230, 50, 50);
		btn_table[64].setBounds(1350, 230, 50, 50);
		
		payload_label.setBounds(20, 570, 350, 30);
		payload_text.setBounds(20, 600, 440, 30);
		payload_text.setFont(new Font("arian", Font.BOLD, 15));
	
		path_label.setBounds(20, 500, 350, 30);
		path_text.setBounds(20, 530, 440, 30);
		path_text.setFont(new Font("arian", Font.BOLD, 15));
		
		display_label.setBounds(20, 10, 100, 20);
		display_text.setLineWrap(true);
		display_text.setFont(new Font("arian", Font.BOLD, 15));
		display_text_jp.setBounds(20, 40, 750, 430);
		
		open_table_label.setBounds(800, 300, 500, 50);
		
		open_table_label_2top.setBounds(800, 320, 500, 50);
		open_2top.setBounds(800, 370, 30, 25);
		open_2top.setFont(new Font("arian", Font.BOLD, 15));
		open_table_label_3top.setBounds(850, 320, 500, 50);
		open_3top.setBounds(850, 370, 30, 25);
		open_3top.setFont(new Font("arian", Font.BOLD, 15));
		open_table_label_4top.setBounds(900, 320, 500, 50);
		open_4top.setBounds(900, 370, 30, 25);
		open_4top.setFont(new Font("arian", Font.BOLD, 15));
		open_table_label_6top.setBounds(950, 320, 500, 50);
		open_6top.setBounds(950, 370, 30, 25);
		open_6top.setFont(new Font("arian", Font.BOLD, 15));
		
		out_table_label.setBounds(800, 400, 500, 50);
		
		out_table_label_2top.setBounds(800, 420, 500, 50);
		out_2top.setBounds(800, 470, 30, 25);
		out_2top.setFont(new Font("arian", Font.BOLD, 15));
		out_table_label_3top.setBounds(850, 420, 500, 50);
		out_3top.setBounds(850, 470, 30, 25);
		out_3top.setFont(new Font("arian", Font.BOLD, 15));
		out_table_label_4top.setBounds(900, 420, 500, 50);
		out_4top.setBounds(900, 470, 30, 25);
		out_4top.setFont(new Font("arian", Font.BOLD, 15));
		out_table_label_6top.setBounds(950, 420, 500, 50);
		out_6top.setBounds(950, 470, 30, 25);
		out_6top.setFont(new Font("arian", Font.BOLD, 15));
		
			
		eating_table_label.setBounds(800, 500, 500, 50);
		
		eat_table_label_2top.setBounds(800, 520, 30, 50);
		eat_2top.setBounds(800, 570, 30, 25);
		eat_2top.setFont(new Font("arian", Font.BOLD, 15));
		eat_table_label_3top.setBounds(850, 520, 500, 50);
		eat_3top.setBounds(850, 570, 30, 25);
		eat_3top.setFont(new Font("arian", Font.BOLD, 15));
		eat_table_label_4top.setBounds(900, 520, 500, 50);
		eat_4top.setBounds(900, 570, 30, 25);
		eat_4top.setFont(new Font("arian", Font.BOLD, 15));
		eat_table_label_6top.setBounds(950, 520, 500, 50);
		eat_6top.setBounds(950, 570, 30, 25);
		eat_6top.setFont(new Font("arian", Font.BOLD, 15));
		

		
		btn_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = path_text.getText();
				String payload = payload_text.getText();				
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.GET, path, true); //중요! 요청 메세지 생성, 
				displayRequest(request); // request 메세지를 보여줌
				clientChannel.sendMessage(request); // 연결된 서버로 생성된 request 메세지를 전송
			}
		});
		
		btn_output.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = path_text.getText();
				String payload = payload_text.getText();
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, path, true);
				request.setPayload(new CoapData(payload, CoapMediaType.text_plain));
				displayRequest(request);
				clientChannel.sendMessage(request);
				
				int outcount_2top = Integer.parseInt(out_2top.getText());
				int opencount_2top = Integer.parseInt(open_2top.getText());
				int eatcount_2top = Integer.parseInt(eat_2top.getText());
				
				int outcount_3top= Integer.parseInt(out_3top.getText());
				int opencount_3top = Integer.parseInt(open_3top.getText());
				int eatcount_3top = Integer.parseInt(eat_3top.getText());
				
				int outcount_4top = Integer.parseInt(out_4top.getText());
				int opencount_4top = Integer.parseInt(open_4top.getText());
				int eatcount_4top = Integer.parseInt(eat_4top.getText());
				
				int outcount_6top = Integer.parseInt(out_6top.getText());
				int opencount_6top = Integer.parseInt(open_6top.getText());
				int eatcount_6top = Integer.parseInt(eat_6top.getText());
				
				int search = Integer.parseInt(payload);
				
				for(int i = 0; i < table_num.length; i++) {
					if(search == Integer.parseInt(table_num[i])) {
						display_text.append("Table Number : " + table_num[i] + " Out \n");
						display_text.append(System.lineSeparator());
						if(search == 21 || search == 22 ||  search == 23) {
							btn_table[i].setBackground(Color.orange);
							outcount_2top += 1;
							opencount_2top -= 1;
							eatcount_2top -= 1;
							if(eatcount_2top < 0)
								eatcount_2top = 0;
							if(opencount_2top < 0)
								opencount_2top = 0;
							if(outcount_2top > 3) {
								outcount_2top = 3;
								eatcount_2top = 0;
								opencount_2top = 0;
							}
							out_2top.setText(String.valueOf(outcount_2top));
							open_2top.setText(String.valueOf(opencount_2top));
							eat_2top.setText(String.valueOf(eatcount_2top));
						}
						else if(search == 24 || search == 27 || search == 61 || search == 64) {
							btn_table[i].setBackground(Color.orange);
							outcount_3top += 1;
							opencount_3top -= 1;
							eatcount_3top -= 1;
							if(eatcount_3top < 0)
								eatcount_3top = 0;
							if(opencount_3top < 0)
								opencount_3top = 0;
							if(outcount_3top > 4 ) {
								outcount_3top = 4;
								eatcount_3top = 0;
								opencount_3top = 0;
							}
							out_3top.setText(String.valueOf(outcount_3top));
							open_3top.setText(String.valueOf(opencount_3top));
							eat_3top.setText(String.valueOf(eatcount_3top));
						}else if(search == 1 || search == 5 || search == 51 || search == 52 || search == 54) {
							btn_table[i].setBackground(Color.orange);
							outcount_6top += 1;
							opencount_6top -= 1;
							eatcount_6top -= 1;
							if(eatcount_6top < 0)
								eatcount_6top = 0;
							if(opencount_6top < 0)
								opencount_6top = 0;
							if(outcount_6top > 5 ) {
								outcount_6top = 5;
								eatcount_6top = 0;
								opencount_6top = 0;
							}
							out_6top.setText(String.valueOf(outcount_6top));
							open_6top.setText(String.valueOf(opencount_6top));
							eat_6top.setText(String.valueOf(eatcount_6top));
						}else {
							btn_table[i].setBackground(Color.orange);
							outcount_4top += 1;
							opencount_4top -= 1;
							eatcount_4top -= 1;
							if(eatcount_4top < 0)
								eatcount_4top = 0;
							if(opencount_2top < 0)
								opencount_2top = 0;
							if(outcount_4top > 25 ) {
								outcount_4top = 25;
								eatcount_4top = 0;
								opencount_4top = 0;
							}
							out_4top.setText(String.valueOf(outcount_4top));
							open_4top.setText(String.valueOf(opencount_4top));
							eat_4top.setText(String.valueOf(eatcount_4top));
						}
					}	
				}
		
			}
		});
		
		btn_openput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = path_text.getText();
				String payload = payload_text.getText();
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, path, true);	
				request.setPayload(new CoapData(payload, CoapMediaType.text_plain));
				displayRequest(request);
				clientChannel.sendMessage(request);

				int search = Integer.parseInt(payload);
				
				int outcount_2top = Integer.parseInt(out_2top.getText());
				int opencount_2top = Integer.parseInt(open_2top.getText());
				int eatcount_2top = Integer.parseInt(eat_2top.getText());
				
				int outcount_3top= Integer.parseInt(out_3top.getText());
				int opencount_3top = Integer.parseInt(open_3top.getText());
				int eatcount_3top = Integer.parseInt(eat_3top.getText());
				
				int outcount_4top = Integer.parseInt(out_4top.getText());
				int opencount_4top = Integer.parseInt(open_4top.getText());
				int eatcount_4top = Integer.parseInt(eat_4top.getText());
				
				int outcount_6top = Integer.parseInt(out_6top.getText());
				int opencount_6top = Integer.parseInt(open_6top.getText());
				int eatcount_6top = Integer.parseInt(eat_6top.getText());
				
				for(int i = 0; i < table_num.length; i++) {
					if(search == Integer.parseInt(table_num[i])) {
						display_text.append("Table Number : " + table_num[i] + " OPen!! \n");
						display_text.append(System.lineSeparator());
						if(search == 21 || search == 22 ||  search == 23) {
							btn_table[i].setBackground(Color.cyan);
							outcount_2top -= 1;
							opencount_2top += 1;
							eatcount_2top -= 1;
							if(eatcount_2top < 0)
								eatcount_2top = 0;
							if(outcount_2top < 0)
								outcount_2top = 0;
							if(opencount_2top > 3) {
								opencount_2top = 3;
								eatcount_2top = 0;
								outcount_2top = 0;
							}
							out_2top.setText(String.valueOf(outcount_2top));
							open_2top.setText(String.valueOf(opencount_2top));
							eat_2top.setText(String.valueOf(eatcount_2top));
						}
						else if(search == 24 || search == 27 || search == 61 || search == 64) {
							btn_table[i].setBackground(Color.cyan);
							outcount_3top -= 1;
							opencount_3top += 1;
							eatcount_3top -= 1;
							if(eatcount_3top < 0)
								eatcount_3top = 0;
							if(outcount_3top < 0)
								outcount_3top = 0;
							if(opencount_3top > 4) {
								opencount_3top = 4;
								eatcount_3top = 0;
								outcount_3top = 0;
							}
							out_3top.setText(String.valueOf(outcount_3top));
							open_3top.setText(String.valueOf(opencount_3top));
							eat_3top.setText(String.valueOf(eatcount_3top));
						}else if(search == 1 || search == 5 || search == 51 || search == 52 || search == 54) {
							btn_table[i].setBackground(Color.cyan);
							outcount_6top -= 1;
							opencount_6top += 1;
							eatcount_6top -= 1;
							if(eatcount_6top < 0)
								eatcount_6top = 0;
							if(outcount_6top < 0)
								outcount_6top = 0;
							if(opencount_6top > 5) {
								opencount_6top = 5;
								eatcount_6top = 0;
								outcount_6top = 0;
							}
							out_6top.setText(String.valueOf(outcount_6top));
							open_6top.setText(String.valueOf(opencount_6top));
							eat_6top.setText(String.valueOf(eatcount_6top));
						}else {
							btn_table[i].setBackground(Color.cyan);
							outcount_4top -= 1;
							opencount_4top += 1;
							eatcount_4top -= 1;
							if(eatcount_4top < 0)
								eatcount_4top = 0;
							if(outcount_4top < 0)
								outcount_4top = 0;
							if(opencount_4top > 25) {
								opencount_4top = 25;
								eatcount_4top = 0;
								outcount_4top = 0;
							}
							out_4top.setText(String.valueOf(outcount_4top));
							open_4top.setText(String.valueOf(opencount_4top));
							eat_4top.setText(String.valueOf(eatcount_4top));
						}
					}	
				}
			}
		});
		
		btn_eat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = path_text.getText();
				String payload = payload_text.getText();				
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.GET, path, true); //중요! 요청 메세지 생성, 
				request.setPayload(new CoapData(payload, CoapMediaType.text_plain));
				displayRequest(request); // request 메세지를 보여줌
				clientChannel.sendMessage(request); // 연결된 서버로 생성된 request 메세지를 전송
				
				int search = Integer.parseInt(payload); 
				
				int outcount_2top = Integer.parseInt(out_2top.getText());
				int opencount_2top = Integer.parseInt(open_2top.getText());
				int eatcount_2top = Integer.parseInt(eat_2top.getText());
				
				int outcount_3top= Integer.parseInt(out_3top.getText());
				int opencount_3top = Integer.parseInt(open_3top.getText());
				int eatcount_3top = Integer.parseInt(eat_3top.getText());
				
				int outcount_4top = Integer.parseInt(out_4top.getText());
				int opencount_4top = Integer.parseInt(open_4top.getText());
				int eatcount_4top = Integer.parseInt(eat_4top.getText());
				
				int outcount_6top = Integer.parseInt(out_6top.getText());
				int opencount_6top = Integer.parseInt(open_6top.getText());
				int eatcount_6top = Integer.parseInt(eat_6top.getText());
				
				for(int i = 0; i < table_num.length; i++) {
					if(search == Integer.parseInt(table_num[i])) {
						display_text.append("Table Number : " + table_num[i] + " Eating Now \n");
						display_text.append(System.lineSeparator());
						if(search == 21 || search == 22 ||  search == 23) {
							btn_table[i].setBackground(Color.pink);
							outcount_2top -= 1;
							opencount_2top -= 1;
							eatcount_2top += 1;
							if(outcount_2top < 0)
								outcount_2top = 0;
							if(opencount_2top < 0)
								opencount_2top = 0;
							if(eatcount_2top > 3) {
								eatcount_2top = 3;
								opencount_2top = 0;
								outcount_2top = 0;
							}
							out_2top.setText(String.valueOf(outcount_2top));
							open_2top.setText(String.valueOf(opencount_2top));
							eat_2top.setText(String.valueOf(eatcount_2top));
						}
						else if(search == 24 || search == 27 || search == 61 || search == 64) {
							btn_table[i].setBackground(Color.pink);
							outcount_3top -= 1;
							opencount_3top -= 1;
							eatcount_3top += 1;
							if(outcount_3top < 0)
								outcount_3top = 0;
							if(opencount_3top < 0)
								opencount_3top = 0;
							if(eatcount_3top > 4) {
								eatcount_3top = 4;
								opencount_3top = 0;
								outcount_3top = 0;
							}
							out_3top.setText(String.valueOf(outcount_3top));
							open_3top.setText(String.valueOf(opencount_3top));
							eat_3top.setText(String.valueOf(eatcount_3top));
						}else if(search == 1 || search == 5 || search == 51 || search == 52 || search == 54) {
							btn_table[i].setBackground(Color.pink);
							outcount_6top -= 1;
							opencount_6top -= 1;
							eatcount_6top += 1;
							if(outcount_6top < 0)
								outcount_6top = 0;
							if(opencount_6top < 0)
								opencount_6top = 0;
							if(eatcount_6top > 5) {
								eatcount_6top = 5;
								opencount_6top = 0;
								outcount_6top = 0;
							}
							out_6top.setText(String.valueOf(outcount_6top));
							open_6top.setText(String.valueOf(opencount_6top));
							eat_6top.setText(String.valueOf(eatcount_6top));
						}else {
							btn_table[i].setBackground(Color.pink);
							outcount_4top -= 1;
							opencount_4top -= 1;
							eatcount_4top += 1;
							if(outcount_4top < 0)
								outcount_4top = 0;
							if(opencount_4top < 0)
								opencount_4top = 0;
							if(eatcount_4top > 25) {
								eatcount_4top = 25;
								opencount_4top = 0;
								outcount_4top = 0;
							}
							out_4top.setText(String.valueOf(outcount_4top));
							open_4top.setText(String.valueOf(opencount_4top));
							eat_4top.setText(String.valueOf(eatcount_4top));
						}
					}	
				}
			}
		});

		btn_observetable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = path_text.getText();
				String payload = payload_text.getText();				
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.GET, path, true); //중요! 요청 메세지 생성, 
				request.setToken(Encoder.StringToByte("obTocken")); //observe 요청이 있을 때 토큰이 함께 전송
				request.setObserveOption(0); // 0번부터 sequence number 시작
				// 5초마다 응답메세지를 받음, server가 observe에 대한 응답을 보낼때 마다 숫자를 부여해서 보냄, client가 번호를 보고 최근의 메세지를 구별함
				displayRequest(request); // request 메세지를 보여줌
				clientChannel.sendMessage(request); // 연결된 서버로 생성된 request 메세지를 전송
			}
		});

		
		this.add(btn_start);
		this.add(btn_output);
		this.add(btn_openput);
		this.add(btn_eat);
		this.add(btn_observetable);
		this.add(path_text);
		this.add(path_label);
		this.add(payload_label);
		this.add(payload_text);
		this.add(display_text_jp);
		this.add(display_label);
		
		this.add(open_table_label);
		this.add(open_table_label_2top);
		this.add(open_table_label_3top);
		this.add(open_table_label_4top);
		this.add(open_table_label_6top);
		this.add(out_table_label);
		this.add(out_table_label_2top);
		this.add(out_table_label_3top);
		this.add(out_table_label_4top);
		this.add(out_table_label_6top);
		this.add(eating_table_label);
		this.add(eat_table_label_2top);
		this.add(eat_table_label_3top);
		this.add(eat_table_label_4top);
		this.add(eat_table_label_6top);
		
		this.add(open_2top);
		this.add(open_3top);
		this.add(open_4top);
		this.add(open_6top);
		this.add(out_2top);
		this.add(out_3top);
		this.add(out_4top);
		this.add(out_6top);
		this.add(eat_2top);
		this.add(eat_3top);
		this.add(eat_4top);
		this.add(eat_6top);
		
		this.add(btn_table[1]);
		this.add(btn_table[3]);
		this.add(btn_table[5]);
		this.add(btn_table[7]);
		this.add(btn_table[8]);
		this.add(btn_table[9]);
		this.add(btn_table[11]);
		this.add(btn_table[12]);
		this.add(btn_table[13]);
		this.add(btn_table[14]);
		this.add(btn_table[15]);
		this.add(btn_table[21]);
		this.add(btn_table[22]);
		this.add(btn_table[23]);
		this.add(btn_table[24]);
		this.add(btn_table[25]);
		this.add(btn_table[26]);
		this.add(btn_table[27]);
		this.add(btn_table[28]);
		this.add(btn_table[29]);
		this.add(btn_table[30]);
		this.add(btn_table[31]);
		this.add(btn_table[32]);
		this.add(btn_table[33]);
		this.add(btn_table[34]);
		this.add(btn_table[41]);
		this.add(btn_table[42]);
		this.add(btn_table[43]);
		this.add(btn_table[44]);
		this.add(btn_table[51]);
		this.add(btn_table[52]);
		this.add(btn_table[53]);
		this.add(btn_table[54]);
		this.add(btn_table[61]);
		this.add(btn_table[62]);
		this.add(btn_table[63]);
		this.add(btn_table[64]);
		

		//프레임 크기 지정	
		this.setSize(1500, 800);

		//프레임 보이기
		this.setVisible(true);

		//swing에만 있는 X버튼 클릭시 종료
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void onConnectionFailed(CoapClientChannel channel, boolean notReachable, boolean resetByServer) {
		System.out.println("Connection Failed");
		System.exit(-1);
	}

	@Override
	public void onResponse(CoapClientChannel channel, CoapResponse response) { //언제든 클라이언트가 서버로부터 응답메세지를 받으면 실행되는 메소드
		// display창에 전달받은 응답메세지 내용들을 띄워줌
		if (response.getPayload() != null) {
			display_text.append(
					"Response: " + response.toString() + " payload: " + Encoder.ByteToString(response.getPayload()));
			display_text.setCaretPosition(display_text.getDocument().getLength());  
		} else {
			display_text.append("Response: " + response.toString());
			display_text.setCaretPosition(display_text.getDocument().getLength());
		}
		if (Outback_Client.exitAfterResponse) {
			display_text.append("===END===");
			System.exit(0);
		}
		display_text.append(System.lineSeparator());
		display_text.append("*");
		display_text.append(System.lineSeparator());	
		
		if (Encoder.ByteToString(response.getToken()).equals("obTocken")) { // Observe Option으로 상태가 변한다면 토큰 전송
			detected_movement(Encoder.ByteToString(response.getPayload())); 
		}
	}
	
	public void detected_movement(String movement) { //Observe Option으로 수신된 GET메세지가 수신되면 실행되는 메소드
		if(movement.equals("Detected")) { //Detected 메세지가 수신되면
				display_text.append(System.lineSeparator());
				display_text.append("Customer Enjoy their time \n");
				display_text.append("Table Can't Use. Search Other Table");
				display_text.append(System.lineSeparator());
				// request 메세지 전송
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/pir", true);
				request.setPayload(new CoapData("Detected", CoapMediaType.text_plain));
				displayRequest(request);
				clientChannel.sendMessage(request);
				
				btn_table[31].setBackground(Color.pink);  //테이블 색깔을 변경해준다
				int outcount = Integer.parseInt(out_4top.getText()); //각 라벨의 숫자도 변경해준다.
				int opencount = Integer.parseInt(open_4top.getText());
				int eatingcount = Integer.parseInt(eat_4top.getText());
				outcount -= 1;
				opencount -= 1;
				eatingcount += 1;
				if(opencount < 0 ) {
					opencount = 0;
				}else if(outcount < 0) {
					outcount = 0;
				}
				out_4top.setText(String.valueOf(outcount));
				open_4top.setText(String.valueOf(opencount));
				eat_4top.setText(String.valueOf(eatingcount));
			}
		else { // no detected
			display_text.append(System.lineSeparator());
			display_text.append("Customer Finished Eating \n");
			display_text.append("Please Clean this Table");
			display_text.append(System.lineSeparator());

			CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/pir", true);
			request.setPayload(new CoapData("Not Detected", CoapMediaType.text_plain));
			displayRequest(request);
			clientChannel.sendMessage(request);
			
			btn_table[31].setBackground(Color.orange); 
			int outcount = Integer.parseInt(out_4top.getText());
			int opencount = Integer.parseInt(open_4top.getText());
			int eatingcount = Integer.parseInt(eat_4top.getText());
			outcount += 1;
			eatingcount -= 1; 
			if(outcount > 25) {
				outcount = 25;
			}else if (eatingcount < 0) {
				eatingcount = 0;
			}
			out_4top.setText(String.valueOf(outcount));
			open_4top.setText(String.valueOf(opencount));
			eat_4top.setText(String.valueOf(eatingcount));
	
		}

	}	

	@Override
	public void onMCResponse(CoapClientChannel channel, CoapResponse response, InetAddress srcAddress, int srcPort) {
		// TODO Auto-generated method stub
	}
	
	private void displayRequest(CoapRequest request){
		
		if(request.getPayload() != null){
			display_text.append("Request: "+request.toString() + " payload: " + Encoder.ByteToString(request.getPayload()) + " resource: " + request.getUriPath());
			display_text.setCaretPosition(display_text.getDocument().getLength());  
		} 
		else{
			display_text.append("Request: "+request.toString() + " resource: " + request.getUriPath());
			display_text.setCaretPosition(display_text.getDocument().getLength());  
		}
		display_text.append(System.lineSeparator());
		display_text.append("*");
		display_text.append(System.lineSeparator());
		
	}
	


	public static void main(String[] args){
		//프레임 열기
		Outback_Client gui = new Outback_Client("172.1.80.16", CoapConstants.COAP_DEFAULT_PORT);
		//fe80::81cf:7810:fb1c:61a9 유선 아이피
		//192.168.0.20 집 와이파이 아이피
		//172.1.80.16 앗백 아이피
		
		
	}
}

