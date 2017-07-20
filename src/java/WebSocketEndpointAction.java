/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sogawa
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/loadMessage")
public class WebSocketEndpointAction {

    public static List<Session> sessions = new ArrayList<Session>();
    private UserAdministrator userAdmin = new UserAdministrator();

    @OnOpen
    public void onOpen(Session session) {
        // 開始時
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        // クライアントからの受信時
        String[] messageArray = message.split(":", 2);
        switch (messageArray[1]) {
            case "":
                // ユーザー名の取得と登録を行う
                userAdmin.addUser(messageArray[0], 25000);
                break;
            case "READY":
                // ユーザー状態を変更し、したら準備完了メッセージを送信する。
                if (userAdmin.setUserStateReady(messageArray[0])) {
                    for (Session session : sessions) {
                        String sendMessage = messageArray[0] + "さんの準備が完了しました。";
                        session.getBasicRemote().sendText("{\"command\":\"message\", \"text\": \"" + sendMessage.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
                    }
                }
                if (userAdmin.isAllUsersReady()) {
                    // チンチロリンの実行と結果出力
                    List<String> userNames = new ArrayList<>();
                    for (User user : userAdmin.getUsers()) {
                        userNames.add(user.getName());
                    }
                    List<String> sendMessages = ChinchirorinRun.chinchiroRun(userNames);
                    for (Session session : sessions) {
                        session.getBasicRemote().sendText("{\"command\":\"message\", \"text\": \"" + "--------------------------------------------------".replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
                        for (String sendMessage : sendMessages) {
                            session.getBasicRemote().sendText("{\"command\":\"message\", \"text\": \"" + sendMessage.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
                        }
                        session.getBasicRemote().sendText("{\"command\":\"message\", \"text\": \"" + "---------------------------------------------------".replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
                    }
                    userAdmin.userStatesReset();
                }
                break;
            case "LOGOUT":
                userAdmin.removeUser(messageArray[0]);
                // ログアウトしたことの通知メッセージの送信
                for (Session session : sessions) {
                    String sendMessage = messageArray[0] + "さんがログアウトしました。";
                    session.getBasicRemote().sendText("{\"command\":\"message\", \"text\": \"" + sendMessage.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
                }
                break;
            default:
                for (Session session : sessions) {
                    session.getBasicRemote().sendText("{\"command\":\"message\", \"text\": \"" + message.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
                }
        }
    }

    @OnError
    public void onError(Throwable t) throws IOException {
        // エラー発生時
        for (Session session : sessions) {
            session.getBasicRemote().sendText("{\"command\":\"error\", \"text\": \"" + t.getMessage().replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
        }
    }

    @OnClose
    public void onClose(Session session) {
        // 完了時
        sessions.remove(session);
    }
}
