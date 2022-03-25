
import java.text.ParseException;
import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public class Room implements Serializable {
	private int price = 2000; // 시간 당 가격
	private int max = 0; // 최대 인원 : 9, 4, 2
	private String roomName = null; // 방 이름
	
	private boolean isEmpty = true; // 빈 방인지 : default 비어있음.
	private CafeUser user = null; // 사용자 객체
	private Date start_date = new Date(); // 입장 시간
	private Date end_date = new Date(); // 퇴장시간.
	private int usedTime = 0; // 사용시간
	private int payment = 0; // 지불해야 하는 금액 
	
	// Room 생성자 : setMax : 생성자 함수로 객체를 생성할 때 max 값을 정해주는 것.
	Room(int max, String roomName, int price){ // checkin 할 때 이용.
		this.max = max;
		this.roomName = roomName;
		this.price = price;
	}
	
	Room(int max, String roomName){ // 인원에 따른 방을 찾을 때 이용.
		this.max = max;
		this.roomName = roomName;
	}
	
	Room(String roomname){ // 방 이름으로 찾을 때 이용.
		this.roomName = roomname;
	}
	
	Room(CafeUser user){ // user 만으로 찾을 때 사용.
		this.user = user;
	}
	
	Room(boolean isEmpty){ // 빈 방인지만으로 찾을 때 사용.
		this.isEmpty = isEmpty;
	}
	
	
	// price : get, set 함수
	public void setPrice(int price) { // 가격을 설정하는 함수
		this.price = price;
	}
	public int getPrice() { // 가격을 출력하는 함수
		return price;
	}
	
	// max : get, set 함수
	public void setMax(int max) { // max 값을 설정하는 함수 : UI에서 입력받은 max 값을 업데이트 하는 기능.
		this.max = max;
	}
	public int getMax() {
		return max;
	}
	
	// roomName : get, set 함수
	public void setRoomName(String roomname) {
		this.roomName = roomname;
	}
	public String getRoomName() {
		return roomName;
	}
	
	// user : get, set 함수
	public void setUser(CafeUser user) {
		this.user = user;
	}
	public CafeUser getUser() {
		return user;
	}
	
	// isEmpty : get, set 함수
	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	public boolean getIsEmpty() {
		return isEmpty;
	}
	
	// checkIn 시간을 저장하는 함수
	public Date checkInTime() { // 입장 시간을 start_date 에 저장함. 그 후 manager 에서 계산할 때 사용하는 함수
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
	
	// checkOut 시간을 저장하고, 사용 시간까지 리턴하는 함수
	// 하루가 지났을 때의 경우도 생각하여 코드 추가하기.
	public int checkOutTime() throws ParseException { // manager 에서 checkout 시 사용하는 함수인데, 사용자가 사용했던 시간을 리턴
		// 2. Calendar 객체 생성
		this.end_date = new Date();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		// 3. Date 객체를 Calendar로 변환
		start.setTime(start_date);
		end.setTime(end_date);
		
		usedTime = 0; // 총 사용시간(시간으로 계산. ex 2시간 30분 사용 >> usedTime = 3)

		// 날짜를 넘어갔을 경우
		if (start.get(Calendar.DATE) != end.get(Calendar.DATE)) {
			//2일 이상 날짜가 지나갔을 경우 24시간을 더한다.
			usedTime += 24*(end.get(Calendar.DATE) - start.get(Calendar.DATE) - 1); 
			// 날이 지나가고, 한시간 이내로 사용했을 경우
			if (start.get(Calendar.HOUR_OF_DAY) == 23 && end.get(Calendar.HOUR_OF_DAY) == 0
					&& start.get(Calendar.MINUTE) + end.get(Calendar.MINUTE) <= 60) {
				usedTime += 1;
			}
			// 나머지 경우
			else {
				// 기본 시간
				usedTime = 24 - start.get(Calendar.HOUR_OF_DAY) + end.get(Calendar.HOUR_OF_DAY);
				// 사용한 분 고려
				if ((60 - start.get(Calendar.MINUTE)) + end.get(Calendar.MINUTE) > 60)
					usedTime += 1;
			}

		}
		// 그 이외의 경우
		else {
			// 기본 시간
			usedTime = end.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY);
			// 사용한 분 고려
			if ((60 - start.get(Calendar.MINUTE)) + end.get(Calendar.MINUTE) > 60
					|| start.get(Calendar.MINUTE) - end.get(Calendar.MINUTE) == 0)
				usedTime += 1;
		}
		return usedTime;
	}
	
	// 체크아웃 시간을 리턴하는 함수 : 체크 아웃 시 안내문에 출력하는 용도로 활용
	public Date getCheckOutTime() {
		return end_date;
	}
	
	// 지불해야 하는 금액을 리턴하는 함수
	public int pay() throws ParseException { 
		// manager 에서 checkout 시 사용하는 함수인데, 사용자에게 지불해야 하는 금액을 알려줌.
		this.payment = checkOutTime() * price;
		return payment;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Room) {
			//방 이름
			if(this.roomName!=null && ((Room) o).getRoomName()!=null && this.roomName.equals(((Room) o).getRoomName()))
					return true;
			//사용자 전화번호
			else if(this.user!=null && ((Room) o).getUser()!=null && this.user.getPhone().equals(((Room) o).getUser().getPhone()))
				return true;
			
			else
				return false;
		}
		
		return false;
	}

}

