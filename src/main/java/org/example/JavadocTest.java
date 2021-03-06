package org.example;

import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.Comment;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.MethodJavadoc;
import com.github.therapi.runtimejavadoc.OtherJavadoc;
import com.github.therapi.runtimejavadoc.ParamJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import com.github.therapi.runtimejavadoc.SeeAlsoJavadoc;
import com.github.therapi.runtimejavadoc.ThrowsJavadoc;

/**
 * @author zhangzicheng
 * @date 2021/03/06
 */
public class JavadocTest {

    // formatters are reusable and thread-safe
    private static final CommentFormatter formatter = new CommentFormatter();

    public static void printJavadoc(String fullyQualifiedClassName) {
        ClassJavadoc classDoc = RuntimeJavadoc.getJavadoc(fullyQualifiedClassName);
        if (classDoc.isEmpty()) {
            // optionally skip absent documentation
            System.out.println("no documentation for " + fullyQualifiedClassName);
            return;
        }
        System.out.println(classDoc.getName());
        System.out.println(format(classDoc.getComment()));
        // miscellaneous and custom javadoc tags (@author, etc.)
        for (OtherJavadoc other : classDoc.getOther()) {
            System.out.println(other.getName() + ": " + format(other.getComment()));
        }

        System.out.println();
        System.out.println("METHODS");
        for (MethodJavadoc methodDoc : classDoc.getMethods()) {
            printMethodJavadoc(methodDoc);
        }
    }

    private static void printMethodJavadoc(MethodJavadoc methodDoc) {
        System.out.println(methodDoc.getName() + methodDoc.getParamTypes());
        System.out.println(format(methodDoc.getComment()));

        if (!methodDoc.isConstructor()) {
            System.out.println("  returns " + format(methodDoc.getReturns()));
        }

        for (OtherJavadoc other : methodDoc.getOther()) {
            System.out.println("  " + other.getName() + ": "
                    + format(other.getComment()));
        }
        for (ParamJavadoc paramDoc : methodDoc.getParams()) {
            System.out.println("  param " + paramDoc.getName() + " "
                    + format(paramDoc.getComment()));
        }
        for (ThrowsJavadoc throwsDoc : methodDoc.getThrows()) {
            System.out.println("  throws " + throwsDoc.getName() + " "
                    + format(throwsDoc.getComment()));
        }
        System.out.println();
    }

    private static String format(Comment c) {
        return formatter.format(c);
    }

    public static void main(String[] args) {
        printJavadoc("org.example.TestController");
    }
}
