
import java.util.*;
import java.io.*;

// ArrayList �� �����ؼ� ��� �Լ��� ���� Ȱ���ϱ�.
@SuppressWarnings("serial")
public class Manager implements Serializable {

	// manager �ʵ�
	transient private Room using = null; // ������� �� ���� : �Լ����� �� ��ü�� ���� Ȱ���ϱ� ����.
	transient private ArrayList<Room> Room = new ArrayList<Room>();
	private int[] income = new int[31]; // �Ѵ� ������ �ҵ��� ��Ƴ��� �迭
	transient private String managerID = null; // ������ �ź� Ȯ���� ���� id

	// ������
	public Manager(String managerID) {
		this.managerID = managerID;
	}

	/*
	 * public Manager(String managerID, int income[]) { this.managerID = managerID;
	 * for(int i = 0; i<income.length; i++) { this.income[i] = income[i]; } }
	 */

	public Manager() {
	}

	// ManagerID set �Լ�
	public void setManagerID(String changeID) {
		this.managerID = changeID;
	}

	public String getManagerID() {
		return managerID;
	}

	// ��� �� ã��.

	public ArrayList<Room> allRoom() throws Exception {
		if (Room.size() == 0) {
			throw new Exception("������ ���� �����ϴ�.");
		} else {
			return Room;
		}
	}

	// �� ũ�⿡ ���� �� �� ã��.
	public ArrayList<Room> searchRoom(int max) throws Exception {

		ArrayList<Room> buff = new ArrayList<Room>();
		boolean foundRoom = false;

		for (int i = 0; i < Room.size(); i++) {
			Room obj = Room.get(i);
			if (obj.getIsEmpty() && obj.getMax() >= max) {
				buff.add(obj);
				foundRoom = true;
			}
		}

		if (!foundRoom) // ã�� ������ �ÿ� ���� ó��
			throw new Exception("�˸��� ���� �����ϴ�.");

		return buff;
	}

	// room ���� : roomName, max, price �޾Ƽ� �� �����ϱ�.
	public boolean setSeat(Room newRoom) {
		return Room.add(newRoom);
	}

	// �� ����.
	public boolean deleteRoom(String Roomname) throws Exception { // ������ �� �¼� ��Ȳ���� ����
		Room obj = new Room(Roomname);
		boolean foundRoom = Room.contains(obj);

		if (foundRoom) {
			return Room.remove(obj);
		} else {
			throw new Exception("���� �����ϴ�.");
		}
	}

	// ���� ������Ʈ, ��ȸ.
	public void incomeUpdate(int date, int charge) { // ��¥���� income �迭 ������Ʈ
		income[date] += charge;
	}

	public int get_income(int start, int end) { // �˰� ���� �Ⱓ �� ����, �� ��¥�� �Է��Ͽ� ����Ͽ� �����ϴ� �Լ�
		int total = 0;
		for (int i = start - 1; i < end - 1; i++) {
			total += income[i];
		}
		return total;
	}

	// ���� ��ü ��ȸ
	public int[] all_get_income() {
		return income;
	}

	// üũ�� �Լ� : user �� max �ο��� �Է¹��� searchRoom �� ��ü�� �� üũ��.
	public void checkIn(int max, String roomname, CafeUser user) throws Exception {

		Room obj = new Room(roomname);
		boolean foundRoom = Room.contains(obj);

		if (foundRoom) {
			using = Room.get(Room.indexOf(obj));
			using.checkInTime();
			using.setUser(user); // �ش� �濡 user ����.
			using.setIsEmpty(false); // �� ���� �ƴ϶�� ǥ��.
		} else {
			throw new Exception("���� �����ϴ�.");
		}
	}

	// üũ ���ϰ� �ִ� ��ü ��ȯ
	public Room checkI() {
		return using;
	}

	// üũ�ƿ� �Լ� : user �� ����� ��� �ݾ��� ����ϰ� �� �ش� ��ü���� ���.
	public void checkOut(CafeUser user) throws Exception { // �濡 �ӹ��� ������� ���� ������� ������ �Է¹޾Ƽ� ���� ã��.
		// üũ�ƿ� �� �����ؾ� �ϴ� �ݾ� ��� �� �� �濡 null �� �Ҵ��Ͽ� �� ������
		Room room = new Room(user);
		boolean foundRoom = Room.contains(room);

		if (foundRoom) {
			using = Room.get(Room.indexOf(room));
		} else {
			throw new Exception("�ش��ϴ� ���� �����ϴ�.");
		}
	}

	// üũ �ƿ��ϰ� �ִ� ��ü ��ȯ
	public Room checkO() {
		return using;
	}

	// ���� �ް� �� �޾Ҵ��� �Ǵ��ϴ� �Լ�
	public boolean payMent(CafeUser user, int pay) throws Exception {
		if (using.pay() > pay || using.pay() < pay) {
			new Exception("�ݾ��� �˸��� �ʽ��ϴ�."); // �ͼ��� ������ ���� �� �����ֱ�.
			return false;
		} else if (using.pay() == pay) {
			using.setUser(null);
			using.setIsEmpty(true);
			return true;
		}
		return false;
	}

	// Room ������ ���� ����, ������Ʈ �ϴ� �Լ�.
	public void writeRoom(ObjectOutputStream out) throws Exception { // ������ �� ����.

		if (Room.size() == 0) {
			throw new Exception("������ ���� �����ϴ�.");
		}
		for (int i = 0; i < Room.size(); i++) {
			out.writeObject(Room.get(i));
		}
	}

	public void readRoom(ObjectInputStream in) throws Exception { // ���Ͽ� ����Ǿ� �ִ� ���� �о���� �� ������Ʈ
		while (true) {
			Room obj = (Room) in.readObject();

			if (obj.getIsEmpty()) {
				Room room = new Room(obj.getMax(), obj.getRoomName(), obj.getPrice());
				Room.add(room);
			} else if (!obj.getIsEmpty()) {
				Room room = new Room(obj.getMax(), obj.getRoomName(), obj.getPrice());
				room.setIsEmpty(false);
				CafeUser user = new CafeUser(obj.getUser().getName(), obj.getUser().getPhone());
				room.setUser(user);
				Room.add(room);
			} else {
				throw new Exception("������ ������ �����ϴ�.");
			}
		}
	}
	
	// Manager ������ ���� ����, ������Ʈ �ϴ� �Լ�.
	public void writeManager(ObjectOutputStream out2) throws Exception {
		for (int i = 0; i < income.length; i++) {
			out2.writeInt(income[i]);
		}
	}

	public void readManager(ObjectInputStream in2) throws Exception {
		while (true) {
			for (int i = 0; i < income.length; i++) {
				int obj_income = in2.readInt();
				this.income[i] = obj_income;
			}
		}
	}
	
}
