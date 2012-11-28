package model;

public final class ColisionDetector {

	public static boolean isCollision(Snake snake, Apple apple) {
		SnakePiece head = snake.getHead();
		if((head.xPos == apple.xPos) && (head.yPos == apple.yPos)) return true;
		return false;
	}
	
	public static boolean isCollision(Snake snake) {
		SnakePiece head = snake.getHead();
		for(int i = 1; i<snake.snakeBody.size(); i++){
			SnakePiece s = snake.snakeBody.get(i);
			if((head.xPos == s.xPos) && (head.yPos == s.yPos)) return true;
		}
		return false;
	}
}