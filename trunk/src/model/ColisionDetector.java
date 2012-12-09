package model;

import java.util.ListIterator;

import android.util.Log;
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

		if((head.getXPos() == apple.xPos) && (head.getYPos() == apple.yPos)) return true;
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
	
	public static boolean isCollisionWalls(Snake snake, Map map, String gameMode){
		SnakePiece head = snake.getHead();
		int dir = snake.getDir();
		ListIterator<Coordinates> it = map.getLevel().listIterator();
		while(it.hasNext()){
			Coordinates c = it.next();
			if(dir == Snake.NORTH)
				if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()+20)) return true;
			if(dir == Snake.SOUTH)
				if((head.getXPos() == c.getXPos()) && (head.getYPos() == c.getYPos()-20)) return true;
			if(dir == Snake.EAST)
				if(gameMode.equals("portals")){
					if(map.getBluePortal().getDirection() == Portal.WEST){
						if((head.getXPos() == map.getBluePortal().getXPos()-20) && (head.getYPos() == map.getBluePortal().getYPos())) return false;
						if((head.getXPos() == map.getOrangePortal().getXPos()-20) && (head.getYPos() == map.getOrangePortal().getYPos())) return true;
						if((head.getXPos() == c.getXPos()-20) && (head.getYPos() == c.getYPos())) return true;
					}else 
						
						
						if((head.getXPos() == c.getXPos()-20) && (head.getYPos() == c.getYPos())) return true;
					
				}else if((head.getXPos() == c.getXPos()-20) && (head.getYPos() == c.getYPos())) return true;
			if(dir == Snake.WEST)
				if(gameMode.equals("portals")){
					if(map.getOrangePortal().getDirection() == Portal.EAST){
						if((head.getXPos() == map.getOrangePortal().getXPos()+20) && (head.getYPos() == map.getOrangePortal().getYPos())) return false;
						if((head.getXPos() == map.getBluePortal().getXPos()+20) && (head.getYPos() == map.getBluePortal().getYPos())) return true;
						if((head.getXPos() == c.getXPos()+20) && (head.getYPos() == c.getYPos())) return true;
					}else
						
						if((head.getXPos() == c.getXPos()+20) && (head.getYPos() == c.getYPos())) return true;
					
				}else if((head.getXPos() == c.getXPos()+20) && (head.getYPos() == c.getYPos())) return true;
		}
		
		return false;
	}
	
	public static boolean isCollision(Apple apple, Map map){
		ListIterator<Coordinates> it = map.getLevel().listIterator();
		while(it.hasNext()){
			Coordinates c = it.next();
			if((c.getXPos() == apple.xPos) && (c.getYPos() == apple.yPos)) return true;
			return false;
		}		
		return false;
	}
	
	public static boolean isCollision(SnakePiece piece, Map map){
			
		return false;
	}
	
	//public static boolean isCollisionPortals(Snake snake, Map map){
		
	//}
}