#include <jni.h>
#include <string>
#include <iostream>
#include <fstream>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_banckaccount_MainActivity_stringFromJNI(JNIEnv* env,jobject /* this */)
{
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}



extern "C" JNIEXPORT jstring JNICALL
Java_com_example_banckaccount_MainActivity_EnCode(JNIEnv* env,jobject /* this */, jstring hashedbrute)
{
    jboolean isCopy;
    const char *convertedValue = (env)->GetStringUTFChars(hashedbrute, &isCopy);
    std::string hashed = std::string(convertedValue);
    std::string tohash = hashed;
    int i=0;
    for(char& c : tohash) {
        if(i%2!=0)
        {
            if(i==3 || i==13 ||i==19 ||i==29 ||i==39 ||i==41 ||i==57 ||i==79 ||i==81 ||i==101 ||i==97 ||i==95 ||i==15 ){
                c-=5;
            }
            else{
                if(i%17==0){
                    c-=6;
                }
                else{
                    if(i==17 || i==31 ||i==27 ||i==63 ||i==43 ||i==25 ||i==45  ||i==115 ||i==125 ){
                        c-=2;
                    }
                    else{
                        if(i%5==0 ||i%19==0){
                            c-=4;
                        }
                        else{
                            if(i%3==0){
                                c-=3;
                            }else{
                                if(i%7==0){
                                    c-=1;
                                }
                                else{
                                    c+=1;
                                }
                            }
                        }
                    }
                }
            }
        } else{
            if(i==8 || i==18 ||i==12 ||i==20 ||i==34 ||i==36 ||i==38 ||i==40 ||i==52 ||i==88 ||i==84 ||i==76 ||i==100 ||i==102){
                c-=4;
            }
            else{
                if(i%20==0 || i%18==0){
                    c-=3;
                }
                else{
                    if(i%8==0){
                        c-=5;
                    }
                    else{
                        if(i%44==0){
                            c-=1;
                        }
                        else{
                            if(i%16==0){
                                c-=4;
                            } else{
                                c-=5;
                            }
                        }
                    }
                }
            };
        }
        i++;
    }
    return env->NewStringUTF(tohash.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_banckaccount_MainActivity_DeCode(JNIEnv* env,jobject /* this */, jstring hashedbrute)
{
    jboolean isCopy;
    const char *convertedValue = (env)->GetStringUTFChars(hashedbrute, &isCopy);
    std::string hashed = std::string(convertedValue);
    std::string tohash = hashed;
    int i=0;

    for(char& c : tohash) {
        if(i%2!=0)
        {
            if(i==3 || i==13 ||i==19 ||i==29 ||i==39 ||i==41 ||i==57 ||i==79 ||i==81 ||i==101 ||i==97 ||i==95 ||i==15 ){
                c+=5;
            }
            else{
                if(i%17==0){
                    c+=6;
                }
                else{
                    if(i==17 || i==31 ||i==27 ||i==63 ||i==43 ||i==25 ||i==45 ||i==115 ||i==125 ){
                        c+=2;
                    }
                    else{
                        if(i%5==0 ||i%19==0){
                            c+=4;
                        }
                        else{
                            if(i%3==0){
                                c+=3;
                            }else{
                                if(i%7==0){
                                    c+=1;
                                }
                                else{
                                    c-=1;
                                }
                            }
                        }
                    }
                }
            }
        } else{
            if(i==8 || i==18 ||i==12 ||i==20 ||i==34 ||i==36 ||i==38 ||i==40 ||i==52 ||i==88 ||i==84 ||i==76 ||i==100 ||i==102){
                c+=4;
            }
            else{
                if(i%20==0 || i%18==0){
                    c+=3;
                }
                else{
                    if(i%8==0){
                        c+=5;
                    }
                    else{
                        if(i%44==0){
                            c+=1;
                        }
                        else{
                            if(i%16==0){
                                c+=4;
                            } else{
                                c+=5;
                            }
                        }
                    }
                }
            };
        }
        i++;
    }
    return env->NewStringUTF(tohash.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_banckaccount_MainActivity_CPPgetURL(JNIEnv* env,jobject ){
    return env->NewStringUTF("https://60102f166c21e10017050128.mockapi.io/labbbank/accounts");
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_banckaccount_MainActivity_CPPgetCLEF(JNIEnv* env,jobject ){
    return env->NewStringUTF("123456");
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_banckaccount_MainActivity_CPPgetPRENOM(JNIEnv* env,jobject ){
    return env->NewStringUTF("Georgiana");
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_banckaccount_MainActivity_CPPgetNOM(JNIEnv* env,jobject ){
    return env->NewStringUTF("Maggio");
}
