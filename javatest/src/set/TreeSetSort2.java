/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年9月14日 下午4:25:23 * 
*/
package set;
//第二种：

//
//在集合中定义排序 实现Comparator接口 覆盖compare方法。

//TreeSet(Comparator<? super E> comparator) 
//        构造一个新的空 TreeSet，它根据指定比较器进行排序。

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetSort2 {
	public static void main(String[] args) {
		TreeSet treeset = new TreeSet(new MyComp());// 定义一个集合
		treeset.add(new Person3(10, "liuyia"));
		treeset.add(new Person3(10, "liuyib"));
		treeset.add(new Person3(15, "liuyi34"));
		treeset.add(new Person3(11, "liuyi4"));
		treeset.add(new Person3(12, "liuyi4"));

		Iterator itera = treeset.iterator();
		while (itera.hasNext()) {
			System.out.println(itera.next());
		}

	}
}

class Person3 {
	private int age;

	private String name;

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

	public Person3(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public String toString() {
		return age + " = " + "name" + name;
	}
}

class MyComp implements Comparator {
	public int compare(Object o1, Object o2) {
		Person3 p1 = (Person3) o1;
		Person3 p2 = (Person3) o2;
		System.out.println((p1.getAge() - p2.getAge()));
		return (p1.getAge() - p2.getAge());
	}
}