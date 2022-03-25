import java.io.Serializable;

@SuppressWarnings("serial")
public class CafeUser implements Serializable{
	private String name; // 사용자 이름
	private String phone; // 사용자 휴대폰 번호
	
	public CafeUser(){} // 매개 변수 없는 생성자
	public CafeUser(String name, String phone){ // 이름과 전화번호가 매개변수로 있는 생성자
		this.name=name;
		this.phone=phone;
	}
	
	public void setName(String name) { // 이름을 설정할 수 있는 set 함수
		this.name = name;
	}
	public void setPhone(String phone) { // 전화번호를 설정할 수 있는 set 함수
		this.phone = phone;
	}
	public String getName() { // 이름을 리턴하는 get 함수
		return name;
	}
	public String getPhone() { // 전화번호를 리턴하는 get 함수
		return phone;
	}
}
