
import java.text.ParseException;
import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public class Room implements Serializable {
	private int price = 2000; // �ð� �� ����
	private int max = 0; // �ִ� �ο� : 9, 4, 2
	private String roomName = null; // �� �̸�
	
	private boolean isEmpty = true; // �� ������ : default �������.
	private CafeUser user = null; // ����� ��ü
	private Date start_date = new Date(); // ���� �ð�
	private Date end_date = new Date(); // ����ð�.
	private int usedTime = 0; // ���ð�
	private int payment = 0; // �����ؾ� �ϴ� �ݾ� 
	
	// Room ������ : setMax : ������ �Լ��� ��ü�� ������ �� max ���� �����ִ� ��.
	Room(int max, String roomName, int price){ // checkin �� �� �̿�.
		this.max = max;
		this.roomName = roomName;
		this.price = price;
	}
	
	Room(int max, String roomName){ // �ο��� ���� ���� ã�� �� �̿�.
		this.max = max;
		this.roomName = roomName;
	}
	
	Room(String roomname){ // �� �̸����� ã�� �� �̿�.
		this.roomName = roomname;
	}
	
	Room(CafeUser user){ // user ������ ã�� �� ���.
		this.user = user;
	}
	
	Room(boolean isEmpty){ // �� ������������ ã�� �� ���.
		this.isEmpty = isEmpty;
	}
	
	
	// price : get, set �Լ�
	public void setPrice(int price) { // ������ �����ϴ� �Լ�
		this.price = price;
	}
	public int getPrice() { // ������ ����ϴ� �Լ�
		return price;
	}
	
	// max : get, set �Լ�
	public void setMax(int max) { // max ���� �����ϴ� �Լ� : UI���� �Է¹��� max ���� ������Ʈ �ϴ� ���.
		this.max = max;
	}
	public int getMax() {
		return max;
	}
	
	// roomName : get, set �Լ�
	public void setRoomName(String roomname) {
		this.roomName = roomname;
	}
	public String getRoomName() {
		return roomName;
	}
	
	// user : get, set �Լ�
	public void setUser(CafeUser user) {
		this.user = user;
	}
	public CafeUser getUser() {
		return user;
	}
	
	// isEmpty : get, set �Լ�
	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	public boolean getIsEmpty() {
		return isEmpty;
	}
	
	// checkIn �ð��� �����ϴ� �Լ�
	public Date checkInTime() { // ���� �ð��� start_date �� ������. �� �� manager ���� ����� �� ����ϴ� �Լ�
		// calendar = new GregorianCalendar();
		// startTime = new GregorianCalendar();
		start_date = new Date();
		return start_date;
	}
	
	public void setChcekInTime(Date start_date) {
		this.start_date = start_date;
	}
	
	public Date getcheckInTime() {
		return start_date;
	}
	
	// checkOut �ð��� �����ϰ�, ��� �ð����� �����ϴ� �Լ�
	// �Ϸ簡 ������ ���� ��쵵 �����Ͽ� �ڵ� �߰��ϱ�.
	public int checkOutTime() throws ParseException { // manager ���� checkout �� ����ϴ� �Լ��ε�, ����ڰ� ����ߴ� �ð��� ����
		// 2. Calendar ��ü ����
		this.end_date = new Date();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		// 3. Date ��ü�� Calendar�� ��ȯ
		start.setTime(start_date);
		end.setTime(end_date);
		
		usedTime = 0; // �� ���ð�(�ð����� ���. ex 2�ð� 30�� ��� >> usedTime = 3)

		// ��¥�� �Ѿ�� ���
		if (start.get(Calendar.DATE) != end.get(Calendar.DATE)) {
			//2�� �̻� ��¥�� �������� ��� 24�ð��� ���Ѵ�.
			usedTime += 24*(end.get(Calendar.DATE) - start.get(Calendar.DATE) - 1); 
			// ���� ��������, �ѽð� �̳��� ������� ���
			if (start.get(Calendar.HOUR_OF_DAY) == 23 && end.get(Calendar.HOUR_OF_DAY) == 0
					&& start.get(Calendar.MINUTE) + end.get(Calendar.MINUTE) <= 60) {
				usedTime += 1;
			}
			// ������ ���
			else {
				// �⺻ �ð�
				usedTime = 24 - start.get(Calendar.HOUR_OF_DAY) + end.get(Calendar.HOUR_OF_DAY);
				// ����� �� ���
				if ((60 - start.get(Calendar.MINUTE)) + end.get(Calendar.MINUTE) > 60)
					usedTime += 1;
			}

		}
		// �� �̿��� ���
		else {
			// �⺻ �ð�
			usedTime = end.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY);
			// ����� �� ���
			if ((60 - start.get(Calendar.MINUTE)) + end.get(Calendar.MINUTE) > 60
					|| start.get(Calendar.MINUTE) - end.get(Calendar.MINUTE) == 0)
				usedTime += 1;
		}
		return usedTime;
	}
	
	// üũ�ƿ� �ð��� �����ϴ� �Լ� : üũ �ƿ� �� �ȳ����� ����ϴ� �뵵�� Ȱ��
	public Date getCheckOutTime() {
		return end_date;
	}
	
	// �����ؾ� �ϴ� �ݾ��� �����ϴ� �Լ�
	public int pay() throws ParseException { 
		// manager ���� checkout �� ����ϴ� �Լ��ε�, ����ڿ��� �����ؾ� �ϴ� �ݾ��� �˷���.
		this.payment = checkOutTime() * price;
		return payment;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Room) {
			//�� �̸�
			if(this.roomName!=null && ((Room) o).getRoomName()!=null && this.roomName.equals(((Room) o).getRoomName()))
					return true;
			//����� ��ȭ��ȣ
			else if(this.user!=null && ((Room) o).getUser()!=null && this.user.getPhone().equals(((Room) o).getUser().getPhone()))
				return true;
			
			else
				return false;
		}
		
		return false;
	}

}

