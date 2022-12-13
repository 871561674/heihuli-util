#!/bin/bash

#该脚本为模板，内部嵌套expect脚本
#用户
user=$1
echo "user：$user"
#密码
password=$2
echo "password：$password"
#端口
port=$3
echo "port：$port"
#ip字符串，英文逗号分割
nodes=$4
echo "nodes：$nodes"
node_arr=(${nodes//,/ })
#超时时间
time_out=$5
echo "time_out：$time_out"
#注意expect内容不能用空格，要用制表符，不然会报错
for (( i = 0; i < ${#node_arr[@]}; i++ )); do
expect <<EOF
set timeout -1
if { "$time_out" != "" } {
  set timeout $time_out
}
puts "超时时间: $time_out"
spawn /usr/bin/ssh -p $port $user@${node_arr[i]}
expect {
        "yes/no*" {send "yes\r"; exp_continue}
        "password:" {send "$password\r"};
}
expect "]*" { send "mkdir /heihuli\r" }
expect "]*" { send "sleep 3\r" }
expect "]*" { send "exit\r" }
expect eof
EOF
done

# 调用案例: sh shellexp.sh root 123456 22 172.30.0.121,172.30.0.77