'use strict';

let currentUser = null;
let selectedUser = null;
let stompClient = null;
let nickname = null;
let fullname = null;
let idUser=null;

let onlineUsers = null;


function connection(Event) {

    nickname = document.getElementById("nickname").value;
    fullname = document.getElementById("fullname").value;

    if (!nickname || !fullname) {
        alert("Please fill in all fields.");
        return;
    }


    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);
    stompClient.debug = null

    stompClient.connect({}, onConnected, onError)

    Event.preventDefault();

}


function onConnected() {

    stompClient.subscribe(`/user/${fullname}/queue/message`, onMessageReceived);
    stompClient.subscribe('/topic/users', onMessageReceived);
    stompClient.send('/app/user/connect', {}, JSON.stringify({
        nickName: nickname,
        fullName: fullname
    }))

}


function onError(err) {
    console.error("STOMP connection gone", err);
    alert("Connection lost");
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
    showScreen("usersScreen");
    findConnectedUser();
}




function loadUsers() {
    const list = document.getElementById("userList");
    list.innerHTML = "";

    onlineUsers.forEach(user => {

        const div = document.createElement("div");
        div.className = "user";
        div.innerText = user.fullName;
        div.onclick = () => openChat(user.fullName, user.id);
        list.appendChild(div);
    });
}


function openChat(userName, userId) {
    selectedUser = userName;
    document.getElementById("chatWith").innerText = "Chat with: " + userName;
    document.getElementById("chatBox").innerHTML = "";
    showScreen("chatScreen");
    loadChat();
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

function loadChat() {

    const messageLogged = [

    ];

    const chatBox = document.getElementById("chatBox");
    chatBox.innerHTML = "";

    messageLogged.forEach(message => {

        const div = document.createElement("div");

        const isMe = message.sender === fullname;

        div.className = isMe ? "message me" : "message";

        div.innerText = message.content;

        chatBox.appendChild(div);
    });

    chatBox.scrollTop = chatBox.scrollHeight;
}
