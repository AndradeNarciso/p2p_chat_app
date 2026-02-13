let currentUser = "";
let selectedUser = "";

const onlineUsers = ["Maria", "Carlos", "Joana", "Pedro"];

function enterChat() {
    const nick = document.getElementById("nickname").value;
    const name = document.getElementById("fullname").value;

    if (!nick || !name) {
        alert("Preencha todos os campos!");
        return;
    }

    currentUser = nick;

    showScreen("usersScreen");
    loadUsers();
}

function loadUsers() {
    const list = document.getElementById("userList");
    list.innerHTML = "";

    onlineUsers.forEach(user => {
        if (user !== currentUser) {
            const div = document.createElement("div");
            div.className = "user";
            div.innerText = user;
            div.onclick = () => openChat(user);
            list.appendChild(div);
        }
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
