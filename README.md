# MDSimpleNotes
简单笔记

##项目介绍

###项目简介
* 基于Material Design的简单记事本（然而目前状态栏没有变色）

###实现的功能
* 返回自动存储笔记，后台时自动存储笔记
* 调用系统闹钟
* 导出文本txt
* 分享与接收分享
* 编辑界面字号大小调节、复制到剪切板、清空及删除
* 预览界面点击链接跳转，长按卡片多种操作

###遇到的问题及解决办法
* 设置autoLink后，链接的点击事件和TextView的点击事件相冲突，复写TextView解决
* 为了免去手动存储，而将存储方法放在了onPause()里，这导致数据库的save方法会在后台——呼出——后台——呼出中重复执行，致使同一个笔记在预览界面出现多次。后将save方法移出，仅在onPause()中执行update方法解决
* recyclerView的notifyDataSetChanged()方法无效，无法更新预览界面，目前原因未知，只得以重新加载recyclerView替代


##截图
![navi界面1](http://ww2.sinaimg.cn/mw690/74de6eafgw1euf5hkxhknj20a00hs75c.jpg)
![预览界面](http://ww2.sinaimg.cn/mw690/74de6eafgw1euf5hltlcjj20a00hs76d.jpg)
![预览界面2](http://ww1.sinaimg.cn/mw690/74de6eafgw1euf5hmvydtj20a00hsq47.jpg)
![编辑界面1](http://ww1.sinaimg.cn/mw690/74de6eafgw1euf5hnxu7aj20a00hsgnq.jpg)
![编辑界面2](http://ww3.sinaimg.cn/mw690/74de6eafgw1euf5hotr0sj20a00hs0uv.jpg)


