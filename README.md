#CommonCompat 
this is a collection for the better development Of android developers. 
#How to use  
Activity: 
`TemplateActivity`  is an activity that supports `double tap quitting` and   `certificate checking`.you can use it like this: 
```java 
public class MyActivity extends TemplateActivity{ 
    @Override 
    public void onCreate(Bundle b){ 
        super.onCreate(b); 
        //set maximum duration between double tap to quit. 
        setDoubleTapDuration(3000);//3 seconds maximum 
        setQuitWhenDoublePressEnabled(false);//disable to quit when double press. 
        checkCertificate("My certificate name");//check certificate and return if it is expected 
        toast("hi");//make a Toast here. 
    } 
} 
``` 
Annotations 
ViewID is an annotation that can just set the View for you.  
Declare it in activity like this: 
```java
@ViewID(R.id.myview) 
Button hi;
``` 
and on activity#onCreate write this: 
```java
ViewGetter.initViews(this,this);//first arg is the Object you want to replace Fields in it.second arg is for findViewById.
``` 
#P.S:The class SnackBar is still in progress.
