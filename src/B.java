/** Test class you can remove it **/

public class B {
	private String name;
	
	

	public B(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "(B) " + name ;
	}
	
}
