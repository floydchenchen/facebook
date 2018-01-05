/*
* 问题描述
* 给一个linkedlist，里面的element都排序好了，但是是一个blackbox，有三个function可以调用。
* pop()随机pop出最前面或最后面的element，peek()随机偷看最前面或最后面的element，isEmpty()回传linkedlist是不是空了。
* 问设计一个数据结构，list或是array都可以，把linkedlist里面所有的element都拿出来，并保持他们的排序。followup是如果不能用peek()该怎么做。
*
* 思路：
* 1. 声明两个list 一个队列前面前面的数， 一个放后面的数
* 2. 一次性pop 两个值，比较他们的大小，然后再peek第三个值，来判断前面两个各应该放在哪里.
* 3.直接把两个队列收尾相接拼在一起
* */