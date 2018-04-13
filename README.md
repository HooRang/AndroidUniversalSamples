# Demo Knowledge point #

## AndroidManifest.xml ##
    android:excludeFromRecents = "true" 不在最新使用列表中显示
    android:taskAffinity="" 指定任务栈 ,包名
    android:allowTaskReparenting = "true"用来标记Activity能否从启动的Task移动到taskAffinity指定的Task ,true 表示可以转移Task

 
 
 
## JavaScript && APP ##

### Custom URI scheme ###
    scheme://host/path?key=value
    js调用 ： 
    hoorang://jsopenapp?param=Custom URI scheme
    
    java解析：
    Uri uri = Uri.parse(url)
    
    防攻击，安全启动方法：
    // convert intent scheme URL to intent object  
    Intent intent = Intent.parseUri(uri);  
    // forbid launching activities without BROWSABLE category  
    intent.addCategory("android.intent.category.BROWSABLE");  
    // forbid explicit call  
    intent.setComponent(null);  
    // forbid intent with selector intent  
    intent.setSelector(null);  
    // start the activity by the intent  
    context.startActivityIfNeeded(intent, -1);  
    
    
### Intent-based URI ###
    intent://host#Intent;parameters;end
    
    js调用：
    hoorang://jsopenapp#Intent;action=android.intent.action.VIEW;category=android.intent.category.DEFAULT;category=android.intent.category.BROWSABLE;S.param=Intent-based URI;end
    
    参数获取：
    Intent dataStringIntent = Intent.parseUri(intent.getDataString() , 0);
    if(dataStringIntent.hasExtra("param")){
           message.append("param#" + dataStringIntent.getStringExtra("param"));
    }