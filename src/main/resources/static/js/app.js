'use strict';

let currentUser = "";
let selectedUser = "";
let stompClient = "";

let nickname = "";
let fullname = "";

let onlineUsers = "";




function connection(Event) {


    nickname = document.getElementById("nickname").value;
    fullname = document.getElementById("fullname").value;

    if (!nickname || !fullname) {
        alert("Please fill in all fields.");
        return;
    }
    

    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError)

    Event.preventDefault();

   


}


function onConnected() {

    console.log("=====================================");
    console.log(">>> CONEXÃO WEBSOCKET ESTABELECIDA");
    console.log(">>> Usuário tentando conectar:");
    console.log("Nickname:", nickname);
    console.log("FullName:", fullname);
    console.log("=====================================");

    stompClient.subscribe(`/user/${fullname}/queue/message`, onMessageReceived);
    stompClient.subscribe('/user/topic', onMessageReceived);
    stompClient.send('/app/user/connect', {}, JSON.stringify({
        nickName: nickname,
        fullName: fullname
    }))
 
}


function onError(err) {
    console.error("Erro de conexão STOMP:", err);
    alert("Erro ao conectar ao servidor. Veja console.");
}

async function findConnectedUser() {
    const connectedUser = await fetch('/users');
    let connectUserList = await connectedUser.json();

    connectUserList = connectUserList.filter(user => user.fullName !== fullname)
    onlineUsers = connectUserList;
    loadUsers();

}

function onMessageReceived(payload) {

    const user = JSON.parse(payload.body);

    console.log("User created:", user);

    showScreen("usersScreen");

    findConnectedUser();
}




function loadUsers() {
    const list = document.getElementById("userList");
    list.innerHTML = "";

    onlineUsers.forEach(user => {
        // user é um objeto { fullName, nickName }
        const div = document.createElement("div");
        div.className = "user";
        div.innerText = user.fullName; // mostra o nome completo
        div.onclick = () => openChat(user.fullName); // passa o nome completo
        list.appendChild(div);
    });
}


function openChat(user) {
    selectedUser = user;
    document.getElementById("chatWith").innerText = "Chat com " + user;
    document.getElementById("chatBox").innerHTML = "";
    showScreen("chatScreen");
}

function sendMessage() {
    const input = document.getElementById("messageInput");
    const message = input.value;

    if (!message) return;

    const chatBox = document.getElementById("chatBox");

    const msg = document.createElement("div");
    msg.className = "message me";
    msg.innerText = message;

    chatBox.appendChild(msg);

    input.value = "";
    chatBox.scrollTop = chatBox.scrollHeight;
}

function leaveChat() {
    showScreen("usersScreen");
}

function logout() {
    showScreen("loginScreen");
}

function showScreen(screenId) {
    document.getElementById("loginScreen").classList.add("hidden");
    document.getElementById("usersScreen").classList.add("hidden");
    document.getElementById("chatScreen").classList.add("hidden");

    document.getElementById(screenId).classList.remove("hidden");
}
