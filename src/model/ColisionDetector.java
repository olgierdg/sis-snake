package model;

/**
 * Zerzniety z zajec detektor kolizji. Co sie bede meczyl, swoje wymyslal, jak to jest tak pieknie. 
 * Dziala to inaczej niz na zajeciach, nie ma zadnych kwadratow, tylko wspolrzedne.
 * 
 * @author Olo
 *
 */
public final class ColisionDetector {

	/**
	 * Kolizja z jablkiem
	 * 
	 * @param snake wonz
	 * @param apple japko
	 * @return true jesli kolizja, false jesli nie ma kolizji
	 */
	public static boolean isCollision(Snake snake, Apple apple) {
		SnakePiece head = snake.getHead();
		if((head.xPos == apple.xPos) && (head.yPos == apple.yPos)) return true;
		return false;
	}
	
	/**
	 * Kolizja w wezem
	 * 
	 * @param snake wonz
	 * @return to samo co wczesniej
	 */
	public static boolean isCollision(Snake snake) {
		SnakePiece head = snake.getHead();
		for(int i = 1; i<snake.snakeBody.size(); i++){
			SnakePiece s = snake.snakeBody.get(i);
			if((head.xPos == s.xPos) && (head.yPos == s.yPos)) return true;
		}
		return false;
	}
}