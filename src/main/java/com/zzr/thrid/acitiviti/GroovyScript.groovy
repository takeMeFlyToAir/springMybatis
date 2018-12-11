package com.zzr.thrid.acitiviti

import com.zzr.activity.user.JavaTaskScript


class GroovyScript {
    void say(String name){
        println("Hello, $name! ")
    }
    def foo(){
        JavaTaskScript javaTaskScript = new JavaTaskScript();
        javaTaskScript.scriptTaskWriteToConsole();
    }

    static void main(String[] args) {
        GroovyScript groovyScript = new GroovyScript();
        groovyScript.say("zzr")
        groovyScript.foo();
    }
}



