package ex1;

public class BizServiceImple implements BizService {

	@Override
	public void bizMethod1(String name) {
		System.out.println(name+"bizMethod1 ����!");		
	}

	@Override
	public String second() {
		StringBuffer sb = new StringBuffer();
		sb.append("-------------").append("\n");
		sb.append("��浿").append("\n");
		sb.append("-------------").append("\n");
		return sb.toString();
	}
}
