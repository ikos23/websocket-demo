<html>
<head>
<title>WebSocket Demo</title>
<script>

    // that is how a client can connect to a WebSocket
    var chatWebSocket = new WebSocket("ws://localhost:8080/websocket-demo/mychat");
    var userWebSocket = new WebSocket("ws://localhost:8080/websocket-demo/user");

    // websocket instance callbacks
    chatWebSocket.onopen = function() {
        showMessage('[^_^] Connected successfully.');
    }

    chatWebSocket.onclose = function(event) {
        if (event.wasClean) {
          showMessage('[x_x] Connection closed successfully.');
        } else {
          showMessage('[x_x] Connection was broken up.');
        }
        showMessage('[x_x] Code: ' + event.code + ', reason:'
            + event.reason);
    }

    // message received
    chatWebSocket.onmessage = function(event) {
      var incomingMsg = event.data;
      showMessage(incomingMsg);
    }

    chatWebSocket.onerror = function(error) {
      showMessage('ERROR: ' + error.message);
    }

    function sendMessage() {
        var userId = document.getElementById("userId").value;

        if (typeof userId == 'undefined' || userId == "") {
            showMessage("[o_O] It looks like you forgot to enter your name.");
        } else {
            var msg = document.getElementById("message").value;
            document.getElementById("message").value = "";
            // to send a message
            chatWebSocket.send(userId.toUpperCase() + ',,,' + msg);
        }
    }

    function showMessage(msg) {
      var messageElem = document.createElement('div');
      if (msg.indexOf(',,,') != -1) {
        var userName = msg.split(',,,')[0];
        var userMsg = msg.split(',,,')[1];

        userName = '[[' + userName + ']]';
        msg = userName + '  says:  ' + userMsg;
      }
      messageElem.appendChild(document.createTextNode(msg));
      messageElem.style.marginBottom = '5px';
      document.getElementById('messages').appendChild(messageElem);
    }


    userWebSocket.onmessage = function (event) {
        var incomingMsg = event.data;
        alert(incomingMsg);
    }

    function getUser() {
        userWebSocket.send('firstName:Foo,lastName:Bar,email:foo@bar.com');
        return false;
    }

</script>

</head>
<body>

<h1>Hello WebSocket World :)</h1>
<p>This page is a mini chat which uses WebSockets for communication.</p>

<div>
  <input type="text" id="userId" style="width: 200px; margin-bottom: 5px;"
      placeholder="Enter your name" required></input>
</div>
<div>
  <textarea id="message" rows="3" cols="20" placeholder="Enter your message..." style="width: 200px;">
  </textarea> <br />
  <button id="sendBtn" onclick="sendMessage(); return false;" style="margin-bottom: 10px;">Send</button>
  <button id="userBtn" onclick="getUser();" style="margin-bottom: 10px;">User</button>
</div>

<div id="messages" style="margin-top: 10px;">

</div>
<script>
    (function() {
        document.getElementById("message").value = "";
        document.getElementById("userId").value = "";
    })();
</script>
</body>
</html>
