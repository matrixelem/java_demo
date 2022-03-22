package com.example.java_demo.dynamic_bean;

import net.openhft.compiler.CompilerUtils;

/**
 * 根据字符串动态生成Java代码
 */
public class DynamicCodeGen {
    public static void main(String[] args) throws Exception{
        String className = "com.example.practice_demo.t1.User";
        String code = "package com.example.practice_demo.t1;\n" +
                "\n" +
                "public class User implements UserInterface{\n" +
                "\n" +
                "\n" +
                "    @Override\n" +
                "    public void add() {\n" +
                "        System.out.println(\"sdsdsdsdsdsd\");\n" +
                "    }\n" +
                "}";
        Class clz = CompilerUtils.CACHED_COMPILER.loadFromJava(className, code);
        UserInterface user = (UserInterface) clz.newInstance();
        user.add();
    }
}
