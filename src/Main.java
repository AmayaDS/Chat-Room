

class User {
    private String username;
    private ChatRoom chatRoom;

    public User(String username) {
        this.username = username;
    }

    public void joinChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.addUser(this);
    }

    public void sendMessage(String message) {
        chatRoom.sendMessage(this, message);
    }

    public String getUsername() {
        return username;
    }


    public void receiveMessage(Message newMessage) {
        Message message = null;
        System.out.println("Received message from " + message.getSender().getUsername() + ": " + message.getContent());

    }
}


class Message {
    private User sender;
    private String content;

    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
}

class ChatRoom {
    private String name;
    private List<User> users = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public ChatRoom(String name) {
        this.name = name;
    }

    public synchronized void addUser(User user) {
        users.add(user);
    }

    public synchronized void sendMessage(User sender, String message) {
        Message newMessage = new Message(sender, message);
        messages.add(newMessage);

        for (User user : users) {
            if (!user.equals(sender)) {
                user.receiveMessage(newMessage);
            }
        }
    }

    public synchronized List<Message> getMessages() {
        return messages;
    }
}
public class Main {
    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom("General");

        User user1 = new User("Amaya");
        User user2 = new User("Amila");

        user1.joinChatRoom(chatRoom);
        user2.joinChatRoom(chatRoom);

        user1.sendMessage("Hello, Amila!");
        user2.sendMessage("Hi, Amaya!");

        List<Message> chatMessages = chatRoom.getMessages();
        for (Message message : chatMessages) {
            System.out.println(message.getSender().getUsername() + ": " + message.getContent());
        }
    }
}
        


