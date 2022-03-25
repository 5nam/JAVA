
import java.util.*;
import java.io.*;

// ArrayList 로 리턴해서 멤버 함수를 많이 활용하기.
@SuppressWarnings("serial")
public class Manager implements Serializable {

	// manager 필드
	transient private Room using = null; // 사용중인 룸 저장 : 함수에서 방 객체를 쉽게 활용하기 위해.
	transient private ArrayList<Room> Room = new ArrayList<Room>();
	private int[] income = new int[31]; // 한달 단위로 소득을 담아놓는 배열
	transient private String managerID = null; // 관리자 신분 확인을 위한 id

	// 생성자
	public Manager(String managerID) {
		this.managerID = managerID;
	}

	/*
	 * public Manager(String managerID, int income[]) { this.managerID = managerID;
	 * for(int i = 0; i<income.length; i++) { this.income[i] = income[i]; } }
	 */

	public Manager() {
	}

	// ManagerID set 함수
	public void setManagerID(String changeID) {
		this.managerID = changeID;
	}

	public String getManagerID() {
		return managerID;
	}

	// 모든 방 찾기.

	public ArrayList<Room> allRoom() throws Exception {
		if (Room.size() == 0) {
			throw new Exception("생성된 방이 없습니다.");
		} else {
			return Room;
		}
	}

	// 방 크기에 따른 빈 방 찾기.
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

		if (!foundRoom) // 찾지 못했을 시에 예외 처리
			throw new Exception("알맞은 방이 없습니다.");

		return buff;
	}

	// room 생성 : roomName, max, price 받아서 방 생성하기.
	public boolean setSeat(Room newRoom) {
		return Room.add(newRoom);
	}

	// 방 삭제.
	public boolean deleteRoom(String Roomname) throws Exception { // 삭제한 후 좌석 현황까지 리턴
		Room obj = new Room(Roomname);
		boolean foundRoom = Room.contains(obj);

		if (foundRoom) {
			return Room.remove(obj);
		} else {
			throw new Exception("방이 없습니다.");
		}
	}

	// 수입 업데이트, 조회.
	public void incomeUpdate(int date, int charge) { // 날짜별로 income 배열 업데이트
		income[date] += charge;
	}

	public int get_income(int start, int end) { // 알고 싶은 기간 중 시작, 끝 날짜를 입력하여 계산하여 리턴하는 함수
		int total = 0;
		for (int i = start - 1; i < end - 1; i++) {
			total += income[i];
		}
		return total;
	}

	// 수입 전체 조회
	public int[] all_get_income() {
		return income;
	}

	// 체크인 함수 : user 와 max 인원을 입력받은 searchRoom 의 객체로 방 체크인.
	public void checkIn(int max, String roomname, CafeUser user) throws Exception {

		Room obj = new Room(roomname);
		boolean foundRoom = Room.contains(obj);

		if (foundRoom) {
			using = Room.get(Room.indexOf(obj));
			using.checkInTime();
			using.setUser(user); // 해당 방에 user 설정.
			using.setIsEmpty(false); // 빈 방이 아니라고 표시.
		} else {
			throw new Exception("방이 없습니다.");
		}
	}

	// 체크 인하고 있는 객체 반환
	public Room checkI() {
		return using;
	}

	// 체크아웃 함수 : user 가 사용한 방과 금액을 출력하고 그 해당 객체까지 출력.
	public void checkOut(CafeUser user) throws Exception { // 방에 머무른 사람들의 수와 사용자의 정보를 입력받아서 방을 찾음.
		// 체크아웃 시 지불해야 하는 금액 출력 후 그 방에 null 을 할당하여 빈 방으로
		Room room = new Room(user);
		boolean foundRoom = Room.contains(room);

		if (foundRoom) {
			using = Room.get(Room.indexOf(room));
		} else {
			throw new Exception("해당하는 방이 없습니다.");
		}
	}

	// 체크 아웃하고 있는 객체 반환
	public Room checkO() {
		return using;
	}

	// 돈을 받고 잘 받았는지 판단하는 함수
	public boolean payMent(CafeUser user, int pay) throws Exception {
		if (using.pay() > pay || using.pay() < pay) {
			new Exception("금액이 알맞지 않습니다."); // 익셉션 구분을 위해 꼭 적어주기.
			return false;
		} else if (using.pay() == pay) {
			using.setUser(null);
			using.setIsEmpty(true);
			return true;
		}
		return false;
	}

	// Room 정보를 파일 저장, 업데이트 하는 함수.
	public void writeRoom(ObjectOutputStream out) throws Exception { // 생성한 방 저장.

		if (Room.size() == 0) {
			throw new Exception("저장할 방이 없습니다.");
		}
		for (int i = 0; i < Room.size(); i++) {
			out.writeObject(Room.get(i));
		}
	}

	public void readRoom(ObjectInputStream in) throws Exception { // 파일에 저장되어 있는 정보 읽어오기 및 업데이트
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
				throw new Exception("파일의 내용이 없습니다.");
			}
		}
	}
	
	// Manager 정보를 파일 저장, 업데이트 하는 함수.
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
