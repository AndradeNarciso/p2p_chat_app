'use strict';

let currentUser = null;
let selectedUser = null;
let stompClient = null;
let nickname = null;
let fullname = null;
let currentUserId = null;

let onlineUsers = [];


function connection(event) {
    event.preventDefault();

    nickname = document.getElementById("nickname").value.trim();
    fullname = document.getElementById("fullname").value.trim();

    if (!nickname || !fullname) {
        alert("Please fill in all fields.");
        return;
    }

    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);
    stompClient.debug = null;

    getId().then(() => {
        stompClient.connect({}, onConnected, onError);
    });
}


async function getId() {
    const response = await fetch('/id', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nickName: nickname, fullName: fullname })
    });
    currentUserId = await response.text();
    console.log("Meu ID:", currentUserId);
}


function onConnected() {
   
    stompClient.subscribe('/topic/users', onUsersUpdate);

    stompClient.subscribe(`/user/${currentUserId}/queue/message`, onChatMessageReceived);

   
    stompClient.send('/app/user/connect', {}, JSON.stringify({
        nickName: nickname,
        fullName: fullname
    }));

    showScreen("usersScreen");
    findConnectedUser();
}


function onError(err) {
    console.error("STOMP connection error:", err);
    alert("Connection lost");
}


async function findConnectedUser() {
    const res = await fetch('/users');
    const users = await res.json();
    onlineUsers = users.filter(u => u.fullName !== fullname);
    loadUsers();
}


function onUsersUpdate(payload) {
    findConnectedUser();
}

function loadUsers() {
    const list = document.getElementById("userList");
    list.innerHTML = "";

    onlineUsers.forEach(user => {
        const div = document.createElement("div");
        div.className = "user";
        div.innerText = user.fullName;
        div.onclick = () => {
            selectedUser = user.id;  
            openChat(user.fullName);
        };
        list.appendChild(div);
    });
}


function openChat(chooseUserName) {
    document.getElementById("chatWith").innerText = "Chat with: " + chooseUserName;
    document.getElementById("chatBox").innerHTML = "";
    showScreen("chatScreen");
    loadChat();
}


function onChatMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    const chatBox = document.getElementById("chatBox");

    const div = document.createElement("div");
    const isMe = message.senderId === currentUserId;
    div.className = isMe ? "message me" : "message";
    div.innerText = message.content;

    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
}


function sendMessage() {
    const input = document.getElementById("messageInput");
    const message = input.value.trim();
    if (!message || !stompClient || !selectedUser) return;

    stompClient.send("/app/chat", {}, JSON.stringify({
        senderId: currentUserId,
        recipientId: selectedUser,
        content: message
    }));

    const chatBox = document.getElementById("chatBox");
    const msg = document.createElement("div");
    msg.className = "message me";
    msg.innerText = message;
    chatBox.appendChild(msg);

    input.value = "";
    chatBox.scrollTop = chatBox.scrollHeight;
}


async function loadChat() {
    if (!selectedUser) return;

    const res = await fetch(`/message/${currentUserId}/${selectedUser}`);
    const messages = await res.json();

    const chatBox = document.getElementById("chatBox");
    chatBox.innerHTML = "";

    messages.forEach(msg => {
        const div = document.createElement("div");
        const isMe = msg.senderId === currentUserId;
        div.className = isMe ? "message me" : "message";
        div.innerText = msg.content;
        chatBox.appendChild(div);
    });

    chatBox.scrollTop = chatBox.scrollHeight;
}


function leaveChat() {
    showScreen("usersScreen");
}

function logout() {
    if (stompClient) {
        stompClient.send("/app/user/disconnect", {}, JSON.stringify({ id: currentUserId }));
        stompClient.disconnect(() => console.log("Disconnected"));
    }

    stompClient = null;
    currentUser = null;
    selectedUser = null;
    nickname = null;
    fullname = null;

    showScreen("loginScreen");
    loadUsers();
}


function showScreen(screenId) {
    document.getElementById("loginScreen").classList.add("hidden");
    document.getElementById("usersScreen").classList.add("hidden");
    document.getElementById("chatScreen").classList.add("hidden");
    document.getElementById(screenId).classList.remove("hidden");
}
