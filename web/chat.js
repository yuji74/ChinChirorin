/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var webSocket;
// ユーザー名を取得，表示
var name = window.localStorage.getItem("name")
window.onload = function () {
    //var user = new Packages.user.User("sogawa", 1000);
    var forRtoA = document.createElement('a');
    forRtoA.href = "loadMessage";

    // WebSocketオブジェクトの作成
    webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));

    // メッセージエリアのオブジェクトを取得
    var messageArea = document.getElementById("messageArea");

    // メッセージエリアにテキストを出力
    var appendMessage = function (value, color) {
        var messageElement = document.createElement("div");
        messageElement.style.color = color;
        messageElement.innerText = value;
        messageArea.insertBefore(messageElement, messageArea.firstChild);
    }


    // 通信用WebSocketのイベントハンドラ設定
    webSocket.onopen = function () {
        appendMessage("opened", "blue");
        webSocket.send(name + ":");
    }
    webSocket.onclose = function () {
        appendMessage("Closed", "red");
    }
    webSocket.onmessage = function (message) {
        var data = JSON.parse(message.data);
        if ("message" == data.command) {
            appendMessage(data.text, "black");
        } else if ("error" == data.command) {
            appendMessage(data.text, "red");
        }
    }
    webSocket.onerror = function (message) {
        appendMessage(message, "red");
    }

    // エンターキーが押されたとき、空文字でなければメッセージを
    // WebSocketに送信し、メッセージエリアを空にする
    var messageInput = document.getElementById("messageInput");
    messageInput.onkeypress = function (e) {
        if (13 == e.keyCode) {
            var message = messageInput.value;
            if (webSocket && "" != message) {
                webSocket.send(name + ":" + message);
                messageInput.value = "";
            }
        }
    }
}
function sendready() {
    if (webSocket) {
        webSocket.send(name + ":READY");
    }
}
function sendlogout() {
    if (webSocket) {
        webSocket.send(name + ":LOGOUT");
        window.location.href = "./login.html";
    }
}