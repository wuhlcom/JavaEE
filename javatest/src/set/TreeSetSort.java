/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年9月14日 下午4:23:49 * 
*/
package set;
//Treeset 的自定义的两种排序方式

//第一种：在元素中定义排序规则。元素自身具有比较性实现Comparable接口 覆盖compareTo方法
import java.util.Iterator;
import java.util.TreeSet;

/***
 * TreeSet是一个有序集合，TreeSet中元素将按照升序排列，缺省是按照 自然顺序进行排列，意味着TreeSet中元素要实现Comparable接口。
 * 我们可以在构造TreeSet对象时，传递实现了Comparator接口的比较器对象。 注意排序時：當主要的條件相同時，判斷次要條件。
 * 
 * @author Administrator
 *
 */
public class TreeSetSort {
	public static void main(String[] args) {
		TreeSet treeset = new TreeSet();// 定义一个集合
		treeset.add(new Person(10, "liuyia"));
		treeset.add(new Person(10, "liuyib"));
		treeset.add(new Person(15, "liuyi34"));
		treeset.add(new Person(11, "liuyi4"));
		treeset.add(new Person(12, "liuyi4"));
		System.out.println(treeset);
		Iterator itera = treeset.iterator();
		while (itera.hasNext()) {
			System.out.println(itera.next());
		}

	}
}

class Person implements Comparable {// 实现Comparable 接口 private int age;

	private String name;
	private int age;

	public Person(int age, String name) {
		this.age = age;
		this.name = name;

	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Object o) {
		if (!(o instanceof Person))
			throw new RuntimeException("對象不對哇！！");
		Person p = (Person) o;
		if (this.age > p.age) {
			return 1;
		}
		if (this.age < p.age) {
			return -1;
		}

		if (this.age == p.age) {
			return this.name.compareTo(p.name);// 當主要的條件也就是age的值相同時時候此時判斷次要條件姓名
		}

		return 1;

	}

	// 用於設置打印結果
	public String toString() {
		return age + " = " + "name" + name;
	}
}
