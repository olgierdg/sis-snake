package model;

import java.util.ListIterator;

import game.Map;

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

		if((head.getXPos() == apple.getXPos()) && (head.getYPos() == apple.getYPos())) return true;
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
				if((head.getXPos() == s.getXPos()) && (head.getYPos() == s.getYPos()+20)) return true;
			if(dir == Snake.SOUTH)
				if((head.getXPos() == s.getXPos()) && (head.getYPos() == s.getYPos()-20)) return true;
			if(dir == Snake.EAST)
				if((head.getXPos() == s.getXPos()-20) && (head.getYPos() == s.getYPos())) return true;
			if(dir == Snake.WEST)
				if((head.getXPos() == s.getXPos()+20) && (head.getYPos() == s.getYPos())) return true;
		}
		return false;
	}
	
	/**
	 * Kolizja ze scianami
	 * 
	 * @param snake
	 * @param map
	 * @param gameMode
	 * @return
	 */
	public static boolean isCollisionWalls(Snake snake, Map map, String gameMode){
		SnakePiece head = snake.getHead();
		int dir = snake.getDir();
		ListIterator<Coordinates> it = map.getLevel().listIterator();
		while(it.hasNext()){
			Coordinates c = it.next();
			if(dir == Snake.NORTH)
				if(gameMode.equals("portals")){
					if(map.getBluePortal().getDirection() == Portal.SOUTH){
						if((head.getXPos() == map.getBluePortal().getXPos()) && (head.getYPos() == map.getBluePortal().getYPos()+20)) return false;
						if((head.getXPos() == map.getOrangePortal().getXPos()) && (head.getYPos() == map.getOrangePortal().getYPos()+20)) return true;
						if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.NORTH) return true;
					}else if(map.getOrangePortal().getDirection() == Portal.SOUTH){
						if((head.getXPos() == map.getOrangePortal().getXPos()) && (head.getYPos() == map.getOrangePortal().getYPos()+20)) return false;
						if((head.getXPos() == map.getBluePortal().getXPos()) && (head.getYPos() == map.getBluePortal().getYPos()+20)) return true;
						if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.NORTH) return true;
					}else if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.NORTH) return true;
				}else if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.NORTH) return true;
			
			if(dir == Snake.SOUTH)
				if(gameMode.equals("portals")){
					if(map.getBluePortal().getDirection() == Portal.NORTH){
						if((head.getXPos() == map.getBluePortal().getXPos()) && (head.getYPos() == map.getBluePortal().getYPos()-20)) return false;
						if((head.getXPos() == map.getOrangePortal().getXPos()) && (head.getYPos() == map.getOrangePortal().getYPos()-20)) return true;
						if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.SOUTH) return true;
					}else if(map.getOrangePortal().getDirection() == Portal.NORTH){
						if((head.getXPos() == map.getOrangePortal().getXPos()) && (head.getYPos() == map.getOrangePortal().getYPos()-20)) return false;
						if((head.getXPos() == map.getBluePortal().getXPos()) && (head.getYPos() == map.getBluePortal().getYPos()-20)) return true;
						if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.SOUTH) return true;
					}else if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.SOUTH) return true;
				}else if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.SOUTH) return true;
			
			if(dir == Snake.EAST)
				if(gameMode.equals("portals")){
					if(map.getBluePortal().getDirection() == Portal.WEST){
						if((head.getXPos() == map.getBluePortal().getXPos()) && (head.getYPos() == map.getBluePortal().getYPos())) return false;
						if((head.getXPos() == map.getOrangePortal().getXPos()) && (head.getYPos() == map.getOrangePortal().getYPos())) return true;
						if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.EAST) return true;
					}else if(map.getOrangePortal().getDirection() == Portal.WEST){
						if((head.getXPos() == map.getOrangePortal().getXPos()) && (head.getYPos() == map.getOrangePortal().getYPos())) return false;
						if((head.getXPos() == map.getBluePortal().getXPos()) && (head.getYPos() == map.getBluePortal().getYPos())) return true;
						if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.EAST) return true;
					}else if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.EAST) return true;				
				}else if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.EAST) return true;
			
			if(dir == Snake.WEST)
				if(gameMode.equals("portals")){
					if(map.getOrangePortal().getDirection() == Portal.EAST){
						if((head.getXPos() == map.getOrangePortal().getXPos()) && (head.getYPos() == map.getOrangePortal().getYPos())) return false;
						if((head.getXPos() == map.getBluePortal().getXPos()) && (head.getYPos() == map.getBluePortal().getYPos())) return true;
						if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.WEST) return true;
					}else if(map.getBluePortal().getDirection() == Portal.EAST){
						if((head.getXPos() == map.getBluePortal().getXPos()) && (head.getYPos() == map.getBluePortal().getYPos())) return false;
						if((head.getXPos() == map.getOrangePortal().getXPos()) && (head.getYPos() == map.getOrangePortal().getYPos())) return true;
						if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.WEST) return true;
					} else if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.WEST) return true;			
				}else if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()) && snake.getNextDir() == Snake.WEST) return true;
		}
		
		return false;
	}
	
	/**
	 * Kolizja tworzonego jablka z wezem i scianami.
	 * 
	 * @param apple
	 * @param map
	 * @param snake
	 * @return
	 */
	public static boolean isCollision(Apple apple, Map map, Snake snake){
		ListIterator<Coordinates> it = map.getLevel().listIterator();
		while(it.hasNext()){
			Coordinates c = it.next();
			if((c.getXPos() == apple.getXPos()) && (c.getYPos() == apple.getYPos())) return true;
		}
		ListIterator<SnakePiece> its = snake.getSnakeBody().listIterator();
		while(its.hasNext()){
			SnakePiece s = its.next();
			if((s.getXPos() == apple.getXPos()) && (s.getYPos() == apple.getYPos())) return true;
		}
		return false;
	}
	
	/**
	 * Kolizja poszczegolnej czesci weza z portalem.
	 * 
	 * @param piece
	 * @param portal
	 * @return
	 */
	public static boolean isCollision(SnakePiece piece, Portal portal){
		if(piece.getXPos() == portal.getXPos() && piece.getYPos() == portal.getYPos()) return true;	
		return false;
	}
}