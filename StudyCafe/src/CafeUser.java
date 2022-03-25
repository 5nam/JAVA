import java.io.Serializable;

@SuppressWarnings("serial")
public class CafeUser implements Serializable{
	private String name; // ����� �̸�
	private String phone; // ����� �޴��� ��ȣ
	
	public CafeUser(){} // �Ű� ���� ���� ������
	public CafeUser(String name, String phone){ // �̸��� ��ȭ��ȣ�� �Ű������� �ִ� ������
		this.name=name;
		this.phone=phone;
	}
	
	public void setName(String name) { // �̸��� ������ �� �ִ� set �Լ�
		this.name = name;
	}
	public void setPhone(String phone) { // ��ȭ��ȣ�� ������ �� �ִ� set �Լ�
		this.phone = phone;
	}
	public String getName() { // �̸��� �����ϴ� get �Լ�
		return name;
	}
	public String getPhone() { // ��ȭ��ȣ�� �����ϴ� get �Լ�
		return phone;
	}
}
