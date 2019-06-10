package us.myles_selim.starota.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import us.myles_selim.starota.Starota;

public class StarotaTest {

	public static void main(String... args) {
		Starota.main(args);

		int numRan = 0;
		int numPassed = 0;
		int numFailed = 0;
		Reflections ref = new Reflections("us.myles_selim.starota", new MethodAnnotationsScanner());
		for (Method cl : ref.getMethodsAnnotatedWith(TestTarget.class)) {
			String methodName = cl.getDeclaringClass().getName() + "." + cl.getName();
			if (cl.getReturnType() != boolean.class || !Modifier.isStatic(cl.getModifiers())) {
				System.out.println("method " + methodName + " is not a static boolean returning method");
				return;
			}
			try {
				boolean result = (boolean) cl.invoke(null);
				numRan++;
				if (result) {
					numPassed++;
					System.out.println("Test " + methodName + " has passed");
				} else {
					numFailed++;
					System.out.println("Test " + methodName + " has NOT passed");
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				numFailed++;
				e.printStackTrace();
			}
		}

		System.out.println("num tests ran: " + numRan);
		System.out.println("num tests passed: " + numPassed + "/" + numRan);
		System.out.println("num tests failed: " + numFailed + "/" + numRan);
	}

}
