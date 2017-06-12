package Structure;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AList extends java.util.ArrayList<Object>{

	private List<Object> aList;

	@XmlElement
	public List<Object> getaList() {
		return aList;
	}

	public void setaList(List<Object> aList) {
		this.aList = aList;
	}
	
	
}
