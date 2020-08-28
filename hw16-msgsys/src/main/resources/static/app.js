
if (window.stompClient === undefined) {
  window.stompClient = Stomp.over(new SockJS('/users-websocket'));
}

const connect = (initFunction) => {
  window.stompClient.connect({}, initFunction);
};
