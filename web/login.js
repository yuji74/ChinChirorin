/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var Login = {};
var href = window.location.href ;
/** 
 * 超簡易ログイン認証
 * @param loginForm formエレメント
 * @return 成功時true,失敗時false
 */
Login.doLogin = function doLogin(loginForm) {

    //空チェック
    if(loginForm.username.value == '') {
        return Login.doError('ユーザー名を入力してください。');
    }
    if(loginForm.password.value == '') {
       return Login.doError('パスワードを入力してください。');
    }
    document.write();
    window.location.href = "./ChinChirorin.html";
//    document.write("username:" + loginForm.username.value);
    //エラーなし
    return true;
}

/**
 * エラー時の動作
 * @param msg エラーメッセージ
 * @return falseを返す
 */
Login.doError = function doError(msg) {
    alert(msg);
    return false;
}