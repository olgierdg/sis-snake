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
	 * Kolizja w wezem. Kolizja jest sprawdzana z wyprzedzeniem, zeby zatrzymac render w momencie gdy glowa weza 
	 * dotyka jakiejs jego czesci, a nie w momencie gdy zajmuje ta sama przestrzen co ta czesc.
	 * 
	 * @param snake wonz
	 * @return to samo co wczesniej
	 */
	public static boolean isCollision(Snake snake) {
		SnakePiece head = snake.getHead();
		int dir = snake.getDir();
		for(int i = 1; i<snake.snakeBody.size(); i++){
			SnakePiece s = snake.snakeBody.get(i);
			if(dir == Snake.NORTH)
				if((head.xPos == s.xPos) && (head.yPos == s.yPos+20)) return true;
			if(dir == Snake.SOUTH)
				if((head.xPos == s.xPos) && (head.yPos == s.yPos-20)) return true;
			if(dir == Snake.EAST)
				if((head.xPos == s.xPos-20) && (head.yPos == s.yPos)) return true;
			if(dir == Snake.WEST)
				if((head.xPos == s.xPos+20) && (head.yPos == s.yPos)) return true;
		}
		return false;
	}
}