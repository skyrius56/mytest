package ex1;

public class BizServiceImple implements BizService {

	@Override
	public void bizMethod1(String name) {
		System.out.println(name+"bizMethod1 ¼öÇà!");		
	}

	@Override
	public String second() {
		StringBuffer sb = new StringBuffer();
		sb.append("-------------").append("\n");
		sb.append("±è±æµ¿").append("\n");
		sb.append("-------------").append("\n");
		return sb.toString();
	}
}
