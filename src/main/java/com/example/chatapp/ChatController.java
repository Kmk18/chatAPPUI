package com.example.chatapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class ChatController {
    @FXML
    private ListView<Message> chatListView;

    @FXML
    private TextField messageInputField;

    @FXML
    private Button sendButton;

    public void initialize() {
        // Set up the custom cell factory for the ListView
        chatListView.setCellFactory(listView -> new ChatCell());
    }

    @FXML
    public void sendMessage() {
        String messageText = messageInputField.getText().trim();
        if (!messageText.isEmpty()) {
            Message outgoingMessage = new Message(" You", messageText, true);
            chatListView.getItems().add(outgoingMessage);
            messageInputField.clear();
            scrollToBottom();
        }
    }

    private void scrollToBottom() {
        int lastIndex = chatListView.getItems().size() - 1;
        chatListView.scrollTo(lastIndex);
        chatListView.getSelectionModel().select(lastIndex);
    }

    private static class Message {
        private String sender;
        private String text;
        private boolean outgoing;

        public Message(String sender, String text, boolean outgoing) {
            this.sender = sender;
            this.text = text;
            this.outgoing = outgoing;
        }

        public String getSender() {
            return sender;
        }

        public String getText() {
            return text;
        }

        public boolean isOutgoing() {
            return outgoing;
        }
    }

    private static class ChatCell extends ListCell<Message> {
        private final HBox bubble = new HBox();
        private final Text senderText = new Text();
        private final Text messageText = new Text();
        private final Circle indicator = new Circle(5);

        public ChatCell() {
            bubble.getChildren().addAll(indicator, senderText, messageText);
        }

        @Override
        protected void updateItem(Message message, boolean empty) {
            super.updateItem(message, empty);

            if (empty || message == null) {
                setGraphic(null);
            } else {
                senderText.setText(message.getSender() + ": ");
                messageText.setText(message.getText());

                if (message.isOutgoing()) {
                    indicator.setFill(Color.GREEN);
                } else {
                    indicator.setFill(Color.RED);
                }

                setGraphic(bubble);
            }
        }
    }
}