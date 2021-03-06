package com.ann.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User t = new User();
		System.out.println(t.sing());

		Foo f = new Foo();
		f.foo();

		try {
			// 1使用类加载器
			Class<?> clazz = Class.forName("com.ann.test.Child");
			// 获取类注解
			boolean isExist = clazz.isAnnotationPresent(Description.class);
			if (isExist) {
				Description d = (Description) clazz.getAnnotation(Description.class);
				System.out.println(d.value());
			}

			Method[] ms = clazz.getMethods();
			for (Method m : ms) {
				boolean isMExist = clazz.isAnnotationPresent(Description.class);
				if (isMExist) {
					Description d = m.getAnnotation(Description.class);
					System.out.println(d.value());
				}
			}

			for (Method m : ms) {
				Annotation[] as = m.getAnnotations();
				for (Annotation a : as) {
					if (a instanceof Description) {						
						Description d = (Description) a;
						System.out.println(d.value());
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(22);
			System.out.println(e.getMessage());		
		}
	}
}
