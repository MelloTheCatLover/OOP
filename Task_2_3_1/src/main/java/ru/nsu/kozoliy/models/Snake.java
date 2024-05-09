package ru.nsu.kozoliy.models;

import ru.nsu.kozoliy.Direction;
import ru.nsu.kozoliy.view.SnakeGameView;

import java.util.ArrayList;

public class Snake {

    private Direction direction;

    ArrayList<SnakePart> snakeBody = new ArrayList<>();

    public Snake(SnakePart head, Direction direction) {
        this.snakeBody.add(head);
        this.direction = direction;
    }

    public Snake(int x, int y, Direction direction) {
        SnakePart head = new SnakePart(x, y);
        this.snakeBody.add(head);
        this.direction = direction;
    }

    public int getSize() {
        return snakeBody.size();
    }

    public ArrayList<SnakePart> getSnakeBody() {
        return snakeBody;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
