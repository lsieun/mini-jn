# mini-jn

:bug:

`mini-jn`是对 [ja-netfilter](https://github.com/ja-netfilter/ja-netfilter) 的精简。

代码克隆：

```text
git clone https://gitee.com/lsieun/mini-jn
```

视频介绍：

```text
https://www.bilibili.com/video/BV1nS4y1r7Ud
```

## 开发环境

- Java 8
- Maven

## 为什么进行精简

之所以进行精简，是为了将原ja-netfilter项目当中最核心的部分（Core）提取出来，而忽略掉一些配置（Configuration）和扩展性（Extensibility）方面的代码逻辑。

当然，并不是说原ja-netfilter项目的代码不好，只是说它的扩展代码方式（scaffold）隐藏了诸多的细节，不容易让我们理解到代码的本质。

## 知识准备

如果我们想要完整理解`mini-jn`的代码，则需要两方面的知识：

- [Java Agent](https://ke.qq.com/course/4335150) 相关的知识，它提供的是一个修改字节码的机会（Opportunity）
- [Java ASM](https://gitee.com/lsieun/learn-java-asm) 相关的知识，它提供的是一个修改字节码的工具（Tool）

这两者之间的关系，可以描述成这样：

```text
.class --- Java ASM --- Java Agent --- JVM
```

形象的来说，有一个JVM，它是一个密闭的房子；
Java Agent为这个房子开了一扇门，里面的物品（正在加载的类、已经加载的类）就可以被外界访问了；
Java ASM是一个工具箱，利用这个工具箱可以对房子里的物品进行修改。

## 代码结构

在`mini-jn`项目当中，代码分成了三个部分：

- `boot`包，是不可缺少的辅助部分。
- `jn`包，是主体部分，其入口是`jn.agent.LoadTimeAgent`类。
- `run`和`sample`包，用于测试。

基于`mini-jn`的代码，我们会生成两个Jar文件：

- `mini-jn.jar`：包含`jn`目录的代码，由System ClassLoader进行加载。
- `boot-support.jar`：包含`boot`目录的代码，由Bootstrap ClassLoader进行加载。

这两个Jar包是如何关联在一起的呢？在`mini-jn.jar`的`META-INF/MANIFEST.MF`文件中，配置了`Boot-Class-Path`属性：

```text
Boot-Class-Path: boot-support.jar
```

## 如何使用

首先，生成`mini-jn.jar`文件：

```text
$ mvn clean package
```

其次，生成`boot-support.jar`文件：

```text
$ cd ./target/classes/
$ jar -cvf boot-support.jar boot/
```

接着，将`mini-jn.jar`和`boot-support.jar`这两个文件复制到同一个目录当中。

最后，配合`-javaagent`选项和`Const.ACTIVATION_CODE`一起使用：

```text
-javaagent:/path/to/mini-jn.jar
```

## 如何配置

在`mini-jn`代码中，并没有提供配置文件；如果想修改某些信息，该怎么办呢？

直接修改`boot.filter`目录下的类文件就可以了。
