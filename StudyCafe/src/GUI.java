import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class GUI {

	private JFrame WELCOME;
	private JTextField login_textField;
	private JTable check_out_table;
	private JTextField phone_textField;
	private JTextField pay_textField;
	private JTable check_in_table;
	private JTextField checkin_max_textField;
	private JTextField cehckin_user_textField;
	private JTextField checkin_phone_textField;
	private JTable room_manager_table;
	private JTextField search_empty_max_textField;
	private JTextField room_manager_textField;
	private JTextField room_manager_max_textField;
	private JTextField room_manager_price_textField;
	private JTable look_income_table;
	private JTextField name_textField;

	/**
	 * Launch the application.
	 * 
	 */
	public static void main(String[] args) throws Exception {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.WELCOME.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 */
	public GUI() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 */
	private void initialize() throws Exception {

		GregorianCalendar ca = new GregorianCalendar();
		Scanner scan = new Scanner(System.in);
		Manager m = new Manager("1234"); // manager id 미리 설정.
		File file = new File("RoomInfo.dat");
		file.createNewFile();
		File file2 = new File("ManagerInfo.dat");
		file2.createNewFile();

		try { // Room 정보 읽어오기
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("RoomInfo.dat"));
			m.readRoom(in);
		} catch (EOFException EOFE) {
		}

		try { // Manager 정보 읽어오기
			ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("ManagerInfo.dat"));
			m.readManager(in2);
		} catch (EOFException EOFE) {
		}

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("RoomInfo.dat"));
		ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("ManagerInfo.dat"));

		int date = ca.get(Calendar.DATE); // 오늘 날짜, 소득 업데이트 시 사용
		int month = ca.get(Calendar.MONTH);

		WELCOME = new JFrame();
		WELCOME.setTitle("Welcome To STUDY CAFE");
		// setIcon 으로 타이틀 옆의 아이콘도 지정할 수 있음.
		// JScrollPane welcome_scrollpane = new JScrollPane(WELCOME);
		WELCOME.setBounds(100, 100, 1020, 600);
		WELCOME.setLocationRelativeTo(null);
		// WELCOME.setResizable(false);

		WELCOME.addWindowListener(new WindowAdapter() { // 윈도우 창의 X 로 종료할 때, 객체 저장 및 프로그램 종료
			public void windowClosing(WindowEvent e) {
				scan.close();
				try {
					m.writeRoom(out); // Room 객체 저장.
					m.writeManager(out2); // Manager 객체 저장.
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				} finally {
					JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.");
					try {
						out.close();
						out2.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.exit(0);
				}
			}
		});
		WELCOME.getContentPane().setLayout(null);

		JPanel START = new JPanel();
		// JScrollPane start_scrollpane = new JScrollPane(START);
		START.setBounds(0, 0, 1004, 561);
		// WELCOME.add(start_scrollpane);
		WELCOME.getContentPane().add(START);
		START.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("StudyCafe");
		lblNewLabel_2.setBounds(12, 10, 58, 15);
		START.add(lblNewLabel_2);

		JLabel lblNewLabel_1_2 = new JLabel("Welcome to StudyCafe");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 50));
		lblNewLabel_1_2.setBounds(193, 183, 641, 58);
		START.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_1_1 = new JLabel("Please select the mode you want to use");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("나눔고딕 Light", Font.PLAIN, 20));
		lblNewLabel_1_1_1.setBounds(309, 268, 387, 42);
		START.add(lblNewLabel_1_1_1);

		JButton manager_button = new JButton("MANAGER MODE");

		manager_button.setBounds(421, 321, 158, 23);
		START.add(manager_button);

		JButton user_button = new JButton("USER MODE");

		user_button.setBounds(421, 354, 158, 23);
		START.add(user_button);

		// room_manager 테이블
		String[] columnNames = new String[] { "RoomName", "MAX", "PRICE", "EMPTY" };
		DefaultTableModel model_room_manager = new DefaultTableModel(columnNames, 0);

		// income_table 설정
		String[] columnNames_income = new String[] { "DATE", "INCOME", "ACCUMULATED" };
		DefaultTableModel model_income_manager = new DefaultTableModel(columnNames_income, 0);

		JPanel MANAGER_LOGIN = new JPanel();
		MANAGER_LOGIN.setBounds(0, 0, 1004, 561);
		WELCOME.getContentPane().add(MANAGER_LOGIN);
		MANAGER_LOGIN.setLayout(null);

		JLabel lblStudycafeManager = new JLabel("StudyCafe : MANAGER LOGIN");
		lblStudycafeManager.setBounds(12, 10, 200, 15);
		MANAGER_LOGIN.add(lblStudycafeManager);

		JLabel lblNewLabel_1_2_1 = new JLabel("MANAGER MODE");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_1.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 50));
		lblNewLabel_1_2_1.setBounds(187, 133, 641, 58);
		MANAGER_LOGIN.add(lblNewLabel_1_2_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("MANAGER ID");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1.setFont(new Font("나눔고딕 Light", Font.PLAIN, 20));
		lblNewLabel_1_1_1_1.setBounds(312, 264, 387, 42);
		MANAGER_LOGIN.add(lblNewLabel_1_1_1_1);

		login_textField = new JTextField();
		login_textField.setBounds(373, 316, 187, 30);
		MANAGER_LOGIN.add(login_textField);
		login_textField.setColumns(10);

		JButton login_button = new JButton("LOGIN");

		login_button.setBounds(572, 316, 88, 30);
		MANAGER_LOGIN.add(login_button);

		JButton home_button_manager = new JButton("HOME");

		home_button_manager.setBounds(463, 528, 97, 23);
		MANAGER_LOGIN.add(home_button_manager);

		JPanel MANAGER = new JPanel();
		MANAGER.setBounds(0, 0, 1004, 561);
		WELCOME.getContentPane().add(MANAGER);
		MANAGER.setLayout(null);

		JPanel ROOM_MANAGER = new JPanel();
		ROOM_MANAGER.setLayout(null);
		ROOM_MANAGER.setBounds(0, 73, 1004, 488);
		MANAGER.add(ROOM_MANAGER);

		JButton home_button_room_manager = new JButton("HOME");
		home_button_room_manager.setBounds(482, 455, 69, 23);
		ROOM_MANAGER.add(home_button_room_manager);
		room_manager_table = new JTable(model_room_manager);

		JScrollPane room_manager_scrollpane = new JScrollPane(room_manager_table);
		room_manager_scrollpane.setBounds(12, 10, 745, 435);

		ROOM_MANAGER.add(room_manager_scrollpane);
		// room_manager_table.setPreferredScrollableViewportSize(new Dimension(700,
		// 600));
		// room_manager_table.setFillsViewportHeight(true);

		JLabel lblNewLabel_1_1_3_3 = new JLabel("SEARCH EMPTY : MAX");
		lblNewLabel_1_1_3_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_3_3.setBounds(769, 43, 135, 22);
		ROOM_MANAGER.add(lblNewLabel_1_1_3_3);

		search_empty_max_textField = new JTextField();
		search_empty_max_textField.setColumns(10);
		search_empty_max_textField.setBounds(769, 67, 113, 23);
		ROOM_MANAGER.add(search_empty_max_textField);

		JButton room_manager_max_search_button = new JButton("SEARCH");
		room_manager_max_search_button.setBounds(894, 67, 98, 23);
		ROOM_MANAGER.add(room_manager_max_search_button);

		JLabel lblNewLabel_1_1_3_1_1 = new JLabel("ROOM");
		lblNewLabel_1_1_3_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_3_1_1.setBounds(769, 355, 40, 22);
		ROOM_MANAGER.add(lblNewLabel_1_1_3_1_1);

		room_manager_textField = new JTextField();
		room_manager_textField.setColumns(10);
		room_manager_textField.setBounds(820, 355, 69, 23);
		ROOM_MANAGER.add(room_manager_textField);

		JButton add_button = new JButton("ADD");
		add_button.setBounds(894, 355, 98, 40);
		ROOM_MANAGER.add(add_button);

		JLabel lblNewLabel_1_1_3_2_2 = new JLabel("MAX");
		lblNewLabel_1_1_3_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_3_2_2.setBounds(769, 387, 47, 22);
		ROOM_MANAGER.add(lblNewLabel_1_1_3_2_2);

		room_manager_max_textField = new JTextField();
		room_manager_max_textField.setColumns(10);
		room_manager_max_textField.setBounds(820, 387, 69, 23);
		ROOM_MANAGER.add(room_manager_max_textField);

		room_manager_price_textField = new JTextField();
		room_manager_price_textField.setColumns(10);
		room_manager_price_textField.setBounds(820, 419, 69, 23);
		ROOM_MANAGER.add(room_manager_price_textField);

		JLabel lblNewLabel_1_1_3_2_1_1 = new JLabel("PRICE");
		lblNewLabel_1_1_3_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_3_2_1_1.setBounds(769, 419, 47, 22);
		ROOM_MANAGER.add(lblNewLabel_1_1_3_2_1_1);

		JLabel lblNewLabel_1_1_3_3_1 = new JLabel("SEARCH ALL");
		lblNewLabel_1_1_3_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_3_3_1.setBounds(769, 105, 92, 22);
		ROOM_MANAGER.add(lblNewLabel_1_1_3_3_1);

		JButton room_manager_all_search_button = new JButton("SEARCH");
		room_manager_all_search_button.setBounds(873, 105, 98, 23);
		ROOM_MANAGER.add(room_manager_all_search_button);

		JButton delete_button = new JButton("DEL");
		delete_button.setBounds(894, 405, 98, 40);
		ROOM_MANAGER.add(delete_button);

		JLabel LABEL_MANAGER_1 = new JLabel("StudyCafe : MANAGER");
		LABEL_MANAGER_1.setBounds(12, 10, 142, 15);
		MANAGER.add(LABEL_MANAGER_1);

		JButton room_manager_button = new JButton("ROOM MANAGER");
		room_manager_button.setBounds(22, 35, 137, 34);
		MANAGER.add(room_manager_button);

		JButton look_income_button = new JButton("LOOK INCOME");
		look_income_button.setBounds(174, 35, 137, 34);
		MANAGER.add(look_income_button);

		JPanel LOOK_INCOME = new JPanel();
		LOOK_INCOME.setLayout(null);
		LOOK_INCOME.setBounds(0, 73, 1004, 488);
		MANAGER.add(LOOK_INCOME);

		JButton home_button_look_income = new JButton("HOME");
		home_button_look_income.setBounds(482, 455, 69, 23);
		LOOK_INCOME.add(home_button_look_income);
		look_income_table = new JTable(model_income_manager);

		JScrollPane income_scrollpane = new JScrollPane(look_income_table);
		income_scrollpane.setBounds(12, 10, 745, 435);

		LOOK_INCOME.add(income_scrollpane);

		JButton income_search_button = new JButton("SEARCH");
		income_search_button.setBounds(894, 66, 98, 23);
		LOOK_INCOME.add(income_search_button);

		JLabel lblNewLabel_1 = new JLabel("INCOME SEARCH");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(880, 46, 124, 15);
		LOOK_INCOME.add(lblNewLabel_1);
		MANAGER.setVisible(false);

		JPanel USER = new JPanel();
		USER.setBounds(0, 0, 1004, 561);
		WELCOME.getContentPane().add(USER);
		USER.setLayout(null);

		JPanel CHECK_OUT = new JPanel();
		CHECK_OUT.setBounds(0, 73, 1004, 488);
		USER.add(CHECK_OUT);
		CHECK_OUT.setLayout(null);

		JButton home_button_checkout = new JButton("HOME");

		home_button_checkout.setBounds(483, 455, 69, 23);
		CHECK_OUT.add(home_button_checkout);

		// 체크 아웃 테이블 설정.
		String[] checkout_heading = new String[] { "USER NAME", "CHECK OUT TIME", "USED HOUR", "PAYMENT" };
		DefaultTableModel model_checkout = new DefaultTableModel(checkout_heading, 0);
		check_out_table = new JTable(model_checkout);

		JScrollPane checkout_scrollpane = new JScrollPane(check_out_table);
		checkout_scrollpane.setBounds(12, 10, 745, 435);

		// ROOM_MANAGER.add(checkout_scrollpane);
		CHECK_OUT.add(checkout_scrollpane);

		JButton check_out_search_button = new JButton("OUT");
		check_out_search_button.setBounds(894, 10, 98, 54);
		CHECK_OUT.add(check_out_search_button);

		JLabel lblNewLabel_1_1_2 = new JLabel("PHONE");
		lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_2.setBounds(769, 45, 47, 15);
		CHECK_OUT.add(lblNewLabel_1_1_2);

		phone_textField = new JTextField();
		phone_textField.setColumns(10);
		phone_textField.setBounds(819, 41, 69, 23);
		CHECK_OUT.add(phone_textField);

		JLabel lblNewLabel_1_3 = new JLabel("PAY");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3.setBounds(774, 397, 27, 15);
		CHECK_OUT.add(lblNewLabel_1_3);

		pay_textField = new JTextField();
		pay_textField.setColumns(10);
		pay_textField.setBounds(813, 393, 69, 23);
		CHECK_OUT.add(pay_textField);

		JButton pay_button = new JButton("PAYMENT");
		pay_button.setBounds(894, 393, 98, 23);
		CHECK_OUT.add(pay_button);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("NAME");
		lblNewLabel_1_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_2_1.setBounds(769, 16, 47, 15);
		CHECK_OUT.add(lblNewLabel_1_1_2_1);

		name_textField = new JTextField();
		name_textField.setColumns(10);
		name_textField.setBounds(819, 12, 69, 23);
		CHECK_OUT.add(name_textField);

		JLabel lblNewLabel = new JLabel("STUDY CAFE : USER");
		lblNewLabel.setBounds(12, 10, 131, 15);
		USER.add(lblNewLabel);

		JPanel CHECK_IN = new JPanel();
		CHECK_IN.setBounds(0, 73, 1004, 488);
		USER.add(CHECK_IN);
		CHECK_IN.setLayout(null);

		JButton home_button_checkin = new JButton("HOME");
		home_button_checkin.setBounds(482, 455, 69, 23);
		CHECK_IN.add(home_button_checkin);

		// 체크인 테이블 설정
		String[] columnNames_checkin = new String[] { "ROOMNAME", "MAX", "PRICE" };
		DefaultTableModel model_checkin = new DefaultTableModel(columnNames_checkin, 0);
		check_in_table = new JTable(model_checkin);
		JScrollPane checkin_scrollpane = new JScrollPane(check_in_table);
		checkin_scrollpane.setBounds(12, 10, 745, 435);

		CHECK_IN.add(checkin_scrollpane);

		JLabel lblNewLabel_1_1_3 = new JLabel("MAX");
		lblNewLabel_1_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_3.setBounds(769, 28, 32, 22);
		CHECK_IN.add(lblNewLabel_1_1_3);

		checkin_max_textField = new JTextField();
		checkin_max_textField.setColumns(10);
		checkin_max_textField.setBounds(813, 28, 69, 23);
		CHECK_IN.add(checkin_max_textField);

		JButton max_search_button = new JButton("SEARCH");
		max_search_button.setBounds(894, 28, 98, 23);
		CHECK_IN.add(max_search_button);

		JLabel lblNewLabel_1_1_3_1 = new JLabel("USER");
		lblNewLabel_1_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_3_1.setBounds(770, 385, 32, 22);
		CHECK_IN.add(lblNewLabel_1_1_3_1);

		cehckin_user_textField = new JTextField();
		cehckin_user_textField.setColumns(10);
		cehckin_user_textField.setBounds(814, 385, 69, 23);
		CHECK_IN.add(cehckin_user_textField);

		JButton in_button = new JButton("CHECK IN");
		in_button.setBounds(894, 385, 98, 55);
		CHECK_IN.add(in_button);

		JLabel lblNewLabel_1_1_3_2 = new JLabel("PHONE");
		lblNewLabel_1_1_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_3_2.setBounds(763, 417, 47, 22);
		CHECK_IN.add(lblNewLabel_1_1_3_2);

		checkin_phone_textField = new JTextField();
		checkin_phone_textField.setColumns(10);
		checkin_phone_textField.setBounds(814, 417, 69, 23);
		CHECK_IN.add(checkin_phone_textField);

		JButton check_in_button = new JButton("CHECK IN");

		check_in_button.setBounds(22, 29, 137, 34);
		USER.add(check_in_button);

		JButton check_out_button = new JButton("CHECK OUT");
		check_out_button.setBounds(171, 29, 137, 34);
		USER.add(check_out_button);

		// START 페이지에서 MANAGER, USER 페이지 보지 않게 하는 것.
		MANAGER_LOGIN.setVisible(false);
		USER.setVisible(false);
		MANAGER_LOGIN.setVisible(false);

		// 버튼들 기능 구현
		home_button_checkin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				START.setVisible(true);
				USER.setVisible(false);
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(false);
				ROOM_MANAGER.setVisible(false);
				MANAGER_LOGIN.setVisible(false);
				LOOK_INCOME.setVisible(false);
			}
		});

		check_in_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CHECK_IN.setVisible(true);
				CHECK_OUT.setVisible(false);
			}
		});

		check_out_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(true);
			}
		});

		max_search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 다시 검색하면 새로운 검색 결과만 반영하기 위해 기존 데이터 지우기.
					for (int j = 0; j < check_in_table.getRowCount(); j++) {
						model_checkin.setNumRows(0);
					}

					int max = Integer.parseInt(checkin_max_textField.getText());
					ArrayList<Room> buff = m.searchRoom(max);
					int size = buff.size();
					for (int i = 0; i < size; i++) {
						Vector<String> r = new Vector<String>(3);
						r.add(buff.get(i).getRoomName());
						r.add(Integer.toString(buff.get(i).getMax()));
						r.add(Integer.toString(buff.get(i).getPrice())); // int -> String
						model_checkin.addRow(r);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		in_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = cehckin_user_textField.getText();
				String phone = checkin_phone_textField.getText();
				CafeUser user = new CafeUser(name, phone);

				int row = check_in_table.getSelectedRow();
				if (row == -1)
					JOptionPane.showMessageDialog(null, "체크인 할 테이블을 선택해주세요.");
				DefaultTableModel model = (DefaultTableModel) check_in_table.getModel();
				String RoomName = (String) model.getValueAt(row, 0);
				model.removeRow(row);

				// String RoomName = checkin_room_textField.getText();
				int max = Integer.parseInt(checkin_max_textField.getText());
				try {
					m.checkIn(max, RoomName, user);
					JOptionPane.showMessageDialog(null, "[Check In] 룸 이름 : " + m.checkI().getRoomName() + ", 시간 당 가격 : "
							+ m.checkI().getPrice() + ", 입장 시간 : " + m.checkI().checkInTime());
					cehckin_user_textField.setText("");
					checkin_phone_textField.setText("");
					checkin_max_textField.setText("");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		home_button_checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				START.setVisible(true);
				USER.setVisible(false);
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(false);
				ROOM_MANAGER.setVisible(false);
				MANAGER_LOGIN.setVisible(false);
				LOOK_INCOME.setVisible(false);
			}
		});

		check_out_search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String name = name_textField.getText();
				String phone = phone_textField.getText();

				CafeUser user = new CafeUser(name, phone);

				try {

					// 다시 검색하면 새로운 검색 결과만 반영하기 위해 기존 데이터 지우기.
					for (int j = 0; j < check_out_table.getRowCount(); j++) {
						model_checkout.setNumRows(0);
					}
					m.checkOut(user);
					Vector<String> r = new Vector<String>(4);
					r.add(user.getName());
					SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String to = transFormat.format(m.checkO().getCheckOutTime());
					r.add(to);
					r.add(Integer.toString(m.checkO().checkOutTime()));
					r.add(Integer.toString(m.checkO().pay())); // int -> String
					model_checkout.addRow(r);
					JOptionPane.showMessageDialog(null, "pay 에서 지불해야 하는 금액을 지불해야 체크아웃이 완료됩니다.");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		pay_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = name_textField.getText();
				String phone = phone_textField.getText();

				CafeUser user = new CafeUser(name, phone);

				while (true) { // 결제 과정
					int pay = Integer.parseInt(pay_textField.getText());

					try {
						if (m.payMent(user, pay)) { // 금액이 맞게 입력될 경우.
							m.incomeUpdate(date - 1, pay); // 소득 업데이트
							JOptionPane.showMessageDialog(null, "결제가 완료되었습니다."); // 방을 다시 비우는 과정 : 결제 완료 후 방 비우기.
							name_textField.setText("");
							phone_textField.setText("");
							pay_textField.setText("");
							break;
						} else {
							JOptionPane.showMessageDialog(null, "금액이 잘못입력되었습니다. 다시 결제 금액을 입력해주세요.");
							pay_textField.setText("");
							continue;
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		});
		home_button_room_manager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				START.setVisible(true);
				USER.setVisible(false);
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(false);
				ROOM_MANAGER.setVisible(false);
				MANAGER_LOGIN.setVisible(false);
				LOOK_INCOME.setVisible(false);
			}
		});

		home_button_look_income.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				START.setVisible(true);
				USER.setVisible(false);
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(false);
				ROOM_MANAGER.setVisible(false);
				MANAGER_LOGIN.setVisible(false);
				LOOK_INCOME.setVisible(false);
			}
		});

		room_manager_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				START.setVisible(false);
				USER.setVisible(false);
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(false);
				ROOM_MANAGER.setVisible(true);
				LOOK_INCOME.setVisible(false);
			}
		});

		look_income_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				START.setVisible(false);
				USER.setVisible(false);
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(false);
				ROOM_MANAGER.setVisible(false);
				LOOK_INCOME.setVisible(true);
			}
		});

		room_manager_max_search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 다시 검색하면 새로운 검색 결과만 반영하기 위해 기존 데이터 지우기.
					for (int j = 0; j < room_manager_table.getRowCount(); j++) {
						model_room_manager.setNumRows(0);
					}

					int max = Integer.parseInt(search_empty_max_textField.getText());
					ArrayList<Room> buff = m.searchRoom(max);
					int size = buff.size();
					for (int i = 0; i < size; i++) {
						Vector<String> r = new Vector<String>(4);
						r.add(buff.get(i).getRoomName());
						r.add(Integer.toString(buff.get(i).getMax()));
						r.add(Integer.toString(buff.get(i).getPrice())); // int -> String
						r.add(String.valueOf(buff.get(i).getIsEmpty())); // boolean -> String
						model_room_manager.addRow(r);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		room_manager_all_search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 다시 검색하면 새로운 검색 결과만 반영하기 위해 기존 데이터 지우기.
					for (int j = 0; j < room_manager_table.getRowCount(); j++) {
						model_room_manager.setNumRows(0);
					}

					ArrayList<Room> allbuff = m.allRoom();
					int size = allbuff.size();
					for (int i = 0; i < size; i++) {
						Vector<String> r = new Vector<String>(4);
						r.add(allbuff.get(i).getRoomName());
						r.add(Integer.toString(allbuff.get(i).getMax()));
						r.add(Integer.toString(allbuff.get(i).getPrice())); // int -> String
						r.add(String.valueOf(allbuff.get(i).getIsEmpty())); // boolean -> String
						model_room_manager.addRow(r);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		add_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int max = Integer.parseInt(room_manager_max_textField.getText());
				String RoomName = room_manager_textField.getText();
				int price = Integer.parseInt(room_manager_price_textField.getText());
				Room room = new Room(max, RoomName, price);

				if (m.setSeat(room)) {
					room_manager_max_textField.setText("");
					room_manager_textField.setText("");
					room_manager_price_textField.setText("");
					JOptionPane.showMessageDialog(null, "성공적으로 방이 생성되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "방이 생성되지 못했습니다. 다시 시도해주세요.");
				}
			}
		});

		delete_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int row = room_manager_table.getSelectedRow();
				if (row == -1)
					JOptionPane.showMessageDialog(null, "삭제할 테이블 데이터를 선택해주세요.");
				DefaultTableModel model = (DefaultTableModel) room_manager_table.getModel();
				String RoomName = (String) model.getValueAt(row, 0);
				model.removeRow(row);

				try {
					if (m.deleteRoom(RoomName)) {
						JOptionPane.showMessageDialog(null, "방이 정상적으로 삭제 되었습니다.");
					} else {
						JOptionPane.showMessageDialog(null, "방이 삭제되지 못했습니다. 방 이름을 다시 확인해주세요.");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		income_search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 다시 검색하면 새로운 검색 결과만 반영하기 위해 기존 데이터 지우기.
					for (int j = 0; j < look_income_table.getRowCount(); j++) {
						model_income_manager.setNumRows(0);
					}

					int incomebuff[] = m.all_get_income();
					int size = incomebuff.length;
					int accu_income = 0;
					for (int i = 0; i < size; i++) {
						if (incomebuff[i] == 0) {
							continue;
						}
						Vector<String> r = new Vector<String>(3);
						r.add(Integer.toString(month) + " / " + Integer.toString(i + 1));
						r.add(Integer.toString(incomebuff[i])); // int -> String
						accu_income += incomebuff[i];
						r.add(Integer.toString(accu_income));
						model_income_manager.addRow(r);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		manager_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MANAGER_LOGIN.setVisible(true);
				START.setVisible(false);
				USER.setVisible(false);
				MANAGER.setVisible(false);
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(false);
			}
		});

		user_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				USER.setVisible(true);
				MANAGER.setVisible(false);
				MANAGER_LOGIN.setVisible(false);
				START.setVisible(false);
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(false);
			}
		});

		login_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = login_textField.getText();
				if (id.equals(m.getManagerID())) {
					login_textField.setText("");
					MANAGER.setVisible(true);
					MANAGER_LOGIN.setVisible(false);
					START.setVisible(false);
					ROOM_MANAGER.setVisible(false);
					LOOK_INCOME.setVisible(false);
				} else {
					login_textField.setText("");
					JOptionPane.showMessageDialog(null, "TRY AGAIN!");
				}
			}
		});

		home_button_manager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				START.setVisible(true);
				USER.setVisible(false);
				CHECK_IN.setVisible(false);
				CHECK_OUT.setVisible(false);
				ROOM_MANAGER.setVisible(false);
				MANAGER_LOGIN.setVisible(false);
				LOOK_INCOME.setVisible(false);
			}
		});

	}
}
